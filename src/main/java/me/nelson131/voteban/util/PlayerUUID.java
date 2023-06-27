package me.nelson131.voteban.util;

import java.util.UUID;

import static me.nelson131.voteban.VoteBan.*;

public class PlayerUUID {

    public static void addVote(UUID uuid){
        int count = 1;
        if(votes.get(uuid) == null){
            votes.put(uuid, 1);
        }
        else {
            votes.put(uuid, votes.get(uuid)+1);
        }
    }

    public static int getVote(UUID uuid){
        int count = votes.get(uuid);
        return count;
    }

    public static void clearVote(UUID uuid){
        votes.remove(uuid);
    }

    public static String getReason(UUID uuid){
        String reason = reasons.get(uuid);
        return reason;
    }

    public static void addReason(UUID uuid, String reason){
        reasons.put(uuid, reason);
    }

    public static void clearReason(UUID uuid){
        reasons.remove(uuid);
    }

}
