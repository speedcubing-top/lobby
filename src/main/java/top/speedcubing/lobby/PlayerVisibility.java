package top.speedcubing.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import top.speedcubing.lobby.list.ItemStackList;
import top.speedcubing.server.lang.LangItem;

/**
 * The hotbar "players visible / hidden" toggle. Session only; vanished players stay hidden from non-staff either way.
 */
public class PlayerVisibility {
    public static final int SLOT = 8;
    private static final long COOLDOWN_MILLIS = 3000;

    public static void toggle(Member member) {
        long now = System.currentTimeMillis();
        if (now - member.lastVisibilityToggle < COOLDOWN_MILLIS) {
            member.user.sendMessage("%lang_lobby_msg_cooldown%");
            return;
        }
        member.lastVisibilityToggle = now;
        member.hidePlayers = !member.hidePlayers;

        Player viewer = member.user.player;
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other == viewer)
                continue;
            if (member.hidePlayers)
                viewer.hidePlayer(other);
            else if (canSee(member, other))
                viewer.showPlayer(other);
        }
        member.user.setLangItem(SLOT, item(member));
        member.user.sound(Sound.CLICK);
        member.user.sendMessage(member.hidePlayers ? "%lang_lobby_msg_players_hidden%" : "%lang_lobby_msg_players_shown%");
    }

    /** Hides a player who just entered the lobby from everyone who has players hidden. */
    public static void applyToJoining(Player joining) {
        for (Member member : Member.users.values()) {
            if (member.hidePlayers && member.user.player != joining)
                member.user.player.hidePlayer(joining);
        }
    }

    public static LangItem item(Member member) {
        return member.hidePlayers ? ItemStackList.PlayersHidden : ItemStackList.PlayersShown;
    }

    // same rule as PlayerJoin.joinLobby: vanished players are only shown to staff
    private static boolean canSee(Member viewer, Player other) {
        Member member = Member.getMember(other);
        return member != null && member.user.canBeSeenBy(viewer.user);
    }
}
