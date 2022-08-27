package speedcubing;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import speedcubing.lib.utils.SQL.SQLUtils;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

import java.util.HashMap;
import java.util.Map;

public class Member {
    public static Map<Integer, Member> users = new HashMap<>();

    public static Member getMember(CommandSender sender) {
        return getMember(User.getUser(sender).id);
    }

    public static Member getMember(int id) {
        return users.get(id);
    }

    public final User user;

    public boolean haveToLogin;
    public boolean discord;
    public boolean messagetoggled;

    public Member(Player player) {
        this.user = User.getUser(player);
        haveToLogin = Main.login.contains(user.id);
        Main.login.remove(user.id);
        messagetoggled = SQLUtils.getBoolean(speedcubingServer.connection.select("playersdata", "messagable", "id=" + user.id));
        users.put(user.id, this);
    }
}
