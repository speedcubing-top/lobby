package top.speedcubing.lobby.bukkitlistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import top.speedcubing.lobby.Member;

public class PlayerQuit implements Listener {
    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Member member = Member.getMember(e.getPlayer());
        if (member != null)
            Member.users.remove(member.user.id);
    }
}
