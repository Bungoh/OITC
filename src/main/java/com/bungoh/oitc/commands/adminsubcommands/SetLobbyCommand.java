package com.bungoh.oitc.commands.adminsubcommands;

import com.bungoh.oitc.commands.SubCommand;
import com.bungoh.oitc.files.Config;
import com.bungoh.oitc.files.Data;
import com.bungoh.oitc.utils.Messages;
import org.bukkit.entity.Player;

public class SetLobbyCommand extends SubCommand {

    @Override
    public String getName() {
        return "setlobby";
    }

    @Override
    public String getDescription() {
        return "Sets the global lobby spawn for OITC";
    }

    @Override
    public String getSyntax() {
        return "/oitcadmin setlobby";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1) {
            Messages message = Data.setLobbySpawn(player.getLocation());
            player.sendMessage(Config.getMessage(message.getPath()));
        } else {
            player.sendMessage(invalidUsageMessage());
        }

    }
}
