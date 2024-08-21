package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.easteregg.JoinEasterEgg;
import top.speedcubing.server.system.command.CubingCommand;

public class easteregg extends CubingCommand {
    public easteregg() {
        super("easteregg");
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        if (sender instanceof Player)
            JoinEasterEgg.start((Player) sender);
    }
}
