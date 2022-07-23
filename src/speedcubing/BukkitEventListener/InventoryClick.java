package speedcubing.BukkitEventListener;

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
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.Member;
import speedcubing.lib.bukkit.BungeePluginMessage;
import speedcubing.lib.bukkit.SideBar;
import speedcubing.lib.utils.SQL.SQLUtils;
import speedcubing.server.speedcubingServer;

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
                    case 16 -> BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "knockbackffa");
                    case 10 -> BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "mlgrush");
                    case 28 -> BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "practice");
                    case 23 -> BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "fastbuilder");
                    case 34 -> BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "clutch");
                    case 21 -> {
                        Player player = (Player) e.getWhoClicked();
                        player.closeInventory();
                        player.playSound(new Location(Bukkit.getWorld("world"), 0.5, 99.01563, -15.5), Sound.ENDERMAN_TELEPORT, 1F, 1F);
                        player.teleport(new Location(Bukkit.getWorld("world"), 7.5, 99, 21.5, 0, 0));
                    }
                }
            } else if (holder == ItemStackList.LangHolder) {
                e.setCancelled(true);
                int x = switch (RawSlot) {
                    case 10 -> 0;
                    case 12 -> 1;
                    case 14 -> 2;
                    case 16 -> 3;
                    default -> -1;
                };
                if (x != -1) {
                    Player player = (Player) e.getWhoClicked();
                    Member user = Member.getMember(player);
                    String[] sidebar = StringList.LobbySideBar[x];
                    SideBar.getSidebar(player).setLine(sidebar[2], 2)
                            .setLine(sidebar[5], 5)
                            .setLine(sidebar[8], 8);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    player.sendMessage(StringList.LangSettings[x]);
                    speedcubingServer.connection.update("playersdata", "lang=" + x, "id=" + user.id);
                    user.lang = x;
                    player.closeInventory();
                }
            } else if (holder == ItemStackList.SettingsHolder) {
                e.setCancelled(true);
                switch (RawSlot) {
                    case 11 -> {
                        Player player = (Player) e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        player.openInventory(ItemStackList.Lang[Member.getMember(player).lang]);
                    }
                    case 13 -> {
                        Player player = (Player) e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        Member user = Member.getMember(player);
                        user.messagetoggled = !user.messagetoggled;
                        e.getClickedInventory().setItem(22, ItemStackList.toggle[user.messagetoggled ? 0 : 1][user.lang]);
                        speedcubingServer.connection.update("playersdata", "messagable=" + (user.messagetoggled ? 1 : 0), "id=" + user.id);
                    }
                    case 15 -> {
                        Player player = (Player) e.getWhoClicked();
                        Member user = Member.getMember(player);
                        switch (e.getAction()) {
                            case PICKUP_ALL -> {
                                player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                                player.closeInventory();
                                player.sendMessage(StringList.discord[user.lang]);
                                user.discord = true;
                                speedcubingServer.node(true, user.id, user.tcpPort);
                            }
                            case PICKUP_HALF -> {
                                player.closeInventory();
                                String discord = SQLUtils.getString(speedcubingServer.connection.select("playersdata", "discord", "id=" + user.id));
                                if (!discord.equals("")) {
                                    speedcubingServer.tcp.send(20000, "a|" + discord);
                                    speedcubingServer.connection.update("playersdata", "discord=''", "id=" + user.id);
                                }
                                user.discord = false;
                                speedcubingServer.node(false, user.id, user.tcpPort);
                                player.sendMessage(StringList.discordremoved[Member.getMember(player).lang]);
                            }
                        }
                    }
                }
            } else if (holder == ItemStackList.GameSelectHolder) {
                e.setCancelled(true);
                if (RawSlot < 4)
                    speedcubingServer.tcp.sendClean(Member.getMember(e.getWhoClicked()).tcpPort, "reduce|" + Member.getMember((Player) e.getWhoClicked()).id + "|" + RawSlot);
            }
        }
    }
}