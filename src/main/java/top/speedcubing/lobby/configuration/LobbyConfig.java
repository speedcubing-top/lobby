package top.speedcubing.lobby.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;
import top.speedcubing.lobby.Lobby;

/**
 * Loads and parses config.yml into typed data structures.
 * Handles NPCs (with optional paired holograms), navigator, and spawn/origin settings.
 */
public class LobbyConfig {

    private static YamlConfiguration config;

    /** Offset vector from origin to spawn, applied to all entity positions. */
    public static Vector offset;

    /** The lobby spawn location. */
    public static Location spawnLocation;

    /** All NPC entries loaded from config (each may include paired holograms). */
    public static List<NpcEntry> npcEntries = new ArrayList<>();

    /** Navigator inventory configuration. */
    public static NavigatorConfig navigatorConfig;

    /** Default Y offset from NPC position for the hologram name line. */
    private static final double DEFAULT_HOLOGRAM_NAME_OFFSET = 1.85;

    /** Default Y offset from NPC position for the hologram count line. */
    private static final double DEFAULT_HOLOGRAM_COUNT_OFFSET = 2.15;

    /**
     * Loads the config file from disk (copies default if missing) and parses it.
     */
    public static void load() {
        File dataFolder = Lobby.getPlugin(Lobby.class).getDataFolder();
        File configFile = new File(dataFolder, "config.yml");

        if (!configFile.exists()) {
            dataFolder.mkdirs();
            try (InputStream in = Lobby.getPlugin(Lobby.class).getResource("config.yml")) {
                if (in != null) {
                    Files.copy(in, configFile.toPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        parseConfig();
    }

    /**
     * Reloads the config from disk.
     */
    public static void reload() {
        load();
    }

    private static void parseConfig() {
        parseSpawnAndOffset();
        parseNpcs();
        parseNavigator();
    }

    // ---- Parsing helpers ----

    private static void parseSpawnAndOffset() {
        double origX = config.getDouble("origin.x");
        double origY = config.getDouble("origin.y");
        double origZ = config.getDouble("origin.z");

        double spawnX = config.getDouble("spawn.x");
        double spawnY = config.getDouble("spawn.y");
        double spawnZ = config.getDouble("spawn.z");
        String spawnWorld = config.getString("spawn.world", "world");

        spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ);
        offset = new Vector(spawnX - origX, spawnY - origY, spawnZ - origZ);
    }

    private static void parseNpcs() {
        npcEntries.clear();
        List<Map<?, ?>> npcs = config.getMapList("npcs");

        for (Map<?, ?> npc : npcs) {
            NpcEntry entry = new NpcEntry();
            entry.id = (String) npc.get("id");
            entry.skinTextures = (String) npc.get("skin-textures");
            entry.skinSignature = (String) npc.get("skin-signature");
            entry.bodyYaw = ((Number) npc.get("body-yaw")).intValue();
            entry.clickAction = (String) npc.get("click-action");
            entry.clickTarget = (String) npc.get("click-target");

            // NPC location
            Map<?, ?> loc = (Map<?, ?>) npc.get("location");
            entry.x = ((Number) loc.get("x")).doubleValue();
            entry.y = ((Number) loc.get("y")).doubleValue();
            entry.z = ((Number) loc.get("z")).doubleValue();
            entry.yaw = ((Number) loc.get("yaw")).floatValue();
            entry.pitch = ((Number) loc.get("pitch")).floatValue();

            // Optional paired hologram (2-line nametag above the NPC)
            if (npc.containsKey("hologram-name")) {
                entry.hasHologram = true;
                entry.hologramName = (String) npc.get("hologram-name");
                entry.hologramServer = npc.containsKey("hologram-server") ? (String) npc.get("hologram-server") : "";
                entry.hologramMaintenance = npc.containsKey("hologram-maintenance") && (Boolean) npc.get("hologram-maintenance");
                entry.hologramNameOffset = npc.containsKey("hologram-name-offset")
                        ? ((Number) npc.get("hologram-name-offset")).doubleValue()
                        : DEFAULT_HOLOGRAM_NAME_OFFSET;
                entry.hologramCountOffset = npc.containsKey("hologram-count-offset")
                        ? ((Number) npc.get("hologram-count-offset")).doubleValue()
                        : DEFAULT_HOLOGRAM_COUNT_OFFSET;
            }

            npcEntries.add(entry);
        }
    }

    private static void parseNavigator() {
        navigatorConfig = new NavigatorConfig();
        ConfigurationSection navSection = config.getConfigurationSection("navigator");
        if (navSection == null) return;

        navigatorConfig.title = navSection.getString("title", "%lang_lobby_item_navigator%");
        navigatorConfig.size = navSection.getInt("size", 45);
        navigatorConfig.items.clear();

        List<Map<?, ?>> items = navSection.getMapList("items");
        for (Map<?, ?> item : items) {
            NavigatorItem navItem = new NavigatorItem();
            navItem.slot = ((Number) item.get("slot")).intValue();
            navItem.material = (String) item.get("material");
            navItem.name = (String) item.get("name");
            navItem.glow = item.containsKey("glow") && (Boolean) item.get("glow");
            navItem.hideAttributes = item.containsKey("hide-attributes") && (Boolean) item.get("hide-attributes");
            navItem.clickAction = (String) item.get("click-action");
            navItem.clickTarget = item.containsKey("click-target") ? (String) item.get("click-target") : "";

            if ("teleport".equals(navItem.clickAction)) {
                navItem.teleportWorld = item.containsKey("teleport-world") ? (String) item.get("teleport-world") : "world";
                navItem.teleportX = ((Number) item.get("teleport-x")).doubleValue();
                navItem.teleportY = ((Number) item.get("teleport-y")).doubleValue();
                navItem.teleportZ = ((Number) item.get("teleport-z")).doubleValue();
                navItem.teleportYaw = ((Number) item.get("teleport-yaw")).floatValue();
                navItem.teleportPitch = ((Number) item.get("teleport-pitch")).floatValue();

                if (item.containsKey("sound-name")) {
                    navItem.soundName = (String) item.get("sound-name");
                    navItem.soundWorld = item.containsKey("sound-world") ? (String) item.get("sound-world") : "world";
                    navItem.soundX = ((Number) item.get("sound-x")).doubleValue();
                    navItem.soundY = ((Number) item.get("sound-y")).doubleValue();
                    navItem.soundZ = ((Number) item.get("sound-z")).doubleValue();
                }
            }

            navigatorConfig.items.add(navItem);
        }
    }

    // ============================================================
    // Data classes
    // ============================================================

    /**
     * Represents a single NPC with optional paired holograms.
     * If hasHologram is true, a name hologram and a player-count hologram
     * are created above the NPC at the configured Y offsets.
     */
    public static class NpcEntry {
        public String id;
        public String skinTextures;
        public String skinSignature;
        public int bodyYaw;
        public double x, y, z;
        public float yaw, pitch;
        public String clickAction;   // "server" or "inventory"
        public String clickTarget;   // server name or inventory id

        // Paired hologram fields
        public boolean hasHologram;
        public String hologramName;       // display name shown above NPC
        public String hologramServer;     // server name for player count
        public boolean hologramMaintenance;
        public double hologramNameOffset;  // Y offset from NPC for name line
        public double hologramCountOffset; // Y offset from NPC for count line
    }

    /** Navigator inventory configuration. */
    public static class NavigatorConfig {
        public String title = "%lang_lobby_item_navigator%";
        public int size = 45;
        public List<NavigatorItem> items = new ArrayList<>();
    }

    /** A single item slot in the navigator inventory. */
    public static class NavigatorItem {
        public int slot;
        public String material;
        public String name;
        public boolean glow;
        public boolean hideAttributes;
        public String clickAction;   // "server" or "teleport"
        public String clickTarget;

        // Teleport fields
        public String teleportWorld;
        public double teleportX, teleportY, teleportZ;
        public float teleportYaw, teleportPitch;

        // Sound fields (optional, for teleport actions)
        public String soundName;
        public String soundWorld;
        public double soundX, soundY, soundZ;
    }
}
