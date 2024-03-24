package top.speedcubing.lobby.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.common.database.Database;
import top.speedcubing.common.server.MinecraftServer;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.inventory.InventoryBuilder;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.lib.bukkit.pluginMessage.BungeePluginMessage;
import top.speedcubing.lib.events.inventory.ClickInventoryEvent;
import top.speedcubing.lib.utils.ByteArrayDataBuilder;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.lang.GlobalString;
import top.speedcubing.server.lang.LangInventory;
import top.speedcubing.server.lang.LangItemStack;

public class ItemStackList {

    public static LangItemStack Navigator = new LangItemStack(new ItemBuilder(Material.COMPASS), "§6Navigator", "§6Navigator", "§6遊戲選單", "§6游戏选单");
    public static LangItemStack Settings = new LangItemStack(new ItemBuilder(Material.REDSTONE_COMPARATOR), "§cEinstellungen", "§cSettings", "§c設定", "§c设置");
    public static LangItemStack lang = new LangItemStack(new ItemBuilder(Material.SKULL_ITEM).durability(3).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY5MTk2YjMzMGM2Yjg5NjJmMjNhZDU2MjdmYjZlY2NlNDcyZWFmNWM5ZDQ0Zjc5MWY2NzA5YzdkMGY0ZGVjZSJ9fX0="), "§cSprache", "§cLanguage", "§c語言", "§c语言");
    public static LangItemStack dm = new LangItemStack(new ItemBuilder(Material.SIGN), "§bDirect Message", "§bDirect Message", "§bDirect Message", "§bDirect Message");

    public static LangItemStack[] toggle = {
            new LangItemStack(new ItemBuilder(Material.INK_SACK).durability(1), "§cNo one can message you.", "§cNo one can message you.", "§cNo one can message you.", "§cNo one can message you."),
            new LangItemStack(new ItemBuilder(Material.INK_SACK).durability(14), "§6Only friends can message you.", "§6Only friends can message you.", "§6Only friends can message you.", "§6Only friends can message you."),
            new LangItemStack(new ItemBuilder(Material.INK_SACK).durability(10), "§aEveryone can message you.", "§aEveryone can message you.", "§aEveryone can message you.", "§aEveryone can message you."),
    };
    public static LangItemStack Reset = new LangItemStack(new ItemBuilder(Material.BARRIER), "§cReset your settings", "§cReset your settings", "§cReset your settings", "§cReset your settings");
    public static LangItemStack ResetStat = new LangItemStack(new ItemBuilder(Material.BARRIER), "§cReset your stats", "§cReset your stats", "§cReset your stats", "§cReset your stats");
    public static LangInventory GameSelect = new LangInventory(18, "§6Reduce", "§6Reduce", "§6Reduce", "§6Reduce")
            .setItem(new LangItemStack(new ItemBuilder(Material.LOG).durability(3), "§6Oneline", "§6Oneline", "§6Oneline", "§6Oneline"), 0)
            .setItem(new LangItemStack(new ItemBuilder(Material.BED), "§6BedWars", "§6BedWars", "§6BedWars", "§6BedWars"), 1)
            .setItem(new LangItemStack(new ItemBuilder(Material.BARRIER), "§6OneBlock", "§6OneBlock", "§6OneBlock", "§6OneBlock"), 2)
            .setItem(new LangItemStack(new ItemBuilder(Material.SMOOTH_BRICK), "§6Chrama", "§6Chrama", "§6Chrama", "§6Chrama"), 3)
            .setItem(new LangItemStack(new ItemBuilder(Material.SANDSTONE), "§6Inclined", "§6Inclined", "§6Inclined", "§6Inclined"), 4)
            .setClickEvent((r) -> {
                Member member = Member.getMember(r.getPlayer());
                MinecraftServer.getServer("reduce").write(new ByteArrayDataBuilder()
                        .writeUTF("reduce")
                        .writeInt(member.user.id)
                        .writeInt(r.getEvent().getRawSlot())
                        .writeUTF(member.user.proxy.getHost())
                        .writeInt(member.user.proxy.getPort())
                        .toByteArray());
            }, 0, 4);
    public static LangInventory navigator = new LangInventory(45, "§6Navigator", "§6Navigator", "§6遊戲選單", "§6游戏选单")
            .setItem(new LangItemStack(new ItemBuilder(Material.STICK), "§bMLGRush", "§bMLGRush", "§bMLGRush", "§bMLGRush"), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "mlgrush"), 10)
            .setItem(new LangItemStack(new ItemBuilder(Material.WOOD_SWORD).glow().hideAttr(), "§bKnockBack FFA", "§bKnockBack FFA", "§bKnockBack FFA", "§bKnockBack FFA"), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "knockbackffa"), 16)
            .setItem(new LangItemStack(new ItemBuilder(Material.BLAZE_ROD).glow(), "§bReduce", "§bReduce", "§bReduce", "§bReduce"), (r) -> {
                r.getPlayer().closeInventory();
                r.getPlayer().playSound(new Location(Bukkit.getWorld("world"), 0.5, 99.01563, -15.5), Sound.ENDERMAN_TELEPORT, 1F, 1F);
                r.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 7.5, 99, 21.5, 0, 0));
            }, 21)
            .setItem(new LangItemStack(new ItemBuilder(Material.SANDSTONE).glow(), "§bFastBuilder", "§bFastBuilder", "§bFastBuilder", "§bFastBuilder"), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "fastbuilder"), 23)
            .setItem(new LangItemStack(new ItemBuilder(Material.DIAMOND_SWORD).glow().hideAttr(), "§bPractice", "§bPractice", "§bPractice", "§bPractice"), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "practice"), 28)
            .setItem(new LangItemStack(new ItemBuilder(Material.SANDSTONE_STAIRS), "§bClutch", "§bClutch", "§bClutch", "§bClutch"), (r) -> BungeePluginMessage.switchServer(r.getPlayer(), "clutch"), 34);
    public static LangInventory Lang = new LangInventory();
    public static final LangInventory ResetConfirm = new LangInventory(27, "§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?")
            .setItem(new LangItemStack(new ItemBuilder(Material.STAINED_CLAY).durability(13), "§aYes", "§aYes", "§aYes", "§aYes"), ItemStackList::a, 11)
            .setItem(new LangItemStack(new ItemBuilder(Material.STAINED_CLAY).durability(14), "§cNo", "§cNo", "§cNo", "§cNo"), (r) -> r.getPlayer().closeInventory(), 15);
    public static final LangInventory ResetConfirmStat = new LangInventory(27, "§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?")
            .setItem(new LangItemStack(new ItemBuilder(Material.STAINED_CLAY).durability(13), "§aYes", "§aYes", "§aYes", "§aYes"), ItemStackList::b, 11)
            .setItem(new LangItemStack(new ItemBuilder(Material.STAINED_CLAY).durability(14), "§cNo", "§cNo", "§cNo", "§cNo"), (r) -> r.getPlayer().closeInventory(), 15);

    public void Load() {
        ItemStack Deutsch = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bDeutsch").url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==").build();
        ItemStack Mandarin = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b繁體中文").url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzAyYTRhZmIyZTFlMmUzYTE4OTRhOGI3NDI3MmY5NWNmYTk5NGNlNTM5MDdmOWFjMTQwYmQzYzkzMmY5ZiJ9fX0=").build();
        ItemStack Chinese = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b简体中文").url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y5YmMwMzVjZGM4MGYxYWI1ZTExOThmMjlmM2FkM2ZkZDJiNDJkOWE2OWFlYjY0ZGU5OTA2ODE4MDBiOThkYyJ9fX0=").build();
        ItemStack English = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bEnglish").url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19").build();
        Lang = new LangInventory(new InventoryBuilder(27, "§cSprache")
                .setItem(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bDeutsch").lore(GlobalString.selected.get(0)).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==").glow().build(), (r) -> c(r, 0), 10)
                .setItem(English, (r) -> c(r, 1), 12)
                .setItem(Mandarin, (r) -> c(r, 2), 14)
                .setItem(Chinese, (r) -> c(r, 3), 16),
                new InventoryBuilder(27, "§cLanguage")
                        .setItem(Deutsch, (r) -> c(r, 0), 10)
                        .setItem(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bEnglish").lore(GlobalString.selected.get(1)).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19").glow().build(), (r) -> c(r, 1), 12)
                        .setItem(Mandarin, (r) -> c(r, 2), 14)
                        .setItem(Chinese, (r) -> c(r, 3), 16),
                new InventoryBuilder(27, "§c語言")
                        .setItem(Deutsch, (r) -> c(r, 0), 10)
                        .setItem(English, (r) -> c(r, 1), 12)
                        .setItem(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b繁體中文").lore(GlobalString.selected.get(2)).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzAyYTRhZmIyZTFlMmUzYTE4OTRhOGI3NDI3MmY5NWNmYTk5NGNlNTM5MDdmOWFjMTQwYmQzYzkzMmY5ZiJ9fX0=").glow().build(), (r) -> c(r, 2), 14)
                        .setItem(Chinese, (r) -> c(r, 3), 16),
                new InventoryBuilder(27, "§c语言")
                        .setItem(Deutsch, (r) -> c(r, 0), 10)
                        .setItem(English, (r) -> c(r, 1), 12)
                        .setItem(Mandarin, (r) -> c(r, 2), 14)
                        .setItem(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b简体中文").lore(GlobalString.selected.get(3)).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y5YmMwMzVjZGM4MGYxYWI1ZTExOThmMjlmM2FkM2ZkZDJiNDJkOWE2OWFlYjY0ZGU5OTA2ODE4MDBiOThkYyJ9fX0=").glow().build(), (r) -> c(r, 3), 16));
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
        e.getPlayer().kickPlayer("Sucessfully reset your settings.\nPlease rejoin.");
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
