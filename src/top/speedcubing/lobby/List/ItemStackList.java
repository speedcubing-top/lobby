package top.speedcubing.lobby.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.server.libs.GlobalString;

public class ItemStackList {

    public static ItemStack[] Navigator = new ItemStack[4];
    public static ItemStack[] Settings = new ItemStack[4];
    public static ItemStack[] lang = new ItemStack[4];

    public static InventoryHolder GameSelectHolder = () -> null;
    public static Inventory[] GameSelect = new Inventory[4];
    public static InventoryHolder NavigatorHolder = () -> null;
    public static Inventory[] navigator = new Inventory[4];
    public static InventoryHolder SettingsHolder = () -> null;
    public static InventoryHolder LangHolder = () -> null;
    public static Inventory[] Lang = new Inventory[4];
    public static ItemStack[] dm = new ItemStack[4];
    public static ItemStack[][] toggle = new ItemStack[2][4];
    public static final InventoryHolder confirm = () -> null;
    public static final InventoryHolder confirmStat = () -> null;
    public static ItemStack[] Reset = new ItemStack[4];
    public static ItemStack[] ResetStat = new ItemStack[4];
    public static final Inventory[] ResetConfirm = new Inventory[4];
    public static final Inventory[] ResetConfirmStat = new Inventory[4];

    public void Load() {
        String[] yes = {"§aYes", "§aYes", "§aYes", "§aYes"};
        String[] no = {"§cNo", "§cNo", "§cNo", "§cNo"};
        for (int i = 0; i < 4; i++) {
            ResetConfirm[i] = Bukkit.createInventory(confirm, 27, new String[]{"§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?"}[i]);
            ResetConfirm[i].setItem(11, new ItemBuilder(Material.STAINED_CLAY).durability(13).name(yes[i]).build());
            ResetConfirm[i].setItem(15, new ItemBuilder(Material.STAINED_CLAY).durability(14).name(no[i]).build());
            ResetConfirmStat[i] = Bukkit.createInventory(confirmStat, 27, new String[]{"§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?", "§cAre you sure you want to reset?"}[i]);
            ResetConfirmStat[i].setItem(11, new ItemBuilder(Material.STAINED_CLAY).durability(13).name(yes[i]).build());
            ResetConfirmStat[i].setItem(15, new ItemBuilder(Material.STAINED_CLAY).durability(14).name(no[i]).build());
            Reset[i] = new ItemBuilder(Material.BARRIER).name(new String[]{"§cReset your settings","§cReset your settings","§cReset your settings","§cReset your settings"}[i]).build();
            ResetStat[i] = new ItemBuilder(Material.BARRIER).name(new String[]{"§cReset your stats","§cReset your stats","§cReset your stats","§cReset your stats"}[i]).build();
            lang[i] = new ItemBuilder(Material.SKULL_ITEM).durability(3).name(new String[]{"§cSprache", "§cLanguage", "§c語言", "§c语言"}[i]).url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY5MTk2YjMzMGM2Yjg5NjJmMjNhZDU2MjdmYjZlY2NlNDcyZWFmNWM5ZDQ0Zjc5MWY2NzA5YzdkMGY0ZGVjZSJ9fX0=").build();
            Navigator[i] = new ItemBuilder(Material.COMPASS).name(new String[]{"§6Navigator", "§6Navigator", "§6遊戲選單", "§6游戏选单"}[i]).build();
            Settings[i] = new ItemBuilder(Material.REDSTONE_COMPARATOR).name(new String[]{"§cEinstellungen", "§cSettings", "§c設定", "§c设置"}[i]).build();
            navigator[i] = Bukkit.createInventory(NavigatorHolder, 45, new String[]{"§6Navigator", "§6Navigator", "§6遊戲選單", "§6游戏选单"}[i]);
            navigator[i].setItem(10, new ItemBuilder(Material.STICK).name(new String[]{"§bMLGRush", "§bMLGRush", "§bMLGRush", "§bMLGRush"}[i]).glow().build());
            navigator[i].setItem(16, new ItemBuilder(Material.WOOD_SWORD).name(new String[]{"§bKnockBack FFA", "§bKnockBack FFA", "§bKnockBack FFA", "§bKnockBack FFA"}[i]).glow().hideAttr().build());
            navigator[i].setItem(21, new ItemBuilder(Material.BLAZE_ROD).name(new String[]{"§bReduce", "§bReduce", "§bReduce", "§bReduce"}[i]).glow().build());
            navigator[i].setItem(23, new ItemBuilder(Material.SANDSTONE).name(new String[]{"§bFastBuilder", "§bFastBuilder", "§bFastBuilder", "§bFastBuilder"}[i]).build());
            navigator[i].setItem(28, new ItemBuilder(Material.DIAMOND_SWORD).name(new String[]{"§bPractice", "§bPractice", "§bPractice", "§bPractice"}[i]).glow().hideAttr().build());
            navigator[i].setItem(34, new ItemBuilder(Material.SANDSTONE_STAIRS).name(new String[]{"§bClutch", "§bClutch", "§bClutch", "§bClutch"}[i]).build());

            dm[i] = new ItemBuilder(Material.SIGN).name(new String[]{"§bDirect Message", "§bDirect Message", "§bDirect Message", "§bDirect Message"}[i]).build();
            toggle[0][i] = new ItemBuilder(Material.INK_SACK).durability(10).name(new String[]{"§aon", "§aon", "§aon", "§aon"}[i]).build();
            toggle[1][i] = new ItemBuilder(Material.INK_SACK).durability(1).name(new String[]{"§coff", "§coff", "§coff", "§coff"}[i]).build();

            GameSelect[i] = Bukkit.createInventory(GameSelectHolder, 18, new String[]{"§6Reduce", "§6Reduce", "§6Reduce", "§6Reduce"}[i]);
            GameSelect[i].setItem(0, new ItemBuilder(Material.LOG).durability(3).name(new String[]{"§6Oneline", "§6Oneline", "§6Oneline", "§6Oneline"}[i]).build());
            GameSelect[i].setItem(1, new ItemBuilder(Material.BED).name(new String[]{"§6BedWars", "§6BedWars", "§6BedWars", "§6BedWars"}[i]).build());
            GameSelect[i].setItem(2, new ItemBuilder(Material.BARRIER).name(new String[]{"§6OneBlock", "§6OneBlock", "§6OneBlock", "§6OneBlock"}[i]).build());
            GameSelect[i].setItem(3, new ItemBuilder(Material.SMOOTH_BRICK).name(new String[]{"§6Chrama", "§6Chrama", "§6Chrama", "§6Chrama"}[i]).build());
            GameSelect[i].setItem(4, new ItemBuilder(Material.SANDSTONE).name(new String[]{"§6Inclined", "§6Inclined", "§6Inclined", "§6Inclined"}[i]).build());
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
}
