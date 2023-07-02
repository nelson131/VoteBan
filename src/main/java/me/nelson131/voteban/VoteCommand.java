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

import static me.nelson131.voteban.afk.AFKManager.*;
import static me.nelson131.voteban.util.ActivePolls.*;
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

        Player player = (Player) sender;

        if (args.length == 0) {
            missingArgs(player, "vote-usage");
            return true;
        }

        if(getActive(args[0].toUpperCase())){
            String playerName = player.getName();

            if(args[0].equals(playerName)){
                voteYourself(player);
                return true;
            }

            if (checkCooldown(playerName, args[0])) {
                cooldown(player);
                return true;
            }

            else {
                run(player, playerName, args[0]);
            }

        }
        else {
            if(!getActive(args[0])){
                noActivePolls(player);
                return true;
            }
            uuidNull(player, "uuid-null");
        }
        return true;
    }

    public static void ban(String key) {
        Date date = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
        String reason = getReason(key);

        Bukkit.getBanList(BanList.Type.NAME).addBan(key, reason, date, null);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            banned(player1, reason);
        }
        if(Bukkit.getPlayer(key) == null);
        else Bukkit.getPlayer(key).kickPlayer(getCFG("ban-msg") + reason);
    }

    public static void run(Player player, String playerName, String targetName){
        addVote(targetName);
        addCooldown(playerName, targetName);
        voteCounted(player);

        if (getVote(targetName) == CountWithoutAFK() / 2) {
            ban(targetName);
            clearVote(targetName);
            clearReason(targetName);
            removeCooldown(playerName, targetName);
            removeRepeats(targetName);
            removeActive(targetName);

        }
    }
}
