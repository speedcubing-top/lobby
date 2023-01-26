package top.speedcubing.lobby.BukkitEventListener;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lobby.List.ItemStackList;
import top.speedcubing.lobby.List.StringList;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.speedcubingServer;

public class InventoryClick implements Listener {

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        InventoryHolder holder = inventory.getHolder();
        int RawSlot = e.getRawSlot();
        if (RawSlot != -999) {
            if (inventory.getName().equals("container.crafting")) {
                if (e.getWhoClicked().getGameMode() == GameMode.ADVENTURE)
                    e.setCancelled(true);
            } else if (holder == ItemStackList.LangHolder) {
                e.setCancelled(true);
                int x = -1;
                switch (RawSlot) {
                    case 10:
                        x = 0;
                        break;
                    case 12:
                        x = 1;
                        break;
                    case 14:
                        x = 2;
                        break;
                    case 16:
                        x = 3;
                        break;
                }
                if (x != -1) {
                    Player player = (Player) e.getWhoClicked();
                    Member member = Member.getMember(player);
                    member.user.lang = x;
                    String[] sidebar = StringList.LobbySideBar[x];
                    SideBar.getSidebar(player).setLineByScore(sidebar[2], 7).setLineByScore(sidebar[5], 4).setLineByScore(sidebar[8], 1);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    member.user.sendLangMessage(StringList.LangSettings);
                    member.user.dbUpdate("lang=" + x);
                    player.closeInventory();
                }
            } else if (holder == ItemStackList.SettingsHolder) {
                e.setCancelled(true);
                switch (RawSlot) {
                    case 11: {
                        Player player = (Player) e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        player.openInventory(ItemStackList.Lang[Member.getMember(player).user.lang]);
                    }
                    break;
                    case 12: {
                        Player player = (Player) e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        Member member = Member.getMember(player);
                        member.messagetoggled = !member.messagetoggled;
                        e.getClickedInventory().setItem(21, ItemStackList.toggle[member.messagetoggled ? 0 : 1][member.user.lang]);
                        member.user.dbUpdate("messagable=" + (member.messagetoggled ? 1 : 0));
                    }
                    break;
                    case 13: {
                        Player player = (Player) e.getWhoClicked();
                        Member member = Member.getMember(player);
                        switch (e.getAction()) {
                            case PICKUP_ALL: {
                                player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
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
                    }
                    break;
                    case 14: {
                        Player player = (Player) e.getWhoClicked();
                        player.openInventory(ItemStackList.ResetConfirm[Member.getMember(player).user.lang]);
                    }
                    break;
                    case 15: {
                        Player player = (Player) e.getWhoClicked();
                        player.openInventory(ItemStackList.ResetConfirmStat[Member.getMember(player).user.lang]);
                    }
                    break;
                }
            }
        }
    }
}