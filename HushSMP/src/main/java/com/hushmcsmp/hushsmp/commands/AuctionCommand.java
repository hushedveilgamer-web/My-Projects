package com.hushmcsmp.hushsmp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;
import com.hushmcsmp.hushsmp.managers.AuctionManager;

public class AuctionCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public AuctionCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("ah").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            showAuctions(player);
            return true;
        }
        
        if (args[0].equalsIgnoreCase("sell")) {
            if (args.length < 2) {
                player.sendMessage("§cUsage: /ah sell <price>");
                return true;
            }
            
            try {
                double price = Double.parseDouble(args[1]);
                if (player.getInventory().getItemInMainHand() != null && 
                    !player.getInventory().getItemInMainHand().getType().isAir()) {
                    
                    plugin.getAuctionManager().addAuction(
                        player.getUniqueId(),
                        player.getInventory().getItemInMainHand().clone(),
                        price
                    );
                    player.getInventory().getItemInMainHand().setAmount(0);
                    player.sendMessage("§aItem listed for $" + price + "!");
                } else {
                    player.sendMessage("§cYou must hold an item to sell!");
                }
            } catch (NumberFormatException e) {
                player.sendMessage("§cInvalid price!");
            }
            return true;
        }
        
        if (args[0].equalsIgnoreCase("buy")) {
            if (args.length < 2) {
                player.sendMessage("§cUsage: /ah buy <auctionId>");
                return true;
            }
            try {
                int id = Integer.parseInt(args[1]);
                if (plugin.getAuctionManager().buyAuction(player.getUniqueId(), id, plugin.getEconomyManager())) {
                    player.sendMessage("§aPurchase successful!");
                    // Update scoreboard after purchase
                    plugin.getScoreboardManager().updateScoreboard(player);
                } else {
                    player.sendMessage("§cCannot afford this item!");
                }
            } catch (NumberFormatException e) {
                player.sendMessage("§cInvalid auction ID!");
            }
            return true;
        }
        
        return true;
    }
    
    private void showAuctions(Player player) {
        player.sendMessage("§b=== Auction House ===");
        var auctions = plugin.getAuctionManager().getAuctions();
        if (auctions.isEmpty()) {
            player.sendMessage("§cNo auctions available!");
            return;
        }
        
        for (int i = 0; i < auctions.size(); i++) {
            AuctionManager.AuctionItem auction = auctions.get(i);
            player.sendMessage("§b[" + i + "] " + auction.getItem().getType() + " x" + 
                             auction.getItem().getAmount() + " - §7$" + auction.getPrice());
        }
    }
}
