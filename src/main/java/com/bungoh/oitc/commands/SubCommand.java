package com.bungoh.oitc.commands;

import com.bungoh.oitc.files.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player player, String[] args);

    public String invalidUsageMessage() {
        return Config.getPrefix() + " " + ChatColor.RED + "Invalid Usage! Use " + getSyntax();
    }

}
