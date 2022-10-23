package top.speedcubing.lobby.BukkitEventListener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import top.speedcubing.lib.bukkit.BungeePluginMessage;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lobby.List.ItemStackList;
import top.speedcubing.lobby.List.StringList;
import top.speedcubing.lobby.Lobby;
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
            } else if (holder == ItemStackList.NavigatorHolder) {
                e.setCancelled(true);
                switch (RawSlot) {
                    case 16:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "knockbackffa");
                        break;
                    case 10:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "mlgrush");
                        break;
                    case 28:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "practice");
                        break;
                    case 23:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "fastbuilder");
                        break;
                    case 34:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "clutch");
                        break;
                    case 21: {
                        Player player = (Player) e.getWhoClicked();
                        player.closeInventory();
                        player.playSound(new Location(Bukkit.getWorld("world"), 0.5, 99.01563, -15.5), Sound.ENDERMAN_TELEPORT, 1F, 1F);
                        player.teleport(new Location(Bukkit.getWorld("world"), 7.5, 99, 21.5, 0, 0));
                    }
                    break;
                }
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
                    case 13: {
                        Player player = (Player) e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        Member member = Member.getMember(player);
                        member.messagetoggled = !member.messagetoggled;
                        e.getClickedInventory().setItem(22, ItemStackList.toggle[member.messagetoggled ? 0 : 1][member.user.lang]);
                        member.user.dbUpdate("messagable=" + (member.messagetoggled ? 1 : 0));
                    }
                    break;
                    case 15: {
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
                                if (!discord.equals("0")) {
                                    speedcubingServer.tcp.send(20000, "a|" + discord);
                                    member.user.dbUpdate("discord=0");
                                }
                                member.discord = false;
                                speedcubingServer.node(false, member.user.id, member.user.tcpPort);
                                member.user.sendLangMessage(StringList.discordremoved);
                            }
                            break;
                        }
                    }
                    break;
                }
            } else if (holder == ItemStackList.GameSelectHolder) {
                e.setCancelled(true);
                if (RawSlot < 4)
                    speedcubingServer.tcp.sendClean(25604 - Bukkit.getPort() % 2, "reduce|" + Member.getMember(e.getWhoClicked()).user.id + "|" + RawSlot + "|" + speedcubingServer.tcp.socket.getLocalPort());
            }
        }
    }
}