package me.nelson131.voteban;

import me.nelson131.voteban.util.MessageBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.plugin;
import static me.nelson131.voteban.util.Config.*;
import static me.nelson131.voteban.util.ImmunePlayers.*;
import static me.nelson131.voteban.util.MessageBuilder.*;

public class ImmunityCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(!(sender instanceof Player)){
            noPlayer((Player) sender);
            return true;
        }
        if(command.getName().equalsIgnoreCase("immune") && sender.isOp()){
            Player player = (Player) sender;

            if(args.length <= 1){
                missingArgs(player, "immune-usage");
                return true;
            }

            else if(args.length == 2){
                Player target = Bukkit.getPlayer(args[1]);
                String targetName = target.getName();

                switch (args[0]) {
                    case "add":
                        addImmune(targetName);
                        appendImmune(player, targetName);
                        return true;

                    case "remove":
                        clearImmune(targetName);
                        pullImmune(player, targetName);
                        return true;
                    case "get":
                        if (getImmune(targetName)){
                            getImmuneTrue(player, targetName);
                        } else {
                            getImmuneFalse(player, targetName);
                        }
                        return true;
                }
            }
        }
        return true;
    }
}
