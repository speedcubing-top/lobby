package top.speedcubing.lobby.bukkitlistener;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.speedcubing.lobby.Lobby;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.configuration.LobbyConfig;
import top.speedcubing.lobby.configuration.LobbyConfig.MovementConfig;
import top.speedcubing.lobby.list.ItemStackList;

/**
 * Double jump, jump pads, the ender butt and the join effects.
 */
public class LobbyMovement implements Listener {
    private static final long JUMP_PAD_COOLDOWN_MILLIS = 500;
    private static final int ENDER_BUTT_MAX_TICKS = 100;
    private static final int ENDER_BUTT_SLOT = 4;

    /** Join sound, speed and double jump; called from PlayerJoin.joinLobby. */
    public static void applyJoin(Member member) {
        MovementConfig config = LobbyConfig.movement;
        Player player = member.user.player;
        if (config.joinSound != null)
            player.playSound(player.getLocation(), config.joinSound, 1, 1);
        player.removePotionEffect(PotionEffectType.SPEED);
        if (config.joinSpeedAmplifier >= 0)
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, config.joinSpeedAmplifier, true, false));
        player.setAllowFlight(member.flying || config.doubleJumpEnabled);
    }

    // double jump: allowFlight is on while grounded, and the flight toggle (double tap space) launches instead
    @EventHandler(ignoreCancelled = true)
    public void PlayerToggleFlightEvent(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        if (!e.isFlying() || !doubleJumpMode(player))
            return;
        Member member = Member.getMember(player);
        MovementConfig config = LobbyConfig.movement;
        if (member == null || member.flying || !config.doubleJumpEnabled)
            return;
        e.setCancelled(true);
        player.setAllowFlight(false);
        player.setVelocity(horizontalDirection(player).multiply(config.doubleJumpForward).setY(config.doubleJumpUp));
        effects(player.getLocation(), config.doubleJumpSound);
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getAllowFlight() || !player.isOnGround() || !doubleJumpMode(player))
            return;
        Member member = Member.getMember(player);
        if (member != null && !member.flying && LobbyConfig.movement.doubleJumpEnabled && player.getWorld() == Bukkit.getWorlds().get(0))
            player.setAllowFlight(true);
    }

    // switching to survival/adventure clears allowFlight (and /fly) and a player standing still sends no move to
    // re-arm it: restore it right after the switch, once the new mode has applied its own abilities
    @EventHandler(ignoreCancelled = true)
    public void PlayerGameModeChangeEvent(PlayerGameModeChangeEvent e) {
        if (e.getNewGameMode() == GameMode.CREATIVE || e.getNewGameMode() == GameMode.SPECTATOR)
            return;
        Player player = e.getPlayer();
        Bukkit.getScheduler().runTask(Lobby.getPlugin(Lobby.class), () -> {
            Member member = Member.getMember(player);
            if (member == null || !player.isOnline() || !doubleJumpMode(player))
                return;
            player.setAllowFlight(member.flying || LobbyConfig.movement.doubleJumpEnabled);
        });
    }

    private static boolean doubleJumpMode(Player player) {
        GameMode mode = player.getGameMode();
        return mode == GameMode.ADVENTURE || mode == GameMode.SURVIVAL;
    }

    @EventHandler
    public void JumpPadEvent(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL || e.getClickedBlock() == null)
            return;
        MovementConfig config = LobbyConfig.movement;
        if (e.getClickedBlock().getType() != config.jumpPadBlock)
            return;
        Player player = e.getPlayer();
        Member member = Member.getMember(player);
        // the plate fires every tick the player stands on it
        long now = System.currentTimeMillis();
        if (member == null || now - member.lastJumpPad < JUMP_PAD_COOLDOWN_MILLIS)
            return;
        member.lastJumpPad = now;
        // fixed per pad, never the player's camera: listed pads launch in their configured direction,
        // the rest straight up
        Block plate = e.getClickedBlock();
        Vector direction = config.jumpPadDirections.get(plate.getX() + "," + plate.getY() + "," + plate.getZ());
        Vector launch = direction == null ? new Vector(0, 0, 0) : direction.clone().multiply(config.jumpPadForward);
        // straight to the client: a purely vertical setVelocity on a grounded player never reached it
        member.user.knockback(launch.setY(config.jumpPadUp));
        effects(player.getLocation(), config.jumpPadSound);
    }

    /** Throws an ender pearl with the player riding it; called from the hotbar item. */
    public static void launchEnderButt(Member member) {
        MovementConfig config = LobbyConfig.movement;
        Player player = member.user.player;
        // the client consumes the pearl on use even though the throw is cancelled; give it back every time
        Bukkit.getScheduler().runTask(Lobby.getPlugin(Lobby.class), () -> {
            if (player.isOnline()) {
                member.user.setLangItem(ENDER_BUTT_SLOT, ItemStackList.EnderButt);
                player.updateInventory();
            }
        });
        if (!config.enderButtEnabled || player.isInsideVehicle())
            return;
        long now = System.currentTimeMillis();
        if (now - member.lastEnderButt < config.enderButtCooldownMillis) {
            member.user.sendMessage("%lang_lobby_msg_cooldown%");
            return;
        }
        member.lastEnderButt = now;
        EnderPearl pearl = player.launchProjectile(EnderPearl.class);
        pearl.setPassenger(player);
        player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1, 1);

        // the pearl's own vanilla landing teleport places the player; this only ends rides that leave the
        // lobby bounds, fall into the void or fly too long (PlayerMove handles the void and border after that)
        new BukkitRunnable() {
            int ticks;

            @Override
            public void run() {
                Location at = pearl.getLocation();
                if (pearl.isDead() || ++ticks > ENDER_BUTT_MAX_TICKS || !player.isOnline() || pearl.getPassenger() != player
                        || at.getY() < 0 || Math.abs(at.getX()) > 501 || Math.abs(at.getZ()) > 501) {
                    pearl.eject();
                    pearl.remove();
                    cancel();
                }
            }
        }.runTaskTimer(Lobby.getPlugin(Lobby.class), 1, 1);
    }

    // vanilla lands the player exactly on the pearl's impact point, i.e. on the block face: half the hitbox
    // sits inside a wall and a jump right after landing clips through. Push the hitbox out of solid blocks
    @EventHandler(ignoreCancelled = true)
    public void PlayerTeleportEvent(PlayerTeleportEvent e) {
        if (e.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL || e.getPlayer().getGameMode() != GameMode.ADVENTURE)
            return;
        Location to = e.getTo().clone();
        // feet inside a block (floor hit slightly below the top, or a corner): stand on top of it
        if (to.getBlock().getType().isSolid())
            to.setY(to.getBlockY() + 1);
        to.setX(pushOut(to, 0.3, 0));
        to.setX(pushOut(to, -0.3, 0));
        to.setZ(pushOut(to, 0, 0.3));
        to.setZ(pushOut(to, 0, -0.3));
        e.setTo(to);
    }

    private static final double HITBOX_MARGIN = 0.001;

    // if the hitbox edge at (dx, dz) is inside a solid block at feet or head height, return the
    // coordinate that puts that edge just outside the block; otherwise the current coordinate
    private static double pushOut(Location at, double dx, double dz) {
        Location edge = at.clone().add(dx, 0, dz);
        if (!edge.getBlock().getType().isSolid() && !edge.clone().add(0, 1, 0).getBlock().getType().isSolid())
            return dx != 0 ? at.getX() : at.getZ();
        if (dx > 0)
            return edge.getBlockX() - dx - HITBOX_MARGIN;
        if (dx < 0)
            return edge.getBlockX() + 1 - dx + HITBOX_MARGIN;
        if (dz > 0)
            return edge.getBlockZ() - dz - HITBOX_MARGIN;
        return edge.getBlockZ() + 1 - dz + HITBOX_MARGIN;
    }

    private static Vector horizontalDirection(Player player) {
        Vector direction = player.getLocation().getDirection().setY(0);
        return direction.lengthSquared() == 0 ? direction : direction.normalize();
    }

    private static void effects(Location location, Sound sound) {
        if (sound != null)
            location.getWorld().playSound(location, sound, 1, 1);
        location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 0);
    }
}
