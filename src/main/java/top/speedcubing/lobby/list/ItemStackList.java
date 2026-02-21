package top.speedcubing.lobby.list;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import top.speedcubing.common.database.Database;
import top.speedcubing.common.server.MinecraftServer;
import top.speedcubing.lib.bukkit.events.inventory.ClickInventoryEvent;
import top.speedcubing.lib.bukkit.pluginMessage.BungeePluginMessage;
import top.speedcubing.lib.utils.SQL.SQLConnection;
import top.speedcubing.lib.utils.bytes.ByteArrayBuffer;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.configuration.LobbyConfig;
import top.speedcubing.lobby.configuration.LobbyConfig.NavigatorConfig;
import top.speedcubing.lobby.configuration.LobbyConfig.NavigatorItem;
import top.speedcubing.server.lang.Lang;
import top.speedcubing.server.lang.LangInv;
import top.speedcubing.server.lang.LangItem;
import top.speedcubing.server.player.User;

/**
 * Defines all lobby inventory items and builds the navigator GUI from config.
 */
public class ItemStackList {

    // ---- Hotbar items ----
    public static LangItem Navigator = new LangItem(Material.COMPASS).name("%lang_lobby_item_navigator%");
    public static LangItem Settings = new LangItem(Material.REDSTONE_COMPARATOR).name("%lang_lobby_item_settings%");
    public static LangItem lang = new LangItem(Material.SKULL_ITEM).name("%lang_lobby_item_lang%").durability(3).skullFromURL("http://textures.minecraft.net/texture/c69196b330c6b8962f23ad5627fb6ecce472eaf5c9d44f791f6709c7d0f4dece");
    public static LangItem dm = new LangItem(Material.SIGN).name("%lang_lobby_item_direct_message%");

    // ---- Settings toggle items (off / friends / all) ----
    public static LangItem[] toggle = {
            new LangItem(Material.INK_SACK).durability(1).name("%lang_settings_message_0%"),
            new LangItem(Material.INK_SACK).durability(14).name("%lang_settings_message_1%"),
            new LangItem(Material.INK_SACK).durability(10).name("%lang_settings_message_2%"),
    };

    // ---- Reset buttons ----
    public static LangItem Reset = new LangItem(Material.BARRIER).name("%lang_lobby_item_reset_settings%");
    public static LangItem ResetStat = new LangItem(Material.BARRIER).name("%lang_lobby_item_reset_stat%");

    // ---- Reduce game select inventory ----
    public static LangInv GameSelect = new LangInv(18, "%lang_lobby_inv_reduce%")
            .setItem(new LangItem(Material.LOG).name("%lang_reduce_map_name_0%").durability(3), 0)
            .setItem(new LangItem(Material.BED).name("%lang_reduce_map_name_1%"), 1)
            .setItem(new LangItem(Material.BARRIER).name("%lang_reduce_map_name_2%"), 2)
            .setItem(new LangItem(Material.SMOOTH_BRICK).name("%lang_reduce_map_name_3%"), 3)
            .setItem(new LangItem(Material.SANDSTONE).name("%lang_reduce_map_name_4%"), 4)
            .setClickEvent((r) -> {
                Member member = Member.getMember(r.getPlayer());
                MinecraftServer.getServer("reduce").write(new ByteArrayBuffer()
                        .writeUTF("reduce")
                        .writeInt(member.user.id)
                        .writeInt(r.getEvent().getRawSlot())
                        .toByteArray());
            }, 0, 4);

    // ---- Navigator inventory (built from config) ----
    public static LangInv navigator;

    // ---- Reset confirmation dialogs ----
    public static final LangInv ResetConfirm = new LangInv(27, Lang.of("%lang_lobby_msg_sure_reset%"))
            .setItem(new LangItem(Material.STAINED_CLAY).name("%lang_general_yes%").durability(13), ItemStackList::resetSettings, 11)
            .setItem(new LangItem(Material.STAINED_CLAY).name("%lang_general_no%").durability(14), (r) -> r.getPlayer().closeInventory(), 15);

    public static final LangInv ResetConfirmStat = new LangInv(27, Lang.of("%lang_lobby_msg_sure_reset%"))
            .setItem(new LangItem(Material.STAINED_CLAY).name("%lang_general_yes%").durability(13), ItemStackList::resetStats, 11)
            .setItem(new LangItem(Material.STAINED_CLAY).name("%lang_general_no%").durability(14), (r) -> r.getPlayer().closeInventory(), 15);

    /**
     * Builds (or rebuilds) the navigator inventory from config.
     * Called on startup and on config reload.
     */
    public static void buildNavigator() {
        NavigatorConfig navConfig = LobbyConfig.navigatorConfig;
        if (navConfig == null) return;

        navigator = new LangInv(navConfig.size, navConfig.title);

        for (NavigatorItem navItem : navConfig.items) {
            LangItem item = new LangItem(Material.valueOf(navItem.material)).name(navItem.name);
            if (navItem.glow) item.glow();
            if (navItem.hideAttributes) item.hideAttr();

            switch (navItem.clickAction) {
                case "server": {
                    String target = navItem.clickTarget;
                    navigator.setItem(item, (r) -> BungeePluginMessage.switchServer(r.getPlayer(), target), navItem.slot);
                    break;
                }
                case "teleport": {
                    String world = navItem.teleportWorld;
                    double tx = navItem.teleportX, ty = navItem.teleportY, tz = navItem.teleportZ;
                    float tyaw = navItem.teleportYaw, tpitch = navItem.teleportPitch;
                    String soundName = navItem.soundName;
                    String soundWorld = navItem.soundWorld;
                    double sx = navItem.soundX, sy = navItem.soundY, sz = navItem.soundZ;
                    navigator.setItem(item, (r) -> {
                        r.getPlayer().closeInventory();
                        if (soundName != null && !soundName.isEmpty()) {
                            r.getPlayer().playSound(new Location(Bukkit.getWorld(soundWorld), sx, sy, sz), Sound.valueOf(soundName), 1F, 1F);
                        }
                        r.getPlayer().teleport(new Location(Bukkit.getWorld(world), tx, ty, tz, tyaw, tpitch));
                    }, navItem.slot);
                    break;
                }
            }
        }
    }

