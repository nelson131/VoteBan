package me.nelson131.voteban.util;

import static me.nelson131.voteban.VoteBan.*;

public class PlayerUUID {

    public static void addVote(String key){
        Integer count = votes.get(key);
        votes.put(key, count == null ? 1 : count + 1);
    }

    public static int getVote(String key){
        return votes.get(key);
    }

    public static void clearVote(String key){
        votes.remove(key);
    }

    public static String getReason(String key){
        return reasons.get(key);
    }

    public static void addReason(String key, String reason){
        reasons.put(key, reason);
    }

    public static void clearReason(String key){
        reasons.remove(key);
    }

}
