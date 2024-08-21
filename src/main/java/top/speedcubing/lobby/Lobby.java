package top.speedcubing.lobby;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import top.speedcubing.common.events.ConfigReloadEvent;
import top.speedcubing.lib.eventbus.CubingEventManager;
import top.speedcubing.lobby.bukkitlistener.EntityDamage;
import top.speedcubing.lobby.bukkitlistener.InventoryClick;
import top.speedcubing.lobby.bukkitlistener.PlayerDropItem;
import top.speedcubing.lobby.bukkitlistener.PlayerInteract;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;
import top.speedcubing.lobby.bukkitlistener.PlayerMove;
import top.speedcubing.lobby.bukkitlistener.PlayerQuit;
import top.speedcubing.lobby.gamble.BaccaratCore;
import top.speedcubing.lobby.list.EntityList;
import top.speedcubing.lobby.list.ItemStackList;
import top.speedcubing.server.speedcubingServer;
import top.speedcubing.server.system.command.CubingCommandLoader;

public class Lobby extends JavaPlugin {
    public static Set<Integer> login = new HashSet<>();
    public static Vector offset;

    public void onEnable() {
        MinecraftServer.getServer().getPropertyManager().setProperty("server-name", "lobby");
        CubingEventManager.registerListeners(new ServerEvent());
        new ConfigReloadEvent().call();

        CubingCommandLoader.loadCommands("top.speedcubing.lobby.cubingcmd", Lobby.class);

        registerListeners();

        //BadApple.init();

        Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("world").setTime(6000);
        getServer().createWorld(new WorldCreator("agreement"));
        Bukkit.getWorld("agreement").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("agreement").setTime(6000);

/*        if (!speedcubingServer.isBungeeOnlineMode) {
            Bukkit.createWorld(new WorldCreator("login"));
            Bukkit.getWorld("login").setDifficulty(Difficulty.PEACEFUL);
            Bukkit.getWorld("login").setTime(6000);
            Bukkit.getPluginCommand("login").setExecutor(new login());
            Bukkit.getPluginCommand("login").setTabCompleter(new login());
            Bukkit.getPluginCommand("register").setExecutor(new register());
            Bukkit.getPluginCommand("register").setTabCompleter(new register());
        } */

        new ItemStackList().Load();
        new EntityList().Load();
    }

    private void registerListeners() {
        speedcubingServer.registerListeners(
                new EntityDamage(),
                new PlayerJoin(),
                new InventoryClick(),
                new PlayerDropItem(),
                new PlayerInteract(),
                new PlayerMove(),
                new PlayerQuit(),
                new BaccaratCore());
    }
}
