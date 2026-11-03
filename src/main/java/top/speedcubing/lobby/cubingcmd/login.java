package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;
import top.speedcubing.server.lang.Lang;
import top.speedcubing.server.system.command.CubingCommand;

public class login extends CubingCommand {
    public login() {
        super();
    }

    @Override
    public void execute(CommandSender commandSender, String commands, String[] args) {
        Member member = Member.getMember(commandSender);
        if (((Player) commandSender).getWorld().getName().equals("login")) {
            String dbpassword = member.user.dbSelect("password").getString(0);
            if (dbpassword.isEmpty())
                member.user.sendMessage("%lang_lobby_login_register_first%");
            else if (args.length == 1) {
                Player player = (Player) commandSender;
                if (dbpassword.equals(args[0])) {
                    member.user.sendMessage("%lang_lobby_login_success%");
                    PlayerJoin.joinLobby(player, false);
                } else player.kickPlayer(Lang.of("%lang_lobby_login_wrong_password%").getString(member.user.lang));
            } else member.user.sendMessage("%lang_lobby_usage_login%");
        } else member.user.sendMessage("%lang_lobby_login_already%");
    }
}
