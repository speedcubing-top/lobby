package top.speedcubing.lobby.BukkitEventListener;

import org.bukkit.*;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;
import top.speedcubing.lib.bukkit.inventory.*;
import top.speedcubing.lobby.List.*;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.player.User;
import top.speedcubing.server.speedcubingServer;

public class PlayerInteract implements Listener {
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!(e.getAction() == Action.PHYSICAL) && e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (player.getItemInHand().getType()) {
                case COMPASS:
                    player.openInventory(ItemStackList.navigator[Member.getMember(player).user.lang]);
                    break;
                case REDSTONE_COMPARATOR:
                    Member member = Member.getMember(player);
                    String data = member.user.dbSelect("discord").getString();
                    Inventory inv = new InventoryBuilder(36, StringList.settingstitle.get(member.user.lang)).deleteOnClose(true)
                            .setItem(ItemStackList.lang[member.user.lang], (p, event) -> {
                                member.user.sound(Sound.CLICK);
                                p.openInventory(ItemStackList.Lang[member.user.lang]);
                            }, 11)
                            .setItem(ItemStackList.dm[member.user.lang], (p, event) -> {
                                member.user.sound(Sound.CLICK);
                                member.messagetoggled++;
                                if (member.messagetoggled > 2)
                                    member.messagetoggled = 0;
                                event.getClickedInventory().setItem(21, ItemStackList.toggle[member.messagetoggled][member.user.lang]);
                                member.user.dbUpdate("messagable=" + (member.messagetoggled));
                            }, 12)
                            .setItem(new ItemBuilder(Material.SKULL_ITEM).durability(3).name(StringList.dc.get(member.user.lang)).lore(
                                                    data == null ? StringList.discordB.get(member.user.lang) : StringList.discordA.get(member.user.lang).replace("%a%", data), StringList.discordEdit.get(member.user.lang))
                                            .url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19").build(),
                                    (p, event) -> {
                                        switch (event.getAction()) {
                                            case PICKUP_ALL: {
                                                member.user.sound(Sound.CLICK);
                                                player.closeInventory();
                                                member.user.sendLangMessage(StringList.discord);
                                                member.discord = true;
                                                speedcubingServer.node(true, member.user.id, member.user.tcpPort);
                                            }
                                            break;
                                            case PICKUP_HALF: {
                                                player.closeInventory();
                                                String discord = member.user.dbSelect("discord").getString();
                                                if (!discord.equals("0"))
                                                    member.user.dbUpdate("discord=0");
                                                member.discord = false;
                                                speedcubingServer.node(false, member.user.id, member.user.tcpPort);
                                                member.user.sendLangMessage(StringList.discordremoved);
                                            }
                                            break;
                                        }
                                    }, 13)
                            .setItem(ItemStackList.Reset[member.user.lang],
                                    (p, event) -> p.openInventory(ItemStackList.ResetConfirm[member.user.lang]),
                                    14)
                            .setItem(ItemStackList.ResetStat[member.user.lang],
                                    (p, event) -> p.openInventory(ItemStackList.ResetConfirmStat[member.user.lang]),
                                    15)
                            .setItem(ItemStackList.toggle[member.messagetoggled][member.user.lang], 21).getInventory();
                    player.openInventory(inv);
                    break;
            }
        }
        if (e.getClickedBlock() != null && player.getGameMode() == GameMode.ADVENTURE)
            e.setCancelled(true);
    }
    @EventHandler
    public void onPlayerInteractionEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof ItemFrame) {
            Player player = e.getPlayer();
            if (player.getGameMode() == GameMode.ADVENTURE) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerUsePressurePlate(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.PHYSICAL) {
            if (e.getClickedBlock().getType() != null && e.getClickedBlock().getType() == Material.GOLD_PLATE) {
                User user = User.getUser(p);
                Vector vector = getPlayerForwardUpVector(p,2.0);
                user.knockback(vector);
            }
        }
    }
    public static Vector getPlayerForwardUpVector(Player player, double distance) {
        Vector direction = player.getLocation().getDirection();
        Vector up = new Vector(0, 0.45, 0);
        Vector forwardUp = direction.add(up).normalize();
        forwardUp.multiply(distance);

        return forwardUp;
    }
}