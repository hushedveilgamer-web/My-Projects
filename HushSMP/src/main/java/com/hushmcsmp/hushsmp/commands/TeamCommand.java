package com.hushmcsmp.hushsmp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;
import com.hushmcsmp.hushsmp.managers.TeamManager;

public class TeamCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public TeamCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("team").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            player.sendMessage("§cUsage: /team [create/invite/leave/home]");
            return true;
        }
        
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length < 2) {
                player.sendMessage("§cUsage: /team create <teamName>");
                return true;
            }
            
            String teamName = args[1];
            if (plugin.getTeamManager().createTeam(player.getUniqueId(), teamName)) {
                player.sendMessage("§a✓ Team '" + teamName + "' created!");
            } else {
                player.sendMessage("§cTeam already exists!");
            }
            return true;
        }
        
        if (args[0].equalsIgnoreCase("invite")) {
            if (args.length < 2) {
                player.sendMessage("§cUsage: /team invite <player>");
                return true;
            }
            
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage("§cPlayer not found!");
                return true;
            }
            
            TeamManager.Team team = plugin.getTeamManager().getPlayerTeam(player.getUniqueId());
            if (team == null) {
                player.sendMessage("§cYou are not in a team!");
                return true;
            }
            
            if (plugin.getTeamManager().addMember(player.getUniqueId(), target.getUniqueId(), team.getName())) {
                player.sendMessage("§a✓ " + target.getName() + " invited!");
                target.sendMessage("§bYou have been invited to team " + team.getName());
            } else {
                player.sendMessage("§cYou must be the team owner!");
            }
            return true;
        }
        
        if (args[0].equalsIgnoreCase("leave")) {
            plugin.getTeamManager().removeMember(player.getUniqueId());
            player.sendMessage("§a✓ You left your team!");
            return true;
        }
        
        if (args[0].equalsIgnoreCase("home")) {
            TeamManager.Team team = plugin.getTeamManager().getPlayerTeam(player.getUniqueId());
            if (team == null) {
                player.sendMessage("§cYou are not in a team!");
                return true;
            }
            
            if (team.getTeamHome() == null) {
                player.sendMessage("§cYour team doesn't have a home set!");
                return true;
            }
            
            player.teleport(team.getTeamHome());
            player.sendMessage("§a✓ Teleported to team home!");
            return true;
        }
        
        return true;
    }
}
