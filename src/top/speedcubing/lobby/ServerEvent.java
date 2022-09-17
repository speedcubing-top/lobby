package top.speedcubing.lobby;

import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.entity.Player;
import top.speedcubing.lib.bukkit.BungeePluginMessage;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.event.PlayInEvent;
import top.speedcubing.lib.eventbus.LibEventHandler;
import top.speedcubing.lib.utils.Console;
import top.speedcubing.lib.utils.SQL.SQLUtils;
import top.speedcubing.lobby.List.EntityList;
import top.speedcubing.lobby.List.StringList;
import top.speedcubing.server.events.CubingTickEvent;
import top.speedcubing.server.events.InputEvent;
import top.speedcubing.server.events.SocketEvent;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.libs.GlobalString;
import top.speedcubing.server.speedcubingServer;

public class ServerEvent {
    @LibEventHandler(priority = 500)
    public void InputEvent(InputEvent e) {
        String[] rs = e.receive.split("\\|");
        switch (rs[3]) {
            case "login":
                Main.login.add(Integer.parseInt(rs[4]));
                break;
        }
    }


    Integer[] onlines;
    int a;

    @LibEventHandler
    public void CubingTickEvent(CubingTickEvent e) {
        a = speedcubingServer.getOnlineCount();
        for (SideBar s : SideBar.getSidebars()) {
            s.setLine(StringList.LobbySideBar[Member.getMember(s.player).user.lang][3] + a, 3);
        }
        onlines = SQLUtils.getIntArray(speedcubingServer.systemConnection.executeQuery("SELECT onlinecount FROM `servers` ORDER BY `name` ASC"));
        EntityList.Lobbys[1].setName("§e§l" + onlines[0] + " Players");
        EntityList.Lobbys[3].setName("§e§l" + onlines[1] + " Players");
        EntityList.Lobbys[5].setName("§e§l" + onlines[2] + " Players");
        EntityList.Lobbys[7].setName("§e§l" + onlines[4] + " Players");
        EntityList.Lobbys[9].setName("§e§l" + onlines[5] + " Players");
        EntityList.Lobbys[11].setName("§e§l" + onlines[6] + " Players");
    }

    @LibEventHandler
    public void socketEvent(SocketEvent e) {
        String[] rs = e.receive.split("\\|");
        switch (rs[0]) {
            case "reduce":
                BungeePluginMessage.switchServer(Member.getMember(Integer.parseInt(rs[1])).user.player, "reduce");
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
                    String theOldOne = SQLUtils.getString(speedcubingServer.connection.select("playersdata", "discord", "id=" + member.user.id));
                    speedcubingServer.connection.update("playersdata", "discord=" + message, "id=" + member.user.id);
                    if (!theOldOne.equals("") && !theOldOne.equals(message))
                        speedcubingServer.tcp.send(20000, "a|" + theOldOne);
                    player.sendMessage("§aSuccessfully updated the discord. (§6" + message + "§a)\nYou can now verify your minecraft account in the Discord Server!");
                } else player.sendMessage(StringList.discordinvalid[member.user.lang]);
                member.discord = false;
                speedcubingServer.node(false, member.user.id, member.user.tcpPort);
            } else if (message.charAt(0) != '/') {
                e.isCancelled = true;
                String[] formats = speedcubingServer.getFormat(member.user.rank);
                String text = formats[0] + player.getName();
                String[] str = GlobalString.MessageFormat[0];
                String[] out = {
                        str[0].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[1].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[2].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[3].replace("%text%", text).replace("%text2%", formats[2] + message)
                };
                player.getWorld().getPlayers().forEach(a -> a.sendMessage(out[Member.getMember(a).user.lang]));
                Console.sendColoredConsole("§7[§aChatLog§7] [§b" + player.getWorld().getName() + "§7] " + out[1]);
            }
        }
    }

    @LibEventHandler
    public void HubEvent(HubEvent e) {
        e.isCancelled = true;
        e.player.sendMessage(GlobalString.AlreadyInHub[Member.getMember(e.player).user.lang]);
    }
}
