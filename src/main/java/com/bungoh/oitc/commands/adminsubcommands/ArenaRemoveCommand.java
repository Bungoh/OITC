package com.bungoh.oitc.commands.adminsubcommands;

import com.bungoh.oitc.commands.SubCommand;
import com.bungoh.oitc.files.Config;
import com.bungoh.oitc.files.Data;
import com.bungoh.oitc.game.Manager;
import com.bungoh.oitc.utils.Messages;
import org.bukkit.entity.Player;

public class ArenaRemoveCommand extends SubCommand {

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "Remove an OITC arena";
    }

    @Override
    public String getSyntax() {
        return "/oitcadmin remove [name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 2) {
            String arenaName = args[1];
            Messages message = Data.removeArena(arenaName);

            switch (message) {
                case ARENA_DOES_NOT_EXIST:
                case ARENA_NOT_EDITABLE:
                    player.sendMessage(Config.getMessage(message.getPath()));
                    break;
                case ARENA_REMOVED:
                    Manager.removeArena(Manager.getArena(args[1]));
                    player.sendMessage(Config.getMessage(message.getPath()));
                default:
                    player.sendMessage(Config.getMessage(Messages.UNEXPECTED_ERROR.getPath()));
            }
        } else {
            player.sendMessage(invalidUsageMessage());
        }
    }
}
