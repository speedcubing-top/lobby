package speedcubing.List;

import speedcubing.server.config;

public class StringList {
    public static String[] LobbyJoinMessage = {
            "%f0%%name%%f1% has joined the Lobby!",
            "%f0%%name%%f1% has joined the Lobby!",
            "%f0%%name%%f1% has joined the Lobby!",
            "%f0%%name%%f1% has joined the Lobby!"};
    public static String[] LangSettings = {
            "§8[§eSystem§8] §aErfolgreich auf Deutsch geändert!\n§8[§eSystem§8] §cDer Server ist noch nicht vollständig übersetzt.",
            "§8[§eSystem§8] §aSuccessfully changed to English!\n§8[§eSystem§8] §cThe server is not fully translated yet.",
            "§8[§e系統§8] §a成功切換為中文!\n§8[§e系統§8] §c伺服器目前尚未完全翻譯",
            "§8[§e系统§8] §a成功切换为简体中文!\n§8[§e系统§8] §c伺服器目前尚未完全翻译"};

    public static String[] discord = {
            "§aPlease input a valid discord id in the chat:",
            "§aPlease input a valid discord id in the chat:",
            "§aPlease input a valid discord id in the chat:",
            "§aPlease input a valid discord id in the chat:"
    };

    public static String[] settingstitle = {
            "§cEinstellungen", "§cSettings", "§c設定", "§c设置"
    };

    public static String[] discordremoved = {
            "§cYour discord id is cleared.",
            "§cYour discord id is cleared.",
            "§cYour discord id is cleared.",
            "§cYour discord id is cleared."
    };

    public static String[] dc = {"§aDiscord", "§aDiscord", "§aDiscord", "§aDiscord"};

    public static String[] discordA = {
            "§aYou are §6%a%",
            "§aYou are §6%a%",
            "§aYou are §6%a%",
            "§aYou are §6%a%"
    };

    public static String[] discordB =
            {"§cYou don't have any data yet.",
                    "§cYou don't have any data yet.",
                    "§cYou don't have any data yet.",
                    "§cYou don't have any data yet.",
            };

    public static String[] discordEdit = {"§aLeft: edit§8 , §cRight: remove", "§aLeft: edit§8 , §cRight: remove", "§aLeft: edit§8 , §cRight: remove", "§aLeft: edit§8 , §cRight: remove"};
    public static String[] discordinvalid = {
            "§cInvalid Discord format! (Cancelled)",
            "§cInvalid Discord format! (Cancelled)",
            "§cInvalid Discord format! (Cancelled)",
            "§cInvalid Discord format! (Cancelled)"
    };
    public static String[][] LobbySideBar = {
            {"§8»§b discord.gg/rnbRWrb", "§aDiscord-Server§a:", "§2", "§8»§b %player%", "§aWillkommen:", "§5", "§8» §b", "§aOnline-Spieler§a:", "§8", "§6" + config.SERVERIP},
            {"§8»§b discord.gg/rnbRWrb", "§aDiscord Server§a:", "§2", "§8»§b %player%", "§aWelcome:", "§5", "§8» §b", "§aOnline players§a:", "§8", "§6" + config.SERVERIP},
            {"§8»§b discord.gg/rnbRWrb", "§aDiscord 伺服器:", "§2", "§8»§b %player%", "§a歡迎:", "§5", "§8» §b", "§a線上玩家:", "§8", "§6" + config.SERVERIP},
            {"§8»§b discord.gg/rnbRWrb", "§aDiscord 服务器:", "§2", "§8»§b %player%", "§a欢迎:", "§5", "§8» §b", "§a线上玩家:", "§8", "§6" + config.SERVERIP}
    };
}
