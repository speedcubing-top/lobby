package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;
import top.speedcubing.server.system.command.CubingCommand;

public class register extends CubingCommand {
    public register() {
        super();
    }

    @Override
    public void execute(CommandSender commandSender, String command, String[] strings) {
        Member member = Member.getMember(commandSender);
        if (((Player) commandSender).getWorld().getName().equals("login")) {
            if (strings.length == 2) {
                int l = strings[0].length();
                if (l > 16)
                    member.user.sendMessage("%lang_lobby_register_too_long%");
                else if (l < 8)
                    member.user.sendMessage("%lang_lobby_register_too_short%");
                else if (strings[0].equals(strings[1])) {
                    member.user.dbUpdate("password='" + strings[0] + "'");
                    member.user.sendMessage("%lang_lobby_register_success%");
                    PlayerJoin.joinLobby((Player) commandSender, false);
                } else member.user.sendMessage("%lang_lobby_register_mismatch%");
            } else member.user.sendMessage("%lang_lobby_usage_register%");
        } else member.user.sendMessage("%lang_lobby_register_already%");
    }
}
