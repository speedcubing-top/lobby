package top.speedcubing.lobby.list;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.common.database.Database;
import top.speedcubing.common.server.MinecraftServer;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.events.inventory.ClickInventoryEvent;
import top.speedcubing.lib.bukkit.inventory.InventoryBuilder;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.lib.bukkit.pluginMessage.BungeePluginMessage;
import top.speedcubing.lib.utils.bytes.ByteArrayBuffer;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.lang.GlobalString;
import top.speedcubing.server.lang.LangInventory;
import top.speedcubing.server.lang.LangItemStack;
import top.speedcubing.server.lang.LangMessage;

public class ItemStackList {

    public static LangItemStack Navigator = new LangItemStack(new ItemBuilder(Material.COMPASS), LangMessage.load("lobby_item_navigator"));
    public static LangItemStack Settings = new LangItemStack(new ItemBuilder(Material.REDSTONE_COMPARATOR), LangMessage.load("lobby_item_settings"));
    public static LangItemStack lang = new LangItemStack(new ItemBuilder(Material.SKULL_ITEM).durability(3).skullFromURL("http://textures.minecraft.net/texture/c69196b330c6b8962f23ad5627fb6ecce472eaf5c9d44f791f6709c7d0f4dece"), "§cSprache", "§cLanguage", "§c語言", "§c语言");
    public static LangItemStack dm = new LangItemStack(new ItemBuilder(Material.SIGN), LangMessage.load("lobby_item_direct_message"));

