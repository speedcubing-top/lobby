package top.speedcubing.lobby;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.eventbus.CubingEventManager;
import top.speedcubing.lobby.BukkitCommands.easteregg;
import top.speedcubing.lobby.BukkitCommands.lang;
import top.speedcubing.lobby.BukkitCommands.terms;
import top.speedcubing.lobby.BukkitEventListener.EntityDamage;
import top.speedcubing.lobby.BukkitEventListener.InventoryClick;
import top.speedcubing.lobby.BukkitEventListener.PlayerDropItem;
import top.speedcubing.lobby.BukkitEventListener.PlayerInteract;
import top.speedcubing.lobby.BukkitEventListener.PlayerJoin;
import top.speedcubing.lobby.BukkitEventListener.PlayerMove;
import top.speedcubing.lobby.BukkitEventListener.PlayerQuit;
import top.speedcubing.lobby.List.EntityList;
import top.speedcubing.lobby.List.ItemStackList;
import top.speedcubing.server.utils.config;

public class Lobby extends JavaPlugin {
    public static Set<Integer> login = new HashSet<>();
    public static Lobby instance;

    public void onEnable() {
        instance = this;
        BadApple.init();
        MinecraftServer.getServer().getPropertyManager().setProperty("server-name", "lobby");
        Bukkit.getPluginCommand("terms").setExecutor(new terms());
        Bukkit.getPluginCommand("terms").setTabCompleter(new terms());
        Bukkit.getPluginCommand("lang").setExecutor(new lang());
        Bukkit.getPluginCommand("lang").setTabCompleter(new lang());
        Bukkit.getPluginCommand("easteregg").setExecutor(new easteregg());
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("world").setTime(6000);
        getServer().createWorld(new WorldCreator("agreement"));
        Bukkit.getWorld("agreement").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("agreement").setTime(6000);
//        if (!speedcubingServer.isBungeeOnlineMode) {
//            Bukkit.createWorld(new WorldCreator("login"));
//            Bukkit.getWorld("login").setDifficulty(Difficulty.PEACEFUL);
//            Bukkit.getWorld("login").setTime(6000);
//            Bukkit.getPluginCommand("login").setExecutor(new login());
//            Bukkit.getPluginCommand("login").setTabCompleter(new login());
//            Bukkit.getPluginCommand("register").setExecutor(new register());
//            Bukkit.getPluginCommand("register").setTabCompleter(new register());
//        }
        new ItemStackList().Load();
        new EntityList().Load();
        CubingEventManager.registerListeners(new ServerEvent());
        config.event.call();
    }
}
