package speedcubing;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import speedcubing.lib.utils.SQL.SQLUtils;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Member extends User {
    public static Map<Integer, Member> users = new HashMap<>();

    public static Member getMember(CommandSender sender) {
        return getMember((Player) sender);
    }

    public static Member getMember(Player player) {
        return getMember(User.getUser(player).id);
    }

    public boolean haveToLogin;
    public boolean discord;
    public boolean messagetoggled;

    public static Member getMember(int id) {
        return users.get(id);
    }

    public Member(UUID uuid) {
        super(uuid);
        messagetoggled = SQLUtils.getBoolean(speedcubingServer.connection.select("playersdata", "messagable", "id=" + id));
        users.put(id, this);
    }
}
