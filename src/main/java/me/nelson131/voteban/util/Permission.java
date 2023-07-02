package me.nelson131.voteban.util;

import org.bukkit.entity.Player;

public class Permission {

    public static boolean checkPermission(Player player, String permission){
        return player.hasPermission(permission);
    }

}
