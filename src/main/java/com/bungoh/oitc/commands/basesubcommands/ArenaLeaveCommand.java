package com.bungoh.oitc.commands.basesubcommands;

import com.bungoh.oitc.commands.SubCommand;
import com.bungoh.oitc.files.Config;
import com.bungoh.oitc.game.Arena;
import com.bungoh.oitc.game.Manager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaLeaveCommand extends SubCommand {

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave an OITC game";
    }

    @Override
    public String getSyntax() {
        return "/oitc leave";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length == 1) {
            if (Manager.isPlaying(player)) {
                Arena arena = Manager.getArena(player);
                arena.removePlayer(player);
                arena.sendMessage(Config.getPrefix() + " " + player.getName() + ChatColor.GREEN + " has left the game!");
                player.sendMessage(Config.getPrefix() + " " + ChatColor.GREEN + "You left the game!");
            } else {
                player.sendMessage(Config.getPrefix() + " " + ChatColor.RED + "You are not in a game!");
            }
        } else {
            player.sendMessage(invalidUsageMessage());
        }

    }
}
