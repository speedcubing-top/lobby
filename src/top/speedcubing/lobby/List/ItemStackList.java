package top.speedcubing.lobby.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.bukkit.inventory.InventoryBuilder;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.lib.bukkit.pluginMessage.BungeePluginMessage;
import top.speedcubing.lib.utils.ByteArrayDataBuilder;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.libs.GlobalString;
import top.speedcubing.server.speedcubingServer;

public class ItemStackList {

    public static ItemStack[] Navigator = new ItemStack[4];
    public static ItemStack[] Settings = new ItemStack[4];
    public static ItemStack[] lang = new ItemStack[4];
    public static Inventory[] GameSelect = new Inventory[4];
    public static Inventory[] navigator = new Inventory[4];
    public static InventoryHolder SettingsHolder = () -> null;
    public static InventoryHolder LangHolder = () -> null;
    public static Inventory[] Lang = new Inventory[4];
    public static ItemStack[] dm = new ItemStack[4];
    public static ItemStack[][] toggle = new ItemStack[2][4];
    public static ItemStack[] Reset = new ItemStack[4];
    public static ItemStack[] ResetStat = new ItemStack[4];
    public static final Inventory[] ResetConfirm = new Inventory[4];
    public static final Inventory[] ResetConfirmStat = new Inventory[4];

