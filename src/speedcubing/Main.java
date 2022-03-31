package speedcubing;

import cubing.lib.bukkit.Event.ServerEventManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;
import speedcubing.BukkitCommands.lang;
import speedcubing.BukkitEventListener.*;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Main extends JavaPlugin {

    public static Set<UUID> discord = new HashSet<>();

    public void onEnable() {
        Bukkit.getPluginCommand("lang").setExecutor(new lang());
        Bukkit.getPluginCommand("lang").setTabCompleter(new lang());
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("world").setTime(6000);
        new ItemStackList().Load();
        new EntityList().Load();
        ServerEventManager.registerListeners(new ServerEvent());
    }
}
