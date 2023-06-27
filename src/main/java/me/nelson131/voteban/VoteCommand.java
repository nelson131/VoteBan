package me.nelson131.voteban;

import me.nelson131.voteban.util.ActivePolls;
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
import static me.nelson131.voteban.util.Cooldowns.*;
import static me.nelson131.voteban.util.PlayerUUID.*;
import static me.nelson131.voteban.util.Repeats.removeRepeats;

public class VoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        UUID targetUUID = target.getUniqueId();
        UUID playerUUID = player.getUniqueId();

        if(!(sender instanceof Player)){
            sender.sendMessage(plugin.getConfig().getString("no-player"));
            return false;
        }
        if(command.getName().equalsIgnoreCase("vote")){
            if(args.length == 0){
                Missing(player);
                return false;
            }

            if(checkCooldown(playerUUID, targetUUID)){
                Cooldown(player);
                return false;
            }

            if(playerUUID == null){
                UUIDNull(player);
                return false;
            }

            if(getActive(player) == false){
                ActivePolls(player);
                return false;
            }

            else {
                TextComponent textComponent = new TextComponent(ChatColor.GREEN + plugin.getConfig().getString("vote-counted"));

                addVote(targetUUID);
                addCooldown(playerUUID, targetUUID);
                sender.sendMessage(textComponent);

                int count = getVote(targetUUID);

                if(count == CountWithoutAFK()){
                    ban(target);
                    clearVote(targetUUID);
                    clearReason(targetUUID);
                    removeCooldown(playerUUID, targetUUID);
                    removeRepeats(playerUUID);
                    return true;
                }
            }
        }
        return true;
    }

    public static void ban(Player player){
        Date date = new Date(System.currentTimeMillis()+60*60*1000);
        String reason = getReason(player.getUniqueId());

        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, date, null);
        for (Player player1 : Bukkit.getOnlinePlayers()){
            player1.sendMessage(org.bukkit.ChatColor.WHITE + player.getName() + "has been banned with reason: " + org.bukkit.ChatColor.RED + reason);
        }
        player.kickPlayer(plugin.getConfig().getString("ban-msg") + reason);
    }

    private static void Missing(Player player){
        player.sendMessage(org.bukkit.ChatColor.RED + plugin.getConfig().getString("missing-args") + org.bukkit.ChatColor.WHITE + plugin.getConfig().getString("vote-usage"));
    }

    private static void UUIDNull(Player player){
        player.sendMessage(ChatColor.RED + plugin.getConfig().getString("uuid-null") + plugin.getConfig().getString("vote-usage"));
    }

    private static void ActivePolls(Player player){
        player.sendMessage(ChatColor.RED + plugin.getConfig().getString("active-polls"));
    }

    private static void Cooldown(Player player){
        player.sendMessage(ChatColor.RED + plugin.getConfig().getString("cooldown"));
    }
}
