package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Timers.Timers;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Timer.Timer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

public class CustomTimerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("hcfactions.command.customtimer") || sender.hasPermission("hcfactions.admin")) {
            if (args.length == 0) {
                sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
            } else if (args.length == 1) {
                sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (!Timers.get().isConfigurationSection("timers." + args[1])) {
                        Timers.get().set("timers." + args[1] + ".name", args[1]);
                        Timers.get().set("timers." + args[1] + ".color", "BLUE");
                        Timers.save();
                        sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.create").replace("%name%", args[1])));
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (Timers.get().isConfigurationSection("timers." + args[1])) {
                        Timers.get().set("timers." + args[1], null);
                        Timers.save();
                        sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.remove").replace("%name%", args[1])));
                        Main.getInstance().customTimers.remove(args[1]);
                        Main.getInstance().customTimersPaused.remove(args[1]);
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                    }
                } else if (args[0].equalsIgnoreCase("pause")) {
                    if (Timers.get().isConfigurationSection("timers." + args[1])) {
                        if (Main.getInstance().customTimers.containsKey(args[1])) {
                            if (Main.getInstance().customTimersPaused.get(args[1])) {
                                Main.getInstance().customTimersPaused.put(args[1], false);
                                sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.pause").replace("%name%", args[1]).replace("%pause-type%", "unpaused")));
                            } else {
                                Main.getInstance().customTimersPaused.put(args[1], true);
                                sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.pause").replace("%name%", args[1]).replace("%pause-type%", "paused")));
                            }
                        } else {
                            sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                    }
                } else if (args[0].equalsIgnoreCase("stop")) {
                    if (Timers.get().isConfigurationSection("timers." + args[1])) {
                        if (Main.getInstance().customTimers.containsKey(args[1])) {
                            Main.getInstance().customTimersPaused.remove(args[1]);
                            Main.getInstance().customTimers.remove(args[1]);
                            sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.stopped").replace("%name%", args[1]).replace("%pause-type%", "unpaused")));
                        } else {
                            sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                    }
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("setcolor")) {
                    if (Timers.get().isConfigurationSection("timers." + args[1])) {
                        if (C.isValidColor(args[2])) {
                            Timers.get().set("timers." + args[1] + ".color", args[2].toUpperCase());
                            Timers.save();
                            sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.setcolor").replace("%name%", args[1]).replace("%color%", C.convertColorCode(args[2]))));
                        } else {
                            sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                    }
                } else if (args[0].equalsIgnoreCase("start")) {
                    if (Timers.get().isConfigurationSection("timers." + args[1])) {
                        if (!Main.getInstance().customTimers.containsKey(args[1])) {
                            try {
                                int time = Integer.parseInt(args[2]);
                                if (time > 0) {
                                    Main.getInstance().customTimers.put(args[1], time);
                                    Main.getInstance().customTimersPaused.put(args[1], false);
                                    Timer.tickCustomTimer(args[1]);
                                    sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.started").replace("%name%", args[1])));
                                } else {
                                    sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                                }
                            } catch (NumberFormatException nfe) {
                                sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                            }
                        } else {
                            sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
                    }
                }
            } else {
                sender.sendMessage(C.chat(Locale.get().getString("command.customtimer.usage").replace("%alias%", label)));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
        }
        return false;
    }
}
