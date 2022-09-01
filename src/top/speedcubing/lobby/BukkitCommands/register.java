package top.speedcubing.lobby.BukkitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.BukkitEventListener.PlayerJoin;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.speedcubingServer;

import java.util.ArrayList;
import java.util.List;

public class register implements CommandExecutor, TabExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (((Player) commandSender).getWorld().getName().equals("login")) {
            if (strings.length == 2) {
                int l = strings[0].length();
                if (l > 16)
                    commandSender.sendMessage("The password is too long.");
                else if (l < 8)
                    commandSender.sendMessage("The password is too short. You need at least 8 characters.");
                else if (strings[0].equals(strings[1])) {
                    speedcubingServer.connection.update("playersdata", "password='" + strings[0] + "'", "id=" + Member.getMember(commandSender).user.id);
                    commandSender.sendMessage("§6Successful Register!");
                    PlayerJoin.joinLobby((Player) commandSender, true);
                } else commandSender.sendMessage("The password and confirm password do not match.");
            } else commandSender.sendMessage("/register <password> <confirm password>");
        } else commandSender.sendMessage("You already registered.");
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
