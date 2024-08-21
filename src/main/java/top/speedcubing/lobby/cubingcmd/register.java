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
        if (((Player) commandSender).getWorld().getName().equals("login")) {
            if (strings.length == 2) {
                int l = strings[0].length();
                if (l > 16)
                    commandSender.sendMessage("The password is too long.");
                else if (l < 8)
                    commandSender.sendMessage("The password is too short. You need at least 8 characters.");
                else if (strings[0].equals(strings[1])) {
                    Member.getMember(commandSender).user.dbUpdate("password='" + strings[0] + "'");
                    commandSender.sendMessage("§6Successful Register!");
                    PlayerJoin.joinLobby((Player) commandSender, false);
                } else commandSender.sendMessage("The password and confirm password do not match.");
            } else commandSender.sendMessage("/register <password> <confirm password>");
        } else commandSender.sendMessage("You already registered.");
    }
}
