package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Timer.Timer;
import me.sub.hcfactions.Utils.Timer.Timers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SOTWCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.sotw") || p.hasPermission("hcfactions.admin")) {
                if (args.length == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.sotw.usage")));
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("stop")) {
                        if (Main.getInstance().sotwStarted) {
                            Main.getInstance().sotwStarted = false;
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.sotw.stopped")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.sotw.not-running")));
                        }
                    } else if (args[0].equalsIgnoreCase("enable")) {
                        if (Main.getInstance().sotwStarted) {
                            if (!Main.getInstance().sotwEnabled.contains(p)) {
                                p.sendMessage(C.chat(Locale.get().getString("command.sotw.player-pvp-enabled")));
                                Main.getInstance().sotwEnabled.add(p);
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.sotw.player-pvp-already-enabled")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.sotw.not-running")));
                        }
                    } else if (args[0].equalsIgnoreCase("pause")) {
                        if (Main.getInstance().sotwStarted) {
                            if (!Main.getInstance().sotwPaused) {
                                Main.getInstance().sotwPaused = true;
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.sotw.paused")));
                                }
                            } else {
                                Main.getInstance().sotwPaused = false;
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.sotw.unpaused")));
                                }
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.sotw.not-running")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.sotw.usage")));
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("start")) {
                        try {
                            Integer time = Integer.parseInt(args[1]);
                            if (!Main.getInstance().sotwStarted) {
                                Main.getInstance().sotwStarted = true;
                                Timer.setTimer(Timers.SOTW, time);
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.sotw.started")));
                                }
                             } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.sotw.running")));
                            }
                        } catch (NumberFormatException e) {
                            p.sendMessage(C.chat(Locale.get().getString("command.sotw.invalid-time")));
                        }
                    } else if (args[0].equalsIgnoreCase("extend")) {
                        Integer time = Integer.parseInt(args[1]);
                        if (Main.getInstance().sotwStarted) {
                            Timer.setTimer(Timers.SOTW, Timer.getTimer(Timers.SOTW) + time);
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.sotw.extended")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.sotw.not-running")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.sotw.usage")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.sotw.usage")));
                }
            } else {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("enable")) {
                        if (Main.getInstance().sotwStarted) {
                            if (!Main.getInstance().sotwEnabled.contains(p)) {
                                p.sendMessage(C.chat(Locale.get().getString("command.sotw.player-pvp-enabled")));
                                Main.getInstance().sotwEnabled.add(p);
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.sotw.player-pvp-already-enabled")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.sotw.not-running")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                }
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
