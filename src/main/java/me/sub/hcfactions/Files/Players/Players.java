package me.sub.hcfactions.Files.Players;

import me.sub.hcfactions.Files.Faction.Faction;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Players {
    File file;
    FileConfiguration customFile;
    String id;

    public Players(String uuid) {
        id = uuid;
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/profiles/", id + ".yml");
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public Boolean exists() {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean hasFaction() {
        return !customFile.getString("faction").equals("");
    }

    public Faction getFaction() {
        return new Faction(customFile.getString("faction"));
    }


    public void setup() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

            }
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

    public void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
