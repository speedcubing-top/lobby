package top.speedcubing.lobby.bukkitlistener;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.common.database.DatabaseData;
import top.speedcubing.common.rank.RankFormat;
import top.speedcubing.lib.bukkit.PlayerUtils;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.inventory.BookBuilder;
import top.speedcubing.lib.bukkit.inventory.ItemBuilder;
import top.speedcubing.lobby.Lobby;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.configuration.Configuration;
import top.speedcubing.lobby.cubingcmd.terms;
import top.speedcubing.lobby.list.ItemStackList;
import top.speedcubing.lobby.list.StringList;
import top.speedcubing.server.lang.Lang;
import top.speedcubing.server.speedcubingServer;

public class PlayerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Random random = new Random();
//        int randomNumber = random.nextInt(500);
//        if (randomNumber == 0) {
//            JoinEasterEgg.start(player);
//        }
        if (Configuration.joinCrash.contains(player.getName().toLowerCase()))
            speedcubingServer.scheduledPool.schedule(() -> PlayerUtils.explosionCrash(player), 1000, TimeUnit.MILLISECONDS);
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
            player.getInventory().setItem(0, terms.book(member.user.lang));
            player.teleport(new Location(Bukkit.getWorld("agreement"), 0.5, 100, 0.5, 0, 0).add(Lobby.offset));
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(player);
                player.hidePlayer(p);
            }
            BookBuilder.openBook(terms.book(member.user.lang), player);
        } else
            joinLobby(player, true);
    }

    public static void joinLobby(Player player, boolean joinEvent) {
        Member member = Member.getMember(player);
        member.haveToLogin = false;
        player.teleport(new Location(Bukkit.getWorlds().get(0), 0.5, 100, 0.5, 0, 0).add(Lobby.offset));
        player.setHealth(player.getMaxHealth());
        player.setSaturation(20);
        Inventory inventory = player.getInventory();
        RankFormat format = member.user.getFormat(false);
        String name = player.getName();
        String text1 = format.getPrefix() + name + format.getChatColor();
        player.getInventory().setHeldItemSlot(0);
        // sidebar and hotbar first, so a failure in the extras below can't leave the player without them
        String[] sidebar = StringList.LobbySideBar[member.user.lang];
        new SideBar(player, sidebar[0])
                .staticLine(sidebar[1], 8)
                .changeableLine(sidebar[2], 7)
                .changeableLine(sidebar[3] + DatabaseData.onlineCount, 6)
                .staticLine(sidebar[4], 5)
                .changeableLine(sidebar[5], 4)
                .staticLine(sidebar[6].replace("%player%", format.getPrefix() + name), 3)
                .staticLine(sidebar[7], 2)
                .changeableLine(sidebar[8], 1)
                .staticLine(sidebar[9], 0).create();
        inventory.clear();
        member.user.setLangItem(0, ItemStackList.Navigator);
        member.user.setLangItem(1, ItemStackList.Settings);
        //profile
        ItemStack profile = new ItemBuilder(Material.SKULL_ITEM)
                .durability(3)
                .name(Lang.of("%lang_lobby_item_profile%").getComponent(member.user.lang).toText())
                .skullFromProfileValue(member.user.defaultSkin.getValue())
                .build();
        inventory.setItem(2, profile);
        //fly
        boolean isFly = member.user.data.getBoolean("flying");
        if (isFly && (member.user.isStaff || member.user.realRank.equals("champ"))) {
            member.user.player.performCommand("fly");
        }
        if (isFly && !member.user.isStaff && member.user.realRank.equals("champ")) {
            member.user.dbUpdate("flying=0");
        }
        if (joinEvent && !member.user.vanished) {
            member.user.sendMessage("%lang_lobby_msg_join_lobby%", text1);
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.showPlayer(player);
                Member m = Member.getMember(p);
                if (m != null && !m.user.vanished)
                    player.showPlayer(p);
            }
        }
        // players whose join failed have no Member; skip them instead of aborting this join
        player.getWorld().getPlayers().forEach(a -> {
            Member m = Member.getMember(a);
            if (m != null)
                m.user.sendMessage("%lang_lobby_msg_join_lobby%", text1);
        });
    }
}





