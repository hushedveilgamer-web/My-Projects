package com.hushmcsmp.hushsmp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;
import com.hushmcsmp.hushsmp.managers.RTPManager;

public class RTPCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public RTPCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("rtp").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            player.sendMessage("§b=== RTP Regions ===");
            for (String region : plugin.getRTPManager().getRegions().keySet()) {
                player.sendMessage("§b  /rtp " + region);
            }
            return true;
        }
        
        String region = args[0];
        if (!plugin.getRTPManager().getRegions().containsKey(region)) {
            player.sendMessage("§cRegion not found! Use /rtp to see available regions.");
            return true;
        }
        
        player.sendMessage("§bTeleporting to " + region + "...");
        plugin.getRTPManager().teleportRandom(player, region);
        return true;
    }
}
