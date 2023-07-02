package me.nelson131.voteban;

import me.nelson131.voteban.afk.ConnectionListener;
import me.nelson131.voteban.afk.MoveListener;
import me.nelson131.voteban.afk.PlayersListCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class VoteBan extends JavaPlugin {

    public static Plugin plugin = VoteBan.getPlugin();

    public static Map<String, Integer> votes = new HashMap<>();
    public static Map<String, String> reasons = new HashMap<>();
    public static Map<String, Set<String>> cooldowns = new HashMap<>();
    public static Map<String, Boolean> repeats = new HashMap<>();
    public static Set<String> active = new HashSet<>();

    FileConfiguration config = getConfig();

    private static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        this.plugin = this;

        getCommand("voteban").setExecutor(new PollCommand());
        getCommand("vote").setExecutor(new VoteCommand());
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
