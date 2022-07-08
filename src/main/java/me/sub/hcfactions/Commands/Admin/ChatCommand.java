package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("hcfactions.command.chat") || sender.hasPermission("hcfactions.admin")) {
            if (args.length == 0) {
                sender.sendMessage(C.chat(Locale.get().getString("command.chat.usage")));
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("slow")) {
                    sender.sendMessage(C.chat(Locale.get().getString("command.chat.unslowed")));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(C.chat(Locale.get().getString("events.slow-mode-off").replace("%player%", sender.getName())));
                    }
                    Main.getInstance().chatSlow = 0;
                } else if (args[0].equalsIgnoreCase("mute")) {
                    if (Main.getInstance().disabledChat) {
                        sender.sendMessage(C.chat(Locale.get().getString("command.chat.unmuted")));
                        Main.getInstance().disabledChat = false;
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(C.chat(Locale.get().getString("command.chat.unmuted-all").replace("%player%", sender.getName())));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.chat.muted")));
                        Main.getInstance().disabledChat = true;
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(C.chat(Locale.get().getString("command.chat.muted-all").replace("%player%", sender.getName())));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.hasPermission("hcfactions.chat.bypass") || p.hasPermission("hcfactions.staff")) {
                            p.sendMessage(C.chat(Locale.get().getString("command.chat.clear-bypass")));
                        } else {
                            for (int i = 0; i < 500; i++) {
                                p.sendMessage(" ");
                            }
                            p.sendMessage(C.chat(Locale.get().getString("command.chat.cleared").replace("%player%", sender.getName())));
                        }
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("command.chat.usage")));
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("slow")) {
                    try {
                        int number = Integer.parseInt(args[1]);
                        if (number > -1) {
                            if (number == 0) {
                                sender.sendMessage(C.chat(Locale.get().getString("command.chat.unslowed")));
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage(C.chat(Locale.get().getString("events.slow-mode-off").replace("%player%", sender.getName())));
                                }
                                Main.getInstance().chatSlow = 0;
                            } else {
                                sender.sendMessage(C.chat(Locale.get().getString("command.chat.slowed").replace("%time%", String.valueOf(number))));
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage(C.chat(Locale.get().getString("events.slow-mode").replace("%player%", sender.getName()).replace("%time%", String.valueOf(number))));
                                }
                                Main.getInstance().chatSlow = number;
                            }
                        } else {
                            sender.sendMessage(C.chat(Locale.get().getString("command.chat.invalid-number")));
                        }
                    } catch (NumberFormatException nfe) {
                        sender.sendMessage(C.chat(Locale.get().getString("command.chat.invalid-number")));
                    }
                } else if (args[0].equalsIgnoreCase("mute")) {
                    if (Main.getInstance().disabledChat) {
                        sender.sendMessage(C.chat(Locale.get().getString("command.chat.unmuted")));
                        Main.getInstance().disabledChat = false;
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(C.chat(Locale.get().getString("command.chat.unmuted-all").replace("%player%", sender.getName())));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.chat.muted")));
                        Main.getInstance().disabledChat = true;
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(C.chat(Locale.get().getString("command.chat.muted-all").replace("%player%", sender.getName())));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.hasPermission("hcfactions.chat.bypass") || p.hasPermission("hcfactions.staff")) {
                            p.sendMessage(C.chat(Locale.get().getString("command.chat.clear-bypass")));
                        } else {
                            for (int i = 0; i < 50000; i++) {
                                p.sendMessage(" ");
                            }
                            p.sendMessage(C.chat(Locale.get().getString("command.chat.cleared").replace("%player%", sender.getName())));
                        }
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("command.chat.usage")));
                }
            } else {
                sender.sendMessage(C.chat(Locale.get().getString("command.chat.usage")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
        }
        return false;
    }
}
