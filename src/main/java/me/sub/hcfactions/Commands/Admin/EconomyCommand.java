package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.economy") || p.hasPermission("hcfactions.admin")) {
                if (args.length == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.economy.usage").replace("%alias%", label)));
                } else if (args.length == 1) {
                    p.sendMessage(C.chat(Locale.get().getString("command.economy.usage").replace("%alias%", label)));
                } else if (args.length == 2) {
                    p.sendMessage(C.chat(Locale.get().getString("command.economy.usage").replace("%alias%", label)));
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("add")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (offlinePlayer != null) {
                            try {
                                Economy economy = new Economy(offlinePlayer.getUniqueId().toString());
                                double amount = Double.parseDouble(args[2]);
                                if (amount <= 0) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-amount")));
                                    return true;
                                }
                                if (economy.isValidNumber(amount)) {
                                    economy.depositBalance(amount);
                                    p.sendMessage(C.chat(Locale.get().getString("command.economy.add-balance").replace("%player%", offlinePlayer.getName()).replace("%amount%", String.valueOf(amount))));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-amount")));
                                }
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-amount")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-player")));
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (offlinePlayer != null) {
                            try {
                                Economy economy = new Economy(offlinePlayer.getUniqueId().toString());
                                double amount = Double.parseDouble(args[2]);
                                if (amount <= 0) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-amount")));
                                    return true;
                                }
                                if (economy.isValidNumber(amount)) {
                                    economy.withdrawBalance(amount);
                                    p.sendMessage(C.chat(Locale.get().getString("command.economy.remove-balance").replace("%player%", offlinePlayer.getName()).replace("%amount%", String.valueOf(amount))));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-amount")));
                                }
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-amount")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-player")));
                        }
                    } else if (args[0].equalsIgnoreCase("set")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (offlinePlayer != null) {
                            try {
                                Economy economy = new Economy(offlinePlayer.getUniqueId().toString());
                                double amount = Double.parseDouble(args[2]);
                                if (amount <= 0) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-amount")));
                                    return true;
                                }
                                if (economy.isValidNumber(amount)) {
                                    economy.setBalance(amount);
                                    p.sendMessage(C.chat(Locale.get().getString("command.economy.set-balance").replace("%player%", offlinePlayer.getName()).replace("%amount%", String.valueOf(amount))));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-amount")));
                                }
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-amount")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.economy.invalid-player")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.economy.usage").replace("%alias%", label)));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.economy.usage").replace("%alias%", label)));
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
