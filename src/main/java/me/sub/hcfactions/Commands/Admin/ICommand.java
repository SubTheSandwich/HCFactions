package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ICommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.give") || p.hasPermission("hcfactions.admin")) {
                if (args.length == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.i.usage")));
                } else if (args.length == 1) {
                    Material material = Material.matchMaterial(args[0]);
                    if (material != null) {
                        p.sendMessage(C.chat(Locale.get().getString("command.i.success").replace("%amount%", "64").replace("%material%", material.name())));
                        ItemStack item = new ItemStack(material, 64);
                        p.getInventory().addItem(item);
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.i.invalid-item")));
                    }
                } else if (args.length == 2) {
                    Material material = Material.matchMaterial(args[0]);
                    if (material != null) {
                        try {
                            Integer number = Integer.parseInt(args[1]);
                            if (number > 0) {
                                p.sendMessage(C.chat(Locale.get().getString("command.i.success").replace("%amount%", String.valueOf(number)).replace("%material%", material.name())));
                                ItemStack item = new ItemStack(material, number);
                                p.getInventory().addItem(item);
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.i.invalid-item")));
                            }
                        } catch (NumberFormatException nfe) {
                            p.sendMessage(C.chat(Locale.get().getString("command.i.invalid-item")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.i.invalid-item")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.i.usage")));
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
