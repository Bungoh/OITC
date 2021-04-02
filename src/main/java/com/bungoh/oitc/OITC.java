package com.bungoh.oitc;

import com.bungoh.oitc.commands.AdminCommandManager;
import com.bungoh.oitc.commands.BaseCommandManager;
import com.bungoh.oitc.files.Config;
import com.bungoh.oitc.files.Data;
import com.bungoh.oitc.game.Manager;
import org.bukkit.plugin.java.JavaPlugin;

public final class OITC extends JavaPlugin {

    private static OITC plugin;

    @Override
    public void onEnable() {
        // Init Global Plugin Reference
        OITC.plugin = this;

        //Create File Managers
        new Config(this);
        new Data(this);

        //Setup Arena Manager
        new Manager();

        //Create Command Managers
        getCommand("oitc").setExecutor(new BaseCommandManager());
        getCommand("oitcadmin").setExecutor(new AdminCommandManager());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static OITC getPlugin() {
        return plugin;
    }
}
