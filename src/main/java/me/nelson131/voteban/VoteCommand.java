package me.nelson131.voteban;

import me.nelson131.voteban.util.ActivePolls;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

import static me.nelson131.voteban.VoteBan.*;
import static me.nelson131.voteban.afk.AFKManager.*;
import static me.nelson131.voteban.util.ActivePolls.getActive;
import static me.nelson131.voteban.util.ActivePolls.removeActive;
import static me.nelson131.voteban.util.Config.*;
import static me.nelson131.voteban.util.Cooldowns.*;
import static me.nelson131.voteban.util.PlayerUUID.*;
import static me.nelson131.voteban.util.Repeats.removeRepeats;

public class VoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(
                    prefix() + getCFG("no-player"));
            return false;
        }
        if (command.getName().equalsIgnoreCase("vote")) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            UUID targetUUID = target.getUniqueId();
            UUID playerUUID = player.getUniqueId();

            if (args.length == 0) {
                missing(player);
                return false;
            }

            if (checkCooldown(playerUUID, targetUUID)) {
                cooldown(player);
                return false;
            }

            if (playerUUID == null) {
                uUIDNull(player);
                return false;
            }

            if (getActive(playerUUID) == false) {
                activePolls(player);
                return false;

            }

            else {
                addVote(targetUUID);
                addCooldown(playerUUID, targetUUID);

                sender.sendMessage(
                        Component.text().content(prefix())
                                .append(Component.text().content(getCFG("vote-counted")).color(colorGreen()))
                                .build()
                );

                if (getVote(targetUUID) == CountWithoutAFK() / 2) {
                        ban(target);
                        clearVote(targetUUID);
                        clearReason(targetUUID);
                        removeCooldown(playerUUID, targetUUID);
                        removeRepeats(playerUUID);
                        removeActive(playerUUID);
                }
            }
        }
        return true;
    }

    public static void ban(Player player) {
        Date date = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
        String reason = getReason(player.getUniqueId());

        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, date, null);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.sendMessage(
                    Component.text().content(prefix() + player.getName()).color(colorWhite())
                            .append(Component.text().content(reason).color(colorRed()))
                            .build()
            );
        }
        player.kickPlayer(getCFG("ban-msg") + reason);
    }

    private static void missing(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("missing-args")).color(colorRed()))
                        .append(Component.text().content(getCFG("vote-usage")).color(colorWhite()))
                        .build()
        );
    }

    private static void uUIDNull(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("uuid-null")).color(colorRed()))
                        .append(Component.text().content(getCFG("vote-usage")).color(colorWhite()))
                        .build()
        );
    }

    private static void activePolls(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("active-polls")).color(colorRed()))
                        .build()
        );
    }

    private static void cooldown(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("cooldown")).color(colorRed()))
                        .build()
        );
    }
}
