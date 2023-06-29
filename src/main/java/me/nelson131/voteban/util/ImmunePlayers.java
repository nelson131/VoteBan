package me.nelson131.voteban.util;

import java.util.ArrayList;
import java.util.List;

import static me.nelson131.voteban.VoteBan.plugin;

public class ImmunePlayers {

    private static List<String> names = new ArrayList<>();

    public static void addImmune(String name){
        names.add(name);
        plugin.getConfig().set("immunePlayers", names);
    }

    public static boolean getImmune(String name){
        return plugin.getConfig().getList("immunePlayers").contains(name);
    }

    public static void clearImmune(String name){
        names.remove(name);
        plugin.getConfig().set("immunePlayers", names);
    }
}
