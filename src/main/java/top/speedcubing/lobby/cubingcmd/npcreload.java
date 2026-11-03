package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import top.speedcubing.server.system.command.CubingCommand;
import top.speedcubing.lobby.list.EntityList;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.list.StringList;
import top.speedcubing.common.text.Msg;

/**
 * Reloads NPCs and holograms from config.
 * Usage: /npcreload
 */
public class npcreload extends CubingCommand {

    public npcreload() {
        super("npcreload");
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        Member member = Member.getMember(sender);
        
        if (!member.user.hasPermission("lobby.npcreload")) {
            sender.sendMessage(StringList.PREFIX + Msg.NO_PERMISSION);
            return;
        }

        sender.sendMessage(StringList.PREFIX + Msg.INFO + "Reloading NPCs and holograms...");
        
        try {
            // Clean up existing entities
            EntityList.cleanup();
            
            // Reinitialize from config
            new EntityList().init();
            
            sender.sendMessage(StringList.PREFIX + Msg.SUCCESS + "Successfully reloaded NPCs and holograms!");
        } catch (Exception e) {
            sender.sendMessage(StringList.PREFIX + Msg.ERROR + "Error reloading NPCs: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
