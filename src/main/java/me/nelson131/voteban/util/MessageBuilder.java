package me.nelson131.voteban.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import static me.nelson131.voteban.afk.AFKManager.CountOnlyAFK;
import static me.nelson131.voteban.afk.AFKManager.CountWithoutAFK;
import static me.nelson131.voteban.util.Config.*;

public class MessageBuilder {

    public static void pollCreated(Player player1, Player player, String targetname, String reason){
        player1.sendMessage(
                Component.text()
                        .content(prefix()).color(colorWhite())
                        .append(Component.text().content(player.getDisplayName()).color(colorRed()))
                        .append(Component.text().content(getCFG("component-1")).color(colorWhite()))
                        .appendNewline()
                        .append(Component.text().content(prefix()).color(TextColor.color(colorWhite()))
                                .append(Component.text().content(getCFG("component-2")).color(colorWhite())))
                        .append(Component.text().content(targetname).color(colorRed()))
                        .appendNewline()
                        .append(Component.text().content(prefix())).color(colorWhite())
                        .append(Component.text().content(getCFG("component-3")).color(colorWhite()))
                        .append(Component.text().content(reason).color(colorRed()))
                        .appendNewline()
                        .append(Component.text().content(prefix()).color(colorWhite()))
                        .append(Component.text().content(getCFG("button")).color(colorGreen()))
                        .clickEvent(ClickEvent.runCommand("/vote " + targetname))
                        .build()
        );
    }

    public static void noPlayer(Player player){
        player.sendMessage(
                Component.text().content(prefix() + getCFG("no-player")).build()
        );
    }

    public static void missingArgs(Player player, String key){
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("missing-args")).color(colorRed()))
                        .append(Component.text().content(getCFG(key)).color(colorWhite()))
                        .build()
        );
    }

    public static void cooldown(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("cooldown")).color(colorRed()))
                        .build()
        );
    }

    public static void noActivePolls(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("no-active-polls")).color(colorRed()))
                        .build()
        );
    }

    public static void uuidNull(Player player, String key) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("uuid-null")).color(colorRed()))
                        .append(Component.text().content(getCFG(key)).color(colorWhite()))
                        .build()
        );
    }

    public static void voteCounted(Player player){
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("vote-counted")).color(colorGreen()))
                        .build()
        );
    }

    public static void banned(Player player, String reason){
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("banned-message")).color(colorRed()))
                        .append(Component.text().content(reason).color(colorWhite()))
                        .build()
        );
    }

    public static void ban(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("ban-yourself")).color(colorRed()))
                        .build()
        );
    }

    public static void repeat(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("repeat")).color(colorRed()))
                        .build()
        );
    }

    public static void playerList(Player player, int TotalCount){
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("list-title-1")).color(colorWhite()))
                        .append(Component.text().content(getCFG("list-title-2")).color(colorRed()))
                        .appendNewline()
                        .append(Component.text().content(prefix()))
                        .append(Component.text().content(getCFG("list-total")).color(colorWhite()))
                        .append(Component.text(TotalCount).color(colorRed()))
                        .appendNewline()
                        .append(Component.text().content(prefix()))
                        .append(Component.text().content(getCFG("list-isAFK")).color(colorWhite()))
                        .append(Component.text(CountOnlyAFK()).color(colorRed()))
                        .appendNewline()
                        .append(Component.text().content(prefix()))
                        .append(Component.text().content(getCFG("list-without-afk")).color(colorWhite()))
                        .append(Component.text(CountWithoutAFK()).color(colorRed()))
                        .build()
        );
    }

    public static void permissionCheck(Player player){
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(player.getName()).color(colorRed()))
                        .append(Component.text().content(getCFG("ban-immune-text")).color(colorWhite()))
                        .appendNewline()
                        .append(Component.text().content(getCFG("ban-immune")).color(colorRed()))
                        .build()
        );
    }

    public static void voteYourself(Player player){
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("vote-yourself")).color(colorRed()))
                        .build()
        );
    }
}
