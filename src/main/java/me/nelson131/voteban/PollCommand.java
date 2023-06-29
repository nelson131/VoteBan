package me.nelson131.voteban;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.plugin;
import static me.nelson131.voteban.util.ActivePolls.addActive;
import static me.nelson131.voteban.util.Config.*;
import static me.nelson131.voteban.util.ImmunePlayers.getImmune;
import static me.nelson131.voteban.util.PlayerUUID.addReason;
import static me.nelson131.voteban.util.Repeats.addRepeats;
import static me.nelson131.voteban.util.Repeats.getRepeats;

public class PollCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix() + getCFG("no-player"));
            return false;
        }
        if (command.getName().equalsIgnoreCase("voteban")) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);

            if(target == null){
                uuidNull(player);
                return false;
            }

            UUID targetUUID = target.getUniqueId();
            UUID playerUUID = player.getUniqueId();

            String targetname = target.getName();

            Boolean status = getImmune(targetname);
            Boolean repeat = getRepeats(playerUUID);



            if (args.length == 0) {
                missing(player);
                return false;
            }

//            if (targetname == player.getName()) {
//                ban(player);
//                return false;
//            }

            if (status == true) {
                immune(player, targetname);
                return false;
            }

            if (repeat == true) {
                repeat(player);
                return false;
            }

            if (playerUUID == null) {
                uuidNull(player);
                return false;
            }
            else {
                addRepeats(playerUUID);

                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    builder.append(args[i]);
                    builder.append(" ");
                }
                String reason = builder.toString();
                reason = reason.stripTrailing();

                for (Player player1 : Bukkit.getOnlinePlayers()) {

                    Component component = Component.text()
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
                            .build();

                    addReason(targetUUID, reason);
                    addActive(playerUUID);
                    player1.sendMessage(component);

                }
            }
        }
        return true;
    }

    private static void missing(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("missing-args")).color(colorRed()))
                        .append(Component.text().content(getCFG("voteban-usage")).color(colorWhite()))
                        .build()
        );
    }

    private static void immune(Player player, String targetname) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(targetname).color(colorRed()))
                        .append(Component.text().content(getCFG("ban-immune-text")).color(colorWhite()))
                        .append(Component.text().content(getCFG("ban-immune")).color(colorRed()))
                        .build()
        );
    }

    private static void ban(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("ban-yourself")).color(colorRed()))
                        .build()
        );
    }

    private static void repeat(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("repeat")).color(colorRed()))
                        .build()
        );
    }

    private static void uuidNull(Player player) {
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("uuid-null")).color(colorRed()))
                        .append(Component.text().content(getCFG("voteban-usage")).color(colorWhite()))
                        .build()
        );
    }
}

