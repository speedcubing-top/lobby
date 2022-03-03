package speedcubing.All;

import cubing.bukkit.api.BungeePluginMessage;
import cubing.utils.Reflections;
import cubingserver.libs.PlayerData;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.Main;

import java.util.List;

public class NPCClick {

    public void PacketInject(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;

        if (channel.pipeline().get("PacketPlayOutInUseEntity") != null)
            return;
        channel.pipeline().addAfter("decoder", "PacketPlayInUseEntity", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
            @Override
            protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) {
                arg.add(packet);
                if (player.getItemInHand().getType() == org.bukkit.Material.AIR && packet.a().equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                    switch (EntityList.NPCS.indexOf(Reflections.getField(packet, "a"))) {
                        case 0:
                            BungeePluginMessage.switchServer(player, "mlgrush");
                            break;
                        case 1:
                            Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> player.openInventory(ItemStackList.GameSelect[PlayerData.getLang(player.getUniqueId())]));
                            break;
                        case 2:
                            BungeePluginMessage.switchServer(player, "kbffa");
                            break;
                        case 3:
                            BungeePluginMessage.switchServer(player, "clutch");
                            break;
                        case 4:
                            BungeePluginMessage.switchServer(player, "fastbuilder");
                            break;
                    }
            }
        });
    }
}
