package top.speedcubing.lobby.BukkitCommands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.List.ItemStackList;
import top.speedcubing.lobby.Member;

import java.util.*;

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
