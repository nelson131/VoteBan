package me.nelson131.voteban;

import me.nelson131.voteban.afk.ConnectionListener;
import me.nelson131.voteban.afk.MoveListener;
import me.nelson131.voteban.afk.PlayersListCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class VoteBan extends JavaPlugin {

    public static Plugin plugin = VoteBan.getPlugin();

    public static Map<UUID, Integer> votes = new HashMap<>();
    public static Map<UUID, String> reasons = new HashMap<>();
    public static Map<UUID, Set<UUID>> cooldowns = new HashMap<>();
    public static Map<UUID, Boolean> repeats = new HashMap<>();
    public static List<Player> active = new ArrayList<>();

    FileConfiguration config = getConfig();

    private static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        this.plugin = this;

        getCommand("voteban").setExecutor(new PollCommand());
        getCommand("vote").setExecutor(new VoteCommand());
        getCommand("immune").setExecutor(new ImmunityCommand());
        getCommand("players").setExecutor(new PlayersListCommand());

        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        getServer().getPluginManager().registerEvents(new MoveListener(), this);

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
