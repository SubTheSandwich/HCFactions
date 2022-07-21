package me.sub.hcfactions.Files.Mountain;

import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Mountain {
    File file;
    FileConfiguration customFile;
    String id;

    public Mountain(String uuid) {
        id = uuid;
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/mountains/", id + ".yml");
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public Boolean isSetup() {
        return customFile.getString("world") != null;
    }

    public Cuboid getCuboid() {
        if (isSetup()) {
            World world = Bukkit.getWorld(customFile.getString("world"));
            Location locationOne = new Location(world, customFile.getInt("1.x"), customFile.getInt("1.y"), customFile.getInt("1.z"));
            Location locationTwo = new Location(world, customFile.getInt("2.x"), customFile.getInt("2.y"), customFile.getInt("2.z"));
            return new Cuboid(locationOne, locationTwo);
        } else {
            return null;
        }
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

    public void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
