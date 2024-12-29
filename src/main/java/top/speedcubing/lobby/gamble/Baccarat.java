package top.speedcubing.lobby.gamble;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import top.speedcubing.lib.bukkit.inventory.InventoryBuilder;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.server.player.User;

import java.util.Random;

public class Baccarat {
    Player player;
    int backerScore;
    int playerScore;
    int coins;
    Random random;
    BaccaratChoose baccaratChoose;

    public Baccarat(Player player, int coins) {
        this.player = player;
        this.coins = coins;
        this.random = new Random();
    }

    public void start() {
        this.backerScore = random.nextInt(10) + 1;
        this.playerScore = random.nextInt(10) + 1;
        reducePlayerTotalCoins(coins);
        InventoryBuilder inv = new InventoryBuilder(9, "Baccarat Game");
        inv.setItem(new ItemBuilder(Material.EMERALD).name("§aBacker").build(), 0);
        inv.setItem(new ItemBuilder(Material.DIAMOND).name("§aPlayer").build(), 4);
        inv.setItem(new ItemBuilder(Material.PAPER).name("§aDraw").build(), 8);
        inv.setItem(new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build(), 1, 2, 3, 5, 6, 7);
        player.openInventory(inv.getInventory());
    }

    private BaccaratResult end() {
        if (getBackerScore() > getPlayerScore()) {
            return BaccaratResult.BACKER;
        } else if (getBackerScore() < getPlayerScore()) {
            return BaccaratResult.PLAYER;
        } else {
            return BaccaratResult.DRAW;
        }
    }

    public int getBackerScore() {
        return backerScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public BaccaratChoose getBaccaratChoose() {
        return baccaratChoose;
    }

    public int getBetCoins() {
        return coins;
    }

    public int getPlayerTotalCoins() {
        return User.getUser(player).dbSelect("coins").getInt(0);
    }

    public void addPlayerTotalCoins(int coins) {
        coins += getPlayerTotalCoins();
        User.getUser(player).dbUpdate("coins=" + coins);
    }
    public void reducePlayerTotalCoins(int coins) {
        coins = getPlayerTotalCoins() - coins;
        User.getUser(player).dbUpdate("coins=" + coins);
    }

    public BaccaratResult setChoose(BaccaratChoose choose) {
        this.baccaratChoose = choose;
        player.closeInventory();
        return end();
    }

    protected enum BaccaratChoose {
        BACKER, PLAYER, DRAW
    }

    protected enum BaccaratResult {
        BACKER, PLAYER, DRAW
    }
}
