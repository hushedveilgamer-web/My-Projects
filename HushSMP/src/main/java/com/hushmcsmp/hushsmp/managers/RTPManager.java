package com.hushmcsmp.hushsmp.managers;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.*;

public class RTPManager {
    
    private Plugin plugin;
    private Random random = new Random();
    // Prices for each region
    private final java.util.Map<String, Integer> rtpPrices = java.util.Map.of(
        "overworld", 100,
        "nether", 250,
        "end", 500
    );
    // Region bounds for each world
    private final java.util.Map<String, RTPRegion> regions = java.util.Map.of(
        "overworld", new RTPRegion("overworld", -10000, -10000, 10000, 10000),
        "nether", new RTPRegion("nether", -4000, -4000, 4000, 4000),
        "end", new RTPRegion("end", -2000, -2000, 2000, 2000)
    );

    public RTPManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void teleportRandom(Player player, String region) {
        region = region.toLowerCase();
        if (!regions.containsKey(region)) {
            player.sendMessage("§cRegion not found!");
            return;
        }
        int price = rtpPrices.getOrDefault(region, 100);
        com.hushmcsmp.hushsmp.managers.EconomyManager eco = ((com.hushmcsmp.hushsmp.HushSMP)plugin).getEconomyManager();
        if (!eco.canAfford(player.getUniqueId(), price)) {
            player.sendMessage("§cYou need $" + price + " to use RTP here!");
            return;
        }
        eco.removeMoney(player.getUniqueId(), price);
        World world;
        switch (region) {
            case "nether":
                world = Bukkit.getWorld("world_nether");
                break;
            case "end":
                world = Bukkit.getWorld("world_the_end");
                break;
            default:
                world = Bukkit.getWorlds().get(0);
        }
        RTPRegion rtpRegion = regions.get(region);
        int x = rtpRegion.getMinX() + random.nextInt(rtpRegion.getMaxX() - rtpRegion.getMinX());
        int z = rtpRegion.getMinZ() + random.nextInt(rtpRegion.getMaxZ() - rtpRegion.getMinZ());
        int y = (world != null) ? world.getHighestBlockYAt(x, z) + 1 : 100;
        if (world == null) {
            player.sendMessage("§cWorld not found for region: " + region);
            return;
        }
        player.teleport(new Location(world, x, y, z));
        player.sendMessage("§bTeleported to " + capitalize(region) + " for $" + price + "!");
        // Update scoreboard after RTP
        ((com.hushmcsmp.hushsmp.HushSMP)plugin).getScoreboardManager().updateScoreboard(player);
    }

    public Map<String, RTPRegion> getRegions() {
        return regions;
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    public static class RTPRegion {
        private String name;
        private int minX, minZ, maxX, maxZ;
        
        public RTPRegion(String name, int minX, int minZ, int maxX, int maxZ) {
            this.name = name;
            this.minX = minX;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxZ = maxZ;
        }
        
        public String getName() {
            return name;
        }
        
        public int getMinX() {
            return minX;
        }
        
        public int getMinZ() {
            return minZ;
        }
        
        public int getMaxX() {
            return maxX;
        }
        
        public int getMaxZ() {
            return maxZ;
        }
    }
}
