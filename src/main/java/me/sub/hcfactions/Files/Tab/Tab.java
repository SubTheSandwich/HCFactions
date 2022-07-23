package me.sub.hcfactions.Files.Tab;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Tab {

    File file;
    FileConfiguration customFile;

    public Tab() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder(), "tab.yml");
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get() {
        return customFile;
    }

    public void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Failed to save tab.yml");
        }
    }
}
