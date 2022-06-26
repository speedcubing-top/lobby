package speedcubing.BukkitEventListener;

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
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.Main;
import speedcubing.lib.bukkit.BungeePluginMessage;
import speedcubing.lib.bukkit.PlayerUtils;
import speedcubing.lib.bukkit.SideBar;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

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
                    String[] sidebar = StringList.LobbySideBar[x];
                    SideBar.setLine(player, sidebar[7], 0);
                    SideBar.setLine(player, sidebar[4], 1);
                    SideBar.setLine(player, sidebar[1], 2);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    player.sendMessage(StringList.LangSettings[x]);
                    int finalX = x;
                    new Thread(() -> speedcubingServer.connection.update("playersdata", "lang=" + finalX, "uuid='" + uuid + "'")).start();
                    User.getUser(uuid).lang = x;
                    player.closeInventory();
                }
            } else if (holder == ItemStackList.SettingsHolder) {
                e.setCancelled(true);
                switch (RawSlot) {
                    case 11: {
                        Player player = (Player) e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        player.openInventory(ItemStackList.Lang[User.getUser(player.getUniqueId()).lang]);
                    }
                    break;
                    case 15: {
                        Player player = (Player) e.getWhoClicked();
                        User user = User.getUser(player.getUniqueId());
                        switch (e.getAction()) {
                            case PICKUP_ALL:
                                player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                                player.closeInventory();
                                player.sendMessage(StringList.discord[user.lang]);
                                Main.discord.add(player.getUniqueId());
                                speedcubingServer.node(true, player.getUniqueId(), user.tcpPort);
                                break;
                            case PICKUP_HALF:
                                player.closeInventory();
                                String discord = speedcubingServer.connection.selectString("playersdata", "discord", "uuid='" + player.getUniqueId() + "'");
                                if (!discord.equals("")) {
                                    speedcubingServer.tcp.send(20000, "a|" + discord);
                                    speedcubingServer.connection.update("playersdata", "discord=''", "uuid='" + player.getUniqueId() + "'");
                                }
                                Main.discord.remove(player.getUniqueId());
                                speedcubingServer.node(false, player.getUniqueId(), user.tcpPort);
                                player.sendMessage(StringList.discordremoved[User.getUser(player.getUniqueId()).lang]);
                                break;
                        }
                    }
                    break;
                }
            } else if (holder == ItemStackList.GameSelectHolder) {
                e.setCancelled(true);
                if (RawSlot < 4) {
                    UUID uuid = e.getWhoClicked().getUniqueId();
                    speedcubingServer.tcp.sendClean(User.getUser(uuid).tcpPort, "reduce|" + uuid + "|" + RawSlot);
                }
            }
        }
    }
}