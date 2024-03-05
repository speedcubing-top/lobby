package top.speedcubing.lobby.BukkitEventListener;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import top.speedcubing.lib.bukkit.*;
import top.speedcubing.lib.bukkit.inventory.BookBuilder;
import top.speedcubing.lobby.*;
import top.speedcubing.lobby.BukkitCommands.terms;
import top.speedcubing.lobby.List.*;
import top.speedcubing.lobby.easteregg.JoinEasterEgg;
import top.speedcubing.server.database.DataCenter;
import top.speedcubing.server.lang.GlobalString;

import java.util.*;

public class PlayerJoin implements Listener {

    public static final Set<String> joinCrash = new HashSet<>();

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Random random = new Random();
        int randomNumber = random.nextInt(500);
        if (randomNumber == 0) {
            JoinEasterEgg.start(player);
        }
        if (joinCrash.contains(player.getName().toLowerCase()))
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    PlayerUtils.explosionCrash(player);
                }
            }, 1000);
        Member member = new Member(player);
        player.sendTitle("", "");
        player.setGameMode(GameMode.ADVENTURE);
//        if (!speedcubingServer.isBungeeOnlineMode && member.haveToLogin) {
//            player.teleport(new Location(Bukkit.getWorld("login"), 0.5, 100, 0.5, 0, 0));
//            player.sendMessage("§aDo §6/premium§a to enable Skip-Login if this account is premium!");
//            if (member.user.dbSelect("password").getString().equals(""))
//                player.sendMessage("/register <password> <confirm password>");
//            else player.sendMessage("/login <password>");
//        } else
        if (!member.agree) {
            player.getInventory().clear();
            player.getInventory().setItem(0, terms.book);
            player.teleport(new Location(Bukkit.getWorld("agreement"), 0.5, 100, 0.5, 0, 0));
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(player);
                player.hidePlayer(p);
            }
            BookBuilder.openBook(terms.book, player);
        } else
            joinLobby(player, true);
    }
    public static void joinLobby(Player player, boolean joinEvent) {
        Member member = Member.getMember(player);
        member.haveToLogin = false;
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0));
        player.setHealth(player.getMaxHealth());
        player.setSaturation(20);
        Inventory inventory = player.getInventory();
        String[] format = member.user.getFormat();
        String name = player.getName();
        String text1 = format[0] + name + format[2];
        player.getInventory().setHeldItemSlot(0);
        //fly
        boolean isFly = member.user.dbSelect("fly").getBoolean();
        if (isFly && member.user.isStaff || member.user.realRank.equals("champ")) {
            member.user.player.performCommand("fly");
        }
        if (isFly && !member.user.isStaff && member.user.realRank.equals("champ")) {
            member.user.dbUpdate("fly=" + 0);
        }
        if (joinEvent && !member.user.vanished) {
            member.user.sendLangMessage(GlobalString.LobbyJoinMessage, text1);
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.showPlayer(player);
                if (!Member.getMember(p).user.vanished)
                    player.showPlayer(p);
            }
        }
        player.getWorld().getPlayers().forEach(a -> Member.getMember(a).user.sendLangMessage(GlobalString.LobbyJoinMessage, text1));
        String[] sidebar = StringList.LobbySideBar[member.user.lang];
        new SideBar(player, sidebar[0])
                .staticLine(sidebar[1], 8)
                .changeableLine(sidebar[2], 7)
                .changeableLine(sidebar[3] + DataCenter.onlineCount, 6)
                .staticLine(sidebar[4], 5)
                .changeableLine(sidebar[5], 4)
                .staticLine(sidebar[6].replace("%player%", format[0] + name), 3)
                .staticLine(sidebar[7], 2)
                .changeableLine(sidebar[8], 1)
                .staticLine(sidebar[9], 0).create();
        inventory.clear();
        inventory.setItem(0, ItemStackList.Navigator[member.user.lang]);
        inventory.setItem(1, ItemStackList.Settings[member.user.lang]);
    }
}





