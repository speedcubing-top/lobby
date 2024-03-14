package top.speedcubing.lobby;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.entity.Player;
import top.speedcubing.common.database.Database;
import top.speedcubing.common.database.DatabaseData;
import top.speedcubing.common.rank.RankFormat;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.eventbus.CubingEventHandler;
import top.speedcubing.lib.events.packet.PlayInEvent;
import top.speedcubing.lobby.BukkitEventListener.PlayerJoin;
import top.speedcubing.lobby.List.EntityList;
import top.speedcubing.lobby.List.StringList;
import top.speedcubing.server.events.ConfigReloadEvent;
import top.speedcubing.server.events.CubingTickEvent;
import top.speedcubing.server.events.InputEvent;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.lang.GlobalString;
import top.speedcubing.server.share.Chat;
import top.speedcubing.server.speedcubingServer;
import top.speedcubing.server.utils.config;

public class ServerEvent {
    @CubingEventHandler
    public void ConfigReloadEvent(ConfigReloadEvent e) {
        PlayerJoin.joinCrash.clear();
        config.config.get("onlinecrash").getAsJsonArray().forEach(a -> PlayerJoin.joinCrash.add(a.getAsString().toLowerCase()));
    }

    @CubingEventHandler(priority = 500)
    public void InputEvent(InputEvent e) {
        switch (e.subHeader) {
            case "login":
                try {
                    Lobby.login.add(e.receive.readInt());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }

    @CubingEventHandler
    public void CubingTickEvent(CubingTickEvent e) {
        SideBar.getSidebars().forEach(a -> a.setLineByScore(StringList.LobbySideBar[Member.getMember(a.player).user.lang][3] + DatabaseData.onlineCount, 6));
        Integer[] onlines = Database.systemConnection.select("onlinecount").from("servers").orderBy("name ASC").getIntArray();
        EntityList.Lobbys[1].setName("§e§l" + onlines[0] + " Players");
        EntityList.Lobbys[3].setName("§e§l" + onlines[1] + " Players");
        EntityList.Lobbys[5].setName("§e§l" + onlines[2] + " Players");
        EntityList.Lobbys[7].setName("§e§l" + onlines[4] + " Players");
        EntityList.Lobbys[9].setName("§e§l" + onlines[5] + " Players");
        EntityList.Lobbys[11].setName("§e§l" + onlines[6] + " Players");
    }

    @CubingEventHandler
    public void PlayInEvent(PlayInEvent e) {
        if (e.packet instanceof PacketPlayInChat) {
            Player player = e.player;
            String message = ((PacketPlayInChat) e.packet).a();
            Member member = Member.getMember(player);
            if (member.discord) {
                e.isCancelled = true;
                try {
                    if (message.matches("^.{3,32}#[0-9]{4}$") || message.matches("^[0-9,a-z_.]*$")) {
                        member.user.dbUpdate("discord='" + message + "'");
                        member.user.sendLangMessage(StringList.updatedDiscord, message);
                    } else member.user.sendLangMessage(StringList.discordinvalid);
                } catch (Exception exception) {
                    member.user.sendLangMessage(StringList.discordinvalid);
                }
                member.discord = false;
                member.user.setInput(false);
            } else if (message.charAt(0) != '/') {
                e.isCancelled = true;
                RankFormat format = member.user.getFormat(false);
                Chat.globalChat(player.getWorld().getPlayers(), player, GlobalString.MessageFormat, message, format.getPrefix() + player.getName(), format.getChatColor());
            }
        }
    }

    @CubingEventHandler
    public void HubEvent(HubEvent e) {
        e.isCancelled = true;
        Member.getMember(e.player).user.sendLangMessage(StringList.AlreadyInHub);
    }
}
