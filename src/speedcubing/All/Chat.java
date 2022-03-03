package speedcubing.All;

import cubing.utils.Console;
import cubingserver.StringList.GlobalString;
import cubingserver.libs.PlayerData;
import cubingserver.libs.Rank;
import cubingserver.speedcubingServer;
import cubingserver.things.events.PlayerJoin;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import speedcubing.List.StringList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class Chat {
    Pattern url = Pattern.compile("([a-z]+#[0-9]{4})");

    public static Set<UUID> discord = new HashSet<>();

    public void PacketInject(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        if (channel.pipeline().get("PacketPlayInChat") != null)
            return;
        channel.pipeline().addAfter("decoder", "PacketPlayInChat", new MessageToMessageDecoder<PacketPlayInChat>() {
            @Override
            protected void decode(ChannelHandlerContext channel, PacketPlayInChat packet, List<Object> arg) {
                String message = packet.a();
                UUID uuid = player.getUniqueId();
                if (discord.contains(uuid)) {
                    if (message.split(" ").length == 1 && url.matcher(message).find()) {
                        speedcubingServer.connection.update("playersdata", "discord='" + message + "'", "uuid='" + uuid + "'");
                        player.sendMessage("§aSuccessfully updated the discord. (§6" + message + "§a)\nYou can now link your minecraft account in the Discord Server!");
                    } else player.sendMessage(StringList.discordinvalid[PlayerData.getLang(uuid)]);
                    Chat.discord.remove(uuid);
                    return;
                }
                long time = System.currentTimeMillis();
                boolean spam = PlayerJoin.Canspam.contains(uuid);
                if (!spam && time - speedcubingServer.spam.get(uuid) < 500)
                    player.sendMessage(GlobalString.spam[PlayerData.getLang(uuid)]);
                else if (message.charAt(0) != '/') {
                    if (!spam && speedcubingServer.lastmsg.get(uuid).equals(message))
                        player.sendMessage(GlobalString.samemsg[PlayerData.getLang(uuid)]);
                    else {
                        String[] formats = Rank.format(uuid);
                        String text = formats[0] + formats[1] + player.getName();
                        String[] str = GlobalString.MessageFormat[0];
                        String[] out = new String[]{
                                str[0].replace("%text%", text).replace("%text2%", formats[2] + message),
                                str[1].replace("%text%", text).replace("%text2%", formats[2] + message),
                                str[2].replace("%text%", text).replace("%text2%", formats[2] + message),
                                str[3].replace("%text%", text).replace("%text2%", formats[2] + message)
                        };
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(out[PlayerData.getLang(p.getUniqueId())]);
                        }
                        Console.sendColoredConsole("§7[§aChatLog§7] [§b" + player.getWorld().getName() + "§7] " + out[1]);
                        speedcubingServer.lastmsg.put(uuid, message);
                    }
                } else arg.add(packet);
                speedcubingServer.spam.put(uuid, time);
            }
        });
    }
}
