package com.hushmcsmp.hushsmp.managers;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import java.io.*;
import java.util.*;

public class HomeManager {
    
    private Plugin plugin;
    private Map<UUID, Map<String, Location>> playerHomes = new HashMap<>();
    private File dataFile;
    private static final int DEFAULT_HOMES = 2;
    
    public HomeManager(Plugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "homes.dat");
        plugin.getDataFolder().mkdirs();
        loadHomes();
    }
    
    public boolean setHome(UUID uuid, String name, Location location) {
        Map<String, Location> homes = playerHomes.computeIfAbsent(uuid, k -> new HashMap<>());
        if (homes.size() >= DEFAULT_HOMES && !homes.containsKey(name)) {
            return false; // Cannot exceed home limit
        }
        homes.put(name, location);
        return true;
    }
    
    public Location getHome(UUID uuid, String name) {
        Map<String, Location> homes = playerHomes.getOrDefault(uuid, new HashMap<>());
        return homes.get(name);
    }
    
    public void deleteHome(UUID uuid, String name) {
        Map<String, Location> homes = playerHomes.getOrDefault(uuid, new HashMap<>());
        homes.remove(name);
    }
    
    public Map<String, Location> getHomes(UUID uuid) {
        return playerHomes.getOrDefault(uuid, new HashMap<>());
    }
    
    public int getHomeCount(UUID uuid) {
        return getHomes(uuid).size();
    }
    
    public void saveHomes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(playerHomes);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save homes: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public void loadHomes() {
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                playerHomes = (Map<UUID, Map<String, Location>>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                plugin.getLogger().warning("Failed to load homes: " + e.getMessage());
                playerHomes = new HashMap<>();
            }
        }
    }
}
