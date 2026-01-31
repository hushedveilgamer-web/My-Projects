package com.hushmcsmp.hushsmp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import com.hushmcsmp.hushsmp.HushSMP;

public class PlayerJoinListener implements Listener {
    
    private HushSMP plugin;
    
    public PlayerJoinListener(HushSMP plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("§b" + event.getPlayer().getName() + " §ajoined!");
        event.getPlayer().sendMessage("§b===== Welcome to HushSMP =====");
        event.getPlayer().sendMessage("§bUse §7/store §bto get free items with keys!");
        event.getPlayer().sendMessage("§bUse §7/sethome §bto set your base!");
        event.getPlayer().sendMessage("§bUse §7/rtp §bto explore the world!");
        plugin.getStatManager().playerLogin(event.getPlayer());
        plugin.getScoreboardManager().updateScoreboard(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getHomeManager().saveHomes();
        plugin.getTeamManager().saveTeams();
        plugin.getEconomyManager().saveMoney();
        plugin.getShardManager().saveShards();
        plugin.getStatManager().playerQuit(event.getPlayer());
        event.setQuitMessage("§c" + event.getPlayer().getName() + " §cleft!");
    }
}
