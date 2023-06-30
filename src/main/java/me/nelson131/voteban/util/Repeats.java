package me.nelson131.voteban.util;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.repeats;

public class Repeats {

    public static void addRepeats(UUID uuid){
        repeats.put(uuid, true);
    }

    public static boolean getRepeats(UUID uuid){
        return repeats.getOrDefault(uuid, false);
    }

    public static void removeRepeats(UUID uuid){
        repeats.remove(uuid);
    }
}
