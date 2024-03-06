package top.speedcubing.lobby.BukkitCommands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.bukkit.inventory.BookBuilder;
import top.speedcubing.lib.minecraft.text.TextBuilder;
import top.speedcubing.lib.minecraft.text.TextClickEvent;
import top.speedcubing.lib.minecraft.text.TextHoverEvent;
import top.speedcubing.lobby.BukkitEventListener.PlayerJoin;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.lang.GlobalString;

public class terms implements CommandExecutor, TabCompleter {

    public static ItemStack book = new BookBuilder("a", "b")
            .addPage(new TextBuilder()
                    .str("§0Welcome to\n§6speedcubing.top\n\n§0Before you can play on this server, you have to accept our: \n- ")
                    .both("§8[§5rules§8]", TextClickEvent.openURL("https://speedcubing.top/serverrules"), TextHoverEvent.showText("§eclick here to open the §5rules §epage."))
                    .str("§0\n- ")
                    .both("§8[§5privacy policy§8]", TextClickEvent.openURL("https://speedcubing.top/privacypolicy"), TextHoverEvent.showText("§eclick here to open the §5privacy and policy §epage."))
                    .str("\n\n")
                    .both("§8[§2Accept§8]", TextClickEvent.runCommand("/terms accept"), TextHoverEvent.showText("§eclick here to §2accept§e."))
                    .str(" ")
                    .both("§8[§4Deny§8]", TextClickEvent.runCommand("/terms deny"), TextHoverEvent.showText("§eclick here to §4deny§e.")).toBungee()).build();

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
