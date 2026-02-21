package top.speedcubing.lobby.configuration;

import java.util.HashSet;
import java.util.Set;
import top.speedcubing.common.configuration.ServerConfig;
import top.speedcubing.common.events.ConfigReloadEvent;
import top.speedcubing.lib.eventbus.CubingEventHandler;
import top.speedcubing.lobby.Lobby;
import top.speedcubing.lobby.list.ItemStackList;

/**
 * Handles server-wide config reload events and manages the join crash list.
 */
public class Configuration {

    public static final Set<String> joinCrash = new HashSet<>();

    public static void reload() {
        Lobby.getPlugin(Lobby.class).getLogger().info("loading config");
        joinCrash.clear();
        ServerConfig.getConfig().get("onlinecrash").getAsJsonArray().forEach(a -> joinCrash.add(a.getAsString().toLowerCase()));

        // Reload lobby config (NPCs, holograms, navigator)
        LobbyConfig.reload();
        ItemStackList.buildNavigator();
    }

    @CubingEventHandler(priority = 30)
    public void configReloadEvent(ConfigReloadEvent e) {
        reload();
    }
}
