package com.bungoh.oitc.commands.basesubcommands;

import com.bungoh.oitc.commands.SubCommand;
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

        } else {
            player.sendMessage(invalidUsageMessage());
        }
    }
}
