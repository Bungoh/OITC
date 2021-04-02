package com.bungoh.oitc.game;

import com.bungoh.oitc.files.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.UUID;

public class Arena {

    private String name;
    private ArrayList<UUID> players;
    private Location lobbyLocation;
    private ArrayList<Location> spawnLocations;
    private boolean ready;
    private World world;
    private GameState state;
    private Game game;
    private Countdown countdown;

    public Arena(String name) {
        this.name = name;
        players = new ArrayList<>();
        ready = Data.isArenaReady(name);
        world = Data.getWorld(name);
        if (ready) {
            setup();
        }
    }

    public void setup() {
        //Set lobby location
        lobbyLocation = Data.getLobbyLocation();
        //Set Spawn Locations
        spawnLocations.addAll(Data.getArenaSpawns(name));
        //Set Game State
        state = GameState.RECRUITING;
        //Instantiate Game and Countdown
        game = new Game(this);
        countdown = new Countdown(this);
        //Arena is now completely ready
        ready = true;
        System.out.println("Arena " + name + " successfully setup.");
    }

    public void start() {
        game.start();
    }

    public void reset() {

    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getName() {
        return name;
    }

    public ArrayList<UUID> getPlayers() {
        return players;
    }

    public GameState getState() {
        return state;
    }

    public ArrayList<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void sendMessage(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }
}
