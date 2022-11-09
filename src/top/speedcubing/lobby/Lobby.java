package top.speedcubing.lobby;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.eventbus.LibEventManager;
import top.speedcubing.lobby.BukkitCommands.lang;
import top.speedcubing.lobby.BukkitCommands.login;
import top.speedcubing.lobby.BukkitCommands.register;
import top.speedcubing.lobby.BukkitEventListener.*;
import top.speedcubing.lobby.List.EntityList;
import top.speedcubing.lobby.List.ItemStackList;
import top.speedcubing.server.config;
import top.speedcubing.server.speedcubingServer;

import java.util.HashSet;
import java.util.Set;

public class Lobby extends JavaPlugin {
    public static Set<Integer> login = new HashSet<>();

    public void onEnable() {
        MinecraftServer.getServer().getPropertyManager().setProperty("server-name", "lobby");
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
        config.event.call();
    }
}
