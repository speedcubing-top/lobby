package top.speedcubing.lobby.BukkitEventListener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.pluginMessage.BungeePluginMessage;
import top.speedcubing.lib.utils.ByteArrayDataBuilder;
import top.speedcubing.lobby.List.ItemStackList;
import top.speedcubing.lobby.List.StringList;
import top.speedcubing.lobby.Member;
import top.speedcubing.server.speedcubingServer;

public class InventoryClick implements Listener {

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        InventoryHolder holder = inventory.getHolder();
        int RawSlot = e.getRawSlot();
        if (RawSlot != -999) {
            if (inventory.getName().equals("container.crafting")) {
                if (e.getWhoClicked().getGameMode() == GameMode.ADVENTURE)
                    e.setCancelled(true);
            } else if (holder == ItemStackList.NavigatorHolder) {
                e.setCancelled(true);
                switch (RawSlot) {
                    case 16:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "knockbackffa");
                        break;
                    case 10:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "mlgrush");
                        break;
                    case 28:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "practice");
                        break;
                    case 23:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "fastbuilder");
                        break;
                    case 34:
                        BungeePluginMessage.switchServer((Player) e.getWhoClicked(), "clutch");
                        break;
                    case 21: {
                        Player player = (Player) e.getWhoClicked();
                        player.closeInventory();
                        player.playSound(new Location(Bukkit.getWorld("world"), 0.5, 99.01563, -15.5), Sound.ENDERMAN_TELEPORT, 1F, 1F);
                        player.teleport(new Location(Bukkit.getWorld("world"), 7.5, 99, 21.5, 0, 0));
                    }
                    break;
                }
            } else if (holder == ItemStackList.LangHolder) {
                e.setCancelled(true);
                int x = -1;
                switch (RawSlot) {
                    case 10:
                        x = 0;
                        break;
                    case 12:
                        x = 1;
                        break;
                    case 14:
                        x = 2;
                        break;
                    case 16:
                        x = 3;
                        break;
                }
                if (x != -1) {
                    Player player = (Player) e.getWhoClicked();
                    Member member = Member.getMember(player);
                    member.user.lang = x;
                    String[] sidebar = StringList.LobbySideBar[x];
                    SideBar.getSidebar(player).setLineByScore(sidebar[2], 7).setLineByScore(sidebar[5], 4).setLineByScore(sidebar[8], 1);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    member.user.sendLangMessage(StringList.LangSettings);
                    member.user.dbUpdate("lang=" + x);
                    player.closeInventory();
                }
            } else if (holder == ItemStackList.SettingsHolder) {
                e.setCancelled(true);
                switch (RawSlot) {
                    case 11: {
                        Player player = (Player) e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        player.openInventory(ItemStackList.Lang[Member.getMember(player).user.lang]);
                    }
                    break;
                    case 12: {
                        Player player = (Player) e.getWhoClicked();
                        player.openInventory(ItemStackList.ResetConfirm[Member.getMember(player).user.lang]);
                    }
                    break;
                    case 13: {
                        Player player = (Player) e.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        Member member = Member.getMember(player);
                        member.messagetoggled = !member.messagetoggled;
                        e.getClickedInventory().setItem(22, ItemStackList.toggle[member.messagetoggled ? 0 : 1][member.user.lang]);
                        member.user.dbUpdate("messagable=" + (member.messagetoggled ? 1 : 0));
                    }
                    break;
                    case 15: {
                        Player player = (Player) e.getWhoClicked();
                        Member member = Member.getMember(player);
                        switch (e.getAction()) {
                            case PICKUP_ALL: {
                                player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                                player.closeInventory();
                                member.user.sendLangMessage(StringList.discord);
                                member.discord = true;
                                speedcubingServer.node(true, member.user.id, member.user.tcpPort);
                            }
                            break;
                            case PICKUP_HALF: {
                                player.closeInventory();
                                String discord = member.user.dbSelect("discord").getString();
                                if (!discord.equals("0"))
                                    member.user.dbUpdate("discord=0");
                                member.discord = false;
                                speedcubingServer.node(false, member.user.id, member.user.tcpPort);
                                member.user.sendLangMessage(StringList.discordremoved);
                            }
                            break;
                        }
                    }
                    break;
                }
            } else if (holder == ItemStackList.GameSelectHolder) {
                e.setCancelled(true);
                if (RawSlot < 5)
                    speedcubingServer.tcpClient.send(25604 - Bukkit.getPort() % 2, new ByteArrayDataBuilder().writeUTF("reduce").writeInt(Member.getMember(e.getWhoClicked()).user.id).writeInt(RawSlot).writeInt(speedcubingServer.tcpServer.getLocalPort()).toByteArray());
            } else if (holder == ItemStackList.confirm){
                e.setCancelled(true);
                switch (RawSlot){
                    case 11:
                        Member member = Member.getMember(e.getWhoClicked());
                    {
                        speedcubingServer.connection.update("playersdata",
                                "api=default(api)," +
                                        "chatfilt=default(chatfilt)," +
                                        "messagable=default(messagable)," +
                                        "chat=default(chat)," +
                                        "lang=default(lang)," +
                                        "skinvalue=default(skinvalue)," +
                                        "skinsignature=default(skinsignature)," +
                                        "mlgrush_stick=default(mlgrush_stick)," +
                                        "mlgrush_pickaxe=default(mlgrush_pickaxe)," +
                                        "mlgrush_block=default(mlgrush_block)," +
                                        "mlgrush_stickskin=default(mlgrush_stickskin)," +
                                        "mlgrush_blockskin=default(mlgrush_blockskin)," +
                                        "mlgrush_autoreset=default(mlgrush_autoreset)," +
                                        "mlgrush_hitdelay=default(mlgrush_hitdelay)," +
                                        "mlgrush_falldamage=default(mlgrush_falldamage)," +
                                        "mlgrush_autoremove=default(mlgrush_autoremove)," +
                                        "mlgrush_oldkb=default(mlgrush_oldkb)," +
                                        "reduce_stick=default(reduce_stick)," +
                                        "reduce_setting=default(reduce_setting)," +
                                        "reduce_reach=default(reduce_reach)," +
                                        "reduce_ver=default(reduce_ver)," +
                                        "reduce_hor=default(reduce_hor)," +
                                        "reduce_vanilla_reach=default(reduce_vanilla_reach)," +
                                        "reduce_vanilla_hor=default(reduce_vanilla_hor)," +
                                        "reduce_block=default(reduce_block)," +
                                        "reduce_stickskin=default(reduce_stickskin)," +
                                        "reduce_blockskin=default(reduce_blockskin)," +
                                        "reduce_npcstick=default(reduce_npcstick)," +
                                        "reduce_spec=default(reduce_spec)," +
                                        "reduce_allowspec=default(reduce_allowspec)," +
                                        "reduce_npccolor=default(reduce_npccolor)," +
                                        "reduce_npcsound=default(reduce_npcsound)," +
                                        "reduce_npcswing=default(reduce_npcswing)," +
                                        "reduce_vanilla=default(reduce_vanilla)," +
                                        "kbffa_inventory=default(kbffa_inventory)," +
                                        "kbffa_kill=default(kbffa_kill)," +
                                        "kbffa_death=default(kbffa_death)," +
                                        "kbffa_killstreak=default(kbffa_killstreak)," +
                                        "kbffa_dailykill=default(kbffa_dailykill)," +
                                        "duels_nodebuff=default(duels_nodebuff)," +
                                        "duels_combo=default(duels_combo)," +
                                        "duels_nodamage=default(duels_nodamage)," +
                                        "duels_boxing=default(duels_boxing)," +
                                        "duels_gapple=default(duels_gapple)," +
                                        "duels_bow=default(duels_bow)," +
                                        "fb_0_pb=default(fb_0_pb)," +
                                        "fb_1_pb=default(fb_1_pb)," +
                                        "fb_2_pb=default(fb_2_pb)," +
                                        "fb_3_pb=default(fb_3_pb)," +
                                        "fb_4_pb=default(fb_4_pb)," +
                                        "fastbuild_blockskin=default(fastbuild_blockskin)," +
                                        "fastbuild_falldelay=default(fastbuild_falldelay)," +
                                        "clutch_blockskin=default(clutch_blockskin)," +
                                        "clutch_normal_hits=default(clutch_normal_hits)," +
                                        "clutch_inclined_hits=default(clutch_inclined_hits)," +
                                        "clutch_normal_ver=default(clutch_normal_ver)," +
                                        "clutch_inclined_ver=default(clutch_inclined_ver)," +
                                        "clutch_normal_hitdelay=default(clutch_normal_hitdelay)," +
                                        "clutch_inclined_hitdelay=default(clutch_inclined_hitdelay)," +
                                        "customfb_x=default(customfb_x)," +
                                        "customfb_y=default(customfb_y)," +
                                        "nickpriority=default(nickpriority)", "id=" + member.user.id);
                    }
                    ((Player) e.getWhoClicked()).kickPlayer("Sucessfully reset your stats.\nPlease rejoin.");
                        break;
                    case 15:
                        e.getWhoClicked().closeInventory();
                        break;
                }
            }
        }
    }
}