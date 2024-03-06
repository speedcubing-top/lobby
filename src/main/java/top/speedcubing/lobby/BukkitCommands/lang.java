package top.speedcubing.lobby.BukkitCommands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.List.ItemStackList;
import top.speedcubing.lobby.Member;

public class lang implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0)
            ((Player) commandSender).openInventory(ItemStackList.Lang[Member.getMember(commandSender).user.lang]);
        else commandSender.sendMessage("/lang");
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
