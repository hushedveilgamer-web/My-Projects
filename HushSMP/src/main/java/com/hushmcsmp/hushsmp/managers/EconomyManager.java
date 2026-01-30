package com.hushmcsmp.hushsmp.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {
    
    private Plugin plugin;
    private Map<UUID, Double> playerMoney = new HashMap<>();
    private File dataFile;
    
    public EconomyManager(Plugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "money.dat");
        plugin.getDataFolder().mkdirs();
        loadMoney();
    }
    
    public void addMoney(UUID uuid, double amount) {
        playerMoney.put(uuid, getMoney(uuid) + amount);
    }
    
    public void removeMoney(UUID uuid, double amount) {
        double current = getMoney(uuid);
        if (current >= amount) {
            playerMoney.put(uuid, current - amount);
        }
    }
    
    public double getMoney(UUID uuid) {
        return playerMoney.getOrDefault(uuid, 0.0);
    }
    
    public boolean canAfford(UUID uuid, double amount) {
        return getMoney(uuid) >= amount;
    }
    
    public void saveMoney() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(playerMoney);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save money data: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public void loadMoney() {
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                playerMoney = (Map<UUID, Double>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                plugin.getLogger().warning("Failed to load money data: " + e.getMessage());
                playerMoney = new HashMap<>();
            }
        }
    }
}
