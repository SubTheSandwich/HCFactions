package me.sub.hcfactions.Commands.Staff;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Staff.Staff;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.vanish") || p.hasPermission("hcfactions.staff")) {
                if (Staff.get().getBoolean("staff.enabled")) {
                    if (args.length == 0) {
                        if (Main.getInstance().vanished.contains(p)) {
                            p.sendMessage(C.chat(Locale.get().getString("command.vanish.disabled")));
                            Main.getInstance().vanished.remove(p);
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.showPlayer(p);
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.vanish.enabled")));
                            Main.getInstance().vanished.add(p);
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (!player.hasPermission("hcfactions.staff")) {
                                    player.hidePlayer(p);
                                }
                            }
                        }
                    } else if (args.length == 1) {
                        if (p.hasPermission("hcfactions.command.vanish.others")) {
                            Player spec = Bukkit.getPlayer(args[0]);
                            if (spec != null) {
                                if (Main.getInstance().vanished.contains(spec)) {
                                    spec.sendMessage(C.chat(Locale.get().getString("command.vanish.disabled")));
                                    p.sendMessage(C.chat(Locale.get().getString("command.vanish.vanish-other-disabled").replace("%player%", spec.getName())));
                                    Main.getInstance().vanished.remove(spec);
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        player.showPlayer(spec);
                                    }
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.vanish.vanish-other-enabled").replace("%player%", spec.getName())));
                                    spec.sendMessage(C.chat(Locale.get().getString("command.vanish.enabled")));
                                    Main.getInstance().vanished.add(spec);
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        if (!player.hasPermission("hcfactions.staff")) {
                                            player.hidePlayer(spec);
                                        }
                                    }
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.vanish.invalid-player")));
                            }
                        } else {
                            sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                        }
                    } else {
                        if (Main.getInstance().vanished.contains(p)) {
                            p.sendMessage(C.chat(Locale.get().getString("command.vanish.disabled")));
                            Main.getInstance().vanished.remove(p);
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.showPlayer(p);
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.vanish.enabled")));
                            Main.getInstance().vanished.add(p);
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (!player.hasPermission("hcfactions.staff")) {
                                    player.hidePlayer(p);
                                }
                            }
                        }
                    }
                }
            } else {
                sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
