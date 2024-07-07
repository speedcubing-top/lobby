package top.speedcubing.lobby;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.entity.Player;
import top.speedcubing.common.configuration.ServerConfig;
import top.speedcubing.common.database.DatabaseData;
import top.speedcubing.common.events.ConfigReloadEvent;
import top.speedcubing.common.events.CubingTickEvent;
import top.speedcubing.common.events.SocketInputEvent;
import top.speedcubing.common.rank.RankFormat;
import top.speedcubing.common.server.MinecraftServer;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.events.packet.PlayInEvent;
import top.speedcubing.lib.eventbus.CubingEventHandler;
import top.speedcubing.lobby.list.EntityList;
import top.speedcubing.lobby.list.StringList;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.lang.GlobalString;
import top.speedcubing.server.share.Chat;

public class ServerEvent {
    @CubingEventHandler
    public void configReloadEvent(ConfigReloadEvent e) {
        PlayerJoin.joinCrash.clear();
        ServerConfig.config.get("onlinecrash").getAsJsonArray().forEach(a -> PlayerJoin.joinCrash.add(a.getAsString().toLowerCase()));
    }

    @CubingEventHandler(priority = 500)
    public void inputEvent(SocketInputEvent e) {
        switch (e.subHeader) {
            case "login":
                try {
                    Lobby.login.add(e.getData().readInt());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }

    @CubingEventHandler
    public void cubingTickEvent(CubingTickEvent e) {
        SideBar.getSidebars().forEach(a -> a.setLineByScore(StringList.LobbySideBar[Member.getMember(a.player).user.lang][3] + DatabaseData.onlineCount, 6));
        EntityList.Lobbys[1].setName("§e§l" + MinecraftServer.getServer("clutch").getPlayerCount() + " Players");
        EntityList.Lobbys[3].setName("§e§l" + MinecraftServer.getServer("fastbuilder").getPlayerCount() + " Players");
        EntityList.Lobbys[5].setName("§e§l" + MinecraftServer.getServer("knockbackffa").getPlayerCount() + " Players");
        EntityList.Lobbys[7].setName("§e§l" + MinecraftServer.getServer("mlgrush").getPlayerCount() + " Players");
        EntityList.Lobbys[9].setName("§e§l" + MinecraftServer.getServer("practice").getPlayerCount() + " Players");
        EntityList.Lobbys[11].setName("§e§l" + MinecraftServer.getServer("reduce").getPlayerCount() + " Players");
        EntityList.Lobbys[13].setName("§e§l" + MinecraftServer.getServer("smp").getPlayerCount() + " Players");
        EntityList.Lobbys[15].setName("§e§l" + MinecraftServer.getServer("bedwars").getPlayerCount() + " Players");
    }

    @CubingEventHandler
    public void playInEvent(PlayInEvent e) {
        if (e.getPacket() instanceof PacketPlayInChat) {
            Player player = e.getPlayer();
            String message = ((PacketPlayInChat) e.getPacket()).a();
            Member member = Member.getMember(player);
            if (member.discord) {
                e.setCancelled(true);
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
                e.setCancelled(true);
                RankFormat format = member.user.getFormat(false);
                Chat.globalChat(player.getWorld().getPlayers(), player, GlobalString.MessageFormat, message, format.getPrefix() + player.getName(), format.getChatColor());
            }
        }
    }

    @CubingEventHandler
    public void hubEvent(HubEvent e) {
        e.setCancelled(true);
        Member.getMember(e.getPlayer()).user.sendLangMessage(StringList.AlreadyInHub);
    }
}
