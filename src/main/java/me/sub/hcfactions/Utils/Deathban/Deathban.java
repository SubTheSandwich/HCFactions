package me.sub.hcfactions.Utils.Deathban;

import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Files.Reclaims.Reclaims;
import me.sub.hcfactions.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Deathban {

    String UUID;

    public Deathban(String uuid) {
        UUID = uuid;
    }

    public void createDeathban() {
        Players players = new Players(UUID);
        long bannedTill = System.currentTimeMillis() + (long) getDeathbanLength() * 1000;
        players.get().set("bannedTill", bannedTill);
        players.get().set("deathBanned", true);
        players.save();
    }

    public Boolean hasDeathban() {
        Players players = new Players(UUID);
        return players.get().getBoolean("deathBanned");
    }

    public Long getDeathban() {
        Players players = new Players(UUID);
        return players.get().getLong("bannedTill");
    }

    public void removeDeathban() {
        Players players = new Players(UUID);
        players.get().set("startDeathban", 0);
        players.get().set("bannedTill", 0);
        players.get().set("deathBanned", false);
        players.save();
    }

    public Integer getDeathbanLength() {
        ArrayList<String> ranks = new ArrayList<>();
        ArrayList<String> permissions = new ArrayList<>();
        for (String s : Reclaims.get().getConfigurationSection("reclaim").getKeys(false)) {
            ranks.add(s);
            permissions.add(Reclaims.get().getString("reclaim." + s + ".permission"));
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(java.util.UUID.fromString(UUID));
        if (offlinePlayer.isOnline()) {
            Player player = Bukkit.getPlayer(java.util.UUID.fromString(UUID));
            Collections.reverse(ranks);
            Collections.reverse(permissions);
            for (String s : permissions) {
                if (player.hasPermission(s)) {
                    return Reclaims.get().getInt("reclaim." + ranks.get(permissions.indexOf(s)) + ".deathban");
                }
            }
        }
        return Main.getInstance().getConfig().getInt("settings.default-deathban");
    }

    public String getRank() {
        ArrayList<String> ranks = new ArrayList<>();
        ArrayList<String> permissions = new ArrayList<>();
        for (String s : Reclaims.get().getConfigurationSection("reclaim").getKeys(false)) {
            ranks.add(s);
            permissions.add(Reclaims.get().getString("reclaim." + s + ".permission"));
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(java.util.UUID.fromString(UUID));
        if (offlinePlayer.isOnline()) {
            Player player = Bukkit.getPlayer(java.util.UUID.fromString(UUID));
            Collections.reverse(ranks);
            Collections.reverse(permissions);
            for (String s : permissions) {
                if (player.hasPermission(s)) {
                    return ranks.get(permissions.indexOf(s));
                }
            }
        }
        return "DEFAULT";
    }
}