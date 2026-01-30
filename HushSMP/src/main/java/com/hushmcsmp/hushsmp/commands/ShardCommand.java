package com.hushmcsmp.hushsmp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;

public class ShardCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public ShardCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("shard").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            long shards = plugin.getShardManager().getShards(player.getUniqueId());
            player.sendMessage("§bYour Shards: §7" + shards);
            return true;
        }
        
        if (args[0].equalsIgnoreCase("shop")) {
            showShardShop(player);
            return true;
        }
        
        if (args[0].equalsIgnoreCase("balance")) {
            long shards = plugin.getShardManager().getShards(player.getUniqueId());
            player.sendMessage("§bYour Shards: §7" + shards);
            return true;
        }
        
        return true;
    }
    
    private void showShardShop(Player player) {
        player.sendMessage("§b=== Shard Shop ===");
        player.sendMessage("§b  Skeleton Spawner: §7500 shards");
        player.sendMessage("§b  Iron Golem Spawner: §7300 shards");
        player.sendMessage("§b  Zombie Spawner: §7150 shards");
        player.sendMessage("§b  Creeper Spawner: §7200 shards");
        player.sendMessage("§bUsage: /shard buy <spawner>");
    }
}
