package com.bungoh.oitc.files;

import com.bungoh.oitc.OITC;
import com.bungoh.oitc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Data {

    private OITC plugin;
    private static File file;
    private static YamlConfiguration config;

    public Data(OITC plugin) {
        this.plugin = plugin;

        file = new File(plugin.getDataFolder(), "data.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
        save();
    }

    /**
     * Checks if the arena with this name exists in config
     * @return true if the arena exists in the config
     */
    public static boolean arenaExists(String name) {
        return config.contains("arenas." + name);
    }

    /**
     * Initializes the arena in the configuration file with a world and a unique name if the arena doesn't exist.
     * Sets the ready state of the arena to false.
     * @param name - The unique name of the Arena
     * @param world - The world that the Arena is in
     * @return ARENA_CREATED if successfully created or ARENA_ALREADY_EXISTS
     */
    public static Messages initArena(String name, World world) {
        if (!arenaExists(name)) {
            config.set("arenas." + name + ".world", world.getName());
            config.set("arenas." + name + ".ready", false);
            save();
            return Messages.ARENA_CREATED;
        }

        return Messages.ARENA_ALREADY_EXISTS;
    }

    /**
     * Sets the global lobby location for OITC
     * @param location The location of the lobby spawn
     * @return LOBBY_LOCATION_SET when the lobby location has been set.
     */
    public static Messages setLobbySpawn(Location location) {
        String path = "lobby-spawn";
        config.set(path, location);
        save();
        return Messages.LOBBY_LOCATION_SET;
    }

    /**
     * Removes the Arena from the Data File
     * @param name The unique name of the Arena
     * @return A Message Enum
     */
    public static Messages removeArena(String name) {
        if (arenaExists(name)) {
            if (!isArenaReady(name)) {
                config.set("arenas." + name, null);
                save();
                return Messages.ARENA_REMOVED;
            } else {
                return Messages.ARENA_NOT_EDITABLE;
            }
        }
        return Messages.ARENA_DOES_NOT_EXIST;
    }

    /**
     * Adds a spawn location to the arena in the configuration file if the arena exists.
     * @param name - The unique name of the Arena
     * @param location - The target spawn location
     * @return a Message Enum
     */
    public static Messages addArenaSpawn(String name, Location location) {
        if (arenaExists(name)) {
            if (!isArenaReady(name)) {
                String path = "arenas." + name + ".spawns";
                List<Location> lastLocations = new ArrayList<>();

                if (config.get(path) != null) {
                    lastLocations = (List<Location>) config.getList("arenas." + name + ".spawns");
                }

                lastLocations.add(location);
                config.set(path, lastLocations);
                save();
                return Messages.ARENA_SPAWN_ADDED;
            } else {
                return Messages.ARENA_NOT_EDITABLE;
            }
        }
        return Messages.ARENA_DOES_NOT_EXIST;
    }

    /**
     * Check if Arena is in the ready state.
     * @param name The unique the name of the Arena
     * @return true if the Arena is ready and false if the Arena doesn't exist or it is not ready.
     */
    public static boolean isArenaReady(String name) {
        if (arenaExists(name)) {
            return config.getBoolean("arenas." + name + ".ready");
        }

        return false;
    }

    /**
     * Toggles the ready state of the Arena
     * @param name The unique name of the Arena
     * @return Appropriate Messages Enumerator
     */
    public static Messages arenaToggleReady(String name) {
        if (arenaExists(name)) {
            if (checkArenaSetupCompletion(name)) {
                String path = "arenas." + name + ".ready";
                boolean bool = config.getBoolean(path);
                config.set(path, !bool);
                save();
                if (!bool) {
                    return Messages.ARENA_READY; // The Arena is ready.
                } else {
                    return Messages.ARENA_NOT_READY; //The Arena is not ready.
                }
            } else {
                return Messages.ARENA_NOT_SETUP; // The Arena has not completed setup.
            }
        } else {
            return Messages.ARENA_DOES_NOT_EXIST; // The Arena does not exist.
        }
    }

    /**
     * Checks if the Arena has all required information in the config.
     * @param name The unique name of the Arena
     * @return true if the arena is ready to be used
     */
    public static boolean checkArenaSetupCompletion(String name) {
        if (arenaExists(name)) {
            String basePath = "arenas." + name + ".";
            if (config.get("arenas.lobby-spawn") == null || config.get(basePath + ".spawns") == null) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Get the world of a certain arena
     * @param name The name of an Arena
     * @return The Arena's World or null if the Arena does not exist.
     */
    public static World getWorld(String name) {
        if (arenaExists(name)) {
            return Bukkit.getWorld(config.getString("arenas." + name + ".world"));
        }
        return null;
    }

    /**
     * Get the lobby location of the OITC plugin
     * @return The lobby location or null if it doesn't exist.
     */
    public static Location getLobbyLocation() {
        return (Location) config.get("lobby-spawn");
    }

    /**
     * Generates a List of Arena Spawn Locations if the Arena exists.
     * @param name - The unique name of the Arena
     * @return a List of Locations or null if the Arena does not exist.
     */
    public static List<Location> getArenaSpawns(String name) {

        if (arenaExists(name)) {
            List<Location> locations = new ArrayList<>();

            if (config.get("arenas." + name + ".spawns") != null) {
                locations = (List<Location>) config.getList("arenas." + name + ".spawns");
            }

            return locations;
        }

        return null;
    }

    /**
     * Gives all Arena Names
     * @return Set of Arena Names but null if no Arenas exist.
     */
    public static Set<String> getArenaNames() {
        if (config.getConfigurationSection("arenas") != null) {
            return config.getConfigurationSection("arenas").getKeys(false);
        }
        return new HashSet<>();
    }

    private static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
