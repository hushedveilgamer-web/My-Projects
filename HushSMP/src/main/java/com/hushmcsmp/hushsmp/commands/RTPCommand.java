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
            org.bukkit.Bukkit.getScheduler().runTask(plugin, () -> {
                org.bukkit.inventory.Inventory inv = org.bukkit.Bukkit.createInventory(null, 27, "§b§lRTP Menu");
                // Centered blocks: 11, 13, 15
                inv.setItem(11, createRTPItem(org.bukkit.Material.GRASS_BLOCK, "§aOverworld", "Random Overworld"));
                inv.setItem(13, createRTPItem(org.bukkit.Material.NETHERRACK, "§cNether", "Random Nether"));
                inv.setItem(15, createRTPItem(org.bukkit.Material.END_STONE, "§eEnd", "Random End"));
                player.openInventory(inv);
            });
            return true;
        }
        String region = args[0].toLowerCase();
        if (!(region.equals("overworld") || region.equals("nether") || region.equals("end"))) {
            player.sendMessage("§cRegion not found! Use /rtp to see available regions.");
            return true;
        }
        player.sendMessage("§bTeleporting to " + region + "...");
        plugin.getRTPManager().teleportRandom(player, region);
        return true;
    }

    private org.bukkit.inventory.ItemStack createRTPItem(org.bukkit.Material mat, String name, String lore) {
        org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(mat);
        org.bukkit.inventory.meta.ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            java.util.List<String> loreList = new java.util.ArrayList<>();
            loreList.add("§7" + lore);
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
        return item;
    }
}
