package top.speedcubing.lobby;

import java.io.IOException;
import java.util.Map;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.entity.Player;
import top.speedcubing.common.configuration.ServerConfig;
import top.speedcubing.common.database.DatabaseData;
import top.speedcubing.common.events.ConfigReloadEvent;
import top.speedcubing.common.events.CubingTickEvent;
import top.speedcubing.common.events.SocketReadEvent;
import top.speedcubing.common.rank.RankFormat;
import top.speedcubing.common.server.MinecraftServer;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.entity.Hologram;
import top.speedcubing.lib.bukkit.events.packet.PlayInEvent;
import top.speedcubing.lib.eventbus.CubingEventHandler;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;
import top.speedcubing.lobby.list.EntityList;
import top.speedcubing.lobby.list.StringList;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.lang.Lang;
import top.speedcubing.server.share.Chat;

/**
 * Handles custom event bus events: tick updates, chat, socket IO, and hub navigation.
 */
public class ServerEvent {

    @CubingEventHandler
    public void cubingTickEvent(CubingTickEvent e) {
        if (!Lobby.loaded) {
            return;
        }

        // Update sidebar online count
        SideBar.getSidebars().forEach(a -> a.setLineByScore(StringList.LobbySideBar[Member.getMember(a.player).user.lang][3] + DatabaseData.onlineCount, 6));

        // Update player count holograms for each server
        for (Map.Entry<String, Hologram> entry : EntityList.playerCountHolograms.entrySet()) {
            String serverName = entry.getKey();
            Hologram hologram = entry.getValue();
            hologram.setName("§e§l" + MinecraftServer.getServer(serverName).getPlayerCount() + " Players");
        }
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
                        member.user.sendMessage("%lang_lobby_msg_discord_updated%", message);
                    } else member.user.sendMessage("%lang_lobby_msg_discord_invalid%");
                } catch (Exception exception) {
                    member.user.sendMessage("%lang_lobby_msg_discord_invalid%");
                }
                member.discord = false;
                member.user.setInput(false);
            } else if (message.charAt(0) != '/') {
                e.setCancelled(true);
                RankFormat format = member.user.getFormat(false);
                Chat.globalChat(player.getWorld().getPlayers(), player, Lang.of("%lang_message_format%"), message, format.getPrefix() + player.getName(), format.getChatColor());
            }
        }
    }

    @CubingEventHandler(priority = 500)
    public void socketInputEvent(SocketReadEvent e) {
        switch (e.getPacketID()) {
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
    public void hubEvent(HubEvent e) {
        e.setCancelled(true);
        Member.getMember(e.getPlayer()).user.sendMessage("%lang_lobby_msg_alr_in_lobby%");
    }
}
