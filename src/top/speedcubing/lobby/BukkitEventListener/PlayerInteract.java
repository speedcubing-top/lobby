package top.speedcubing.lobby.BukkitEventListener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.lobby.List.ItemStackList;
import top.speedcubing.lobby.List.StringList;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.speedcubingServer;

public class PlayerInteract implements Listener {

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        switch (player.getItemInHand().getType()) {
            case COMPASS:
                player.openInventory(ItemStackList.navigator[Member.getMember(player).user.lang]);
                break;
            case REDSTONE_COMPARATOR:
                Member member = Member.getMember(player);
                String data = speedcubingServer.connection.select("discord").from("playersdata").where("id=" + member.user.id).getString();
                Inventory inv = Bukkit.createInventory(ItemStackList.SettingsHolder, 36, StringList.settingstitle[member.user.lang]);
                inv.setItem(11, ItemStackList.lang[member.user.lang]);
                inv.setItem(13, ItemStackList.dm[member.user.lang]);
                inv.setItem(22, ItemStackList.toggle[member.messagetoggled ? 0 : 1][member.user.lang]);
                inv.setItem(15, new ItemBuilder(Material.SKULL_ITEM).durability(3).name(StringList.dc[member.user.lang]).lore(
                                data.equals("0") ? StringList.discordB[member.user.lang] : StringList.discordA[member.user.lang].replace("%a%", data), StringList.discordEdit[member.user.lang])
                        .url("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19").build());
                player.openInventory(inv);
                break;
        }
        if (e.getClickedBlock() != null && player.getGameMode() == GameMode.ADVENTURE)
            e.setCancelled(true);
    }
}