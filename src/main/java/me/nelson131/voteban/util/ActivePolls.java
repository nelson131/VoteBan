package me.nelson131.voteban.util;

import org.bukkit.entity.Player;

import static me.nelson131.voteban.VoteBan.active;

public class ActivePolls {

    public static void addActive(Player player){
        active.add(player);
    }

    public static boolean getActive(Player player){
        if(active.contains(player)){
            return true;
        }
        else {
            return false;
        }
    }

    public static void removeActive(Player player){
        active.remove(player);
    }
}
