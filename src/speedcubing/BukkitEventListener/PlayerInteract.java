package speedcubing.BukkitEventListener;

import cubingserver.libs.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import speedcubing.List.ItemStackList;
import speedcubing.Main;

public class PlayerInteract implements Listener {

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        switch (player.getItemInHand().getType()) {
            case COMPASS:
                player.openInventory(ItemStackList.navigator[PlayerData.getLang(player.getUniqueId())]);
                break;
            case REDSTONE_COMPARATOR:
                player.openInventory(ItemStackList.settings[PlayerData.getLang(player.getUniqueId())]);
                break;
        }
        if (e.getClickedBlock() != null && player.getGameMode() == GameMode.ADVENTURE)
            e.setCancelled(true);
    }
}