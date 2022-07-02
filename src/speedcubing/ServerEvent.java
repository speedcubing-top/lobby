package speedcubing;

import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import speedcubing.List.EntityList;
import speedcubing.List.StringList;
import speedcubing.lib.bukkit.BungeePluginMessage;
import speedcubing.lib.bukkit.SideBar;
import speedcubing.lib.bukkit.event.PlayInEvent;
import speedcubing.lib.eventbus.LibEventHandler;
import speedcubing.lib.utils.Console;
import speedcubing.server.events.InputEvent;
import speedcubing.server.events.SocketEvent;
import speedcubing.server.events.player.HubEvent;
import speedcubing.server.events.player.NickEvent;
import speedcubing.server.events.player.SkinEvent;
import speedcubing.server.events.player.ToggleFlyEvent;
import speedcubing.server.libs.GlobalString;
import speedcubing.server.speedcubingServer;

import java.util.UUID;

public class ServerEvent {
    @LibEventHandler(priority = 500)
    public void InputEvent(InputEvent e) {
        String[] rs = e.receive.split("\\|");
        switch (rs[3]) {
            case "login":
                Member.getMember(Integer.parseInt(rs[4])).haveToLogin = true;
                break;
        }
    }

    @LibEventHandler
    public void socketEvent(SocketEvent e) {
        String[] rs = e.receive.split("\\|");
        switch (rs[0]) {
            case "online":
                switch (rs[1]) {
                    case "1":
                        for (SideBar s : SideBar.getSidebars()) {
                            s.setLine(StringList.LobbySideBar[Member.getMember(s.player).lang][3] + rs[2], 3);
                        }
                        break;
                    case "2":
                        EntityList.Lobbys[9].setName("§e§l" + rs[2] + " Players");
                        break;
                    case "3":
                        EntityList.Lobbys[1].setName("§e§l" + rs[2] + " Players");
                        break;
                    case "4":
                        EntityList.Lobbys[5].setName("§e§l" + rs[2] + " Players");
                        break;
                    case "5":
                        EntityList.Lobbys[11].setName("§e§l" + rs[2] + " Players");
                        break;
                    case "6":
                        EntityList.Lobbys[7].setName("§e§l" + rs[2] + " Players");
                        break;
                    case "7":
                        EntityList.Lobbys[3].setName("§e§l" + rs[2] + " Players");
                        break;
                }
                break;
            case "reduce":
                BungeePluginMessage.switchServer(Bukkit.getPlayer(UUID.fromString(rs[1])), "reduce");
                break;
        }
    }

    @LibEventHandler
    public void PlayInEvent(PlayInEvent e) {
        if (e.packet instanceof PacketPlayInChat) {
            Player player = e.player;
            String message = ((PacketPlayInChat) e.packet).a();
            Member member = Member.getMember(player);
            if (member.discord) {
                e.isCancelled = true;
                if (message.split(" ").length == 1 && message.matches("[0-9]{18}")) {
                    String theOldOne = speedcubingServer.connection.selectString("playersdata", "discord", "id=" + member.id);
                    speedcubingServer.connection.update("playersdata", "discord='" + message + "'", "id=" + member.id);
                    if (!theOldOne.equals("") && !theOldOne.equals(message))
                        speedcubingServer.tcp.send(20000, "a|" + theOldOne);
                    player.sendMessage("§aSuccessfully updated the discord. (§6" + message + "§a)\nYou can now verify your minecraft account in the Discord Server!");
                } else player.sendMessage(StringList.discordinvalid[member.lang]);
                member.discord = false;
                speedcubingServer.node(false, member.id, member.tcpPort);
            } else if (message.charAt(0) != '/') {
                e.isCancelled = true;
                String[] formats = speedcubingServer.getFormat(member.rank);
                String text = formats[0] + player.getName();
                String[] str = GlobalString.MessageFormat[0];
                String[] out = {
                        str[0].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[1].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[2].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[3].replace("%text%", text).replace("%text2%", formats[2] + message)
                };
                player.getWorld().getPlayers().forEach(a -> a.sendMessage(out[Member.getMember(a).lang]));
                Console.sendColoredConsole("§7[§aChatLog§7] [§b" + player.getWorld().getName() + "§7] " + out[1]);
            }
        }
    }

    @LibEventHandler
    public void HubEvent(HubEvent e) {
        e.player.sendMessage(GlobalString.AlreadyInHub[Member.getMember(e.player).lang]);
    }
}
