package com.hushmcsmp.hushsmp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import com.hushmcsmp.hushsmp.HushSMP;

public class StoreCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public StoreCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("store").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (plugin.getStoreManager().canGetKey(player.getUniqueId())) {
            plugin.getStoreManager().giveKey(player.getUniqueId());
            player.sendMessage("§a✓ You received a key!");
            player.sendMessage("§bYou can get another key in 1 hour.");
        } else {
            long remaining = plugin.getStoreManager().getKeyTimeRemaining(player.getUniqueId());
            String timeStr = plugin.getStoreManager().formatTime(remaining);
            player.sendMessage("§cYou must wait " + timeStr + " before getting another key!");
        }
        
        openStoreGUI(player);
        return true;
    }
    
    private void openStoreGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§b§lSTORE");
        
        // Add store items
        ItemStack items = createItem(Material.IRON_INGOT, "§bFree Items", 64);
        ItemStack blocks = createItem(Material.OAK_LOG, "§bFree Blocks", 32);
        ItemStack food = createItem(Material.COOKED_BEEF, "§bFree Food", 16);
        
        inv.setItem(11, items);
        inv.setItem(13, blocks);
        inv.setItem(15, food);
        
        player.openInventory(inv);
    }
    
    private ItemStack createItem(Material material, String name, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }
}
