package me.nelson131.voteban.util;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.repeats;

public class Repeats {

    public static void addRepeats(UUID uuid){
        repeats.put(uuid, true);
    }

    public static Boolean getRepeats(UUID uuid){
        if(repeats.get(uuid) == null) return false;
        return repeats.get(uuid);
    }

    public static void removeRepeats(UUID uuid){
        repeats.remove(uuid);
    }
}
