package com.bungoh.oitc.commands.adminsubcommands;

import com.bungoh.oitc.commands.SubCommand;
import com.bungoh.oitc.files.Config;
import com.bungoh.oitc.files.Data;
import com.bungoh.oitc.game.Manager;
import com.bungoh.oitc.utils.Messages;
import org.bukkit.entity.Player;

public class ArenaReadyCommand extends SubCommand {

    @Override
    public String getName() {
        return "ready";
    }

    @Override
    public String getDescription() {
        return "Toggle the ready state of an arena";
    }

    @Override
    public String getSyntax() {
        return "/oitcadmin ready [name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 2) {
            String arenaName = args[1];
            Messages message = Data.arenaToggleReady(arenaName);

            switch (message) {
                case ARENA_DOES_NOT_EXIST:
                case ARENA_NOT_SETUP:
                    player.sendMessage(Config.getMessage(message.getPath()));
                    break;
                case ARENA_READY:
                    Manager.getArena(arenaName).setup();
                    player.sendMessage(Config.getMessage(message.getPath()));
                    break;
                case ARENA_NOT_READY:
                    Manager.getArena(arenaName).setReady(false);
                    player.sendMessage(Config.getMessage(message.getPath()));
                    break;
                default:
                    player.sendMessage(Config.getMessage(Messages.UNEXPECTED_ERROR.getPath()));
                    break;
            }
        } else {
            player.sendMessage(invalidUsageMessage());
        }
    }
}
