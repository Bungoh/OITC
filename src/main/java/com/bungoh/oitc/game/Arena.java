package com.bungoh.oitc.game;

import com.bungoh.oitc.OITC;
import com.bungoh.oitc.files.Config;
import com.bungoh.oitc.files.Data;
import org.bukkit.*;
import org.bukkit.entity.Player;

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
        spawnLocations = new ArrayList<>();
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
        //Load the Chunk
        world.loadChunk(lobbyLocation.getChunk());
        //Reset Players and Clean them Up
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            resetPlayer(player);
            player.teleport(lobbyLocation);
        }
        players.clear();

        //Reset Arena State
        state = GameState.RECRUITING;
        countdown = new Countdown(this);
        if (game != null) {
            game.cleanup();
        }
        game = new Game(this);
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(lobbyLocation);
        sendMessage(Config.getPrefix() + " " + player.getName() + ChatColor.GREEN + " has joined.");

        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);

        if (players.size() >= Config.getRequiredPlayers()) {
            countdown.begin();
        }
    }

    public void removePlayer(Player player) {
        resetPlayer(player);
        player.teleport(lobbyLocation);
        players.remove(player.getUniqueId());

        if (state == GameState.LIVE) {
            game.removePlayer(player);
        }

        if (state == GameState.COUNTDOWN && players.size() < Config.getRequiredPlayers()) {
            reset();
        }
    }

    public void resetPlayer(Player player) {
        if (player.isDead()) {
            player.spigot().respawn();
        }

        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(OITC.getPlugin(), player);
        }
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

    public boolean isReady() {
        return ready;
    }
}
