package me.sub.hcfactions.Files.Faction;

import me.sub.hcfactions.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Faction {
    File file;
    FileConfiguration customFile;
    String id;

    public Faction(String uuid) {
        id = uuid;
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions/", id + ".yml");
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public Boolean exists() {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }


    public void setup() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public void delete() {
        if (file.exists()) {
            file.delete();
        }
    }

    public FileConfiguration get() {
        return customFile;
    }

    public void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Couldn't save file");
        }
    }

    public ArrayList<Player> getAllOnlinePlayers() {
        ArrayList<Player> online = new ArrayList<>();
        String leader = customFile.getString("leader");
        if (Bukkit.getPlayer(UUID.fromString(leader)) != null) {
            online.add(Bukkit.getPlayer(UUID.fromString(customFile.getString("leader"))));
        }
        for (String s : customFile.getStringList("coleaders")) {
            if (Bukkit.getPlayer(UUID.fromString(s)) != null) {
                Player d = Bukkit.getPlayer(UUID.fromString(s));
                online.add(d);
            }
        }
        for (String s : customFile.getStringList("captains")) {
            if (Bukkit.getPlayer(UUID.fromString(s)) != null) {
                Player d = Bukkit.getPlayer(UUID.fromString(s));
                online.add(d);
            }
        }
        for (String s : customFile.getStringList("members")) {
            if (Bukkit.getPlayer(UUID.fromString(s)) != null) {
                Player d = Bukkit.getPlayer(UUID.fromString(s));
                online.add(d);
            }
        }
        return online;
    }

    public ArrayList<OfflinePlayer> getAllMembers() {
        ArrayList<OfflinePlayer> online = new ArrayList<>();
        String leader = customFile.getString("leader");
        online.add(Bukkit.getOfflinePlayer(UUID.fromString(customFile.getString("leader"))));

        for (String s : customFile.getStringList("coleaders")) {
            OfflinePlayer d = Bukkit.getOfflinePlayer(UUID.fromString(s));
        }

        for (String s : customFile.getStringList("captains")) {
            OfflinePlayer d = Bukkit.getOfflinePlayer(UUID.fromString(s));
        }

        for (String s : customFile.getStringList("members")) {
            OfflinePlayer d = Bukkit.getOfflinePlayer(UUID.fromString(s));
        }

        return online;
    }

    public Double getMaximumDTR() {
        if (getAllMembers().size() >= Main.getInstance().getConfig().getInt("faction.max-members")) {
            return Main.getInstance().getConfig().getDouble("dtr.max");
        } else {
            if (getAllMembers().size() != 1) {
                return Main.getInstance().getConfig().getDouble("dtr.multiple") * getAllMembers().size();
            } else {
                return 1.01;
            }
        }
    }

    public void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
