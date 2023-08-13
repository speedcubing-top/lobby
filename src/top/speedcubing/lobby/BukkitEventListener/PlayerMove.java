package top.speedcubing.lobby.BukkitEventListener;


import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerMoveEvent;
import top.speedcubing.lib.bukkit.inventory.BookBuilder;
import top.speedcubing.lobby.BukkitCommands.terms;

public class PlayerMove implements Listener {

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.ADVENTURE) {
            if (player.getWorld().getName().equals("world")) {
                Location to = e.getTo();
                if (Math.abs(to.getX()) > 501 || Math.abs(to.getZ()) > 501)
                    player.teleport(e.getFrom());
                if (to.getY() <= 80) {
                    player.closeInventory();
                    player.teleport(new Location(player.getWorld(), 0.5, 100, 0.5, 0, 0));
                }
            } else {
                BookBuilder.openBook(terms.book, player);
                player.teleport(e.getFrom());
            }
        }
    }
}


