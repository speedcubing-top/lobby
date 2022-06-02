package speedcubing;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.lib.bukkit.packetwrapper.OutPlayerListHeaderFooter;
import speedcubing.lib.utils.Console;
import speedcubing.lib.utils.Reflections;
import speedcubing.server.PluginMessage;
import speedcubing.server.events.SocketEvent;
import speedcubing.server.libs.GlobalString;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;
import speedcubing.spigot.Event.ServerEventHandler;
import speedcubing.spigot.Event.events.PlayInChatEvent;
import speedcubing.spigot.Event.events.PlayInUseEntityEvent;

import java.util.UUID;

public class ServerEvent {

    @ServerEventHandler
    public void socketEvent(SocketEvent e) {
        String[] rs = e.rs;
        switch (rs[0]) {
            case "online":
                EntityArmorStand stand = null;
                switch (rs[1]) {
                    case "1":
                        speedcubingServer.AllPlayers = Integer.parseInt(rs[2]);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            int lang = User.getUser(p.getUniqueId()).lang;
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(OutPlayerListHeaderFooter.a(GlobalString.LobbyTabList[0][lang], GlobalString.LobbyTabList[1][lang].replace("%int%", rs[2])));
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
                    case "8":
                        EntityList.Lobbys[11].setCustomName("§e§l" + rs[2] + " Players");
                        stand = EntityList.Lobbys[11];
                        break;
                }
                if (stand != null) {
                    PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
                    Bukkit.getOnlinePlayers().forEach(a -> ((CraftPlayer) a).getHandle().playerConnection.sendPacket(packet));
                }
                break;
            case "reduce":
                PluginMessage.switchServer(Bukkit.getPlayer(UUID.fromString(rs[1])), "reduce");
                break;
        }
    }

    @ServerEventHandler
    public void PlayInUseEntityEvent(PlayInUseEntityEvent e) {
        Player player = e.player;
        PacketPlayInUseEntity packet = e.packet;
        if (player.getItemInHand().getType() == org.bukkit.Material.AIR && packet.a().equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
            switch (EntityList.NPCS.indexOf(Reflections.getField(packet, "a"))) {
                case 0:
                    PluginMessage.switchServer(player, "mlgrush");
                    break;
                case 1:
                    Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> player.openInventory(ItemStackList.GameSelect[User.getUser(player.getUniqueId()).lang]));
                    break;
                case 2:
                    PluginMessage.switchServer(player, "knockbackffa");
                    break;
                case 3:
                    PluginMessage.switchServer(player, "clutch");
                    break;
                case 4:
                    PluginMessage.switchServer(player, "fastbuilder");
                    break;
                case 5:
                    PluginMessage.switchServer(player, "practice");
                    break;
            }
    }

    @ServerEventHandler
    public void PlayInChatEvent(PlayInChatEvent e) {
        Player player = e.player;
        String message = e.packet.a();
        UUID uuid = player.getUniqueId();
        if (Main.discord.contains(uuid)) {
            e.setCancelled(true);
            if (message.split(" ").length == 1 && message.matches("[0-9]{18}")) {
                String theOldOne = speedcubingServer.connection.selectString("playersdata", "discord", "uuid='" + uuid + "'");
                speedcubingServer.connection.update("playersdata", "discord='" + message + "'", "uuid='" + uuid + "'");
                if (!theOldOne.equals("") && !theOldOne.equals(message))
                    speedcubingServer.tcp.send(20000, theOldOne);
                player.sendMessage("§aSuccessfully updated the discord. (§6" + message + "§a)\nYou can now verify your minecraft account in the Discord Server!");
            } else player.sendMessage(StringList.discordinvalid[User.getUser(uuid).lang]);
            Main.discord.remove(uuid);
            speedcubingServer.node(false, uuid, User.getUser(uuid).tcpPort);
        } else if (message.charAt(0) != '/') {
            e.setCancelled(true);
            String[] formats = speedcubingServer.getFormat(User.getUser(uuid).rank);
            String text = formats[0] + player.getName();
            String[] str = GlobalString.MessageFormat[0];
            String[] out = new String[]{
                    str[0].replace("%text%", text).replace("%text2%", formats[2] + message),
                    str[1].replace("%text%", text).replace("%text2%", formats[2] + message),
                    str[2].replace("%text%", text).replace("%text2%", formats[2] + message),
                    str[3].replace("%text%", text).replace("%text2%", formats[2] + message)
            };
            Bukkit.getOnlinePlayers().forEach(a -> a.sendMessage(out[User.getUser(a.getUniqueId()).lang]));
            Console.sendColoredConsole("§7[§aChatLog§7] [§b" + player.getWorld().getName() + "§7] " + out[1]);
        }
    }
}
