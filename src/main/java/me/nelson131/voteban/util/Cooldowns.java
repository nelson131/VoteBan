package me.nelson131.voteban.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static me.nelson131.voteban.VoteBan.cooldowns;

public class Cooldowns {

    public static void addCooldown(UUID player, UUID target){
        Set<UUID> targets = new HashSet<>();
        targets.add(target);
        cooldowns.put(player, targets);
    }

    public static Boolean checkCooldown(UUID player, UUID target){
        if(cooldowns.get(player) == null) return false;
        return cooldowns.get(player).contains(target);
    }

    public static void removeCooldown(UUID player, UUID target){
        cooldowns.get(player).remove(target);
    }
}
