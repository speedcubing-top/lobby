package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.list.ItemStackList;
import top.speedcubing.server.system.command.CubingCommand;

public class lang extends CubingCommand {
    public lang() {
        super("lang");
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        if (args.length == 0)
            Member.getMember(sender).user.openLangInventory(ItemStackList.Lang);
        else sender.sendMessage("/lang");
    }
}
