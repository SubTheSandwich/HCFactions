package me.sub.hcfactions.Files.Locale;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Locale {

    public static FileConfiguration get() {
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder(), "locale.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void save() {
        try {
            File file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder(), "locale.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.save(file);
        } catch (IOException e) {
            System.out.println("Unable to save file locale.yml");
        }
    }
}
