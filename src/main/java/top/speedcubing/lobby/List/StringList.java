package top.speedcubing.lobby.List;

import top.speedcubing.server.lang.LangMessage;
import top.speedcubing.server.utils.config;

public class StringList {
    public static LangMessage LangSettings = new LangMessage(
            "§8[§eSystem§8] §aErfolgreich auf Deutsch geändert!\n§8[§eSystem§8] §cDer Server ist noch nicht vollständig übersetzt.",
            "§8[§eSystem§8] §aSuccessfully changed to English!\n§8[§eSystem§8] §cThe server is not fully translated yet.",
            "§8[§e系統§8] §a成功切換為中文!\n§8[§e系統§8] §c伺服器目前尚未完全翻譯",
            "§8[§e系统§8] §a成功切换为简体中文!\n§8[§e系统§8] §c伺服器目前尚未完全翻译");

    public static LangMessage reset = new LangMessage(
            "Successfully reset your stats.",
            "Successfully reset your stats.",
            "Successfully reset your stats.",
            "Successfully reset your stats.");

    public static LangMessage AlreadyInHub = new LangMessage(
            "§cDu befindest dich bereits in der hub.",
            "§cYou are already in hub.",
            "§c你已經在主大廳了",
            "§c你已经在主大厅了");
    public static LangMessage updatedDiscord = new LangMessage(
            "§aSuccessfully updated the discord. (§6%1%§a)\nYou can now verify your minecraft account in the Discord Server!",
            "§aSuccessfully updated the discord. (§6%1%§a)\nYou can now verify your minecraft account in the Discord Server!",
            "§aSuccessfully updated the discord. (§6%1%§a)\nYou can now verify your minecraft account in the Discord Server!",
            "§aSuccessfully updated the discord. (§6%1%§a)\nYou can now verify your minecraft account in the Discord Server!");
    public static LangMessage discord = new LangMessage(
            "§aPlease input a valid discord id in the chat:",
            "§aPlease input a valid discord id in the chat:",
            "§aPlease input a valid discord id in the chat:",
            "§aPlease input a valid discord id in the chat:");

    public static LangMessage settingstitle = new LangMessage(
            "§cEinstellungen", "§cSettings", "§c設定", "§c设置");

    public static LangMessage discordremoved = new LangMessage(
            "§cYour discord id is cleared.",
            "§cYour discord id is cleared.",
            "§cYour discord id is cleared.",
            "§cYour discord id is cleared.");

    public static LangMessage dc = new LangMessage("§aDiscord", "§aDiscord", "§aDiscord", "§aDiscord");

    public static LangMessage discordA = new LangMessage(
            "§aYou are §6%a%",
            "§aYou are §6%a%",
            "§aYou are §6%a%",
            "§aYou are §6%a%");

    public static LangMessage discordB =
            new LangMessage("§cYou don't have any data yet.",
                    "§cYou don't have any data yet.",
                    "§cYou don't have any data yet.",
                    "§cYou don't have any data yet.");

    public static LangMessage discordEdit = new LangMessage("§aLeft: edit§8 , §cRight: remove", "§aLeft: edit§8 , §cRight: remove", "§aLeft: edit§8 , §cRight: remove", "§aLeft: edit§8 , §cRight: remove");
    public static LangMessage discordinvalid = new LangMessage(
            "§cInvalid Discord format! (Cancelled)",
            "§cInvalid Discord format! (Cancelled)",
            "§cInvalid Discord format! (Cancelled)",
            "§cInvalid Discord format! (Cancelled)");
    public static String[][] LobbySideBar = {
            {"§6" + config.SERVERIP, "§8", "§aOnline-Spieler§a:", "§8» §b", "§5", "§aWillkommen:", "§8»§b %player%", "§2", "§aDiscord-Server§a:", "§8»§b speedcubing.top/discord"},
            {"§6" + config.SERVERIP, "§8", "§aOnline players§a:", "§8» §b", "§5", "§aWelcome:", "§8»§b %player%", "§2", "§aDiscord Server§a:", "§8»§b speedcubing.top/discord"},
            {"§6" + config.SERVERIP, "§8", "§a線上玩家:", "§8» §b", "§5", "§a歡迎:", "§8»§b %player%", "§2", "§aDiscord 伺服器:", "§8»§b speedcubing.top/discord"},
            {"§6" + config.SERVERIP, "§8", "§a线上玩家:", "§8» §b", "§5", "§a欢迎:", "§8»§b %player%", "§2", "§aDiscord 服务器:", "§8»§b speedcubing.top/discord"}
    };
}
