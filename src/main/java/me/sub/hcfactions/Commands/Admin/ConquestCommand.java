package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Conquest.Conquest;
import me.sub.hcfactions.Files.Faction.Faction;
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
import java.util.HashMap;
import java.util.UUID;

public class ConquestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.conquest") || p.hasPermission("hcfactions.admin")) {
                if (args.length == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.usage")));
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("setarea")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.usage")));
                    } else if (args[0].equalsIgnoreCase("setpoints")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setpoints.usage")));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.usage")));
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("create")) {
                        if (!exists(args[1])) {
                            String id = UUID.randomUUID().toString();
                            Conquest conquest = new Conquest(id);
                            conquest.setup();
                            conquest.get().set("uuid", id);
                            conquest.get().set("name", args[1]);
                            conquest.save();
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.created").replace("%conquest%", args[1])));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.already-exists").replace("%conquest%", args[1])));
                        }
                    } else if (args[0].equalsIgnoreCase("delete")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            conquest.delete();
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.deleted").replace("%conquest%", args[1])));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist").replace("%conquest%", args[1])));
                        }
                    } else if (args[0].equalsIgnoreCase("start")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            if (isSetup(conquest)) {
                                if (Main.getInstance().conquestTimer.keySet().size() == 0) {
                                    HashMap<String, Integer> colorIntegers = new HashMap<>();
                                    colorIntegers.put("BLUE", 30);
                                    colorIntegers.put("RED", 30);
                                    colorIntegers.put("GREEN", 30);
                                    colorIntegers.put("YELLOW", 30);
                                    Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), colorIntegers);
                                    HashMap<String, UUID> colorUUIDs = new HashMap<>();
                                    Main.getInstance().randomGeneratedUUIDConquest = UUID.randomUUID();
                                    colorUUIDs.put("RED", Main.getInstance().randomGeneratedUUIDConquest);
                                    colorUUIDs.put("BLUE", Main.getInstance().randomGeneratedUUIDConquest);
                                    colorUUIDs.put("GREEN", Main.getInstance().randomGeneratedUUIDConquest);
                                    colorUUIDs.put("YELLOW", Main.getInstance().randomGeneratedUUIDConquest);
                                    Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), colorUUIDs);
                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.started").replace("%conquest%", args[1])));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.ongoing").replace("%conquest%", args[1])));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.not-setup").replace("%conquest%", args[1])));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist").replace("%conquest%", args[1])));
                        }
                    } else if (args[0].equalsIgnoreCase("stop")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            if (isSetup(conquest)) {
                                if (Main.getInstance().conquestTimer.containsKey(conquest.get().getString("uuid"))) {
                                    Main.getInstance().conquestTimer.clear();
                                    Main.getInstance().conquestPoints.clear();
                                    Main.getInstance().capturingColorFaction.clear();
                                    Main.getInstance().randomGeneratedUUIDConquest = null;
                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.stopped").replace("%conquest%", args[1])));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.not-right").replace("%conquest%", args[1])));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.not-setup").replace("%conquest%", args[1])));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist").replace("%conquest%", args[1])));
                        }
                    } else if (args[0].equalsIgnoreCase("setpoints")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setpoints.usage")));
                    } else if (args[0].equalsIgnoreCase("setarea")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.usage")));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.usage")));
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("setpoints")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setpoints.usage")));
                    } else if (args[0].equalsIgnoreCase("setarea")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.usage")));
                    } else if (args[0].equalsIgnoreCase("addreward")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            ArrayList<String> rewards = new ArrayList<>(conquest.get().getStringList("rewards"));
                            String[] split = Arrays.copyOfRange(args, 2, args.length);
                            String reason = String.join(" ", split);
                            rewards.add(reason);
                            conquest.get().set("rewards", rewards);
                            conquest.save();
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.addreward").replace("%conquest%", getConquestByName(args[1]).get().getString("name")).replace("%reward%", reason)));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist")));
                        }
                    } else if (args[0].equalsIgnoreCase("delreward")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            ArrayList<String> rewards = new ArrayList<>(conquest.get().getStringList("rewards"));
                            String[] split = Arrays.copyOfRange(args, 2, args.length);
                            String reason = String.join(" ", split);
                            if (rewards.contains(reason)) {
                                rewards.remove(reason);
                                conquest.get().set("rewards", rewards);
                                conquest.save();
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.delreward").replace("%conquest%", getConquestByName(args[1]).get().getString("name")).replace("%reward%", reason)));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.usage")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.usage")));
                    }
                } else if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("setpoints")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            if (Main.getInstance().conquestTimer.containsKey(conquest.get().getString("uuid"))) {
                                try {
                                    int points = Integer.parseInt(args[3]);
                                    if (points > 0 && points < 300) {
                                        if (factionExists(args[2]) && getFactionByName(args[2]).get().getString("type").equals("PLAYER")) {
                                            Main.getInstance().conquestPoints.remove(getFactionByName(args[2]).get().getString("uuid"));
                                            Main.getInstance().conquestPoints.put(getFactionByName(args[2]).get().getString("uuid"), points);
                                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.setpoints.success").replace("%faction%", args[2]).replace("%points%", String.valueOf(points))));
                                        } else {
                                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.setpoints.invalid-faction")));
                                        }
                                    } else {
                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setpoints.invalid-points")));
                                    }
                                } catch (NumberFormatException nfe) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.setpoints.invalid-points")));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.not-right").replace("%conquest%", args[1])));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist").replace("%conquest%", args[1])));
                        }
                    } else if (args[0].equalsIgnoreCase("addreward")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            ArrayList<String> rewards = new ArrayList<>(conquest.get().getStringList("rewards"));
                            String[] split = Arrays.copyOfRange(args, 2, args.length);
                            String reason = String.join(" ", split);
                            rewards.add(reason);
                            conquest.get().set("rewards", rewards);
                            conquest.save();
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.addreward").replace("%conquest%", getConquestByName(args[1]).get().getString("name")).replace("%reward%", reason)));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist")));
                        }
                    } else if (args[0].equalsIgnoreCase("delreward")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            ArrayList<String> rewards = new ArrayList<>(conquest.get().getStringList("rewards"));
                            String[] split = Arrays.copyOfRange(args, 2, args.length);
                            String reason = String.join(" ", split);
                            if (rewards.contains(reason)) {
                                rewards.remove(reason);
                                conquest.get().set("rewards", rewards);
                                conquest.save();
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.delreward").replace("%conquest%", getConquestByName(args[1]).get().getString("name")).replace("%reward%", reason)));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.usage")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist")));
                        }
                    } else if (args[0].equalsIgnoreCase("setarea")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            try {
                                int position = Integer.parseInt(args[3]);
                                switch (position) {
                                    case 1:
                                        switch (args[2].toUpperCase()) {
                                            case "RED":
                                                if (conquest.get().getString("area.world") == null) {
                                                    conquest.get().set("area.world", p.getWorld().getName());
                                                    conquest.get().set("area.red.1.x", p.getLocation().getBlockX());
                                                    conquest.get().set("area.red.1.y", 0);
                                                    conquest.get().set("area.red.1.z", p.getLocation().getBlockZ());
                                                    conquest.save();
                                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.one").replace("%conquest%", args[1]).replace("%zonetype%", "Red")));
                                                } else {
                                                    if (conquest.get().getString("area.world").equals(p.getWorld().getName())) {
                                                        conquest.get().set("area.red.1.x", p.getLocation().getBlockX());
                                                        conquest.get().set("area.red.1.y", 0);
                                                        conquest.get().set("area.red.1.z", p.getLocation().getBlockZ());
                                                        conquest.save();
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.one").replace("%conquest%", args[1]).replace("%zonetype%", "Red")));
                                                    } else {
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.not-world")));
                                                    }
                                                }
                                                break;
                                            case "YELLOW":
                                                if (conquest.get().getString("area.world") == null) {
                                                    conquest.get().set("area.world", p.getWorld().getName());
                                                    conquest.get().set("area.yellow.1.x", p.getLocation().getBlockX());
                                                    conquest.get().set("area.yellow.1.y", 0);
                                                    conquest.get().set("area.yellow.1.z", p.getLocation().getBlockZ());
                                                    conquest.save();
                                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.one").replace("%conquest%", args[1]).replace("%zonetype%", "Yellow")));
                                                } else {
                                                    if (conquest.get().getString("area.world").equals(p.getWorld().getName())) {
                                                        conquest.get().set("area.yellow.1.x", p.getLocation().getBlockX());
                                                        conquest.get().set("area.yellow.1.y", 0);
                                                        conquest.get().set("area.yellow.1.z", p.getLocation().getBlockZ());
                                                        conquest.save();
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.one").replace("%conquest%", args[1]).replace("%zonetype%", "Yellow")));
                                                    } else {
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.not-world")));
                                                    }
                                                }
                                                break;
                                            case "GREEN":
                                                if (conquest.get().getString("area.world") == null) {
                                                    conquest.get().set("area.world", p.getWorld().getName());
                                                    conquest.get().set("area.green.1.x", p.getLocation().getBlockX());
                                                    conquest.get().set("area.green.1.y", 0);
                                                    conquest.get().set("area.green.1.z", p.getLocation().getBlockZ());
                                                    conquest.save();
                                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.one").replace("%conquest%", args[1]).replace("%zonetype%", "Green")));
                                                } else {
                                                    if (conquest.get().getString("area.world").equals(p.getWorld().getName())) {
                                                        conquest.get().set("area.green.1.x", p.getLocation().getBlockX());
                                                        conquest.get().set("area.green.1.y", 0);
                                                        conquest.get().set("area.green.1.z", p.getLocation().getBlockZ());
                                                        conquest.save();
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.one").replace("%conquest%", args[1]).replace("%zonetype%", "Green")));
                                                    } else {
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.not-world")));
                                                    }
                                                }
                                                break;
                                            case "BLUE":
                                                if (conquest.get().getString("area.world") == null) {
                                                    conquest.get().set("area.world", p.getWorld().getName());
                                                    conquest.get().set("area.blue.1.x", p.getLocation().getBlockX());
                                                    conquest.get().set("area.blue.1.y", 0);
                                                    conquest.get().set("area.blue.1.z", p.getLocation().getBlockZ());
                                                    conquest.save();
                                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.one").replace("%conquest%", args[1]).replace("%zonetype%", "Blue")));
                                                } else {
                                                    if (conquest.get().getString("area.world").equals(p.getWorld().getName())) {
                                                        conquest.get().set("area.blue.1.x", p.getLocation().getBlockX());
                                                        conquest.get().set("area.blue.1.y", 0);
                                                        conquest.get().set("area.blue.1.z", p.getLocation().getBlockZ());
                                                        conquest.save();
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.one").replace("%conquest%", args[1]).replace("%zonetype%", "Blue")));
                                                    } else {
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.not-world")));
                                                    }
                                                }
                                                break;

                                            default:
                                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.invalid-zone").replace("%zone%", args[2])));
                                                break;
                                        }
                                        break;

                                    case 2:
                                        switch (args[2].toUpperCase()) {
                                            case "RED":
                                                if (conquest.get().getString("area.world") == null) {
                                                    conquest.get().set("area.world", p.getWorld().getName());
                                                    conquest.get().set("area.red.2.x", p.getLocation().getBlockX());
                                                    conquest.get().set("area.red.2.y", 0);
                                                    conquest.get().set("area.red.2.z", p.getLocation().getBlockZ());
                                                    conquest.save();
                                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.two").replace("%conquest%", args[1]).replace("%zonetype%", "Red")));
                                                } else {
                                                    if (conquest.get().getString("area.world").equals(p.getWorld().getName())) {
                                                        conquest.get().set("area.red.2.x", p.getLocation().getBlockX());
                                                        conquest.get().set("area.red.2.y", 0);
                                                        conquest.get().set("area.red.2.z", p.getLocation().getBlockZ());
                                                        conquest.save();
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.two").replace("%conquest%", args[1]).replace("%zonetype%", "Red")));
                                                    } else {
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.not-world")));
                                                    }
                                                }
                                                break;
                                            case "YELLOW":
                                                if (conquest.get().getString("area.world") == null) {
                                                    conquest.get().set("area.world", p.getWorld().getName());
                                                    conquest.get().set("area.yellow.2.x", p.getLocation().getBlockX());
                                                    conquest.get().set("area.yellow.2.y", 0);
                                                    conquest.get().set("area.yellow.2.z", p.getLocation().getBlockZ());
                                                    conquest.save();
                                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.two").replace("%conquest%", args[1]).replace("%zonetype%", "Yellow")));
                                                } else {
                                                    if (conquest.get().getString("area.world").equals(p.getWorld().getName())) {
                                                        conquest.get().set("area.yellow.2.x", p.getLocation().getBlockX());
                                                        conquest.get().set("area.yellow.2.y", 0);
                                                        conquest.get().set("area.yellow.2.z", p.getLocation().getBlockZ());
                                                        conquest.save();
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.two").replace("%conquest%", args[1]).replace("%zonetype%", "Yellow")));
                                                    } else {
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.not-world")));
                                                    }
                                                }
                                                break;
                                            case "GREEN":
                                                if (conquest.get().getString("area.world") == null) {
                                                    conquest.get().set("area.world", p.getWorld().getName());
                                                    conquest.get().set("area.green.2.x", p.getLocation().getBlockX());
                                                    conquest.get().set("area.green.2.y", 0);
                                                    conquest.get().set("area.green.2.z", p.getLocation().getBlockZ());
                                                    conquest.save();
                                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.two").replace("%conquest%", args[1]).replace("%zonetype%", "Green")));
                                                } else {
                                                    if (conquest.get().getString("area.world").equals(p.getWorld().getName())) {
                                                        conquest.get().set("area.green.2.x", p.getLocation().getBlockX());
                                                        conquest.get().set("area.green.2.y", 0);
                                                        conquest.get().set("area.green.2.z", p.getLocation().getBlockZ());
                                                        conquest.save();
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.two").replace("%conquest%", args[1]).replace("%zonetype%", "Green")));
                                                    } else {
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.not-world")));
                                                    }
                                                }
                                                break;
                                            case "BLUE":
                                                if (conquest.get().getString("area.world") == null) {
                                                    conquest.get().set("area.world", p.getWorld().getName());
                                                    conquest.get().set("area.blue.2.x", p.getLocation().getBlockX());
                                                    conquest.get().set("area.blue.2.y", 0);
                                                    conquest.get().set("area.blue.2.z", p.getLocation().getBlockZ());
                                                    conquest.save();
                                                    p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.two").replace("%conquest%", args[1]).replace("%zonetype%", "Blue")));
                                                } else {
                                                    if (conquest.get().getString("area.world").equals(p.getWorld().getName())) {
                                                        conquest.get().set("area.blue.2.x", p.getLocation().getBlockX());
                                                        conquest.get().set("area.blue.2.y", 0);
                                                        conquest.get().set("area.blue.2.z", p.getLocation().getBlockZ());
                                                        conquest.save();
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.two").replace("%conquest%", args[1]).replace("%zonetype%", "Blue")));
                                                    } else {
                                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.not-world")));
                                                    }
                                                }
                                                break;

                                            default:
                                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.invalid-zone").replace("%zone%", args[2])));
                                                break;
                                        }
                                        break;

                                    default:
                                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.invalid-position").replace("%position%", args[3])));
                                        break;
                                }
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.setarea.invalid-position").replace("%position%", args[3])));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist").replace("%conquest%", args[1])));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.usage")));
                    }
                } else {
                    if (args[0].equalsIgnoreCase("addreward")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            ArrayList<String> rewards = new ArrayList<>(conquest.get().getStringList("rewards"));
                            String[] split = Arrays.copyOfRange(args, 2, args.length);
                            String reason = String.join(" ", split);
                            rewards.add(reason);
                            conquest.get().set("rewards", rewards);
                            conquest.save();
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.addreward").replace("%conquest%", getConquestByName(args[1]).get().getString("name")).replace("%reward%", reason)));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist").replace("%conquest%", args[1])));
                        }
                    } else if (args[0].equalsIgnoreCase("delreward")) {
                        if (exists(args[1])) {
                            Conquest conquest = getConquestByName(args[1]);
                            ArrayList<String> rewards = new ArrayList<>(conquest.get().getStringList("rewards"));
                            String[] split = Arrays.copyOfRange(args, 2, args.length);
                            String reason = String.join(" ", split);
                            if (rewards.contains(reason)) {
                                rewards.remove(reason);
                                conquest.get().set("rewards", rewards);
                                conquest.save();
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.delreward").replace("%conquest%", getConquestByName(args[1]).get().getString("name")).replace("%reward%", reason)));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.usage")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.doesnt-exist").replace("%conquest%", args[1])));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.conquest.usage")));
                    }
                }
            } else {
                p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }

    public Faction getFactionByName(String conquestName) {
        File[] koths = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (koths != null) {
            for (File f : koths) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.getString("name").equals(conquestName)) {
                    return new Faction(file.getString("uuid"));
                }
            }
        }

        return null;
    }

    public Conquest getConquestByName(String conquestName) {
        File[] koths = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/conquests").listFiles();
        if (koths != null) {
            for (File f : koths) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.getString("name").equals(conquestName)) {
                    return new Conquest(file.getString("uuid"));
                }
            }
        }

        return null;
    }

    public boolean exists(String conquestName) {
        File[] koths = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/conquests").listFiles();
        if (koths != null) {
            for (File f : koths) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.getString("name").equals(conquestName)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean factionExists(String factionName) {
        File[] koths = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (koths != null) {
            for (File f : koths) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.getString("name").equals(factionName)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isSetup(Conquest conquest) {
        return conquest.get().isConfigurationSection("area.red.1") && conquest.get().isConfigurationSection("area.red.2") && conquest.get().isConfigurationSection("area.green.1") && conquest.get().isConfigurationSection("area.green.2") && conquest.get().isConfigurationSection("area.blue.2") && conquest.get().isConfigurationSection("area.blue.1") && conquest.get().isConfigurationSection("area.yellow.1") && conquest.get().isConfigurationSection("area.yellow.2");
    }


}
