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
import static me.nelson131.voteban.util.MessageBuilder.*;
import static me.nelson131.voteban.util.PlayerUUID.addReason;
import static me.nelson131.voteban.util.Repeats.addRepeats;
import static me.nelson131.voteban.util.Repeats.getRepeats;

public class PollCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            noPlayer((Player) sender);
        }
        if (command.getName().equalsIgnoreCase("voteban")) {
            Player player = (Player) sender;

            if (args.length == 0) {
                missingArgs(player, "voteban-usage");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null){
                uuidNull(player, "voteban-usage");
                return true ;
            }

            UUID targetUUID = target.getUniqueId();
            UUID playerUUID = player.getUniqueId();

            String targetName = target.getName();
            String playerName = player.getName();

            boolean status = getImmune(targetName);
            boolean repeat = getRepeats(playerUUID);

            if (targetName.equals(playerName)) {
                ban(player);
                return true;
            }

            if (status) {
                immune(player, targetName);
                return true;
            }

            if (repeat) {
                repeat(player);
                return true;
            }

            else {
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    builder.append(args[i]);
                    builder.append(" ");
                }
                String reason = builder.toString();
                reason = reason.stripTrailing();

                addRepeats(playerUUID);
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    addReason(targetUUID, reason);
                    addActive(playerUUID);
                    pollCreated(player1, player, targetName, reason);

                }
            }
        }
        return true;
    }

}

