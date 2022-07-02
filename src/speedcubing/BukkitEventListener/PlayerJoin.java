package speedcubing.BukkitEventListener;

import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.Member;
import speedcubing.lib.bukkit.SideBar;
import speedcubing.lib.bukkit.entity.NPC;
import speedcubing.server.speedcubingServer;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.sendTitle("", "");
        player.setGameMode(GameMode.ADVENTURE);
        Member member = Member.getMember(player);
        if (!speedcubingServer.isBungeeOnlineMode && member.haveToLogin) {
            player.teleport(new Location(Bukkit.getWorld("login"), 0.5, 100, 0.5, 0, 0));
            player.sendMessage("§aDo §6/premium§a to enable Skip-Login if this account is premium!");
            if (speedcubingServer.connection.selectString("playersdata", "password", "id=" + member.id).equals(""))
                player.sendMessage("/register <password> <confirm password>");
            else player.sendMessage("/login <password>");
        } else joinLobby(player, false);
    }

    public static void joinLobby(Player player, boolean login) {
        Member user = new Member(player.getUniqueId());
        user.haveToLogin = false;
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0));
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        Inventory inventory = player.getInventory();
        String[] format = speedcubingServer.getFormat(user.rank);
        String name = player.getName();
        player.getInventory().setHeldItemSlot(0);
        if (!login)
            player.sendMessage(StringList.LobbyJoinMessage[user.lang]
                    .replace("%f0%", format[0])
                    .replace("%f1%", format[2])
                    .replace("%name%", name));
        player.getWorld().getPlayers().forEach(a -> a.sendMessage(StringList.LobbyJoinMessage[Member.getMember(a).lang].replace("%f0%", format[0]).replace("%f1%", format[2]).replace("%name%", name)));
        String[] sidebar = StringList.LobbySideBar[user.lang];
        new SideBar(player, sidebar[0])
                .staticLine(sidebar[1], 8)
                .changeableLine(sidebar[2], 7)
                .changeableLine(sidebar[3] + "loading...", 6)
                .staticLine(sidebar[4], 5)
                .changeableLine(sidebar[5], 4)
                .staticLine(sidebar[6].replace("%player%", format[0] + format[1] + name), 3)
                .staticLine(sidebar[7], 2)
                .changeableLine(sidebar[8], 1)
                .staticLine(sidebar[9], 0).create();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (NPC npc : EntityList.npcs) {
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc.entityPlayer));
                }
            }
        }, 4000);
        inventory.clear();
        inventory.setItem(0, ItemStackList.Navigator[user.lang]);
        inventory.setItem(1, ItemStackList.Settings[user.lang]);
    }
}






