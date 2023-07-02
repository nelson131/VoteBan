package me.nelson131.voteban.util;

import static me.nelson131.voteban.VoteBan.active;

public class ActivePolls {

    public static void addActive(String player){
        active.add(player);
    }

    public static boolean getActive(String player){
        return active.contains(player);
    }

    public static void removeActive(String player){
        active.remove(player);
    }

}