    public static LangItemStack[] toggle = {
            new LangItemStack(new ItemBuilder(Material.INK_SACK).durability(1), LangMessage.load("settings_message_0")),
            new LangItemStack(new ItemBuilder(Material.INK_SACK).durability(14), LangMessage.load("settings_message_1")),
            new LangItemStack(new ItemBuilder(Material.INK_SACK).durability(10), LangMessage.load("settings_message_2")),
    };
    public static LangItemStack Reset = new LangItemStack(new ItemBuilder(Material.BARRIER), LangMessage.load("lobby_item_reset_settings"));
    public static LangItemStack ResetStat = new LangItemStack(new ItemBuilder(Material.BARRIER), LangMessage.load("lobby_item_reset_stat"));
    public static LangInventory GameSelect = new LangInventory(18, LangMessage.load("lobby_inv_reduce"))
            .setItem(new LangItemStack(new ItemBuilder(Material.LOG).durability(3), LangMessage.load("reduce_map_name_0")), 0)
            .setItem(new LangItemStack(new ItemBuilder(Material.BED), LangMessage.load("reduce_map_name_1")), 1)
            .setItem(new LangItemStack(new ItemBuilder(Material.BARRIER), LangMessage.load("reduce_map_name_2")), 2)
            .setItem(new LangItemStack(new ItemBuilder(Material.SMOOTH_BRICK), LangMessage.load("reduce_map_name_3")), 3)
            .setItem(new LangItemStack(new ItemBuilder(Material.SANDSTONE), LangMessage.load("reduce_map_name_4")), 4)
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
    public static LangInventory navigator = new LangInventory(45, LangMessage.load("lobby_item_navigator"))
            .setItem(new LangItemStack(new ItemBuilder(Material.STICK), LangMessage.load("lobby_item_mlgrush")), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "mlgrush"), 10)
            .setItem(new LangItemStack(new ItemBuilder(Material.WOOD_SWORD).glow().hideAttr(), LangMessage.load("lobby_item_knockbackffa")), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "knockbackffa"), 16)
            .setItem(new LangItemStack(new ItemBuilder(Material.BLAZE_ROD).glow(), LangMessage.load("lobby_item_reduce")), (r) -> {
                r.getPlayer().closeInventory();
                r.getPlayer().playSound(new Location(Bukkit.getWorld("world"), 0.5, 99.01563, -15.5), Sound.ENDERMAN_TELEPORT, 1F, 1F);
                r.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 7.5, 99, 21.5, 0, 0));
            }, 21)
            .setItem(new LangItemStack(new ItemBuilder(Material.SANDSTONE).glow(), LangMessage.load("lobby_item_fastbuilder")), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "fastbuilder"), 23)
            .setItem(new LangItemStack(new ItemBuilder(Material.DIAMOND_SWORD).glow().hideAttr(), LangMessage.load("lobby_item_practice")), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "practice"), 28)
            .setItem(new LangItemStack(new ItemBuilder(Material.SANDSTONE_STAIRS), LangMessage.load("lobby_item_clutch")), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "clutch"), 34);
    public static LangInventory Lang = new LangInventory();
    public static final LangInventory ResetConfirm = new LangInventory(27, LangMessage.load("lobby_msg_sure_reset"))
            .setItem(new LangItemStack(new ItemBuilder(Material.STAINED_CLAY).durability(13), LangMessage.load("general_yes")), ItemStackList::a, 11)
            .setItem(new LangItemStack(new ItemBuilder(Material.STAINED_CLAY).durability(14), LangMessage.load("general_no")), (r) -> r.getPlayer().closeInventory(), 15);
    public static final LangInventory ResetConfirmStat = new LangInventory(27, LangMessage.load("lobby_msg_sure_reset"))
            .setItem(new LangItemStack(new ItemBuilder(Material.STAINED_CLAY).durability(13), LangMessage.load("general_yes")), ItemStackList::b, 11)
            .setItem(new LangItemStack(new ItemBuilder(Material.STAINED_CLAY).durability(14), LangMessage.load("general_no")), (r) -> r.getPlayer().closeInventory(), 15);

    public void Load() {
        ItemStack Deutsch = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bDeutsch").skullFromURL("http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f").build();
        ItemStack Mandarin = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b繁體中文").skullFromURL("http://textures.minecraft.net/texture/702a4afb2e1e2e3a1894a8b74272f95cfa994ce53907f9ac140bd3c932f9f").build();
        ItemStack Chinese = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b简体中文").skullFromURL("http://textures.minecraft.net/texture/7f9bc035cdc80f1ab5e1198f29f3ad3fdd2b42d9a69aeb64de990681800b98dc").build();
        ItemStack English = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bEnglish").skullFromURL("http://textures.minecraft.net/texture/4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4").build();
        Lang = new LangInventory(LangMessage.load("lobby_item_lang"), new InventoryBuilder(27, "")
                .setItem(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bDeutsch").lore(GlobalString.selected.get(0)).skullFromURL("http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f").glow().build(), (r) -> c(r, 0), 10)
                .setItem(English, (r) -> c(r, 1), 12)
                .setItem(Mandarin, (r) -> c(r, 2), 14)
                .setItem(Chinese, (r) -> c(r, 3), 16),
                new InventoryBuilder(27, "§cLanguage")
                        .setItem(Deutsch, (r) -> c(r, 0), 10)
                        .setItem(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bEnglish").lore(GlobalString.selected.get(1)).skullFromURL("http://textures.minecraft.net/texture/4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4").glow().build(), (r) -> c(r, 1), 12)
                        .setItem(Mandarin, (r) -> c(r, 2), 14)
                        .setItem(Chinese, (r) -> c(r, 3), 16),
                new InventoryBuilder(27, "§c語言")
                        .setItem(Deutsch, (r) -> c(r, 0), 10)
                        .setItem(English, (r) -> c(r, 1), 12)
                        .setItem(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b繁體中文").lore(GlobalString.selected.get(2)).skullFromURL("http://textures.minecraft.net/texture/702a4afb2e1e2e3a1894a8b74272f95cfa994ce53907f9ac140bd3c932f9f").glow().build(), (r) -> c(r, 2), 14)
                        .setItem(Chinese, (r) -> c(r, 3), 16),
                new InventoryBuilder(27, "§c语言")
                        .setItem(Deutsch, (r) -> c(r, 0), 10)
                        .setItem(English, (r) -> c(r, 1), 12)
                        .setItem(Mandarin, (r) -> c(r, 2), 14)
                        .setItem(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b简体中文").lore(GlobalString.selected.get(3)).skullFromURL("http://textures.minecraft.net/texture/7f9bc035cdc80f1ab5e1198f29f3ad3fdd2b42d9a69aeb64de990681800b98dc").glow().build(), (r) -> c(r, 3), 16));
    }

    public static void a(ClickInventoryEvent e) {
        Member member = Member.getMember(e.getPlayer());
        {
            Database.connection.update("playersdata",
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
                            "nickpriority=default(nickpriority)", "id=" + member.user.id);
        }
        e.getPlayer().kickPlayer(LangMessage.load("lobby_msg_reset_stat_success_rejoin").get(member.user));
        e.getPlayer().closeInventory();
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
        member.user.sendLangMessage(StringList.reset);
    }

    public void c(ClickInventoryEvent event, int x) {
        Member member = Member.getMember(event.getPlayer());
        member.user.lang = x;
        String[] sidebar = StringList.LobbySideBar[x];
        SideBar.getSidebar(event.getPlayer()).setLineByScore(sidebar[2], 7).setLineByScore(sidebar[5], 4).setLineByScore(sidebar[8], 1);
        member.user.sound(Sound.NOTE_PLING);
        member.user.sendLangMessage(StringList.LangSettings);
        member.user.dbUpdate("lang=" + x);
        event.getPlayer().closeInventory();
    }
}
