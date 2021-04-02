package com.bungoh.oitc.commands.basesubcommands;

import com.bungoh.oitc.commands.SubCommand;
import com.bungoh.oitc.files.Config;
import com.bungoh.oitc.game.Arena;
import com.bungoh.oitc.game.Manager;
import com.bungoh.oitc.utils.Messages;
import org.bukkit.entity.Player;

public class ArenaJoinCommand extends SubCommand {

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join an OITC game";
    }

    @Override
    public String getSyntax() {
        return "/oitc join [name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 2) {
            String arenaName = args[1];
            Arena arena = Manager.getArena(arenaName);
            if (arena != null) {
                if (Manager.isRecruiting(arenaName)) {
                    if (!Manager.isPlaying(player)) {
                        arena.addPlayer(player);
                    } else {
                        player.sendMessage(Config.getMessage(Messages.ARENA_ALREADY_INGAME.getPath()));
                    }
                } else {
                    player.sendMessage(Config.getMessage(Messages.ARENA_NOT_RECRUITING.getPath()));
                }
            } else {
                player.sendMessage(Config.getMessage(Messages.ARENA_DOES_NOT_EXIST.getPath()));
            }
        } else {
            player.sendMessage(invalidUsageMessage());
        }
    }
}
