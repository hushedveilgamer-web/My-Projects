package com.hushmcsmp.hushsmp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.hushmcsmp.hushsmp.HushSMP;

public class PvPListener implements Listener {
    
    private HushSMP plugin;
    
    public PvPListener(HushSMP plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        
        if (killer != null) {
            // Award shards to killer (10 per kill, only once per player per session)
            plugin.getShardManager().addShards(killer.getUniqueId(), 10);
            killer.sendMessage("§b+10 shards for killing " + victim.getName());
            
            // Award money to killer
            plugin.getEconomyManager().addMoney(killer.getUniqueId(), 500.0);
            killer.sendMessage("§a+$500 for killing " + victim.getName());
            
            victim.sendMessage("§cYou lost all your items to " + killer.getName());
        }
        
        // Clear drops in PvP zones (optional - only if in RTP)
        // event.getDrops().clear();
    }
}
