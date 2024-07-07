package top.speedcubing.lobby.easteregg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.speedcubing.lobby.Lobby;

public class JoinEasterEgg {
    public static void start(Player player) {
        player.teleport(new Location(player.getWorld(), 71, 66, -51));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 255, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 255, true));
        player.sendMessage("What...");
        waitForTicks(100, player);
    }

    private static void waitForTicks(int ticks, Player player) {
        Bukkit.getScheduler().runTaskLater(Lobby.getPlugin(Lobby.class), () ->
                        player.sendMessage("what happened?\n" + "why am i here?"),
                ticks);
    }
}
