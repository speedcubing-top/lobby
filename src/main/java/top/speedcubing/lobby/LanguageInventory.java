package top.speedcubing.lobby;

import org.bukkit.Material;
import org.bukkit.Sound;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.events.inventory.ClickInventoryEvent;
import top.speedcubing.lib.bukkit.inventory.InventoryBuilder;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.lobby.list.StringList;
import top.speedcubing.server.lang.Lang;
import top.speedcubing.server.lang.LanguageSystem;

public class LanguageInventory {
    static String[] skullURL = {
            "http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f",
            "http://textures.minecraft.net/texture/702a4afb2e1e2e3a1894a8b74272f95cfa994ce53907f9ac140bd3c932f9f",
            "http://textures.minecraft.net/texture/7f9bc035cdc80f1ab5e1198f29f3ad3fdd2b42d9a69aeb64de990681800b98dc",
            "http://textures.minecraft.net/texture/4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4"
    };
    static ItemBuilder[] skull = {new ItemBuilder(Material.SKULL_ITEM).durability(3)
            , new ItemBuilder(Material.SKULL_ITEM).durability(3)
            , new ItemBuilder(Material.SKULL_ITEM).durability(3)
            , new ItemBuilder(Material.SKULL_ITEM).durability(3)
    };


    public static void open(Member member) {
        int lang = member.user.lang;

        InventoryBuilder inv = new InventoryBuilder(27, Lang.of("%lang_lobby_item_lang%").getString(lang));
        for (int i = 0; i < LanguageSystem.langCount; i++) {
            int finalI = i;

            ItemBuilder item = skull[i];
            if (i == lang) {
                item = new ItemBuilder(Material.SKULL_ITEM).durability(3).glow().lore(Lang.of("%lang_general_selected%").getString(lang));
            }
            item.name(Lang.of("%lang_lobby_item_lang_name%").getString(i))
                    .lore(Lang.of("%lang_general_selected%").getString(i))
                    .skullFromURL(skullURL[i]).build();

            inv.setItem(item.build(), (r) -> c(r, finalI), 10 + i * 2);
        }
    }

    public static void c(ClickInventoryEvent event, int x) {
        Member member = Member.getMember(event.getPlayer());
        member.user.lang = x;
        String[] sidebar = StringList.LobbySideBar[x];
        SideBar.getSidebar(event.getPlayer()).setLineByScore(sidebar[2], 7).setLineByScore(sidebar[5], 4).setLineByScore(sidebar[8], 1);
        member.user.sound(Sound.NOTE_PLING);
        member.user.sendMessage("%lobby_msg_translate_success%");
        member.user.dbUpdate("lang=" + x);
        event.getPlayer().closeInventory();
    }
}
