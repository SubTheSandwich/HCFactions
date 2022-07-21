package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Mountain.Mountain;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
                    } else if (args[0].equalsIgnoreCase("remove")) {
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
                            Mountain mountain = new Mountain(id);
                            p.sendMessage(C.chat(Locale.get().getString("command.mountain.removed").replace("%name%", mountain.get().getString("name"))));
                            mountain.delete();
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                        }
                    } else if (args[0].equalsIgnoreCase("select")) {
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
                            if (!Main.getInstance().selectingMountain.containsKey(p.getUniqueId())) {
                                Main.getInstance().selectingMountain.put(p.getUniqueId(), id);
                                p.sendMessage(C.chat(Locale.get().getString("command.mountain.select.started")));
                                ItemStack wand = new ItemStack(Material.STICK);
                                ItemMeta wandMeta = wand.getItemMeta();
                                wandMeta.setDisplayName(C.chat("&eMountain Selector Wand"));
                                wand.setItemMeta(wandMeta);
                                p.setItemInHand(wand);
                            } else {
                                if (Main.getInstance().mountainPositionOne.containsKey(p.getUniqueId()) && Main.getInstance().mountainPositionTwo.containsKey(p.getUniqueId())) {
                                    Mountain mountain = new Mountain(id);
                                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.select.set").replace("%name%", mountain.get().getString("name"))));
                                    mountain.get().set("world", Main.getInstance().mountainPositionOne.get(p.getUniqueId()).getWorld().getName());
                                    mountain.get().set("1.x", Main.getInstance().mountainPositionOne.get(p.getUniqueId()).getBlockX());
                                    mountain.get().set("1.y", Main.getInstance().mountainPositionOne.get(p.getUniqueId()).getBlockY());
                                    mountain.get().set("1.z", Main.getInstance().mountainPositionOne.get(p.getUniqueId()).getBlockZ());
                                    mountain.get().set("2.x", Main.getInstance().mountainPositionTwo.get(p.getUniqueId()).getBlockX());
                                    mountain.get().set("2.y", Main.getInstance().mountainPositionTwo.get(p.getUniqueId()).getBlockY());
                                    mountain.get().set("2.z", Main.getInstance().mountainPositionTwo.get(p.getUniqueId()).getBlockZ());
                                    mountain.save();
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.select.cancelled")));
                                }
                                Main.getInstance().selectingMountain.remove(p.getUniqueId());
                                Main.getInstance().mountainPositionOne.remove(p.getUniqueId());
                                Main.getInstance().mountainPositionTwo.remove(p.getUniqueId());
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                        }
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
                    } else if (args[0].equalsIgnoreCase("setresetdelay")) {
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
                            Mountain mountain = new Mountain(id);
                            try {
                                int delay = Integer.parseInt(args[2]);
                                if (delay > 0) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.setresetdelay").replace("%name%", mountain.get().getString("name")).replace("%delay%", String.valueOf(delay))));
                                    mountain.get().set("reset-delay", delay);
                                    mountain.save();
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                                }
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                        }
                    } else if (args[0].equalsIgnoreCase("setfaction")) {
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
                            Mountain mountain = new Mountain(id);
                            boolean validFaction = false;
                            String factionID = "";
                            File[] faction = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                            if (faction != null) {
                                for (File f : faction) {
                                    YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                    if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(args[1].toLowerCase(java.util.Locale.ROOT))) {
                                        validFaction = true;
                                        factionID = file.getString("uuid");
                                    }
                                }
                            }

                            if (validFaction) {
                                Faction faction1 = new Faction(factionID);
                                faction1.get().set("mountain", mountain.get().getString("uuid"));
                                faction1.save();
                                mountain.get().set("faction", factionID);
                                mountain.save();
                                p.sendMessage(C.chat(Locale.get().getString("command.mountain.setfaction").replace("%mountain%", mountain.get().getString("name")).replace("%faction%", faction1.get().getString("name")).replace("%uuid%", factionID)));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
                        }
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.usage")));
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
