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

public class ShopCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public ShopCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("shop").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        openShopGUI(player);
        return true;
    }
    
    private void openShopGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, "§b§lSHOP");
        
        // Equipment
        ItemStack helmet = createItem(Material.DIAMOND_HELMET, "§bDiamond Helmet", "§7$5000");
        ItemStack chestplate = createItem(Material.DIAMOND_CHESTPLATE, "§bDiamond Chestplate", "§7$7000");
        ItemStack leggings = createItem(Material.DIAMOND_LEGGINGS, "§bDiamond Leggings", "§7$6000");
        ItemStack boots = createItem(Material.DIAMOND_BOOTS, "§bDiamond Boots", "§7$4000");
        
        inv.setItem(10, helmet);
        inv.setItem(11, chestplate);
        inv.setItem(12, leggings);
        inv.setItem(13, boots);
        
        // Tools
        ItemStack pickaxe = createItem(Material.DIAMOND_PICKAXE, "§bDiamond Pickaxe", "§7$3000");
        ItemStack axe = createItem(Material.DIAMOND_AXE, "§bDiamond Axe", "§7$3000");
        ItemStack shovel = createItem(Material.DIAMOND_SHOVEL, "§bDiamond Shovel", "§7$2000");
        
        inv.setItem(19, pickaxe);
        inv.setItem(20, axe);
        inv.setItem(21, shovel);
        
        // Items
        ItemStack blaze = createItem(Material.BLAZE_ROD, "§bBlaze Rod (10)", "§7$500");
        ItemStack shulker = createItem(Material.SHULKER_BOX, "§bShulker Box", "§7$2000");
        ItemStack ender = createItem(Material.ENDER_CHEST, "§bEnder Chest", "§7$165");
        
        inv.setItem(28, blaze);
        inv.setItem(29, shulker);
        inv.setItem(30, ender);
        
        player.openInventory(inv);
    }
    
    private ItemStack createItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(java.util.Arrays.asList(lore));
            item.setItemMeta(meta);
        }
        return item;
    }
}
