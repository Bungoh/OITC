package com.bungoh.oitc.game;

import com.bungoh.oitc.files.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Manager {

    private static ArrayList<Arena> arenas;

    public Manager() {
        arenas = new ArrayList<>();
        for (String name : Data.getArenaNames()) {
            arenas.add(new Arena(name));
        }
    }

    public static void addArena(String name) {
        arenas.add(new Arena(name));
    }

    public static void removeArena(Arena arena) {
        arenas.remove(arena);
    }

    public static boolean isPlaying(Player player) {
        for (Arena arena: arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public static Arena getArena(String name) {
        for (Arena arena : arenas) {
            if (arena.getName().equals(name)) {
                return arena;
            }
        }

        return null;
    }

    public static Arena getArena(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return arena;
            }
        }

        return null;
    }

    public static void resetAllArenas() {
        for (Arena a : arenas) {
            a.reset();
        }
    }

    public static boolean isRecruiting(String name) {
        return getArena(name).getState() == GameState.RECRUITING;
    }

    public static ArrayList<Arena> getArenas() {
        return arenas;
    }
}
