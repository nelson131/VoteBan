package me.nelson131.voteban;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.plugin;
import static me.nelson131.voteban.util.ActivePolls.addActive;
import static me.nelson131.voteban.util.ImmunePlayers.getImmune;
import static me.nelson131.voteban.util.PlayerUUID.addReason;
import static me.nelson131.voteban.util.Repeats.addRepeats;
import static me.nelson131.voteban.util.Repeats.getRepeats;

public class PollCommand implements CommandExecutor {

    private String targetname;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(plugin.getConfig().getString("no-player"));
            return false;
        }
        if(command.getName().equalsIgnoreCase("voteban")){

            if(args.length == 0){
                Missing(player);
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            UUID targetUUID = target.getUniqueId();
            UUID playerUUID = player.getUniqueId();

            Boolean status = getImmune(targetname);
            Boolean repeat = getRepeats(playerUUID);

            targetname = target.getName();

            if(targetname == player.getName()){
                Ban(player);
                return false;
            }

            if(status == true){
                Immune(player, targetname);
                return false;
            }

            if(repeat == true){
                Repeat(player);
                return false;
            }

            if(playerUUID == null){
                UUIDNull(player);
                return false;
            }


            else{
                addRepeats(playerUUID);

                StringBuilder builder = new StringBuilder();
                for(int i = 1; i <args.length; i++){
                    builder.append(args[i]);
                    builder.append(" ");
                }
                String reason = builder.toString();
                reason = reason.stripTrailing();

                for (Player player1 : Bukkit.getOnlinePlayers()){
                    TextComponent message = new TextComponent("* " + ChatColor.RED + player.getDisplayName() + ChatColor.WHITE + plugin.getConfig().getString("component-1"));
                    TextComponent message1 = new TextComponent("\n* " + ChatColor.WHITE + plugin.getConfig().getString("component-2") + ChatColor.RED + target.getDisplayName());
                    TextComponent message2 = new TextComponent(ChatColor.WHITE + "\n* "+ plugin.getConfig().getString("component-3") + ChatColor.RED + reason);
                    TextComponent message3 = new TextComponent(ChatColor.GREEN + "\n" + plugin.getConfig().getString("button"));
                    message3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote " + targetname));

                    addReason(targetUUID, reason);
                    addActive(player);
                    player1.sendMessage(message, message1, message2, message3);

                }
            }
        }
        return true;
    }

    private static void Missing(Player player){
        player.sendMessage("* " + ChatColor.RED + plugin.getConfig().getString("missing-args") + ChatColor.WHITE + plugin.getConfig().getString("voteban-usage"));
    }

    private static void Immune(Player player, String targetname){
        player.sendMessage("* " + ChatColor.RED + targetname + ChatColor.WHITE + plugin.getConfig().getString("ban-immune-text") + ChatColor.RED + plugin.getConfig().getString("ban-immune"));
    }

    private static void Ban(Player player){
        player.sendMessage("* " + ChatColor.RED + plugin.getConfig().getString("ban-yourself"));
    }

    private static void Repeat(Player player){
        player.sendMessage("* " + ChatColor.RED + plugin.getConfig().getString("repeat"));
    }

    private static void UUIDNull(Player player){
        player.sendMessage("* " + net.md_5.bungee.api.ChatColor.RED + plugin.getConfig().getString("uuid-null") + plugin.getConfig().getString("voteban-usage"));
    }
}

