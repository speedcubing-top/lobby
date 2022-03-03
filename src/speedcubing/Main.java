package speedcubing;

import cubingserver.connection.SocketUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import speedcubing.All.SocketServer;
import speedcubing.BukkitCommands.*;
import speedcubing.BukkitEventListener.*;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;

import java.util.*;

public class Main extends JavaPlugin {

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
        new SocketServer().Load();
    }
}
