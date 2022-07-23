package speedcubing.BukkitEventListener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.Member;
import speedcubing.lib.bukkit.inventory.ItemBuilder;
import speedcubing.lib.utils.SQL.SQLUtils;
import speedcubing.server.speedcubingServer;

public class PlayerInteract implements Listener {

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        switch (player.getItemInHand().getType()) {
            case COMPASS:
                player.openInventory(ItemStackList.navigator[Member.getMember(player).lang]);
                break;
            case REDSTONE_COMPARATOR:
                Member user = Member.getMember(player);
                String data = SQLUtils.getString(speedcubingServer.connection.select("playersdata", "discord", "id=" + user.id));
                Inventory inv = Bukkit.createInventory(ItemStackList.SettingsHolder, 36, StringList.settingstitle[user.lang]);
                inv.setItem(11, ItemStackList.lang[user.lang]);
                inv.setItem(13, ItemStackList.dm[user.lang]);
                inv.setItem(22, ItemStackList.toggle[user.messagetoggled ? 0 : 1][user.lang]);
                inv.setItem(15, new ItemBuilder(Material.SKULL_ITEM).durability(3).name(StringList.dc[user.lang]).lore(
                                data.equals("") ? StringList.discordB[user.lang] : StringList.discordA[user.lang].replace("%a%", data), StringList.discordEdit[user.lang])
                        .url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19").build());
                player.openInventory(inv);
                break;
        }
        if (e.getClickedBlock() != null && player.getGameMode() == GameMode.ADVENTURE)
            e.setCancelled(true);
    }
}