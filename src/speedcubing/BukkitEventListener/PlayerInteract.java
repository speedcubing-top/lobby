package speedcubing.BukkitEventListener;

import speedcubing.server.libs.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import speedcubing.List.ItemStackList;

public class PlayerInteract implements Listener {

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        switch (player.getItemInHand().getType()) {
            case COMPASS:
                player.openInventory(ItemStackList.navigator[User.getUser(player.getUniqueId()).lang]);
                break;
            case REDSTONE_COMPARATOR:
                player.openInventory(ItemStackList.settings[User.getUser(player.getUniqueId()).lang]);
                break;
        }
        if (e.getClickedBlock() != null && player.getGameMode() == GameMode.ADVENTURE)
            e.setCancelled(true);
    }
}