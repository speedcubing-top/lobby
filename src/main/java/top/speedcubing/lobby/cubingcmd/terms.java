package top.speedcubing.lobby.cubingcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.bukkit.inventory.BookBuilder;
import top.speedcubing.lib.minecraft.text.ComponentText;
import top.speedcubing.lib.minecraft.text.TextClickEvent;
import top.speedcubing.lib.minecraft.text.TextHoverEvent;
import top.speedcubing.lobby.Member;
import top.speedcubing.lobby.bukkitlistener.PlayerJoin;
import top.speedcubing.server.system.command.CubingCommand;
import top.speedcubing.server.lang.Lang;

public class terms extends CubingCommand {
    public terms() {
        super("terms");
    }

    private static final ItemStack[] books = new ItemStack[4];

    public static ItemStack book(int lang) {
        if (lang < 0 || lang >= books.length)
            lang = 1;
        if (books[lang] == null)
            books[lang] = new BookBuilder("a", "b")
                    .addPage(new ComponentText()
                            .str(s(lang, "intro"))
                            .both(s(lang, "rules"), TextClickEvent.openURL("https://speedcubing.top/serverrules"), TextHoverEvent.showText(s(lang, "rules_hover")))
                            .str("§0\n- ")
                            .both(s(lang, "privacy"), TextClickEvent.openURL("https://speedcubing.top/privacypolicy"), TextHoverEvent.showText(s(lang, "privacy_hover")))
                            .str("\n\n")
                            .both(s(lang, "accept"), TextClickEvent.runCommand("/terms accept"), TextHoverEvent.showText(s(lang, "accept_hover")))
                            .str(" ")
                            .both(s(lang, "deny"), TextClickEvent.runCommand("/terms deny"), TextHoverEvent.showText(s(lang, "deny_hover"))).toBungee()).build();
        return books[lang];
    }

    private static String s(int lang, String key) {
        return Lang.of("%lang_lobby_terms_" + key + "%").getString(lang);
    }

    @Override
    public void execute(CommandSender commandSender, String command, String[] args) {
        switch (args[0]) {
            case "accept":
                Member member = Member.getMember(commandSender);
                member.user.dbUpdate("agreement=1");
                member.agree = true;
                PlayerJoin.joinLobby((Player) commandSender, false);
                break;
            case "deny":
                ((Player) commandSender).kickPlayer(Lang.of("%lang_lobby_terms_denied_kick%").getString(Member.getMember(commandSender).user.lang));
                break;
        }
    }
}
