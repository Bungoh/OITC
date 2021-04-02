package com.bungoh.oitc.commands.basesubcommands;

import com.bungoh.oitc.commands.SubCommand;
import com.bungoh.oitc.game.Arena;
import com.bungoh.oitc.game.Manager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaListCommand extends SubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "List all available arenas";
    }

    @Override
    public String getSyntax() {
        return "/oitc list";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length == 1) {
            player.sendMessage(ChatColor.DARK_RED + "Available Arenas:");
            for (Arena arena : Manager.getArenas()) {
                if (arena.isReady()) {
                    player.sendMessage(ChatColor.RED + " - " + arena.getName());
                }
            }
        } else {
            player.sendMessage(invalidUsageMessage());
        }

    }

}
