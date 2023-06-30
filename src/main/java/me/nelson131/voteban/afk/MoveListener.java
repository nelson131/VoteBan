package me.nelson131.voteban.afk;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

import static me.nelson131.voteban.afk.AFKManager.*;

public class MoveListener implements Listener {

    @EventHandler
    public static void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if(isAFK(playerUUID)) playerStop(playerUUID);
        else playerMove(playerUUID);

    }
}
