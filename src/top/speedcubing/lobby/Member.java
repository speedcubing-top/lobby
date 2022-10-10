package top.speedcubing.lobby;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.server.libs.User;

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
        haveToLogin = Lobby.login.contains(user.id);
        Lobby.login.remove(user.id);
        messagetoggled = user.dbSelect("messagable").getBoolean();
        users.put(user.id, this);
    }
}
