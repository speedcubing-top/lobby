package top.speedcubing.lobby.bukkitlistener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void InventoryClickEvent(InventoryClickEvent e) {
        if (e.getWhoClicked().getGameMode() == GameMode.ADVENTURE)
            e.setCancelled(true);
    }
}