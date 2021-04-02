package com.bungoh.oitc.commands.adminsubcommands;

import com.bungoh.oitc.commands.SubCommand;
import com.bungoh.oitc.files.Config;
import com.bungoh.oitc.files.Data;
import com.bungoh.oitc.game.Manager;
import com.bungoh.oitc.utils.Messages;
import org.bukkit.entity.Player;

public class ArenaCreateCommand extends SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create an OITC arena";
    }

    @Override
    public String getSyntax() {
        return "/oitcadmin create [name]";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length == 2) {
            String arenaName = args[1];
            Messages message = Data.initArena(arenaName, player.getWorld());

            switch (message) {
                case ARENA_CREATED:
                    player.sendMessage(Config.getMessage(message.getPath()));
                    Manager.addArena(arenaName);
                    break;
                case ARENA_ALREADY_EXISTS:
                    player.sendMessage(Config.getMessage(message.getPath()));
                    break;
            }

        } else {
            player.sendMessage(invalidUsageMessage());
        }

    }
}
