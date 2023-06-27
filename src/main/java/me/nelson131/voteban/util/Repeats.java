package me.nelson131.voteban.util;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.repeats;

public class Repeats {

    public static void addRepeats(UUID uuid){
        repeats.put(uuid, true);
    }

    public static Boolean getRepeats(UUID uuid){
        Boolean bool = repeats.get(uuid);
        if(bool == null){
            bool = false;
        }
        return bool;
    }

    public static void removeRepeats(UUID uuid){
        repeats.remove(uuid);
    }
}
