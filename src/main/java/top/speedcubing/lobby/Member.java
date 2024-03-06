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

    public boolean haveToLogin;
    public boolean discord;
    public int messagetoggled;

    public boolean agree;

    public Member(Player player) {
        this.user = User.getUser(player);
        haveToLogin = Lobby.login.contains(user.id);
        Lobby.login.remove(user.id);
        String[] s = user.dbSelect("agreement,messagable").getStringArray();
        agree = s[0].equals("1");
        messagetoggled = Integer.parseInt(s[1]);
        users.put(user.id, this);
    }
}
