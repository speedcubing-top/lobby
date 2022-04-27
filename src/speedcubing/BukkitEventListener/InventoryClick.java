package speedcubing.BukkitEventListener;

import speedcubing.lib.bukkit.PlayerUtils;
import speedcubing.lib.bukkit.SideBarAPI;
import speedcubing.server.PluginMessage;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scoreboard.Scoreboard;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;

import java.util.UUID;

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
                        PluginMessage.switchServer((Player) e.getWhoClicked(), "kbffa");
                        break;
                    case 10:
                        PluginMessage.switchServer((Player) e.getWhoClicked(), "mlgrush");
                        break;
                    case 28:
                        PluginMessage.switchServer((Player) e.getWhoClicked(), "practice");
                        break;
                    case 23:
                        PluginMessage.switchServer((Player) e.getWhoClicked(), "fastbuilder");
                        break;
                    case 34:
                        PluginMessage.switchServer((Player) e.getWhoClicked(), "clutch");
                        break;
                    case 21:
                        Player player = (Player) e.getWhoClicked();
                        player.closeInventory();
                        player.playSound(new Location(Bukkit.getWorld("world"), 0.5, 99.01563, -15.5), Sound.ENDERMAN_TELEPORT, 1F, 1F);
                        PlayerUtils.teleport(((CraftPlayer) player).getHandle().playerConnection, 7.5, 99, 21.5, 0, 0);
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
                    UUID uuid = player.getUniqueId();
                    Scoreboard scoreboard = player.getScoreboard();
                    String[] sidebar = StringList.LobbySideBar[x];
                    String[] split1 = SideBarAPI.prefixSuffixSplit(sidebar[7]);
                    String[] split2 = SideBarAPI.prefixSuffixSplit(sidebar[4]);
                    String[] split3 = SideBarAPI.prefixSuffixSplit(sidebar[1]);
                    scoreboard.getTeam("§3§f").setPrefix(split1[0]);
                    scoreboard.getTeam("§3§f").setSuffix(split1[1]);
                    scoreboard.getTeam("§2§f").setPrefix(split2[0]);
                    scoreboard.getTeam("§2§f").setSuffix(split2[1]);
                    scoreboard.getTeam("§1§f").setPrefix(split3[0]);
                    scoreboard.getTeam("§1§f").setSuffix(split3[1]);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    player.sendMessage(StringList.LangSettings[x]);
                    int finalX = x;
                    new Thread(() -> speedcubingServer.connection.update("playersdata", "lang=" + finalX, "uuid='" + uuid + "'")).start();
                    User.LangCache.put(uuid, x);
                    player.closeInventory();
                }
            } else if (holder == ItemStackList.SettingsHolder) {
                e.setCancelled(true);
                switch (RawSlot) {
                    case 11: {
                        Player player = (Player) e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        player.openInventory(ItemStackList.Lang[User.getLang(player.getUniqueId())]);
                    }
                    break;
//                    case 15: {
//                        Player player = (Player) e.getWhoClicked();
//                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
//                        player.closeInventory();
//                        player.sendMessage(StringList.discord[PlayerData.getLang(player.getUniqueId())]);
//                        Main.discord.add(player.getUniqueId());
//                    }
//                    break;
                }
            } else if (holder == ItemStackList.GameSelectHolder) {
                e.setCancelled(true);
                if (RawSlot < 4) {
                    try {
                        speedcubingServer.tcp.sendUnsafe(25604 - Bukkit.getPort() % 2, "d|" + e.getWhoClicked().getUniqueId() + "|" + RawSlot);
                    } catch (Exception exception) {

                    }
                }
            }
        }
    }
}