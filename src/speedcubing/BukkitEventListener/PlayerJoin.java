package speedcubing.BukkitEventListener;

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
import speedcubing.Main;
import speedcubing.lib.bukkit.SideBarAPI;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.isOnline()) {
            player.sendTitle("", "");
            player.setGameMode(GameMode.ADVENTURE);
            UUID uuid = player.getUniqueId();
            if (!speedcubingServer.isBungeeOnlineMode && Main.haveToLogin.contains(uuid)) {
                player.teleport(new Location(Bukkit.getWorld("login"), 0.5, 100, 0.5, 0, 0));
                player.sendMessage("§aDo §6/premium§a to enable Skip-Login if this account is premium!");
                if (speedcubingServer.connection.selectString("playersdata", "password", "uuid='" + uuid + "'").equals(""))
                    player.sendMessage("/register <password> <confirm password>");
                else player.sendMessage("/login <password>");
            } else joinLobby(player, uuid, false);
        }
    }

    public static void joinLobby(Player player, UUID uuid, boolean login) {
        Main.haveToLogin.remove(uuid);
        User user = User.getUser(uuid);
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0));
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        Inventory inventory = player.getInventory();
        String[] format = speedcubingServer.getFormat(user.rank);
        String name = player.getName();
        player.getInventory().setHeldItemSlot(0);
        if (!login)
            player.sendMessage(StringList.LobbyJoinMessage[user.lang]
                    .replace("%f0%", format[0])
                    .replace("%f1%", format[2])
                    .replace("%name%", name));
        player.getWorld().getPlayers().forEach(a -> a.sendMessage(StringList.LobbyJoinMessage[User.getUser(a.getUniqueId()).lang].replace("%f0%", format[0]).replace("%f1%", format[2]).replace("%name%", name)));
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        String[] sidebar = StringList.LobbySideBar[user.lang];
        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(sidebar[9]);
        String[] split1 = SideBarAPI.prefixSuffixSplit(sidebar[7]);
        String[] split2 = SideBarAPI.prefixSuffixSplit(sidebar[4]);
        String[] split3 = SideBarAPI.prefixSuffixSplit(sidebar[1]);
        SideBarAPI.sidebarTeams(scoreboard, new String[][]{
                {sidebar[8]},
                {split1[0], "§3§f", split1[1]},
                {"", sidebar[6], "loading..."},
                {sidebar[5]},
                {split2[0], "§2§f", split2[1]},
                {sidebar[3].replace("%player%", format[0] + format[1] + name)},
                {sidebar[2]},
                {split3[0], "§1§f", split3[1]},
                {sidebar[0]},
        }, new int[]{8, 7, 6, 5, 4, 3, 2, 1, 0});
        player.setScoreboard(scoreboard);
        EntityList.LobbyNPCPackets.forEach(connection::sendPacket);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                EntityList.LobbyNPCDelayedPackets.forEach(connection::sendPacket);
            }
        }, 4000);
        EntityList.spawnpackets.forEach(connection::sendPacket);
        inventory.clear();
        inventory.setItem(0, ItemStackList.Navigator[user.lang]);
        inventory.setItem(1, ItemStackList.Settings[user.lang]);
    }
}






