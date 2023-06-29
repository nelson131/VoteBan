package me.nelson131.voteban.afk;

import net.kyori.adventure.text.Component;
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
import static me.nelson131.voteban.util.Config.*;

public class PlayersListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(prefix() + getCFG("no-player"));
            return false;
        }
        if(command.getName().equalsIgnoreCase("players")){
            Player player = (Player) sender;

            Component component = Component.text().content(prefix())
                    .append(Component.text().content(getCFG("list-title-1")).color(colorWhite()))
                    .append(Component.text().content(getCFG("list-title-2")).color(colorRed()))
                    .appendNewline()
                    .append(Component.text().content(prefix()))
                    .append(Component.text().content(getCFG("list-total")).color(colorWhite()))
                    .append(Component.text(TotalCount()).color(colorRed()))
                    .appendNewline()
                    .append(Component.text().content(prefix()))
                    .append(Component.text().content(getCFG("list-isAFK")).color(colorWhite()))
                    .append(Component.text(CountOnlyAFK()).color(colorRed()))
                    .appendNewline()
                    .append(Component.text().content(prefix()))
                    .append(Component.text().content(getCFG("list-without-afk")).color(colorWhite()))
                    .append(Component.text(CountWithoutAFK()).color(colorRed()))
                    .build();

            player.sendMessage(component);
        }
        return true;
    }

    private static int TotalCount(){
        return Bukkit.getOnlinePlayers().size();
    }
}
