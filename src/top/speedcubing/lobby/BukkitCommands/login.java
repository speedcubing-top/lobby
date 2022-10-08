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

public class login implements CommandExecutor, TabExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (((Player) commandSender).getWorld().getName().equals("login")) {
            String dbpassword = speedcubingServer.connection.select("password").from("playersdata").where("id=" + Member.getMember(commandSender).user.id).getString();
            if (dbpassword.equals(""))
                commandSender.sendMessage("Please register.");
            else if (strings.length == 1) {
                Player player = (Player) commandSender;
                if (dbpassword.equals(strings[0])) {
                    commandSender.sendMessage("§6Successful Login!");
                    PlayerJoin.joinLobby(player, true);
                } else player.kickPlayer("Incorrect password.");
            } else commandSender.sendMessage("/login <password>");
        } else commandSender.sendMessage("You already logged in.");
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
