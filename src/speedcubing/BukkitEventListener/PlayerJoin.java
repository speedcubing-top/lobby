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
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.Main;
import speedcubing.lib.bukkit.SideBar;
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
        String[] sidebar = StringList.LobbySideBar[user.lang];
        player.setScoreboard(new SideBar(sidebar[9])
                .staticLine(sidebar[8], 8)
                .changeableLine(sidebar[7], 7)
                .changeableLine(sidebar[6], "loading...", 6)
                .staticLine(sidebar[5], 5)
                .changeableLine(sidebar[4], 4)
                .staticLine(sidebar[3].replace("%player%", format[0] + format[1] + name), 3)
                .staticLine(sidebar[2], 2)
                .staticLine(sidebar[1], 1)
                .staticLine(sidebar[0], 0)
                .scoreboard);
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






