package me.nelson131.voteban.util;

import org.bukkit.entity.Player;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.active;

public class ActivePolls {

    public static void addActive(UUID uuid){
        active.add(uuid);
    }

    public static boolean getActive(UUID uuid){
        return active.contains(uuid);
    }

    public static void removeActive(UUID uuid){
        active.remove(uuid);
    }
}
