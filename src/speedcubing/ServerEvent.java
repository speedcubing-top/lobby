package speedcubing;

import cubing.lib.bukkit.Event.ServerEventHandler;
import cubing.lib.bukkit.Event.events.PlayInChatEvent;
import cubing.lib.bukkit.Event.events.PlayInUseEntityEvent;
import cubing.lib.bukkit.packetwrapper.OutPlayerListHeaderFooter;
import cubing.lib.utils.Console;
import cubing.lib.utils.Reflections;
import cubingserver.PluginMessage;
import cubingserver.StringList.GlobalString;
import cubingserver.customEvents.SocketEvent;
import cubingserver.customEvents.UDPEvent;
import cubingserver.libs.User;
import cubingserver.speedcubingServer;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;

import java.util.UUID;

public class ServerEvent {

    @ServerEventHandler
    public void socketEvent(SocketEvent e) {
        String[] rs = e.rs;
        if (rs != null && rs[0].equals("d"))
            PluginMessage.switchServer(Bukkit.getPlayer(UUID.fromString(rs[1])), "reduce");
    }

    @ServerEventHandler
    public void UDPEvent(UDPEvent e) {
        String[] rs = e.rs;
        if (rs[0].equals("o")) {
            EntityArmorStand stand = null;
            switch (rs[1]) {
                case "1":
                    speedcubingServer.AllPlayers = Integer.parseInt(rs[2]);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        int lang = User.getLang(p.getUniqueId());
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(OutPlayerListHeaderFooter.a(GlobalString.LobbyTabList[0][lang], GlobalString.LobbyTabList[1][lang].replace("%int%", rs[2])));
                        Team team = p.getScoreboard().getTeam(StringList.LobbySideBar[lang][6]);
                        if (team != null)
                            team.setSuffix(rs[2]);
                    }
                    break;
                case "2":
                    EntityList.Lobbys[9].setCustomName("§e§l" + rs[2] + " Players");
                    stand = EntityList.Lobbys[9];
                    break;
                case "3":
                    EntityList.Lobbys[1].setCustomName("§e§l" + rs[2] + " Players");
                    stand = EntityList.Lobbys[1];
                    break;
                case "4":
                    EntityList.Lobbys[5].setCustomName("§e§l" + rs[2] + " Players");
                    stand = EntityList.Lobbys[5];
                    break;
                case "6":
                    EntityList.Lobbys[7].setCustomName("§e§l" + rs[2] + " Players");
                    stand = EntityList.Lobbys[7];
                    break;
                case "7":
                    EntityList.Lobbys[3].setCustomName("§e§l" + rs[2] + " Players");
                    stand = EntityList.Lobbys[3];
                    break;
            }
            if (stand != null) {
                PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
                Bukkit.getOnlinePlayers().forEach(a -> ((CraftPlayer) a).getHandle().playerConnection.sendPacket(packet));
            }
        }
    }

    @ServerEventHandler
    public void PlayInUseEntityEvent(PlayInUseEntityEvent e) {
        Player player = e.player;
        PacketPlayInUseEntity packet = e.packet;
        if (player.getItemInHand().getType() == org.bukkit.Material.AIR && packet.a().equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
            switch (EntityList.NPCS.indexOf(Reflections.getField(packet, "a"))) {
                case 0:
                    PluginMessage.switchServer(player, "mlgrush");
                    break;
                case 1:
                    Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> player.openInventory(ItemStackList.GameSelect[User.getLang(player.getUniqueId())]));
                    break;
                case 2:
                    PluginMessage.switchServer(player, "kbffa");
                    break;
                case 3:
                    PluginMessage.switchServer(player, "clutch");
                    break;
                case 4:
                    PluginMessage.switchServer(player, "fastbuilder");
                    break;
            }
    }

    @ServerEventHandler
    public void PlayInChatEvent(PlayInChatEvent e) {
        e.setCancelled(true);
        Player player = e.player;
        String message = e.packet.a();
        UUID uuid = player.getUniqueId();
        if (Main.discord.contains(uuid)) {
            e.setCancelled(true);
            if (message.split(" ").length == 1 && message.matches("([a-z]+#[0-9]{4})")) {
                speedcubingServer.connection.update("playersdata", "discord='" + message + "'", "uuid='" + uuid + "'");
                player.sendMessage("§aSuccessfully updated the discord. (§6" + message + "§a)\nYou can now link your minecraft account in the Discord Server!");
            } else player.sendMessage(StringList.discordinvalid[User.getLang(uuid)]);
            Main.discord.remove(uuid);
            speedcubingServer.node(false, uuid);
        } else if (message.charAt(0) != '/') {
            e.setCancelled(true);
            String[] formats = User.getFormat(User.getRank(uuid));
            String text = formats[0] + player.getName();
            String[] str = GlobalString.MessageFormat[0];
            String[] out = new String[]{
                    str[0].replace("%text%", text).replace("%text2%", formats[2] + message),
                    str[1].replace("%text%", text).replace("%text2%", formats[2] + message),
                    str[2].replace("%text%", text).replace("%text2%", formats[2] + message),
                    str[3].replace("%text%", text).replace("%text2%", formats[2] + message)
            };
            Bukkit.getOnlinePlayers().forEach(a -> a.sendMessage(out[User.getLang(a.getUniqueId())]));
            Console.sendColoredConsole("§7[§aChatLog§7] [§b" + player.getWorld().getName() + "§7] " + out[1]);
        }
    }
}
