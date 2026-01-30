package com.hushmcsmp.hushsmp.managers;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.*;

public class RTPManager {
    
    private Plugin plugin;
    private Map<String, RTPRegion> regions = new HashMap<>();
    private Random random = new Random();
    
    public RTPManager(Plugin plugin) {
        this.plugin = plugin;
        initializeRegions();
    }
    
    private void initializeRegions() {
        regions.put("NA-East", new RTPRegion("NA-East", 0, 0, 5000, 5000));
        regions.put("NA-West", new RTPRegion("NA-West", -5000, 0, 0, 5000));
        regions.put("EU-Central", new RTPRegion("EU-Central", 0, 5000, 5000, 10000));
        regions.put("Asia-Pacific", new RTPRegion("Asia-Pacific", 5000, 0, 10000, 5000));
    }
    
    public void teleportRandom(Player player, String region) {
        if (!regions.containsKey(region)) {
            player.sendMessage("§cRegion not found!");
            return;
        }
        
        RTPRegion rtpRegion = regions.get(region);
        int x = rtpRegion.getMinX() + random.nextInt(rtpRegion.getMaxX() - rtpRegion.getMinX());
        int z = rtpRegion.getMinZ() + random.nextInt(rtpRegion.getMaxZ() - rtpRegion.getMinZ());
        
        World world = Bukkit.getWorlds().get(0);
        Location location = new Location(world, x, world.getHighestBlockYAt(x, z) + 1, z);
        
        player.teleport(location);
        player.sendMessage("§bTeleported to " + region + "!");
    }
    
    public Map<String, RTPRegion> getRegions() {
        return regions;
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
