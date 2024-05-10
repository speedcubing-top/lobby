package top.speedcubing.lobby.gamble;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import top.speedcubing.lib.bukkit.inventory.InventoryBuilder;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.server.player.User;

import java.util.Objects;

public class BaccaratCore implements Listener {
    Baccarat baccarat;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand() == null) {
            return;
        }
        if (!e.getPlayer().getItemInHand().hasItemMeta()) {
            return;
        }
        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Gamble")) {
            InventoryBuilder inventoryBuilder = new InventoryBuilder(9, "Baccarat");
            inventoryBuilder.setItem(new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build(), 0,1, 2, 3, 5, 6, 7, 8);
            inventoryBuilder.setItem(new ItemBuilder(Material.DIAMOND).name("§aStart").addLore("§eRatio size game.").addLore("§eChoose one of Backer, Player, and Draw.").build(), 4);
            e.getPlayer().openInventory(inventoryBuilder.getInventory());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equals("Baccarat")) {
            e.setCancelled(true);
            if (e.getRawSlot() == 4) {
                InventoryBuilder inv = new InventoryBuilder(9, "Baccarat Bet");
                inv.setItem(new ItemBuilder(Material.GOLD_INGOT).name("§eYour coins: " + User.getUser(player).dbSelect("coins").getInt()).build(), 0);
                inv.setItem(new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build(), 1,2,6,7);
                inv.setItem(new ItemBuilder(Material.REDSTONE).name("§cReduce 100 coins").build(), 3);
                inv.setItem(new ItemBuilder(Material.EMERALD).name("§aCurrent bet coins: 100").build(), 4);
                inv.setItem(new ItemBuilder(Material.LAPIS_BLOCK).name("§9Add 100 coins").build(), 5);
                inv.setItem(new ItemBuilder(Material.EMERALD_BLOCK).name("§aConfirm bet").build(), 8);
                player.openInventory(inv.getInventory());
            }
        }
        if (e.getInventory().getTitle().equals("Baccarat Bet")) {
            e.setCancelled(true);
            int playerCoins = Integer.parseInt(e.getClickedInventory().getItem(0).getItemMeta().getDisplayName().split(": ")[1].trim());
            int betCoins = Integer.parseInt(e.getClickedInventory().getItem(4).getItemMeta().getDisplayName().split(": ")[1].trim());
            if (e.getRawSlot() == 3) {
                if (betCoins - 100 < 100) {
                    player.sendMessage("§cYou can't bet less than 0 coins!");
                    player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                    return;
                }
                ItemMeta meta = e.getClickedInventory().getItem(4).getItemMeta();
                meta.setDisplayName("§aCurrent bet coins: " + (betCoins - 100));
                e.getClickedInventory().getItem(4).setItemMeta(meta);
                player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
            }
            if (e.getRawSlot() == 5) {
                if (betCoins + 100 > playerCoins) {
                    player.sendMessage("§cYou don't have enough coins!");
                    player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                    return;
                }
                ItemMeta meta = e.getClickedInventory().getItem(4).getItemMeta();
                meta.setDisplayName("§aCurrent bet coins: " + (betCoins + 100));
                e.getClickedInventory().getItem(4).setItemMeta(meta);
                player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
            }
            if (e.getRawSlot() == 8) {
                if (betCoins > playerCoins) {
                    player.sendMessage("§cYou don't have enough coins!");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
                    player.closeInventory();
                    return;
                }
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                baccarat = new Baccarat(player, betCoins);
                baccarat.start();
            }
        }
        if (e.getInventory().getTitle().equals("Baccarat Game")) {
            e.setCancelled(true);
            Baccarat.BaccaratResult result = null;
            switch (e.getRawSlot()) {
                case 0:
                    result = baccarat.setChoose(Baccarat.BaccaratChoose.BACKER);
                    player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                    break;
                case 4:
                    result = baccarat.setChoose(Baccarat.BaccaratChoose.PLAYER);
                    player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                    break;
                case 8:
                    result = baccarat.setChoose(Baccarat.BaccaratChoose.DRAW);
                    player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                    break;
            }
            player.sendMessage(result + " Backer: " + baccarat.getBackerScore() + " Player: " + baccarat.getPlayerScore());
            assert result != null;
            if (Objects.equals(baccarat.getBaccaratChoose().toString(), result.toString())) {
                if (result == Baccarat.BaccaratResult.DRAW) {
                    player.sendMessage("You win!");
                    baccarat.addPlayerTotalCoins(baccarat.getBetCoins() * 8);
                    e.getWhoClicked().sendMessage("You now have " + baccarat.getPlayerTotalCoins() + " coins");
                } else if (result == Baccarat.BaccaratResult.PLAYER){
                    player.sendMessage("You win!");
                    baccarat.addPlayerTotalCoins(baccarat.getBetCoins());
                    e.getWhoClicked().sendMessage("You now have " + baccarat.getPlayerTotalCoins() + " coins");
                } else if (result == Baccarat.BaccaratResult.BACKER) {
                    player.sendMessage("You win!");
                    baccarat.addPlayerTotalCoins((int) (baccarat.getBetCoins() * 0.95));
                }
            } else {
                e.getWhoClicked().sendMessage("You lose!");
            }
        }
    }
}
