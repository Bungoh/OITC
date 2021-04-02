package com.bungoh.oitc.game;

import org.bukkit.entity.Player;

public class Spectator extends GameParticipant {

    public Spectator(Game game, Player player) {
        super(game, player);
        init();
    }

    public void init() {

    }

    @Override
    public void cleanup() {

    }
}
