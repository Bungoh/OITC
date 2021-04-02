package com.bungoh.oitc.utils;

import org.bukkit.ChatColor;

public enum Messages {

    PREFIX("prefix"),
    LOBBY_LOCATION_SET("messages.lobby_location_set"),
    ARENA_DOES_NOT_EXIST("messages.arena_does_not_exist"),
    ARENA_ALREADY_EXISTS("messages.arena_already_exists"),
    ARENA_NOT_SETUP("messages.arena_not_setup"),
    ARENA_READY("messages.arena_ready"),
    ARENA_NOT_READY("messages.arena_not_ready"),
    ARENA_NOT_EDITABLE("messages.arena_not_editable"),
    ARENA_CREATED("messages.arena_created"),
    ARENA_SPAWN_ADDED("messages.arena_spawn_added"),
    ARENA_REMOVED("messages.arena_removed"),
    UNEXPECTED_ERROR("messages.unexpected_error");

    private String path;

    Messages(String path) { this.path = path; }

    public String getPath() {
        return path;
    }

}
