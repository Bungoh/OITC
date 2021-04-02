package com.bungoh.oitc.game;

import com.bungoh.oitc.files.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Game {

    private Arena arena;
    private Set<AlivePlayer> alivePlayers;
    private Set<Spectator> spectators;
    private HashMap<AlivePlayer, Integer> killMap;
    private int scoreLimit;

    public Game(Arena arena) {
        this.arena = arena;
    }

    public void start() {
        //Set GameState
        arena.setState(GameState.LIVE);

        //Initialize the Players
        initializePlayers();

        //Create empty spectator HashSet
        spectators = new HashSet<>();

        //Get Score Limit
        scoreLimit = Config.getScoreLimit();

        //Send Message
        arena.sendMessage(Config.getPrefix() + " " + ChatColor.GREEN + "The game has started! First to " + Config.getScoreLimit() + " kills wins!");
    }

    private void initializePlayers() {
        int numPlayers = arena.getPlayers().size();
        int numSpawns = arena.getSpawnLocations().size();
        killMap = new HashMap<>();
        for (int i = 0; i < numPlayers; i++) {
            AlivePlayer player = new AlivePlayer(this, Bukkit.getPlayer(arena.getPlayers().get(i)));
            alivePlayers.add(player);
            killMap.put(player, 0);
            if (i < numSpawns) {
                player.teleport(arena.getSpawnLocations().get(i));
            } else {
                player.teleport(arena.getSpawnLocations().get(0));
            }
        }
    }

    public void addKill(AlivePlayer player) {
        int currScore = killMap.get(player);
        if (currScore + 1 == scoreLimit) {
            gameEnd(player);
        }

        killMap.replace(player, currScore + 1);
    }

    public void gameEnd(AlivePlayer winner) {
        Bukkit.broadcastMessage(Config.getPrefix() + " " + ChatColor.YELLOW + winner.getName() + ChatColor.GREEN + " just won in " + ChatColor.YELLOW + arena.getName() + ChatColor.GREEN + "!");
        arena.reset();
    }

}