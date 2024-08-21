package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;
import top.speedcubing.server.system.command.CubingCommand;

public class login extends CubingCommand {
    public login() {
        super();
    }

    @Override
    public void execute(CommandSender commandSender, String commands, String[] args) {
        if (((Player) commandSender).getWorld().getName().equals("login")) {
            String dbpassword = Member.getMember(commandSender).user.dbSelect("password").getString();
            if (dbpassword.isEmpty())
                commandSender.sendMessage("Please register.");
            else if (args.length == 1) {
                Player player = (Player) commandSender;
                if (dbpassword.equals(args[0])) {
                    commandSender.sendMessage("§6Successful Login!");
                    PlayerJoin.joinLobby(player, false);
                } else player.kickPlayer("Incorrect password.");
            } else commandSender.sendMessage("/login <password>");
        } else commandSender.sendMessage("You already logged in.");
    }
}