    /**
     * Resets all player settings to their database defaults and kicks the player.
     */
    public static void resetSettings(ClickInventoryEvent e) {
        User user = User.getUser(e.getPlayer());
        try (SQLConnection connection = Database.getCubing()) {
            connection.update("playersdata",
                    "api=default(api)," +
                            "chatfilt=default(chatfilt)," +
                            "messagable=default(messagable)," +
                            "chat=default(chat)," +
                            "lang=default(lang)," +
                            "skinvalue=default(skinvalue)," +
                            "skinsignature=default(skinsignature)," +
                            "mlgrush_stick=default(mlgrush_stick)," +
                            "mlgrush_pickaxe=default(mlgrush_pickaxe)," +
                            "mlgrush_block=default(mlgrush_block)," +
                            "mlgrush_stickskin=default(mlgrush_stickskin)," +
                            "mlgrush_blockskin=default(mlgrush_blockskin)," +
                            "mlgrush_autoreset=default(mlgrush_autoreset)," +
                            "mlgrush_hitdelay=default(mlgrush_hitdelay)," +
                            "mlgrush_falldamage=default(mlgrush_falldamage)," +
                            "mlgrush_autoremove=default(mlgrush_autoremove)," +
                            "mlgrush_oldkb=default(mlgrush_oldkb)," +
                            "reduce_stick=default(reduce_stick)," +
                            "reduce_setting=default(reduce_setting)," +
                            "reduce_reach=default(reduce_reach)," +
                            "reduce_ver=default(reduce_ver)," +
                            "reduce_hor=default(reduce_hor)," +
                            "reduce_vanilla_reach=default(reduce_vanilla_reach)," +
                            "reduce_vanilla_hor=default(reduce_vanilla_hor)," +
                            "reduce_block=default(reduce_block)," +
                            "reduce_stickskin=default(reduce_stickskin)," +
                            "reduce_blockskin=default(reduce_blockskin)," +
                            "reduce_npcstick=default(reduce_npcstick)," +
                            "reduce_spec=default(reduce_spec)," +
                            "reduce_allowspec=default(reduce_allowspec)," +
                            "reduce_npccolor=default(reduce_npccolor)," +
                            "reduce_npcsound=default(reduce_npcsound)," +
                            "reduce_npcswing=default(reduce_npcswing)," +
                            "reduce_vanilla=default(reduce_vanilla)," +
                            "kbffa_inventory=default(kbffa_inventory)," +
                            "duels_nodebuff=default(duels_nodebuff)," +
                            "duels_combo=default(duels_combo)," +
                            "duels_nodamage=default(duels_nodamage)," +
                            "duels_boxing=default(duels_boxing)," +
                            "duels_gapple=default(duels_gapple)," +
                            "duels_bow=default(duels_bow)," +
                            "fastbuild_blockskin=default(fastbuild_blockskin)," +
                            "fastbuild_falldelay=default(fastbuild_falldelay)," +
                            "clutch_blockskin=default(clutch_blockskin)," +
                            "clutch_normal_hits=default(clutch_normal_hits)," +
                            "clutch_inclined_hits=default(clutch_inclined_hits)," +
                            "clutch_normal_ver=default(clutch_normal_ver)," +
                            "clutch_inclined_ver=default(clutch_inclined_ver)," +
                            "clutch_normal_hitdelay=default(clutch_normal_hitdelay)," +
                            "clutch_inclined_hitdelay=default(clutch_inclined_hitdelay)," +
                            "customfb_x=default(customfb_x)," +
                            "customfb_y=default(customfb_y)," +
                            "nickpriority=default(nickpriority)", "id=" + user.id);
        }
        user.kick("%lang_lobby_msg_reset_stat_success_rejoin%");
    }

    /**
     * Resets all player statistics to their database defaults.
     */
    public static void resetStats(ClickInventoryEvent e) {
        Member member = Member.getMember(e.getPlayer());
        member.user.dbUpdate(
                "kbffa_kill=default(kbffa_kill)," +
                        "kbffa_death=default(kbffa_death)," +
                        "kbffa_killstreak=default(kbffa_killstreak)," +
                        "kbffa_dailykill=default(kbffa_dailykill)," +
                        "fb_0_pb=default(fb_0_pb)," +
                        "fb_1_pb=default(fb_1_pb)," +
                        "fb_2_pb=default(fb_2_pb)," +
                        "fb_3_pb=default(fb_3_pb)," +
                        "fb_4_pb=default(fb_4_pb)");
        member.user.sendMessage("%lang_lobby_msg_reset_stat_success%");
    }
}
