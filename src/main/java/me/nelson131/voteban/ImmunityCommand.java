package me.nelson131.voteban;

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

public class ImmunityCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(!(sender instanceof Player)){
            sender.sendMessage(plugin.getConfig().getString("no-player"));
            return false;
        }
        if(command.getName().equalsIgnoreCase("immune") && sender.isOp()){
            Player player = (Player) sender;

            if(args.length <= 1){
                missing(player);
                return false;
            }

            if(args.length == 2){
                Player target = Bukkit.getPlayer(args[1]);
                String targetName = target.getName();

                switch (args[0]) {
                    case "add":
                        addImmune(targetName);
                        player.sendMessage(
                                Component.text().content(prefix())
                                        .append(Component.text().content(targetName).color(colorRed()))
                                        .append(Component.text().content(getCFG("immune-add")).color(colorWhite()))
                                        .build()
                        );
                        return true;

                    case "remove":
                        clearImmune(targetName);
                        player.sendMessage(
                                Component.text().content(prefix())
                                        .append(Component.text().content(targetName).color(colorRed()))
                                        .append(Component.text().content(getCFG("immune-remove")).color(colorWhite()))
                                        .build()
                        );
                        return true;
                    case "get":
                        if (getImmune(targetName)){
                            player.sendMessage(
                                    Component.text().content(prefix())
                                            .append(Component.text().content(targetName).color(colorRed()))
                                            .append(Component.text().content(getCFG("immune-get-true")).color(colorWhite()))
                                            .build()
                            );
                        } else {
                            player.sendMessage(
                                    Component.text().content(prefix())
                                            .append(Component.text().content(targetName).color(colorRed()))
                                            .append(Component.text().content(getCFG("immune-get-false")).color(colorWhite()))
                                            .build()
                            );
                        }
                        return true;
                }
            }
        }
        return true;
    }

    private static void missing(Player player){
        player.sendMessage(
                Component.text().content(prefix())
                        .append(Component.text().content(getCFG("missing-args")).color(colorRed()))
                        .append(Component.text().content(getCFG("immune-usage")).color(colorWhite()))
                        .build()
        );
    }
}
