package com.hushmcsmp.hushsmp.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import java.util.*;

public class StoreManager {
    
    private Plugin plugin;
    private Map<UUID, Long> keyTimers = new HashMap<>();
    private static final long KEY_COOLDOWN = 3600000; // 1 hour in milliseconds
    
    public StoreManager(Plugin plugin) {
        this.plugin = plugin;
    }
    
    public boolean canGetKey(UUID uuid) {
        Long lastKeyTime = keyTimers.get(uuid);
        if (lastKeyTime == null) {
            return true;
        }
        return System.currentTimeMillis() - lastKeyTime >= KEY_COOLDOWN;
    }
    
    public long getKeyTimeRemaining(UUID uuid) {
        Long lastKeyTime = keyTimers.get(uuid);
        if (lastKeyTime == null) {
            return 0;
        }
        long remaining = KEY_COOLDOWN - (System.currentTimeMillis() - lastKeyTime);
        return Math.max(0, remaining);
    }
    
    public void giveKey(UUID uuid) {
        keyTimers.put(uuid, System.currentTimeMillis());
        // Give the player a key item (nether star) in inventory
        org.bukkit.entity.Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.getInventory().addItem(new org.bukkit.inventory.ItemStack(org.bukkit.Material.NETHER_STAR, 1));
        }
    }
    
    public String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        if (hours > 0) {
            return hours + "h " + (minutes % 60) + "m";
        } else if (minutes > 0) {
            return minutes + "m " + (seconds % 60) + "s";
        } else {
            return seconds + "s";
        }
    }
}
