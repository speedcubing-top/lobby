package top.speedcubing.lobby.BukkitEventListener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lobby.List.ItemStackList;
import top.speedcubing.lobby.List.StringList;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.libs.GlobalString;
import top.speedcubing.server.speedcubingServer;

public class PlayerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Member member = new Member(player);
        player.sendTitle("", "");
        player.setGameMode(GameMode.ADVENTURE);
        if (!speedcubingServer.isBungeeOnlineMode && member.haveToLogin) {
            player.teleport(new Location(Bukkit.getWorld("login"), 0.5, 100, 0.5, 0, 0));
            player.sendMessage("§aDo §6/premium§a to enable Skip-Login if this account is premium!");
            if (member.user.dbSelect("password").getString().equals(""))
                player.sendMessage("/register <password> <confirm password>");
            else player.sendMessage("/login <password>");
        } else joinLobby(player, false);
    }

    public static void joinLobby(Player player, boolean login) {
        Member member = Member.getMember(player);
        member.haveToLogin = false;
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0));
        Inventory inventory = player.getInventory();
        String[] format = speedcubingServer.getFormat(member.user.rank);
        String name = player.getName();
        String text1 = format[0] + name + format[2];
        player.getInventory().setHeldItemSlot(0);
        if (!login)
            member.user.langMessageSender(GlobalString.LobbyJoinMessage).replace("%t%", text1).send();
        player.getWorld().getPlayers().forEach(a -> Member.getMember(a).user.langMessageSender(GlobalString.LobbyJoinMessage).replace("%t%", text1).send());
        String[] sidebar = StringList.LobbySideBar[member.user.lang];
        new SideBar(player, sidebar[0])
                .staticLine(sidebar[1], 8)
                .changeableLine(sidebar[2], 7)
                .changeableLine(sidebar[3] + speedcubingServer.getOnlineCount(), 6)
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






