package top.speedcubing.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import top.speedcubing.server.speedcubingServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BadApple implements Listener, CommandExecutor {


    public static void init() {
        Bukkit.getPluginManager().registerEvents(new BadApple(), speedcubingServer.getPlugin(speedcubingServer.class));
        Bukkit.getPluginCommand("badapple").setExecutor(new BadApple());
        try {
            File file = new File("../../../datas.txt");
            BufferedReader input = new BufferedReader(new FileReader(file));
            String line = "";
            boolean a = true;
            while (a) {
                line = input.readLine();
                if (line == null)
                    a = false;
                s.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(s.size());
    }

    double frame;
    int timers = -1;
    public static List<String> s = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String a, String[] strings) {
        Player player = (Player) commandSender;
        World world = Bukkit.getWorld("world");
        if (strings.length == 0) {
            if (timers == -1) {
                frame = 0D;
                int daw = Bukkit.getScheduler().scheduleSyncRepeatingTask(Lobby.getPlugin(Lobby.class),
                        new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    if (frame == s.size()) {
                                        Bukkit.getScheduler().cancelTask(timers);
                                        timers = -1;
                                        frame = -1;
                                    }
                                    int aa = (int) frame;
                                    System.out.println(aa);
                                    char[] that = s.get(aa).toCharArray();
                                    frame += 1.5;
                                    int index = 0;
                                    for (int x = 295; x > 295 - 90; x--) {
                                        for (int z = -235; z < -235 + 120; z++) {
                                            world.getBlockAt(x, 99, z).setData(that[index] == '0' ? (byte) 15 : (byte) 0);
                                            index++;
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 20, 1);
                timers = daw;
            } else {
                Bukkit.getScheduler().cancelTask(timers);
                timers = -1;
                frame = -1;
            }
        } else {
            for (int z = -235; z < -235 + 120; z++) {
                for (int x = 295; x > 295 - 90; x--) {
                    world.getBlockAt(x, 99, z).setType(Material.WOOL);
                }
            }
        }

        return true;
    }
}
