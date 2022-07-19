package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Mountain.Mountain;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class MountainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.admin") || p.hasPermission("hcfactions.command.mountain")) {
                if (args.length == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                } else if (args.length == 1) {
                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("create")) {
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/mountains").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(args[1].toLowerCase(java.util.Locale.ROOT))) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                                    return true;
                                }
                            }
                        }

                        String id = UUID.randomUUID().toString();
                        Mountain mountain = new Mountain(id);
                        mountain.setup();
                        mountain.get().set("uuid", id);
                        mountain.get().set("name", args[1]);
                        mountain.get().set("block", "GLOWSTONE");
                        mountain.get().set("reset-delay", 300);
                        mountain.get().set("faction", "");
                        mountain.save();
                        p.sendMessage(C.chat(Locale.get().getString("command.mountain.created").replace("%name%", args[1])));
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("setblock")) {
                        boolean valid = false;
                        String id = "";
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/mountains").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(args[1].toLowerCase(java.util.Locale.ROOT))) {
                                    valid = true;
                                    id = file.getString("uuid");
                                }
                            }
                        }

                        if (valid) {
                            Material item = Material.matchMaterial(args[2]);
                            if (item.isBlock()) {
                                p.sendMessage(C.chat(Locale.get().getString("command.mountain.setblock").replace("%name%", args[1]).replace("%block%", item.name())));
                                Mountain mountain = new Mountain(id);
                                mountain.get().set("block", item.name());
                                mountain.save();
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                        }
                    }
                } else {

                }
            } else {
                p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
