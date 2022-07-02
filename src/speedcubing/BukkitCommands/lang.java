package speedcubing.BukkitCommands;

import speedcubing.Member;
import speedcubing.server.libs.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import speedcubing.List.ItemStackList;

import java.util.ArrayList;
import java.util.List;

public class lang implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0)
            ((Player) commandSender).openInventory(ItemStackList.Lang[Member.getMember(commandSender).lang]);
         else commandSender.sendMessage("/lang");
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
