package top.speedcubing.lobby;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.server.player.User;

public class Member {
    public static Map<Integer, Member> users = new HashMap<>();

    public static Member getMember(CommandSender sender) {
        return getMember(User.getUser(sender).id);
    }

    public static Member getMember(int id) {
        return users.get(id);
    }

    public final User user;

    public boolean discord;
    // cached playersdata.discord; loaded at login, kept in sync by the lobby's own set (packet thread) / remove
    public volatile String discordName;
    public int messagetoggled;

    public boolean agree;
    // /fly is on (staff and champ); everyone else gets double jump
    public boolean flying;
    // session only: hide every other player in the lobby
    public boolean hidePlayers;
    public long lastVisibilityToggle;
    public long lastEnderButt;
    // until then (ms) position packets keep the client's camera rotation; netty thread reads it
    public volatile long keepCameraUntil;
    public long lastJumpPad;

    public Member(Player player) {
        this.user = User.getUser(player);
        agree = user.data.getBoolean("agreement");
        messagetoggled = user.data.getInt("messagable");
        String discordName = user.data.getString("discord");
        this.discordName = discordName == null ? "" : discordName;
        users.put(user.id, this);
    }
}
