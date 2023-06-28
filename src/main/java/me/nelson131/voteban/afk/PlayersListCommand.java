package me.nelson131.voteban.afk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.nelson131.voteban.VoteBan.plugin;
import static me.nelson131.voteban.afk.AFKManager.CountOnlyAFK;
import static me.nelson131.voteban.afk.AFKManager.CountWithoutAFK;

public class PlayersListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(plugin.getConfig().getString("no-player"));
            return false;
        }
        if(command.getName().equalsIgnoreCase("players")){
            Player player = (Player) sender;
            player.sendMessage("* "+ plugin.getConfig().getString("list-title-1") + ChatColor.RED + plugin.getConfig().getString("list-title-2"));
            player.sendMessage("* "+ plugin.getConfig().getString("list-total") + ChatColor.RED + TotalCount());
            player.sendMessage("* "+ plugin.getConfig().getString("list-isAFK") + ChatColor.RED + CountOnlyAFK());
            player.sendMessage("* "+ plugin.getConfig().getString("list-without-afk") + ChatColor.RED + CountWithoutAFK());
        }
        return true;
    }

    private static int TotalCount(){
        int total = Bukkit.getOnlinePlayers().size();
        return total;
    }
}
