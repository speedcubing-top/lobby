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
import top.speedcubing.server.lang.Lang;
import top.speedcubing.server.lang.LangInv;
import top.speedcubing.server.lang.LangItem;
import top.speedcubing.server.player.User;

public class ItemStackList {

    public static LangItem Navigator = new LangItem(Material.COMPASS).name("%lang_lobby_item_navigator%");
    public static LangItem Settings = new LangItem(Material.REDSTONE_COMPARATOR).name("%lang_lobby_item_settings%");
    public static LangItem lang = new LangItem(Material.SKULL_ITEM).name("%lang_lobby_item_lang%").durability(3).skullFromURL("http://textures.minecraft.net/texture/c69196b330c6b8962f23ad5627fb6ecce472eaf5c9d44f791f6709c7d0f4dece");
    public static LangItem dm = new LangItem(Material.SIGN).name("%lang_lobby_item_direct_message%");

    public static LangItem[] toggle = {
            new LangItem(Material.INK_SACK).durability(1).name("%lang_settings_message_0%"),
            new LangItem(Material.INK_SACK).durability(14).name("%lang_settings_message_1%"),
            new LangItem(Material.INK_SACK).durability(10).name("%lang_settings_message_2%"),
    };

    public static LangItem Reset = new LangItem(Material.BARRIER).name("%lang_lobby_item_reset_settings%");
    public static LangItem ResetStat = new LangItem(Material.BARRIER).name("%lang_lobby_item_reset_stat%");
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
                        .writeUTF(member.user.proxy.getHost())
                        .writeInt(member.user.proxy.getPort())
                        .toByteArray());
            }, 0, 4);
    public static LangInv navigator = new LangInv(45, "%lang_lobby_item_navigator%")
            .setItem(new LangItem(Material.STICK).name("%lang_lobby_item_mlgrush%"), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "mlgrush"), 10)
            .setItem(new LangItem(Material.WOOD_SWORD).glow().hideAttr().name("%lang_lobby_item_knockbackffa%"), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "knockbackffa"), 16)
            .setItem(new LangItem(Material.BLAZE_ROD).glow().name("%lang_lobby_item_reduce%"), (r) -> {
                r.getPlayer().closeInventory();
                r.getPlayer().playSound(new Location(Bukkit.getWorld("world"), 0.5, 99.01563, -15.5), Sound.ENDERMAN_TELEPORT, 1F, 1F);
                r.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -60.5, 5, 70.5, -39, 9));
            }, 21)
            .setItem(new LangItem(Material.SANDSTONE).name("%lang_lobby_item_fastbuilder%").glow(), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "fastbuilder"), 23)
            .setItem(new LangItem(Material.DIAMOND_SWORD).name("%lang_lobby_item_practice%").glow().hideAttr(), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "practice"), 28)
            .setItem(new LangItem(Material.SANDSTONE_STAIRS).name("%lang_lobby_item_clutch%"), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "clutch"), 34);
    public static final LangInv ResetConfirm = new LangInv(27, Lang.of("%lang_lobby_msg_sure_reset%"))
            .setItem(new LangItem(Material.STAINED_CLAY).name("%lang_general_yes%").durability(13), ItemStackList::a, 11)
            .setItem(new LangItem(Material.STAINED_CLAY).name("%lang_general_no%").durability(14), (r) -> r.getPlayer().closeInventory(), 15);
    public static final LangInv ResetConfirmStat = new LangInv(27, Lang.of("%lang_lobby_msg_sure_reset%"))
            .setItem(new LangItem(Material.STAINED_CLAY).name("%lang_general_yes%").durability(13), ItemStackList::b, 11)
            .setItem(new LangItem(Material.STAINED_CLAY).name("%lang_general_no%").durability(14), (r) -> r.getPlayer().closeInventory(), 15);


    public static void a(ClickInventoryEvent e) {
        User user = User.getUser(e.getPlayer());
        try(SQLConnection connection = Database.getCubing()){
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

    public static void b(ClickInventoryEvent e) {
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
