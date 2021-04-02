package com.bungoh.oitc.commands.adminsubcommands;

import com.bungoh.oitc.commands.SubCommand;
import com.bungoh.oitc.files.Config;
import com.bungoh.oitc.files.Data;
import com.bungoh.oitc.utils.Messages;
import org.bukkit.entity.Player;

public class ArenaAddSpawnCommand extends SubCommand {

    @Override
    public String getName() {
        return "addspawn";
    }

    @Override
    public String getDescription() {
        return "Adds a spawn location to the OITC arena";
    }

    @Override
    public String getSyntax() {
        return "/oitcadmin addspawn [name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 2) {
            String arenaName = args[1];
            Messages message = Data.addArenaSpawn(arenaName, player.getLocation());
            player.sendMessage(Config.getMessage(message.getPath()));
        }
    }
}
