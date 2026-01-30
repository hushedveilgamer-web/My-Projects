package com.hushmcsmp.hushsmp.managers;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import java.io.*;
import java.util.*;

public class TeamManager {
    
    private Plugin plugin;
    private Map<String, Team> teams = new HashMap<>();
    private Map<UUID, String> playerTeam = new HashMap<>();
    private File dataFile;
    
    public TeamManager(Plugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "teams.dat");
        plugin.getDataFolder().mkdirs();
        loadTeams();
    }
    
    public boolean createTeam(UUID owner, String teamName) {
        if (teams.containsKey(teamName)) {
            return false;
        }
        Team team = new Team(teamName, owner);
        teams.put(teamName, team);
        playerTeam.put(owner, teamName);
        return true;
    }
    
    public Team getTeam(String teamName) {
        return teams.get(teamName);
    }
    
    public Team getPlayerTeam(UUID uuid) {
        String teamName = playerTeam.get(uuid);
        return teamName != null ? teams.get(teamName) : null;
    }
    
    public boolean addMember(UUID owner, UUID member, String teamName) {
        Team team = teams.get(teamName);
        if (team != null && team.getOwner().equals(owner)) {
            team.addMember(member);
            playerTeam.put(member, teamName);
            return true;
        }
        return false;
    }
    
    public void removeMember(UUID member) {
        String teamName = playerTeam.get(member);
        if (teamName != null) {
            Team team = teams.get(teamName);
            if (team != null) {
                team.removeMember(member);
            }
            playerTeam.remove(member);
        }
    }
    
    public void saveTeams() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            Map<String, Object> data = new HashMap<>();
            data.put("teams", teams);
            data.put("playerTeam", playerTeam);
            oos.writeObject(data);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save teams: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public void loadTeams() {
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                Map<String, Object> data = (Map<String, Object>) ois.readObject();
                teams = (Map<String, Team>) data.get("teams");
                playerTeam = (Map<UUID, String>) data.get("playerTeam");
            } catch (IOException | ClassNotFoundException e) {
                plugin.getLogger().warning("Failed to load teams: " + e.getMessage());
                teams = new HashMap<>();
                playerTeam = new HashMap<>();
            }
        }
    }
    
    public static class Team implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private UUID owner;
        private Set<UUID> members = new HashSet<>();
        private Location teamHome;
        
        public Team(String name, UUID owner) {
            this.name = name;
            this.owner = owner;
            this.members.add(owner);
        }
        
        public String getName() {
            return name;
        }
        
        public UUID getOwner() {
            return owner;
        }
        
        public Set<UUID> getMembers() {
            return members;
        }
        
        public void addMember(UUID uuid) {
            members.add(uuid);
        }
        
        public void removeMember(UUID uuid) {
            if (!uuid.equals(owner)) {
                members.remove(uuid);
            }
        }
        
        public Location getTeamHome() {
            return teamHome;
        }
        
        public void setTeamHome(Location location) {
            this.teamHome = location;
        }
    }
}
