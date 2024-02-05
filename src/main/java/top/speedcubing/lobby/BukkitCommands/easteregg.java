package top.speedcubing.lobby.BukkitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.easteregg.JoinEasterEgg;

public class easteregg implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player)
            JoinEasterEgg.start((Player) commandSender);
        return true;
    }
}
