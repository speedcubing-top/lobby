package speedcubing.BukkitCommands;

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
        if (strings.length == 0) {
            Player player = (Player) commandSender;
            player.openInventory(ItemStackList.Lang[User.getLang(player.getUniqueId())]);
        } else commandSender.sendMessage("/lang");
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[]
            strings) {
        return new ArrayList<>();
    }
}
