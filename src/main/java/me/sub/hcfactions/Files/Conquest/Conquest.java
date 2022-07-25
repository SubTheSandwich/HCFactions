package me.sub.hcfactions.Files.Conquest;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Conquest {
    File file;
    FileConfiguration customFile;
    String id;

    public File getFile() {
        return file;
    }

    public Conquest(String uuid) {
        id = uuid;
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/conquests/", id + ".yml");
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public Boolean exists() {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void delete() {
        if (file.exists()) {
            file.delete();
        }
    }

    public Cuboid getColorCuboid(String color) {
        if (file.exists()) {
            switch (color) {
                case "RED":
                case "BLUE":
                case "YELLOW":
                case "GREEN":
                    World world = Bukkit.getWorld(customFile.getString("area.world"));
                    Location positionOne = new Location(world, customFile.getInt("area." + color.toLowerCase() + ".1.x"), customFile.getInt("area." + color.toLowerCase() + ".1.y"), customFile.getInt("area." + color.toLowerCase() + ".1.z"));
                    Location positionTwo = new Location(world, customFile.getInt("area." + color.toLowerCase() + ".2.x"), customFile.getInt("area." + color.toLowerCase() + ".2.y"), customFile.getInt("area." + color.toLowerCase() + ".2.z"));
                    return new Cuboid(positionOne, positionTwo);
                default:
                    return null;
            }
        }
        return null;
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
