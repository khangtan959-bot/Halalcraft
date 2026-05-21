package com.halalcraft.mod.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.halalcraft.mod.component.PlayerState;
import net.minecraft.server.world.ServerWorld;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<UUID, PlayerState> cache = new ConcurrentHashMap<>();
    private static Path dataDir;

    public static void init() {
        // Use the Minecraft server's default save directory
        try {
            // In a real environment, we'd use the server instance.
            // For a mod, we can fallback to a local folder or use the server's world path.
            // We will initialize the path when the first world loads.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDataDirectory(Path directory) {
        dataDir = directory;
        try {
            Files.createDirectories(dataDir.resolve("players"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerState getState(UUID uuid) {
        return cache.computeIfAbsent(uuid, DataManager::loadState);
    }

    public static void saveState(UUID uuid) {
        PlayerState state = cache.get(uuid);
        if (state == null || dataDir == null) return;

        try {
            Path filePath = dataDir.resolve("players/" + uuid + ".json");
            Files.writeString(filePath, GSON.toJson(state));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PlayerState loadState(UUID uuid) {
        if (dataDir == null) return new PlayerState();
        try {
            Path filePath = dataDir.resolve("players/" + uuid + ".json");
            if (Files.exists(filePath)) {
                return GSON.fromJson(Files.readString(filePath), PlayerState.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PlayerState();
    }

    public static void resetState(UUID uuid) {
        cache.remove(uuid);
        if (dataDir != null) {
            try {
                Files.deleteIfExists(dataDir.resolve("players/" + uuid + ".json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