    public void Load() {
        String[] yes = {"§aYes", "§aYes", "§aYes", "§aYes"};
        String[] no = {"§cNo", "§cNo", "§cNo", "§cNo"};
        for (int i = 0; i < 4; i++) {
            ResetConfirm[i] =
                    new InventoryBuilder(27, new String[]{"§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?"}[i])
                            .setItem(new ItemBuilder(Material.STAINED_CLAY).durability(13).name(yes[i]).build(), this::a, 11)
                            .setItem(new ItemBuilder(Material.STAINED_CLAY).durability(14).name(no[i]).build(), (a, b) -> a.closeInventory(), 15)
                            .getInventory();
            ResetConfirmStat[i] =
                    new InventoryBuilder(27, new String[]{"§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?"}[i])
                            .setItem(new ItemBuilder(Material.STAINED_CLAY).durability(13).name(yes[i]).build(), this::b, 11)
                            .setItem(new ItemBuilder(Material.STAINED_CLAY).durability(14).name(no[i]).build(), (a, b) -> a.closeInventory(), 15)
                            .getInventory();
            Reset[i] = new ItemBuilder(Material.BARRIER).name(new String[]{"§cReset your settings", "§cReset your settings", "§cReset your settings", "§cReset your settings"}[i]).build();
            ResetStat[i] = new ItemBuilder(Material.BARRIER).name(new String[]{"§cReset your stats", "§cReset your stats", "§cReset your stats", "§cReset your stats"}[i]).build();
            lang[i] = new ItemBuilder(Material.SKULL_ITEM).durability(3).name(new String[]{"§cSprache", "§cLanguage", "§c語言", "§c语言"}[i]).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY5MTk2YjMzMGM2Yjg5NjJmMjNhZDU2MjdmYjZlY2NlNDcyZWFmNWM5ZDQ0Zjc5MWY2NzA5YzdkMGY0ZGVjZSJ9fX0=").build();
            Navigator[i] = new ItemBuilder(Material.COMPASS).name(new String[]{"§6Navigator", "§6Navigator", "§6遊戲選單", "§6游戏选单"}[i]).build();
            Settings[i] = new ItemBuilder(Material.REDSTONE_COMPARATOR).name(new String[]{"§cEinstellungen", "§cSettings", "§c設定", "§c设置"}[i]).build();
            navigator[i] = new InventoryBuilder(45, new String[]{"§6Navigator", "§6Navigator", "§6遊戲選單", "§6游戏选单"}[i])
                    .setItem(new ItemBuilder(Material.STICK).name(new String[]{"§bMLGRush", "§bMLGRush", "§bMLGRush", "§bMLGRush"}[i]).glow().build(), (a, b) -> BungeePluginMessage.switchServer(a, "mlgrush"), 10)
                    .setItem(new ItemBuilder(Material.WOOD_SWORD).name(new String[]{"§bKnockBack FFA", "§bKnockBack FFA", "§bKnockBack FFA", "§bKnockBack FFA"}[i]).glow().hideAttr().build(), (a, b) -> BungeePluginMessage.switchServer(a, "knockbackffa"), 16)
                    .setItem(new ItemBuilder(Material.BLAZE_ROD).name(new String[]{"§bReduce", "§bReduce", "§bReduce", "§bReduce"}[i]).glow().build(), (a, b) -> {
                        a.closeInventory();
                        a.playSound(new Location(Bukkit.getWorld("world"), 0.5, 99.01563, -15.5), Sound.ENDERMAN_TELEPORT, 1F, 1F);
                        a.teleport(new Location(Bukkit.getWorld("world"), 7.5, 99, 21.5, 0, 0));
                    }, 21)
                    .setItem(new ItemBuilder(Material.SANDSTONE).name(new String[]{"§bFastBuilder", "§bFastBuilder", "§bFastBuilder", "§bFastBuilder"}[i]).glow().build(), (a, b) -> BungeePluginMessage.switchServer(a, "fastbuilder"), 23)
                    .setItem(new ItemBuilder(Material.DIAMOND_SWORD).name(new String[]{"§bPractice", "§bPractice", "§bPractice", "§bPractice"}[i]).glow().hideAttr().build(), (a, b) -> BungeePluginMessage.switchServer(a, "practice"), 28)
                    .setItem(new ItemBuilder(Material.SANDSTONE_STAIRS).name(new String[]{"§bClutch", "§bClutch", "§bClutch", "§bClutch"}[i]).build(), (a, b) -> BungeePluginMessage.switchServer(a, "clutch"), 34)
                    .getInventory();
            dm[i] = new ItemBuilder(Material.SIGN).name(new String[]{"§bDirect Message", "§bDirect Message", "§bDirect Message", "§bDirect Message"}[i]).build();
            toggle[0][i] = new ItemBuilder(Material.INK_SACK).durability(10).name(new String[]{"§aon", "§aon", "§aon", "§aon"}[i]).build();
            toggle[1][i] = new ItemBuilder(Material.INK_SACK).durability(1).name(new String[]{"§coff", "§coff", "§coff", "§coff"}[i]).build();

            GameSelect[i] = new InventoryBuilder(18, new String[]{"§6Reduce", "§6Reduce", "§6Reduce", "§6Reduce"}[i])
                    .setItem(new ItemBuilder(Material.LOG).durability(3).name(new String[]{"§6Oneline", "§6Oneline", "§6Oneline", "§6Oneline"}[i]).build(), 0)
                    .setItem(new ItemBuilder(Material.BED).name(new String[]{"§6BedWars", "§6BedWars", "§6BedWars", "§6BedWars"}[i]).build(), 1)
                    .setItem(new ItemBuilder(Material.BARRIER).name(new String[]{"§6OneBlock", "§6OneBlock", "§6OneBlock", "§6OneBlock"}[i]).build(), 2)
                    .setItem(new ItemBuilder(Material.SMOOTH_BRICK).name(new String[]{"§6Chrama", "§6Chrama", "§6Chrama", "§6Chrama"}[i]).build(), 3)
                    .setItem(new ItemBuilder(Material.SANDSTONE).name(new String[]{"§6Inclined", "§6Inclined", "§6Inclined", "§6Inclined"}[i]).build(), 4)
                    .setClickEvent(
                            (a, b) -> speedcubingServer.tcpClient.send(25604 - Bukkit.getPort() % 2, new ByteArrayDataBuilder().writeUTF("reduce").writeInt(Member.getMember(a).user.id).writeInt(b.getRawSlot()).writeInt(speedcubingServer.tcpServer.getLocalPort()).toByteArray()), 0, 4)
                    .getInventory();
        }
        ItemStack Deutsch = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bDeutsch").url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==").build();
        ItemStack Mandarin = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b繁體中文").url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzAyYTRhZmIyZTFlMmUzYTE4OTRhOGI3NDI3MmY5NWNmYTk5NGNlNTM5MDdmOWFjMTQwYmQzYzkzMmY5ZiJ9fX0=").build();
        ItemStack Chinese = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b简体中文").url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y5YmMwMzVjZGM4MGYxYWI1ZTExOThmMjlmM2FkM2ZkZDJiNDJkOWE2OWFlYjY0ZGU5OTA2ODE4MDBiOThkYyJ9fX0=").build();
        ItemStack English = new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bEnglish").url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19").build();
        Lang[0] = Bukkit.createInventory(LangHolder, 27, "§cSprache");
        Lang[0].setItem(10, new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bDeutsch").lore(GlobalString.selected[0]).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==").glow().build());
        Lang[0].setItem(12, English);
        Lang[0].setItem(14, Mandarin);
        Lang[0].setItem(16, Chinese);
        Lang[1] = Bukkit.createInventory(LangHolder, 27, "§cLanguage");
        Lang[1].setItem(10, Deutsch);
        Lang[1].setItem(12, new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§bEnglish").lore(GlobalString.selected[1]).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19").glow().build());
        Lang[1].setItem(14, Mandarin);
        Lang[1].setItem(16, Chinese);
        Lang[2] = Bukkit.createInventory(LangHolder, 27, "§c語言");
        Lang[2].setItem(10, Deutsch);
        Lang[2].setItem(12, English);
        Lang[2].setItem(14, new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b繁體中文").lore(GlobalString.selected[2]).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzAyYTRhZmIyZTFlMmUzYTE4OTRhOGI3NDI3MmY5NWNmYTk5NGNlNTM5MDdmOWFjMTQwYmQzYzkzMmY5ZiJ9fX0=").glow().build());
        Lang[2].setItem(16, Chinese);
        Lang[3] = Bukkit.createInventory(LangHolder, 27, "§c语言");
        Lang[3].setItem(10, Deutsch);
        Lang[3].setItem(12, English);
        Lang[3].setItem(14, Mandarin);
        Lang[3].setItem(16, new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§b简体中文").lore(GlobalString.selected[3]).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y5YmMwMzVjZGM4MGYxYWI1ZTExOThmMjlmM2FkM2ZkZDJiNDJkOWE2OWFlYjY0ZGU5OTA2ODE4MDBiOThkYyJ9fX0=").glow().build());
    }

    public void a(Player player, InventoryClickEvent e) {
        Member member = Member.getMember(e.getWhoClicked());
        {
            speedcubingServer.connection.update("playersdata",
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
        ((Player) e.getWhoClicked()).kickPlayer("Sucessfully reset your settings.\nPlease rejoin.");
        e.getWhoClicked().closeInventory();
    }

    public void b(Player player, InventoryClickEvent e) {
        Member member = Member.getMember(e.getWhoClicked());
        {
            speedcubingServer.connection.update("playersdata",
                    "kbffa_kill=default(kbffa_kill)," +
                            "kbffa_death=default(kbffa_death)," +
                            "kbffa_killstreak=default(kbffa_killstreak)," +
                            "kbffa_dailykill=default(kbffa_dailykill)," +
                            "fb_0_pb=default(fb_0_pb)," +
                            "fb_1_pb=default(fb_1_pb)," +
                            "fb_2_pb=default(fb_2_pb)," +
                            "fb_3_pb=default(fb_3_pb)," +
                            "fb_4_pb=default(fb_4_pb)", "id=" + member.user.id);
        }
        member.user.sendLangMessage(StringList.reset);
    }
}
