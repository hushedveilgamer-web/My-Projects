package com.hushmcsmp.hushsmp.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.*;

public class DuelManager {
    
    private Plugin plugin;
    private Map<UUID, UUID> duelRequests = new HashMap<>();
    private Map<UUID, UUID> activeDuels = new HashMap<>();
    private Map<UUID, Location> duelSpectators = new HashMap<>();
    private Location duelArena;
    
    public DuelManager(Plugin plugin) {
        this.plugin = plugin;
        initializeDuelArena();
    }
    
    private void initializeDuelArena() {
        // Default duel arena at spawn
        World world = Bukkit.getWorlds().get(0);
        duelArena = new Location(world, 0, 64, 0);
    }
    
    public void sendDuelRequest(UUID requester, UUID target) {
        duelRequests.put(requester, target);
    }
    
    public boolean acceptDuel(UUID requester, UUID target) {
        if (duelRequests.getOrDefault(requester, null) != null && 
            duelRequests.get(requester).equals(target)) {
            
            activeDuels.put(requester, target);
            activeDuels.put(target, requester);
            duelRequests.remove(requester);
            
            Player p1 = Bukkit.getPlayer(requester);
            Player p2 = Bukkit.getPlayer(target);
            
            if (p1 != null && p2 != null) {
                p1.teleport(duelArena.clone().add(5, 0, 0));
                p2.teleport(duelArena.clone().add(-5, 0, 0));
                p1.sendMessage("§bDuel started! Good luck!");
                p2.sendMessage("§bDuel started! Good luck!");
            }
            return true;
        }
        return false;
    }
    
    public void endDuel(UUID player) {
        UUID opponent = activeDuels.get(player);
        if (opponent != null) {
            activeDuels.remove(player);
            activeDuels.remove(opponent);
        }
    }
    
    public boolean isInDuel(UUID player) {
        return activeDuels.containsKey(player);
    }
    
    public UUID getDuelOpponent(UUID player) {
        return activeDuels.get(player);
    }
    
    public void setDuelArena(Location location) {
        this.duelArena = location;
    }
    
    public Location getDuelArena() {
        return duelArena;
    }
}
