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

        // Balanced items (all $60)
        inv.setItem(10, createItem(Material.TOTEM_OF_UNDYING, "§eTotem of Undying", "§7$60"));
        inv.setItem(11, createItem(Material.ENDER_PEARL, "§bEnder Pearl (8)", "§7$60"));
        inv.setItem(12, createItem(Material.END_CRYSTAL, "§dEnd Crystal (2)", "§7$60"));
        inv.setItem(13, createItem(Material.RESPAWN_ANCHOR, "§6Respawn Anchor", "§7$60"));
        inv.setItem(14, createItem(Material.OBSIDIAN, "§8Obsidian (16)", "§7$60"));
        inv.setItem(15, createItem(Material.GLOWSTONE, "§eGlowstone (16)", "§7$60"));
        inv.setItem(16, createItem(Material.CRYING_OBSIDIAN, "§5Crying Obsidian (8)", "§7$60"));
        inv.setItem(19, createItem(Material.TNT, "§cTNT (8)", "§7$60"));
        inv.setItem(20, createItem(Material.SLIME_BLOCK, "§aSlime Block (8)", "§7$60"));
        inv.setItem(21, createItem(Material.HONEY_BLOCK, "§6Honey Block (8)", "§7$60"));
        inv.setItem(22, createItem(Material.ENDER_CHEST, "§9Ender Chest", "§7$60"));
        inv.setItem(23, createItem(Material.SHULKER_BOX, "§dShulker Box", "§7$60"));

        player.openInventory(inv);
    }
    
    private ItemStack createItem(Material material, String name, String lore) {
        int amount = 1;
        if (name.contains("(8)")) amount = 8;
        if (name.contains("(16)")) amount = 16;
        if (name.contains("(2)")) amount = 2;
        if (name.contains("(4)")) amount = 4;
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(java.util.Arrays.asList(lore));
            item.setItemMeta(meta);
        }
        return item;
    }
}
