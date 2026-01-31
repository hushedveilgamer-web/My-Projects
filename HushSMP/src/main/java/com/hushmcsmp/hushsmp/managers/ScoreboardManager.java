package com.hushmcsmp.hushsmp.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import java.util.UUID;

public class ScoreboardManager {
    private Plugin plugin;
    private StatManager statManager;
    private EconomyManager economyManager;
    private ShardManager shardManager;
    private RTPManager rtpManager;
    private StoreManager storeManager;

    public ScoreboardManager(Plugin plugin, StatManager statManager, EconomyManager economyManager, ShardManager shardManager, RTPManager rtpManager, StoreManager storeManager) {
        this.plugin = plugin;
        this.statManager = statManager;
        this.economyManager = economyManager;
        this.shardManager = shardManager;
        this.rtpManager = rtpManager;
        this.storeManager = storeManager;
        startUpdater();
    }

    private void startUpdater() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                updateScoreboard(player);
                updatePlayerListHeaderFooter(player);
            }
        }, 20L, 20L);
    }

    public void updateScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("hushsmp", "dummy", "§bHushSMP.net");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        UUID uuid = player.getUniqueId();
        double money = economyManager.getMoney(uuid);
        long shards = shardManager.getShards(uuid);
        int kills = statManager.getKills(uuid);
        int deaths = statManager.getDeaths(uuid);
        long playtime = statManager.getPlaytime(uuid);

        int score = 7;

        Score s1 = obj.getScore("§aMoney: §7$" + formatMoney(money));
        s1.setScore(score--);
        Score s2 = obj.getScore("§5Shards: §7" + shards);
        s2.setScore(score--);
        Score s3 = obj.getScore("§cKills: §7" + kills);
        s3.setScore(score--);
        Score s4 = obj.getScore("§6Deaths: §7" + deaths);
        s4.setScore(score--);
        Score s5 = obj.getScore("§ePlaytime: §7" + statManager.formatPlaytime(playtime));
        s5.setScore(score--);
        long keyRemaining = storeManager.getKeyTimeRemaining(uuid);
        String keyStr = keyRemaining <= 0 ? "Ready" : storeManager.formatTime(keyRemaining);
        Score s6 = obj.getScore("§bKeyall: §7" + keyStr);
        s6.setScore(score--);
        Score s7 = obj.getScore("§9Region: §7" + getRegionForPlayer(player));
        s7.setScore(score--);

        player.setScoreboard(board);
    }

    private String getRegionForPlayer(Player player) {
        // For now, default to first region name and player's ping in ms
        String region = rtpManager.getRegions().keySet().stream().findFirst().orElse("NA-East");
        int ping = getPing(player);
        return region + " (" + ping + "ms)";
    }

    private int getPing(Player player) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object ping = handle.getClass().getField("ping").get(handle);
            return (int) ping;
        } catch (Exception e) {
            return 0;
        }
    }

    private String formatMoney(double money) {
        if (money >= 1000000) return String.format("%.1fm", money / 1000000.0);
        if (money >= 1000) return String.format("%.1fk", money / 1000.0);
        return String.format("%.0f", money);
    }

    private void updatePlayerListHeaderFooter(Player player) {
        try {
            player.setPlayerListHeader("§bWelcome to §fHushSMP.net");
            player.setPlayerListFooter("§7Use /store, /shop, /rtp, /duel, /ah");
        } catch (NoSuchMethodError ignored) {}
    }
}
