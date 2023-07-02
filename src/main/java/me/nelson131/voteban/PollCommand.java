package me.nelson131.voteban;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.nelson131.voteban.util.ActivePolls.addActive;
import static me.nelson131.voteban.util.MessageBuilder.*;
import static me.nelson131.voteban.util.Permission.checkPermission;
import static me.nelson131.voteban.util.PlayerUUID.addReason;
import static me.nelson131.voteban.util.Repeats.addRepeats;
import static me.nelson131.voteban.util.Repeats.getRepeats;

public class PollCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            noPlayer((Player) sender);
        }

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

        String targetName = target.getName();
        String playerName = player.getName();


        if (targetName.equals(playerName)) {
            ban(player);
            return true;
        }

        if (getRepeats(targetName)) {
            repeat(player);
            return true;
        }

        if(checkPermission(target, "voteban.immune")){
            permissionCheck(target);
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

            addRepeats(targetName);
            addReason(targetName, reason);
            addActive(target.getName());
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                pollCreated(player1, player, targetName, reason);
            }
        }
        return true;
    }
}

