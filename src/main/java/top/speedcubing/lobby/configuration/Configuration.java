package top.speedcubing.lobby.configuration;

import java.util.HashSet;
import java.util.Set;
import top.speedcubing.common.configuration.ServerConfig;
import top.speedcubing.common.events.ConfigReloadEvent;
import top.speedcubing.lib.eventbus.CubingEventHandler;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;

public class Configuration {

    public static final Set<String> joinCrash = new HashSet<>();

    public static void reload() {
        joinCrash.clear();
        ServerConfig.config.get("onlinecrash").getAsJsonArray().forEach(a -> joinCrash.add(a.getAsString().toLowerCase()));
    }

    @CubingEventHandler(priority = 30)
    public void configReloadEvent(ConfigReloadEvent e) {
    }
}
