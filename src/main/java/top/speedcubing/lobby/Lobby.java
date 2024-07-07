package top.speedcubing.lobby;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.eventbus.CubingEventManager;
import top.speedcubing.lobby.bukkitcmd.easteregg;
import top.speedcubing.lobby.bukkitcmd.lang;
import top.speedcubing.lobby.bukkitcmd.terms;
import top.speedcubing.lobby.bukkitlistener.EntityDamage;
import top.speedcubing.lobby.bukkitlistener.InventoryClick;
import top.speedcubing.lobby.bukkitlistener.PlayerDropItem;
import top.speedcubing.lobby.bukkitlistener.PlayerInteract;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;
import top.speedcubing.lobby.bukkitlistener.PlayerMove;
import top.speedcubing.lobby.bukkitlistener.PlayerQuit;
import top.speedcubing.lobby.gamble.BaccaratCore;
import top.speedcubing.lobby.List.EntityList;
import top.speedcubing.lobby.List.ItemStackList;

import java.util.HashSet;
import java.util.Set;

public class Lobby extends JavaPlugin {
    public static Set<Integer> login = new HashSet<>();

    public void onEnable() {
        MinecraftServer.getServer().getPropertyManager().setProperty("server-name", "lobby");
        CubingEventManager.registerListeners(new ServerEvent());

        BadApple.init();
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
        Bukkit.getPluginManager().registerEvents(new BaccaratCore(),this);
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
    }
}
