package com.hushmcsmp.hushsmp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import com.hushmcsmp.hushsmp.HushSMP;

public class AFKListener implements Listener {
    
    private HushSMP plugin;
    
    public AFKListener(HushSMP plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // If player moves, they're not AFK
        if (plugin.getAFKManager().isAFK(event.getPlayer().getUniqueId())) {
            plugin.getAFKManager().toggleAFK(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage("§cAFK mode disabled - you moved!");
        }
    }
}
