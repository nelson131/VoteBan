package me.nelson131.voteban.afk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class AFKManager {

    private static HashMap<UUID, Long> movements = new HashMap<>();

    public static void playerMove(UUID player){
        movements.put(player, System.currentTimeMillis());
    }

    public static void playerStop(UUID player){
        movements.remove(player);
    }

    public static void playerJoin(UUID player){
        movements.put(player, System.currentTimeMillis());
    }

    public static void playerLeave(UUID player){
        movements.remove(player, System.currentTimeMillis());
    }

    public static boolean isAFK(UUID player){
        long time = System.currentTimeMillis() - movements.get(player);

//        if(time >= 300000){
//            return true;
//        }
//        else {
//            return false;
//        }

        return time >= 300000;
    }

    public static int CountWithoutAFK(){
        return movements.size();
    }

    public static int CountOnlyAFK(){
        int countMove = movements.size();
        int countTotal = Bukkit.getOnlinePlayers().size();
        return countTotal - countMove;
    }
}
