package me.nelson131.voteban;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.plugin;
import static me.nelson131.voteban.util.ImmunePlayers.*;

public class ImmunityCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(plugin.getConfig().getString("no-player"));
            return false;
        }
        if(command.getName().equalsIgnoreCase("immune") && player.isOp()){

            if(args.length == 0){
                Missing(player);
                return false;
            }

            if(args.length == 1){
                Player target = Bukkit.getPlayer(args[1]);
                String targetName = target.getName();
                String add = "* " + ChatColor.RED + target.getName() + ChatColor.WHITE + plugin.getConfig().getString("immune-add");
                String remove = "* " + ChatColor.RED + target.getName() + ChatColor.WHITE + plugin.getConfig().getString("immune-remove");
                String getTrue = "* " + ChatColor.RED + target.getName() + ChatColor.WHITE + plugin.getConfig().getString("immune-get-true");
                String getFalse = "* " + ChatColor.RED + target.getName() + ChatColor.WHITE + plugin.getConfig().getString("immune-get-false");

                switch (args[0]) {
                    case "add" -> {
                        addImmune(targetName);
                        player.sendMessage(add);
                        return true;
                    }
                    case "remove" -> {
                        clearImmune(targetName);
                        player.sendMessage(remove);
                        return true;
                    }
                    case "get" -> {
                        Boolean status = getImmune(targetName);
                        if (status == true) {
                            sender.sendMessage(getTrue);
                        } else {
                            sender.sendMessage(getFalse);
                        }
                        return true;
                    }
                }
            }
            else {

            }
        }
        return true;
    }

    private static void Missing(Player player){
        player.sendMessage(ChatColor.RED + plugin.getConfig().getString("missing-args") + ChatColor.WHITE + plugin.getConfig().getString("immune-usage"));
    }
}
