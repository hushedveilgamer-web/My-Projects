package com.hushmcsmp.hushsmp.managers;

import java.io.*;
import java.util.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class StatManager {
    private Plugin plugin;
    private Map<UUID, Integer> kills = new HashMap<>();
    private Map<UUID, Integer> deaths = new HashMap<>();
    private Map<UUID, Long> totalPlaytime = new HashMap<>();
    private Map<UUID, Long> loginTime = new HashMap<>();
    private File dataFile;

    public StatManager(Plugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "stats.dat");
        plugin.getDataFolder().mkdirs();
        loadStats();
    }

    public void playerLogin(Player player) {
        loginTime.put(player.getUniqueId(), System.currentTimeMillis());
        // ensure maps contain keys
        kills.putIfAbsent(player.getUniqueId(), 0);
        deaths.putIfAbsent(player.getUniqueId(), 0);
        totalPlaytime.putIfAbsent(player.getUniqueId(), 0L);
    }

    public void playerQuit(Player player) {
        UUID uuid = player.getUniqueId();
        Long login = loginTime.getOrDefault(uuid, null);
        if (login != null) {
            long session = System.currentTimeMillis() - login;
            totalPlaytime.put(uuid, totalPlaytime.getOrDefault(uuid, 0L) + session);
            loginTime.remove(uuid);
        }
        saveStats();
    }

    public void addKill(UUID uuid) {
        kills.put(uuid, getKills(uuid) + 1);
    }

    public void addDeath(UUID uuid) {
        deaths.put(uuid, getDeaths(uuid) + 1);
    }

    public int getKills(UUID uuid) {
        return kills.getOrDefault(uuid, 0);
    }

    public int getDeaths(UUID uuid) {
        return deaths.getOrDefault(uuid, 0);
    }

    public long getPlaytime(UUID uuid) {
        long total = totalPlaytime.getOrDefault(uuid, 0L);
        Long login = loginTime.getOrDefault(uuid, null);
        if (login != null) {
            total += System.currentTimeMillis() - login;
        }
        return total;
    }

    public String formatPlaytime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        return String.format("%dh %dm", hours, minutes % 60);
    }

    public void saveStats() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            Map<String, Object> data = new HashMap<>();
            data.put("kills", kills);
            data.put("deaths", deaths);
            data.put("playtime", totalPlaytime);
            oos.writeObject(data);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save stats: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadStats() {
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                Map<String, Object> data = (Map<String, Object>) ois.readObject();
                kills = (Map<UUID, Integer>) data.getOrDefault("kills", new HashMap<>());
                deaths = (Map<UUID, Integer>) data.getOrDefault("deaths", new HashMap<>());
                totalPlaytime = (Map<UUID, Long>) data.getOrDefault("playtime", new HashMap<>());
            } catch (IOException | ClassNotFoundException e) {
                plugin.getLogger().warning("Failed to load stats: " + e.getMessage());
                kills = new HashMap<>();
                deaths = new HashMap<>();
                totalPlaytime = new HashMap<>();
            }
        }
    }
}
