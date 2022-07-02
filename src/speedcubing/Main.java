package speedcubing;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import speedcubing.BukkitCommands.lang;
import speedcubing.BukkitCommands.login;
import speedcubing.BukkitCommands.register;
import speedcubing.BukkitEventListener.*;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.lib.eventbus.LibEventManager;
import speedcubing.server.speedcubingServer;

public class Main extends JavaPlugin {;

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
        if (!speedcubingServer.isBungeeOnlineMode) {
            Bukkit.createWorld(new WorldCreator("login"));
            Bukkit.getWorld("login").setDifficulty(Difficulty.PEACEFUL);
            Bukkit.getWorld("login").setTime(6000);
            Bukkit.getPluginCommand("login").setExecutor(new login());
            Bukkit.getPluginCommand("login").setTabCompleter(new login());
            Bukkit.getPluginCommand("register").setExecutor(new register());
            Bukkit.getPluginCommand("register").setTabCompleter(new register());
        }
        new ItemStackList().Load();
        new EntityList().Load();
        LibEventManager.registerListeners(new ServerEvent());
    }
}
