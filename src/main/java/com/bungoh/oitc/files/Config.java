package com.bungoh.oitc.files;

import com.bungoh.oitc.OITC;
import com.bungoh.oitc.utils.Messages;
import org.bukkit.ChatColor;

public class Config {

    private static OITC plugin;

    public Config(OITC plugin) {
        Config.plugin = plugin;

        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();
    }

    public static int getRequiredPlayers() {
        return plugin.getConfig().getInt("required-players");
    }

    public static int getCountdownSeconds() {
        return plugin.getConfig().getInt("countdown-timer");
    }

    public static int getScoreLimit() {
        return plugin.getConfig().getInt("score-limit");
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Messages.PREFIX.getPath()) + "&r");
    }

    public static String getMessage(String path) {
        String msg = plugin.getConfig().getString(path);
        if (msg != null) {
            return ChatColor.translateAlternateColorCodes('&', getPrefix() + " " + msg);
        }
        return "";
    }

}
