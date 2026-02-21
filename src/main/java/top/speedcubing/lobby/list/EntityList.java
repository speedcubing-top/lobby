package top.speedcubing.lobby.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import top.speedcubing.lib.bukkit.entity.Hologram;
import top.speedcubing.lib.bukkit.entity.NPC;
import top.speedcubing.lib.bukkit.pluginMessage.BungeePluginMessage;
import top.speedcubing.lobby.Lobby;
import top.speedcubing.lobby.configuration.LobbyConfig;
import top.speedcubing.lobby.configuration.LobbyConfig.NpcEntry;
import top.speedcubing.server.player.User;

/**
 * Creates and manages all lobby NPCs and their paired holograms.
 */
public class EntityList {

    /**
     * Maps server name to its player-count hologram.
     * Used by ServerEvent to update counts each tick.
     */
    public static Map<String, Hologram> playerCountHolograms = new HashMap<>();

    /** Set of servers currently in maintenance mode. */
    public static Set<String> maintenanceServers = new HashSet<>();

    /** All spawned NPCs. */
    public static List<NPC> npcs = new ArrayList<>();

    /** All spawned holograms (name + count lines). */
    public static List<Hologram> holograms = new ArrayList<>();

    /**
     * Initializes all NPCs and their paired holograms from config.
     * Order mirrors original: create all entities first, then configure them.
     */
    public void init() {
        LobbyConfig.load();

        Vector offset = LobbyConfig.offset;
        Lobby.offset = offset;

        playerCountHolograms.clear();
        maintenanceServers.clear();

        List<NpcEntry> entries = LobbyConfig.npcEntries;

        // --- Phase 1: Create all NPCs (matches original order) ---
        for (NpcEntry entry : entries) {
            NPC npc = new NPC("§8[NPC]", null, true, true, true).world("world").hideNametag();
            npcs.add(npc);
        }

        // --- Phase 2: Create all holograms (matches original order) ---
        for (NpcEntry entry : entries) {
            if (entry.hasHologram) {
                Hologram nameHolo = new Hologram("", true, true).world("world");
                Hologram countHolo = new Hologram("", true, true).world("world");
                holograms.add(nameHolo);
                holograms.add(countHolo);
            }
        }

        // --- Phase 3: Configure NPC skins, locations, and click actions ---
        int npcIndex = 0;
        int holoIndex = 0;
        for (NpcEntry entry : entries) {
            NPC npc = npcs.get(npcIndex++);
            Location npcLocation = new Location(Bukkit.getWorld("world"), entry.x, entry.y, entry.z, entry.yaw, entry.pitch).add(offset);

            npc.setSkin(entry.skinTextures, entry.skinSignature)
                    .setSpawnBodyYaw(entry.bodyYaw)
                    .setLocation(npcLocation);

            registerNpcClickAction(npc, entry);

            // --- Phase 4: Configure paired holograms ---
            if (entry.hasHologram) {
                double baseX = npcLocation.getX();
                double baseY = npcLocation.getY();
                double baseZ = npcLocation.getZ();

                // Name line
                Hologram nameHolo = holograms.get(holoIndex++);
                double nameY = baseY + entry.hologramNameOffset;
                nameHolo.setLocation(new Location(Bukkit.getWorld("world"), baseX, nameY, baseZ, 0, 0))
                        .setName(entry.hologramName)
                        .spawn();

                // Player count line
                Hologram countHolo = holograms.get(holoIndex++);
                double countY = baseY + entry.hologramCountOffset;
                countHolo.setLocation(new Location(Bukkit.getWorld("world"), baseX, countY, baseZ, 0, 0));

                if (entry.hologramMaintenance) {
                    countHolo.setName("§c§lMaintenance");
                    maintenanceServers.add(entry.hologramServer);
                } else {
                    countHolo.setName("§e§l0 Players");
                    playerCountHolograms.put(entry.hologramServer, countHolo);
                }
                countHolo.spawn();
            }
        }
    }

    /**
     * Registers the click action for an NPC based on its config entry.
     */
    private void registerNpcClickAction(NPC npc, NpcEntry entry) {
        switch (entry.clickAction) {
            case "server":
                String serverName = entry.clickTarget;
                npc.setClickEvent((r) -> {
                    if (r.getPlayer().getItemInHand().getType() == Material.AIR
                            && r.getAction().equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT)) {
                        BungeePluginMessage.switchServer(r.getPlayer(), serverName);
                    }
                });
                break;
            case "inventory":
                npc.setClickEvent((r) -> {
                    if (r.getPlayer().getItemInHand().getType() == Material.AIR
                            && r.getAction().equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT)) {
                        Bukkit.getScheduler().runTask(Lobby.getPlugin(Lobby.class),
                                () -> User.getUser(r.getPlayer()).openLangInventory(ItemStackList.GameSelect));
                    }
                });
                break;
        }
    }

}
