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
import static me.nelson131.voteban.util.MessageBuilder.*;
import static me.nelson131.voteban.util.PlayerUUID.*;
import static me.nelson131.voteban.util.Repeats.removeRepeats;

public class VoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            noPlayer((Player) sender);
            return false;
        }
        if (command.getName().equalsIgnoreCase("vote")) {
            Player player = (Player) sender;

            if (args.length == 0) {
                missingArgs(player, "vote-usage");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null){
                uuidNull(player, "vote-usage");
                return true;
            }

            UUID targetUUID = target.getUniqueId();
            UUID playerUUID = player.getUniqueId();

            if (checkCooldown(playerUUID, targetUUID)) {
                cooldown(player);
                return true;
            }

            if (!getActive(playerUUID)) {
                noActivePolls(player);
                return true;
            }

            else {
                addVote(targetUUID);
                addCooldown(playerUUID, targetUUID);
                voteCounted(player);

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
            banned(player1, reason);
        }
        player.kickPlayer(getCFG("ban-msg") + reason);
    }

}
