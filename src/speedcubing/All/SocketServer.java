package speedcubing.All;

import cubing.bukkit.PlayerUtils;
import cubing.bukkit.api.BungeePluginMessage;
import cubingserver.StringList.GlobalString;
import cubingserver.connection.ServerUtils;
import cubingserver.connection.SocketUtils;
import cubingserver.connection.UDPSocketUtils;
import cubingserver.libs.LogListener;
import cubingserver.libs.PlayerData;
import cubingserver.speedcubingServer;
import cubingserver.things.Cps;
import cubingserver.things.froze;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import speedcubing.List.EntityList;
import speedcubing.List.StringList;
import speedcubing.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.util.UUID;

public class SocketServer {

    public void Load() {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
        new Thread(() -> {
            while (true) {
                try {
                    UDPSocketUtils.socket.receive(datagramPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] rs = new String(datagramPacket.getData(), 0, datagramPacket.getLength()).split("\\|");
                if (rs[0].equals("o")) {
                    EntityArmorStand stand = null;
                    switch (rs[1]) {
                        case "1":
                            ServerUtils.AllPlayers = Integer.parseInt(rs[2]);
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                int lang = PlayerData.getLang(p.getUniqueId());
                                PlayerUtils.sendTabHeaderFooter(((CraftPlayer) p).getHandle().playerConnection, GlobalString.LobbyTabList[0][lang], GlobalString.LobbyTabList[1][lang].replace("%int%", rs[2]));
                                Team team = p.getScoreboard().getTeam(StringList.LobbySideBar[lang][6]);
                                if (team != null)
                                    team.setSuffix(rs[2]);
                            }
                            break;
                        case "2":
                            EntityList.Lobbys[9].setCustomName("§e§l" + rs[2] + " Players");
                            stand = EntityList.Lobbys[9];
                            break;
                        case "3":
                            EntityList.Lobbys[1].setCustomName("§e§l" + rs[2] + " Players");
                            stand = EntityList.Lobbys[1];
                            break;
                        case "4":
                            EntityList.Lobbys[5].setCustomName("§e§l" + rs[2] + " Players");
                            stand = EntityList.Lobbys[5];
                            break;
                        case "6":
                            EntityList.Lobbys[7].setCustomName("§e§l" + rs[2] + " Players");
                            stand = EntityList.Lobbys[7];
                            break;
                        case "7":
                            EntityList.Lobbys[3].setCustomName("§e§l" + rs[2] + " Players");
                            stand = EntityList.Lobbys[3];
                            break;
                    }
                    if (stand != null) {
                        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                        }
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                String receive = "";
                try {
                    receive = new BufferedReader(new InputStreamReader(SocketUtils.socket.accept().getInputStream())).readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] rs = ServerUtils.SocketEvent(receive);
                if (rs != null && rs[0].equals("d"))
                    BungeePluginMessage.switchServer(Bukkit.getPlayer(UUID.fromString(rs[1])), "reduce");
            }
        }).start();
    }
}
