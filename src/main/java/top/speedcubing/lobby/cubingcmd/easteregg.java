package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.easteregg.JoinEasterEgg;
import top.speedcubing.common.text.Msg;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.list.StringList;
import top.speedcubing.server.system.command.CubingCommand;

public class easteregg extends CubingCommand {
    public easteregg() {
        super("easteregg");
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        if (!(sender instanceof Player))
            return;
        if (!Member.getMember(sender).user.hasPermission("lobby.easteregg")) {
            sender.sendMessage(StringList.PREFIX + Msg.NO_PERMISSION);
            return;
        }
        JoinEasterEgg.start((Player) sender);
    }
}
