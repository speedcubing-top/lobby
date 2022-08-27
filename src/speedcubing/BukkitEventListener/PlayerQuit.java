package speedcubing.BukkitEventListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import speedcubing.Member;

public class PlayerQuit implements Listener {
    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Member.users.remove(Member.getMember(e.getPlayer()).user.id);
    }
}
