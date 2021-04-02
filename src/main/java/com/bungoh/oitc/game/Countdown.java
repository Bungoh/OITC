package com.bungoh.oitc.game;

import com.bungoh.oitc.OITC;
import com.bungoh.oitc.files.Config;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Arena arena;
    private int seconds;

    public Countdown(Arena arena) {
        this.arena = arena;
        this.seconds = Config.getCountdownSeconds();
    }

    public void begin() {
        arena.setState(GameState.COUNTDOWN);
        this.runTaskTimer(OITC.getPlugin(), 0L, 20L);
    }

    @Override
    public void run() {
        if (seconds == 0) {
            cancel();
            arena.start();
            return;
        }

        if (seconds <= 10 || seconds % 5 == 0) {
            arena.sendMessage(Config.getPrefix() + " " + ChatColor.GREEN + "Game will start in " + ChatColor.YELLOW + seconds + ChatColor.GREEN + " seconds.");
        }

        if (arena.getPlayers().size() < Config.getRequiredPlayers()) {
            cancel();
            arena.setState(GameState.RECRUITING);
            arena.sendMessage(Config.getPrefix() + " " + ChatColor.RED + "There are too few players. Countdown stopped.");
            return;
        }

        seconds--;
    }
}
