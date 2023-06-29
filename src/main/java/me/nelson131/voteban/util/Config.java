package me.nelson131.voteban.util;

import net.kyori.adventure.text.format.TextColor;

import static me.nelson131.voteban.VoteBan.plugin;

public class Config {

    public static String getCFG(String key){
        return plugin.getConfig().getString(key);
    }

    public static String prefix(){
        return getCFG("prefix");
    }

    public static TextColor colorRed(){
        return TextColor.color(255, 0, 0);
    }

    public static TextColor colorWhite(){
        return TextColor.color(255, 255, 255);
    }

    public static TextColor colorGreen(){
        return TextColor.color(0, 255, 0);
    }
}
