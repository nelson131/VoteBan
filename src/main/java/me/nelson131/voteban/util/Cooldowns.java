package me.nelson131.voteban.util;

import java.util.HashSet;
import java.util.Set;

import static me.nelson131.voteban.VoteBan.cooldowns;

public class Cooldowns {

    public static void addCooldown(String player, String target){
        Set<String> targets = new HashSet<>();
        targets.add(target);
        cooldowns.put(player, targets);
    }

    public static boolean checkCooldown(String player, String target){
        if(cooldowns.get(player) == null) return false;
        return cooldowns.get(player).contains(target);
    }

    public static void removeCooldown(String player, String target){
        cooldowns.get(player).remove(target);
    }
}
