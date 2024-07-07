package top.speedcubing.lobby.list;

import top.speedcubing.server.lang.LangMessage;

public class StringList {
    public static LangMessage LangSettings = LangMessage.load("lobby_msg_translate_success");
    public static LangMessage reset = LangMessage.load("lobby_msg_reset_stat_success");
    public static LangMessage AlreadyInHub = LangMessage.load("lobby_msg_alr_in_lobby");
    public static LangMessage updatedDiscord = LangMessage.load("lobby_msg_discord_updated");
    public static LangMessage discord = LangMessage.load("lobby_msg_discord_input");
    public static LangMessage settingstitle = LangMessage.load("lobby_item_settings");
    public static LangMessage discordremoved = LangMessage.load("lobby_msg_discord_removed");
    public static LangMessage dc = LangMessage.load("lobby_item_discord");
    public static LangMessage discordA = LangMessage.load("lobby_discord_youare");
    public static LangMessage discordB = LangMessage.load("lobby_discord_nodata");
    public static LangMessage discordEdit = LangMessage.load("lobby_lore_discord_edit");
    public static LangMessage discordinvalid = LangMessage.load("lobby_msg_discord_invalid");
    public static String[][] LobbySideBar = {
            {"§6speedcubing.top", "§8", "§aOnline-Spieler§a:", "§8» §b", "§5", "§aWillkommen:", "§8»§b %player%", "§2", "§aDiscord-Server§a:", "§8»§b speedcubing.top/discord"},
            {"§6speedcubing.top", "§8", "§aOnline players§a:", "§8» §b", "§5", "§aWelcome:", "§8»§b %player%", "§2", "§aDiscord Server§a:", "§8»§b speedcubing.top/discord"},
            {"§6speedcubing.top", "§8", "§a線上玩家:", "§8» §b", "§5", "§a歡迎:", "§8»§b %player%", "§2", "§aDiscord 伺服器:", "§8»§b speedcubing.top/discord"},
            {"§6speedcubing.top", "§8", "§a线上玩家:", "§8» §b", "§5", "§a欢迎:", "§8»§b %player%", "§2", "§aDiscord 服务器:", "§8»§b speedcubing.top/discord"}
    };
}
