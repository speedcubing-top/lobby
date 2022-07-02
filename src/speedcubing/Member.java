package speedcubing;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import speedcubing.server.libs.User;

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

    public static Member getMember(int id) {
        return users.get(id);
    }

    public Member(UUID uuid) {
        super(uuid);
        users.put(id, this);
    }
}
