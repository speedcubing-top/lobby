package speedcubing.BukkitEventListener;


import cubing.spigot.lib.bukkit.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.ADVENTURE) {
            Location to = e.getTo();
            if (Math.abs(to.getX()) > 101 || Math.abs(to.getZ()) > 101)
                player.teleport(e.getFrom());
            if (to.getY() <= 80) {
                player.closeInventory();
                PlayerUtils.teleport(((CraftPlayer) player).getHandle().playerConnection, 0.5, 100, 0.5, 0, 0);
            }
        }
    }
}


