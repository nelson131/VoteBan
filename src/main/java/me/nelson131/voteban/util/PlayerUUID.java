package me.nelson131.voteban.util;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.*;

public class PlayerUUID {

    public static void addVote(UUID uuid){
        Integer count = votes.get(uuid);
        votes.put(uuid, count == null ? 1 : count + 1);
    }

    public static int getVote(UUID uuid){
        return votes.get(uuid);
    }

    public static void clearVote(UUID uuid){
        votes.remove(uuid);
    }

    public static String getReason(UUID uuid){
        return reasons.get(uuid);
    }

    public static void addReason(UUID uuid, String reason){
        reasons.put(uuid, reason);
    }

    public static void clearReason(UUID uuid){
        reasons.remove(uuid);
    }

}
