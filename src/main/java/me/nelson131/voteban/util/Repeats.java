package me.nelson131.voteban.util;

import static me.nelson131.voteban.VoteBan.repeats;

public class Repeats {

    public static void addRepeats(String key){
        repeats.put(key, true);
    }

    public static boolean getRepeats(String key){
        return repeats.getOrDefault(key, false);
    }

    public static void removeRepeats(String key){
        repeats.remove(key);
    }
}
