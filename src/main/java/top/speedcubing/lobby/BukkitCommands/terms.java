package top.speedcubing.lobby.BukkitCommands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.bukkit.inventory.BookBuilder;
import top.speedcubing.lib.minecraft.text.*;
import top.speedcubing.lobby.*;
import top.speedcubing.lobby.BukkitEventListener.PlayerJoin;
import top.speedcubing.server.lang.GlobalString;

import java.util.*;

public class terms implements CommandExecutor, TabCompleter {

    public static ItemStack book = new BookBuilder("a", "b")
            .addPage(new TextBuilder()
                    .str("§0Welcome to\n§6speedcubing.top\n\n§0Before you can play on this server, you have to accept our: \n- ")
                    .both("§8[§5rules§8]", ClickEvent.openURL("https://speedcubing.top/serverrules"), HoverEvent.showText("§eclick here to open the §5rules §epage."))
                    .str("§0\n- ")
                    .both("§8[§5privacy policy§8]", ClickEvent.openURL("https://speedcubing.top/privacypolicy"), HoverEvent.showText("§eclick here to open the §5privacy and policy §epage."))
                    .str("\n\n")
                    .both("§8[§2Accept§8]", ClickEvent.runCommand("/terms accept"), HoverEvent.showText("§eclick here to §2accept§e."))
                    .str(" ")
                    .both("§8[§4Deny§8]", ClickEvent.runCommand("/terms deny"), HoverEvent.showText("§eclick here to §4deny§e.")).toBungee()).build();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            Member.getMember(commandSender).user.sendLangMessage(GlobalString.terms);
        } else
            switch (strings[0]) {
                case "accept":
                    Member member = Member.getMember(commandSender);
                    member.user.dbUpdate("agreement=1");
                    member.agree = true;
                    PlayerJoin.joinLobby((Player) commandSender, false);
                    break;
                case "deny":
                    ((Player) commandSender).kickPlayer("term.deny");
                    break;
                default:
                    Member.getMember(commandSender).user.sendLangMessage(GlobalString.UnknownCommand);
                    break;
            }
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
