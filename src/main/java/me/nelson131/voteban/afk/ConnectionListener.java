package me.nelson131.voteban.afk;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

import static me.nelson131.voteban.afk.AFKManager.playerJoin;
import static me.nelson131.voteban.afk.AFKManager.playerLeave;

public class ConnectionListener implements Listener {

    @EventHandler
    public static void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        playerJoin(playerUUID);
    }

    @EventHandler
    public static void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        playerLeave(playerUUID);
    }
}
