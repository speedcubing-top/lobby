package top.speedcubing.lobby.BukkitEventListener;

import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class EntityDamage implements Listener {
    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && ((Player) e.getEntity()).getGameMode() == GameMode.ADVENTURE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame && e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (player.getGameMode() == GameMode.ADVENTURE) {
                e.setCancelled(true);
            }
        } else if (e.getEntity() instanceof ItemFrame && e.getDamager() instanceof Arrow || e.getDamager() instanceof TNTPrimed || e.getDamager() instanceof Fireball || e.getDamager() instanceof FishHook
                || e.getDamager() instanceof Snowball || e.getDamager() instanceof Egg) {
            e.setCancelled(true);
        }
    }
}

