package speedcubing.BukkitEventListener;

import cubing.spigot.lib.bukkit.PlayerUtils;
import cubing.spigot.lib.bukkit.api.SideBarAPI;
import cubingserver.libs.User;
import cubingserver.speedcubingServer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.isOnline()) {
            UUID uuid = player.getUniqueId();
            player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0));
            player.sendTitle("", "");
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            Inventory inventory = player.getInventory();
            int lang = User.getLang(uuid);
            String[] format = User.getFormat(User.getRank(uuid));
            String name = player.getName();
            player.getInventory().setHeldItemSlot(0);
            player.sendMessage(StringList.LobbyJoinMessage[lang]
                    .replace("%format0%", format[0])
                    .replace("%format1%", format[1])
                    .replace("%format2%", format[2])
                    .replace("%name%", name));
            for (Player p : player.getWorld().getPlayers()) {
                p.sendMessage(StringList.LobbyJoinMessage[User.getLang(p.getUniqueId())]
                        .replace("%format0%", format[0])
                        .replace("%format1%", format[1])
                        .replace("%format2%", format[2])
                        .replace("%name%", name));
            }
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            String[] sidebar = StringList.LobbySideBar[lang];
            Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(sidebar[9]);
            String[] split1 = SideBarAPI.prefixSuffixSplit(sidebar[7]);
            String[] split2 = SideBarAPI.prefixSuffixSplit(sidebar[4]);
            String[] split3 = SideBarAPI.prefixSuffixSplit(sidebar[1]);
            SideBarAPI.sidebarTeams(scoreboard, new String[][]{
                    {sidebar[8]},
                    {split1[0], "§3§f", split1[1]},
                    {"", sidebar[6], Integer.toString(speedcubingServer.AllPlayers)},
                    {sidebar[5]},
                    {split2[0], "§2§f", split2[1]},
                    {sidebar[3].replace("%player%", format[0] + format[1] + name)},
                    {sidebar[2]},
                    {split3[0], "§1§f", split3[1]},
                    {sidebar[0]},
            }, new int[]{8, 7, 6, 5, 4, 3, 2, 1, 0});
            player.setScoreboard(scoreboard);
            for (Packet<?> packet : EntityList.LobbyNPCPackets) {
                connection.sendPacket(packet);
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Packet<?> packet : EntityList.LobbyNPCDelayedPackets) {
                        connection.sendPacket(packet);
                    }
                }
            }, 4000);
            for (PacketPlayOutSpawnEntityLiving packet : EntityList.spawnpackets)
                connection.sendPacket(packet);
            inventory.clear();
            inventory.setItem(0, ItemStackList.Navigator[lang]);
            inventory.setItem(1, ItemStackList.Settings[lang]);
            PlayerUtils.sendActionBar(connection, "");
            player.setGameMode(GameMode.ADVENTURE);
        }
    }
}






