package top.speedcubing.lobby;

import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.speedcubing.common.database.Database;
import top.speedcubing.common.database.DatabaseData;
import top.speedcubing.common.player.PlayerCache;
import top.speedcubing.lib.utils.SQL.SQLConnection;
import top.speedcubing.common.events.CubingTickEvent;
import top.speedcubing.common.events.SocketReadEvent;
import top.speedcubing.common.rank.RankFormat;
import top.speedcubing.common.server.MinecraftServer;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.entity.Hologram;
import top.speedcubing.lib.bukkit.events.packet.PlayInEvent;
import top.speedcubing.lib.eventbus.CubingEventHandler;
import top.speedcubing.lobby.list.EntityList;
import top.speedcubing.lobby.list.StringList;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.events.player.LanguageChangeEvent;
import top.speedcubing.server.lang.Lang;
import top.speedcubing.server.share.Chat;

import java.io.IOException;
import java.util.Map;

/**
 * Handles custom event bus events: tick updates, chat, socket IO, and hub navigation.
 */
public class ServerEvent {

    @CubingEventHandler
    public void cubingTickEvent(CubingTickEvent e) {
        if (!Lobby.loaded) {
            return;
        }

        // player counts are DB queries: fetch them here on Cubing-Tick-Thread, then apply on the main thread,
        // which owns scoreboards and the holograms' listener sets
        Map<String, Hologram> countHolograms = EntityList.playerCountHolograms;
        Map<Hologram, Integer> counts = new java.util.HashMap<>();
        for (Map.Entry<String, Hologram> entry : countHolograms.entrySet()) {
            MinecraftServer server = MinecraftServer.getServer(entry.getKey());
            counts.put(entry.getValue(), server == null ? 0 : server.getPlayerCount());
        }
        int onlineCount = DatabaseData.onlineCount;

        Bukkit.getScheduler().runTask(Lobby.getPlugin(Lobby.class), () -> {
            for (SideBar sideBar : SideBar.getSidebars()) {
                Member member = Member.getMember(sideBar.player);
                if (member != null)
                    sideBar.setLineByScore(StringList.LobbySideBar[member.user.lang][3] + onlineCount, 6);
            }
            // skip holograms replaced by an /npcreload since this tick started
            if (countHolograms == EntityList.playerCountHolograms)
                counts.forEach((hologram, count) -> hologram.setName("§e§l" + count + " Players"));
        });
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
                        // bound parameter: the "name#1234" format allows quotes and commas
                        try (SQLConnection connection = Database.getCubing()) {
                            connection.prepare("UPDATE playersdata SET discord=? WHERE id=?")
                                    .setString(1, message)
                                    .setInt(2, member.user.id)
                                    .execute();
                        }
                        PlayerCache.invalidate(member.user.uuid);
                        member.discordName = message;
                        member.user.sendMessage("%lang_lobby_msg_discord_updated%", message);
                    } else member.user.sendMessage("%lang_lobby_msg_discord_invalid%");
                } catch (Exception exception) {
                    member.user.sendMessage("%lang_lobby_msg_discord_invalid%");
                }
                member.discord = false;
                member.user.setInput(false);
            } else if (message.isEmpty()) {
                e.setCancelled(true);
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

    @CubingEventHandler
    public void languageChangeEvent(LanguageChangeEvent e) {
        String[] sidebar = StringList.LobbySideBar[e.getNewLang()];
        SideBar bar = SideBar.getSidebar(e.getPlayer());
        if (bar != null)
            bar.setLineByScore(sidebar[2], 7).setLineByScore(sidebar[5], 4).setLineByScore(sidebar[8], 1);
    }
}
