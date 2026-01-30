package com.hushmcsmp.hushsmp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;

public class DuelListener implements Listener {
    
    private HushSMP plugin;
    
    public DuelListener(HushSMP plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onDuelDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        
        if (plugin.getDuelManager().isInDuel(victim.getUniqueId())) {
            if (killer != null && plugin.getDuelManager().isInDuel(killer.getUniqueId())) {
                // Award money to duel winner
                plugin.getEconomyManager().addMoney(killer.getUniqueId(), 5000.0);
                killer.sendMessage("§a✓ Duel won! +$5000");
                killer.sendMessage("§bGood game! Remember to say GG!");
                
                victim.sendMessage("§cYou lost the duel! -$5000");
                
                // End the duel
                plugin.getDuelManager().endDuel(victim.getUniqueId());
                plugin.getDuelManager().endDuel(killer.getUniqueId());
                
                // Clear drops
                event.getDrops().clear();
            }
        }
    }
}
