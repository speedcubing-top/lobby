package top.speedcubing.lobby.bukkitlistener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.list.ItemStackList;
import top.speedcubing.server.lang.Lang;
import top.speedcubing.server.lang.LangInventory;
import top.speedcubing.server.lang.LangItemStack;
import top.speedcubing.server.player.User;

public class PlayerInteract implements Listener {
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Member member = Member.getMember(player);
        if (!(e.getAction() == Action.PHYSICAL) && e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (player.getItemInHand().getType()) {
                case COMPASS:
                    member.user.openLangInventory(ItemStackList.navigator);
                    break;
                case SKULL_ITEM:
                    ItemStackList.language.s[0].setTitle("hi");
                    break;
                case REDSTONE_COMPARATOR:
                    String data = member.user.dbSelect("discord").getString();
                    LangInventory inventory = new LangInventory(player, 36, "%lobby_item_settings%")
                            .setItem(ItemStackList.lang, (r) -> {
                                member.user.sound(Sound.CLICK);
                                member.user.openLangInventory(ItemStackList.language);
                            }, 11)
                            .setItem(ItemStackList.dm, (r) -> {
                                member.user.sound(Sound.CLICK);
                                member.messagetoggled++;
                                if (member.messagetoggled > 2)
                                    member.messagetoggled = 0;
                                r.getEvent().getClickedInventory().setItem(21, ItemStackList.toggle[member.messagetoggled].get(member.user));
                                member.user.dbUpdate("messagable=" + (member.messagetoggled));
                            }, 12)
                            .setItem(new LangItemStack(new ItemBuilder(Material.SKULL_ITEM).durability(3).skullFromURL("http://textures.minecraft.net/texture/7873c12bffb5251a0b88d5ae75c7247cb39a75ff1a81cbe4c8a39b311ddeda"),
                                            Lang.of("%lobby_item_discord%"),
                                            Lang.of(data == null ? "%lobby_discord_nodata%" : "%lobby_discord_youare%", data),
                                            Lang.of("%lobby_lore_discord_edit%"))
                                    ,
                                    (r) -> {
                                        switch (r.getEvent().getAction()) {
                                            case PICKUP_ALL: {
                                                member.user.sound(Sound.CLICK);
                                                player.closeInventory();
                                                member.user.sendMessage("%lobby_msg_discord_input%");
                                                member.discord = true;
                                                member.user.setInput(true);
                                            }
                                            break;
                                            case PICKUP_HALF: {
                                                player.closeInventory();
                                                String discord = member.user.dbSelect("discord").getString();
                                                if (!discord.equals("0"))
                                                    member.user.dbUpdate("discord=0");
                                                member.discord = false;
                                                member.user.setInput(false);
                                                member.user.sendMessage("%lobby_msg_discord_removed%");
                                            }
                                            break;
                                        }
                                    }, 13)
                            .setItem(ItemStackList.Reset,
                                    (r) -> member.user.openLangInventory(ItemStackList.ResetConfirm),
                                    14)
                            .setItem(ItemStackList.ResetStat,
                                    (r) -> member.user.openLangInventory(ItemStackList.ResetConfirmStat),
                                    15)
                            .setItem(ItemStackList.toggle[member.messagetoggled], 21);
                    member.user.openLangInventory(inventory);
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
                Vector vector = getPlayerForwardUpVector(p, 2.0);
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