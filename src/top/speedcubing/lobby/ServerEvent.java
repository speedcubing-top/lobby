package top.speedcubing.lobby;

import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.entity.Player;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.event.PlayInEvent;
import top.speedcubing.lib.bukkit.pluginMessage.BungeePluginMessage;
import top.speedcubing.lib.eventbus.LibEventHandler;
import top.speedcubing.lib.utils.Console;
import top.speedcubing.lobby.BukkitEventListener.PlayerJoin;
import top.speedcubing.lobby.List.EntityList;
import top.speedcubing.lobby.List.StringList;
import top.speedcubing.server.config;
import top.speedcubing.server.events.ConfigReloadEvent;
import top.speedcubing.server.events.CubingTickEvent;
import top.speedcubing.server.events.InputEvent;
import top.speedcubing.server.events.SocketEvent;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.libs.GlobalString;
import top.speedcubing.server.share.Chat;
import top.speedcubing.server.speedcubingServer;

import java.io.IOException;

public class ServerEvent {
    @LibEventHandler
    public void ConfigReloadEvent(ConfigReloadEvent e) {
        PlayerJoin.joinCrash.clear();
        config.config.get("onlinecrash").getAsJsonArray().forEach(a -> PlayerJoin.joinCrash.add(a.getAsString().toLowerCase()));
    }

    @LibEventHandler(priority = 500)
    public void InputEvent(InputEvent e) {
        switch (e.header) {
            case "login":
                try {
                    Lobby.login.add(e.receive.readInt());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }

    @LibEventHandler
    public void CubingTickEvent(CubingTickEvent e) {
        int onlineCount = speedcubingServer.getOnlineCount();
        SideBar.getSidebars().forEach(a -> a.setLineByScore(StringList.LobbySideBar[Member.getMember(a.player).user.lang][3] + onlineCount, 6));
        Integer[] onlines = speedcubingServer.systemConnection.select("onlinecount").from("servers").where("`name` LIKE '%" + speedcubingServer.onlineOroFfline + "%'").orderBy("name ASC").getIntArray();
        EntityList.Lobbys[1].setName("§e§l" + onlines[0] + " Players");
        EntityList.Lobbys[3].setName("§e§l" + onlines[1] + " Players");
        EntityList.Lobbys[5].setName("§e§l" + onlines[2] + " Players");
        EntityList.Lobbys[7].setName("§e§l" + onlines[4] + " Players");
        EntityList.Lobbys[9].setName("§e§l" + onlines[5] + " Players");
        EntityList.Lobbys[11].setName("§e§l" + onlines[6] + " Players");
    }

    @LibEventHandler
    public void socketEvent(SocketEvent e) {
        switch (e.header) {
            case "reduce":
                try {
                    BungeePluginMessage.switchServer(Member.getMember(e.receive.readInt()).user.player, "reduce");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
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
                try {
                    Long.parseLong(message);
                    if (message.split(" ").length == 1) {
                        member.user.dbUpdate("discord=" + message);
                        member.user.langMessageSender(StringList.updatedDiscord).replace("%a%", message).send();
                    } else member.user.sendLangMessage(StringList.discordinvalid);
                } catch (Exception exception) {
                    member.user.sendLangMessage(StringList.discordinvalid);
                }
                member.discord = false;
                speedcubingServer.node(false, member.user.id, member.user.tcpPort);
            } else if (message.charAt(0) != '/') {
                e.isCancelled = true;
                if (Chat.checkBlockedText(message))
                    return;
                String filtered = Chat.filter(message);
                String[] formats = speedcubingServer.getFormat(member.user.rank);
                String text = formats[0] + player.getName();
                String[] str = GlobalString.MessageFormat;
                String[] out = {
                        str[0].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[1].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[2].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[3].replace("%text%", text).replace("%text2%", formats[2] + message)
                };
                String[] out2 = {
                        str[0].replace("%text%", text).replace("%text2%", formats[2] + filtered),
                        str[1].replace("%text%", text).replace("%text2%", formats[2] + filtered),
                        str[2].replace("%text%", text).replace("%text2%", formats[2] + filtered),
                        str[3].replace("%text%", text).replace("%text2%", formats[2] + filtered)
                };
                Chat.globalChat(player.getWorld().getPlayers(), player, out, out2);
                Console.sendColoredConsole("§7[§aChatLog§7] [§b" + player.getWorld().getName() + "§7] " + out[1]);
            }
        }
    }

    @LibEventHandler
    public void HubEvent(HubEvent e) {
        e.isCancelled = true;
        Member.getMember(e.player).user.sendLangMessage(StringList.AlreadyInHub);
    }
}
