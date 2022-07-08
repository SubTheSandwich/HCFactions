package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Utils.Economy.Economy;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Economy economy = new Economy(p.getUniqueId().toString());
            if (args.length == 0) {
                p.sendMessage(C.chat(Locale.get().getString("command.pay.usage")));
            } else if (args.length == 1) {
                p.sendMessage(C.chat(Locale.get().getString("command.pay.usage")));
            } else if (args.length == 2) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                if (player != null) {
                    Economy other = new Economy(player.getUniqueId().toString());
                    try {
                        double amount = Double.parseDouble(args[1]);
                        if (amount <= 0) {
                            p.sendMessage(C.chat(Locale.get().getString("command.pay.invalid-amount")));
                            return true;
                        }
                        if (economy.isValidNumber(amount)) {
                            if (economy.has(amount)) {
                                p.sendMessage(C.chat(Locale.get().getString("command.pay.sent").replace("%amount%", String.valueOf(amount)).replace("%recipient%", player.getName())));
                                if (player.isOnline()) {
                                    Player play = Bukkit.getPlayer(player.getUniqueId());
                                    play.sendMessage(C.chat(Locale.get().getString("command.pay.received").replace("%amount%", String.valueOf(amount)).replace("%sender%", p.getName())));
                                }
                                // doesn't work?? idk
                                economy.withdrawBalance(amount);
                                other.depositBalance(amount);
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.pay.not-enough")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.pay.invalid-amount")));
                        }
                    } catch (NumberFormatException nfe) {
                        if (args[1].equalsIgnoreCase("all")) {
                            double amount = Double.parseDouble("1.0");
                            if (amount <= 0) {
                                p.sendMessage(C.chat(Locale.get().getString("command.pay.invalid-amount")));
                                return true;
                            }
                            if (economy.isValidNumber(amount)) {
                                p.sendMessage(C.chat(Locale.get().getString("command.pay.sent").replace("%amount%", String.valueOf(amount)).replace("%recipient%", player.getName())));
                                if (player.isOnline()) {
                                    Player play = Bukkit.getPlayer(player.getUniqueId());
                                    play.sendMessage(C.chat(Locale.get().getString("command.pay.received").replace("%amount%", String.valueOf(amount)).replace("%sender%", p.getName())));
                                }
                                economy.setBalance(0.0);
                                other.depositBalance(amount);
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.pay.invalid-amount")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.pay.invalid-amount")));
                        }
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.pay.invalid-player")));
                }
            } else {
                p.sendMessage(C.chat(Locale.get().getString("command.pay.usage")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
