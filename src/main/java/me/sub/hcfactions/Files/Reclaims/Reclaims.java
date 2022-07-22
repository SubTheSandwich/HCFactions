package me.sub.hcfactions.Files.Reclaims;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Reclaims {

    File file;
    FileConfiguration customFile;

    public Reclaims() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder(), "reclaims.yml");
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getStatic() {
        return customFile;
    }

    public static FileConfiguration get() {
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder(), "reclaims.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public void saveStatic() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Unable to save file reclaims.yml");
        }
    }

    public static void save() {
        try {
            File file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder(), "reclaims.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.save(file);
        } catch (IOException e) {
            System.out.println("Unable to save file reclaims.yml");
        }
    }
}
