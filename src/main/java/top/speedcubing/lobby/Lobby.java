package top.speedcubing.lobby;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import top.speedcubing.lib.eventbus.CubingEventManager;
import top.speedcubing.lobby.bukkitlistener.EntityDamage;
import top.speedcubing.lobby.bukkitlistener.InventoryClick;
import top.speedcubing.lobby.bukkitlistener.PlayerDropItem;
import top.speedcubing.lobby.bukkitlistener.PlayerInteract;
import top.speedcubing.lobby.bukkitlistener.LobbyMovement;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;
import top.speedcubing.lobby.bukkitlistener.PlayerMove;
import top.speedcubing.lobby.bukkitlistener.PlayerQuit;
import top.speedcubing.lobby.configuration.Configuration;
import top.speedcubing.lobby.configuration.LobbyConfig;
import top.speedcubing.lobby.list.EntityList;
import top.speedcubing.lobby.list.ItemStackList;
import top.speedcubing.server.speedcubingServer;
import top.speedcubing.server.system.command.CubingCommandLoader;
import top.speedcubing.server.utils.MapManager;

public class Lobby extends JavaPlugin {
    public static Vector offset;
    public static boolean loaded;

    public void onEnable() {
        MinecraftServer.getServer().getPropertyManager().setProperty("server-name", "lobby");
        CubingEventManager.registerListeners(new ServerEvent(), new Configuration());
        CubingCommandLoader.loadCommands("top.speedcubing.lobby.cubingcmd", Lobby.class);
        Configuration.reload();
        registerListeners();
        registerWorlds();

        new EntityList().init();
        ItemStackList.buildNavigator();
        loaded = true;
    }

    private void registerListeners() {
        speedcubingServer.registerListeners(
                new EntityDamage(),
                new PlayerJoin(),
                new InventoryClick(),
                new PlayerDropItem(),
                new PlayerInteract(),
                new LobbyMovement(),
                new PlayerMove(),
                new PlayerQuit());
    }

    private void registerWorlds() {
        try {
            MapManager.install("lobby_wtc", "world", Difficulty.PEACEFUL, 6000, false);
            MapManager.install("agreement", "agreement", Difficulty.PEACEFUL, 6000, false);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
    }

}
