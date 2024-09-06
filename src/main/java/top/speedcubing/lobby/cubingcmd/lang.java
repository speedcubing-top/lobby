package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import top.speedcubing.lobby.LanguageInventory;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.system.command.CubingCommand;

public class lang extends CubingCommand {
    public lang() {
        super("lang");
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        if (args.length == 0) {
            LanguageInventory.open(Member.getMember(sender));
        } else sender.sendMessage("/lang");
    }
}
