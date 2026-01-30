package com.hushmcsmp.hushsmp.managers;

import org.bukkit.plugin.Plugin;
import java.io.*;
import java.util.*;

public class ShardManager {
    
    private Plugin plugin;
    private Map<UUID, Long> playerShards = new HashMap<>();
    private Map<String, Long> shardPrices = new HashMap<>();
    private File dataFile;
    
    public ShardManager(Plugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "shards.dat");
        plugin.getDataFolder().mkdirs();
        initializeShardPrices();
        loadShards();
    }
    
    private void initializeShardPrices() {
        shardPrices.put("skeleton_spawner", 500L);
        shardPrices.put("iron_golem_spawner", 300L);
        shardPrices.put("zombie_spawner", 150L);
        shardPrices.put("creeper_spawner", 200L);
    }
    
    public void addShards(UUID uuid, long amount) {
        playerShards.put(uuid, getShards(uuid) + amount);
    }
    
    public void removeShards(UUID uuid, long amount) {
        long current = getShards(uuid);
        if (current >= amount) {
            playerShards.put(uuid, current - amount);
        }
    }
    
    public long getShards(UUID uuid) {
        return playerShards.getOrDefault(uuid, 0L);
    }
    
    public boolean canAffordShards(UUID uuid, long amount) {
        return getShards(uuid) >= amount;
    }
    
    public long getShardPrice(String item) {
        return shardPrices.getOrDefault(item, 0L);
    }
    
    public void saveShards() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(playerShards);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save shards: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public void loadShards() {
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                playerShards = (Map<UUID, Long>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                plugin.getLogger().warning("Failed to load shards: " + e.getMessage());
                playerShards = new HashMap<>();
            }
        }
    }
}
