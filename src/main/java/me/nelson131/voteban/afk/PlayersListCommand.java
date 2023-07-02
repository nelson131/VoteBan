package me.nelson131.voteban.afk;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.nelson131.voteban.util.MessageBuilder.noPlayer;
import static me.nelson131.voteban.util.MessageBuilder.playerList;

public class PlayersListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            noPlayer((Player) sender);
            return true;
        }

        Player player = (Player) sender;
        playerList(player, TotalCount());

        return true;
    }

    private static int TotalCount(){
        return Bukkit.getOnlinePlayers().size();
    }
}
