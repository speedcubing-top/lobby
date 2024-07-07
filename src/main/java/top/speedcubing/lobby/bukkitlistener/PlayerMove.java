package top.speedcubing.lobby.bukkitlistener;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import top.speedcubing.lib.bukkit.inventory.BookBuilder;
import top.speedcubing.lobby.bukkitcmd.terms;

public class PlayerMove implements Listener {

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.ADVENTURE) {
            if (player.getWorld() == Bukkit.getWorlds().get(0)) {
                Location to = e.getTo();
                if (Math.abs(to.getX()) > 501 || Math.abs(to.getZ()) > 501)
                    player.teleport(e.getFrom());
                if (to.getY() <= 50) {
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


