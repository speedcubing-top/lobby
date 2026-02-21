package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import top.speedcubing.server.system.command.CubingCommand;
import top.speedcubing.lobby.list.EntityList;
import top.speedcubing.lobby.Member;

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
            sender.sendMessage("§cYou don't have permission to use this command.");
            return;
        }

        sender.sendMessage("§eReloading NPCs and holograms...");
        
        try {
            // Clean up existing entities
            EntityList.cleanup();
            
            // Reinitialize from config
            new EntityList().init();
            
            sender.sendMessage("§aSuccessfully reloaded NPCs and holograms!");
        } catch (Exception e) {
            sender.sendMessage("§cError reloading NPCs: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
