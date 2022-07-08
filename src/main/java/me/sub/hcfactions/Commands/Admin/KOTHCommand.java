package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Koth.Koth;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class KOTHCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender p, Command cmd, String s, String[] args) {
        if (p.hasPermission("hcfactions.admin") || p.hasPermission("hcfactions.command.koth")) {
            if (args.length == 0) {
                p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
            } else if (args.length == 1) {
                p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("start")) {
                    if (existsKoth(args[0])) {
                        Koth koth = new Koth(args[0]);
                        if (koth.get().isConfigurationSection("position.sideOne") && koth.get().isConfigurationSection("position.sideTwo")) {
                            if (Main.getInstance().kothTimer.keySet().size() == 0) {
                                Bukkit.broadcastMessage(C.chat(Locale.get().getString("events.koth.started").replace("%name%", args[0])));
                                Main.getInstance().kothTimer.put(args[0], koth.get().getInt("time"));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                    }
                } else if (args[1].equalsIgnoreCase("stop")) {
                    if (existsKoth(args[0])) {
                        Koth koth = new Koth(args[0]);
                        if (koth.get().isConfigurationSection("position.sideOne") && koth.get().isConfigurationSection("position.sideTwo")) {
                            if (Main.getInstance().kothTimer.containsKey(args[0])) {
                                Bukkit.broadcastMessage(C.chat(Locale.get().getString("events.koth.stop.cancelled").replace("%name%", args[0])));
                                Main.getInstance().kothTimer.remove(args[0]);
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                    }
                } else if (args[1].equalsIgnoreCase("create")) {
                    if (!existsKoth(args[0])) {
                        Koth koth = new Koth(args[0]);
                        koth.setup();
                        koth.get().set("name", args[0]);
                        koth.get().set("time", 600);
                        koth.save();
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.create").replace("%name%", args[0])));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                    }
                } else if (args[1].equalsIgnoreCase("delete")) {
                    if (existsKoth(args[0])) {
                        Koth koth = new Koth(args[0]);
                        if (koth.getFile().delete()) {
                            p.sendMessage(C.chat(Locale.get().getString("command.koth.delete").replace("%koth%", args[0])));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                    }
                } else if (args[1].equalsIgnoreCase("setpos1")) {
                    if (p instanceof Player) {
                        Player player = (Player) p;
                        if (existsKoth(args[0])) {
                            Koth koth = new Koth(args[0]);
                            if (koth.get().getString("position.world") != null) {
                                if (koth.get().getString("position.world").equals(player.getWorld().getName())) {
                                    koth.get().set("position.sideOne.x", player.getLocation().getX());
                                    koth.get().set("position.sideOne.y", 0);
                                    koth.get().set("position.sideOne.z", player.getLocation().getZ());
                                    player.sendMessage(C.chat(Locale.get().getString("command.koth.setpos").replace("%position%", "1").replace("%koth%", koth.get().getString("name"))));
                                    koth.save();
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                                }
                            } else {
                                koth.get().set("position.world", player.getWorld().getName());
                                koth.get().set("position.sideOne.x", player.getLocation().getX());
                                koth.get().set("position.sideOne.y", 0);
                                koth.get().set("position.sideOne.z", player.getLocation().getZ());
                                player.sendMessage(C.chat(Locale.get().getString("command.koth.setpos").replace("%position%", "2").replace("%koth%", koth.get().getString("name"))));
                                koth.save();
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                    }
                } else if (args[1].equalsIgnoreCase("setpos2")) {
                    if (p instanceof Player) {
                        Player player = (Player) p;
                        if (existsKoth(args[0])) {
                            Koth koth = new Koth(args[0]);
                            if (koth.get().getString("position.world") != null) {
                                if (koth.get().getString("position.world").equals(player.getWorld().getName())) {
                                    koth.get().set("position.sideTwo.x", player.getLocation().getX());
                                    koth.get().set("position.sideTwo.y", 0);
                                    koth.get().set("position.sideTwo.z", player.getLocation().getZ());
                                    koth.save();
                                    player.sendMessage(C.chat(Locale.get().getString("command.koth.setpos").replace("%position%", "2").replace("%koth%", koth.get().getString("name"))));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                                }
                            } else {
                                koth.get().set("position.world", player.getWorld().getName());
                                koth.get().set("position.sideTwo.x", player.getLocation().getX());
                                koth.get().set("position.sideTwo.y", 0);
                                koth.get().set("position.sideTwo.z", player.getLocation().getZ());
                                player.sendMessage(C.chat(Locale.get().getString("command.koth.setpos").replace("%position%", "2").replace("%koth%", koth.get().getString("name"))));
                                koth.save();
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                }
            } else if (args.length == 3) {
                if (args[1].equalsIgnoreCase("settime")) {
                    if (existsKoth(args[0])) {
                        Koth koth = new Koth(args[0]);
                        try {
                            int time = Integer.parseInt(args[2]);
                            if (time > 0) {
                                koth.get().set("time", time);
                                koth.save();
                                p.sendMessage(C.chat(Locale.get().getString("command.koth.settime").replace("%time%", args[2]).replace("%koth%", args[0])));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                            }
                        } catch (NumberFormatException nfe) {
                            p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                }
            } else {
                if (args[1].equalsIgnoreCase("addreward")) {
                    if (existsKoth(args[0])) {
                        Koth koth = new Koth(args[0]);
                        ArrayList<String> rewards = new ArrayList<>(koth.get().getStringList("rewards"));
                        String[] split = Arrays.copyOfRange(args, 2, args.length);
                        String reason = String.join(" ", split);
                        rewards.add(reason);
                        koth.get().set("rewards", rewards);
                        koth.save();
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.addreward").replace("%koth%", args[0])));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                    }
                } else if (args[1].equalsIgnoreCase("delreward")) {
                    if (existsKoth(args[0])) {
                        Koth koth = new Koth(args[0]);
                        ArrayList<String> rewards = new ArrayList<>(koth.get().getStringList("rewards"));
                        String[] split = Arrays.copyOfRange(args, 2, args.length);
                        String reason = String.join(" ", split);
                        if (rewards.contains(reason)) {
                            rewards.remove(reason);
                            koth.get().set("rewards", rewards);
                            koth.save();
                            p.sendMessage(C.chat(Locale.get().getString("command.koth.delreward").replace("%koth%", args[0])));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.koth.usage")));
                }
            }
        } else {
            p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
        }
        return false;
    }

    public boolean existsKoth(String kothName) {
        File[] koths = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/koths").listFiles();
        if (koths != null) {
            for (File f : koths) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.getString("name").equals(kothName)) {
                    return true;
                }
            }
        }

        return false;
    }
}
