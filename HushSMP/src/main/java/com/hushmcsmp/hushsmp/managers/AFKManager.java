package com.hushmcsmp.hushsmp.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.*;

public class AFKManager {
    
    private Plugin plugin;
    private Set<UUID> afkPlayers = new HashSet<>();
    private Map<UUID, Long> lastAFKReward = new HashMap<>();
    private static final long AFK_REWARD_COOLDOWN = 60000; // 1 minute
    private static final int SHARDS_PER_REWARD = 1;
    
    public AFKManager(Plugin plugin) {
        this.plugin = plugin;
        startAFKLoop();
    }
    
    public void toggleAFK(UUID uuid) {
        if (afkPlayers.contains(uuid)) {
            afkPlayers.remove(uuid);
        } else {
            afkPlayers.add(uuid);
        }
    }
    
    public boolean isAFK(UUID uuid) {
        return afkPlayers.contains(uuid);
    }
    
    private void startAFKLoop() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long now = System.currentTimeMillis();
            for (UUID uuid : new HashSet<>(afkPlayers)) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) {
                    afkPlayers.remove(uuid);
                    continue;
                }
                
                Long lastReward = lastAFKReward.getOrDefault(uuid, 0L);
                if (now - lastReward >= AFK_REWARD_COOLDOWN) {
                    // Access plugin through the parameter instead of getInstance
                    if (plugin instanceof com.hushmcsmp.hushsmp.HushSMP) {
                        com.hushmcsmp.hushsmp.HushSMP hushSMP = (com.hushmcsmp.hushsmp.HushSMP) plugin;
                        hushSMP.getShardManager().addShards(uuid, SHARDS_PER_REWARD);
                        lastAFKReward.put(uuid, now);
                        player.sendMessage("§b[AFK] You earned " + SHARDS_PER_REWARD + " shard!");
                    }
                }
            }
        }, 0, 20); // Run every second
    }
}
