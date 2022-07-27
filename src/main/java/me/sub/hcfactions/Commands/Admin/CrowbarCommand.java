package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CrowbarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("hcfactions.admin") || sender.hasPermission("hcfactions.command.crowbar")) {
            if (args.length != 2) {
                sender.sendMessage(C.chat(Locale.get().getString("command.crowbar.usage")));
            } else {
                Player player = Bukkit.getPlayer(args[0]);
                if (player != null) {
                    try {
                        int value = Integer.parseInt(args[1]);
                        if (value > 0) {
                            ItemStack crowbar = new ItemStack(Material.DIAMOND_HOE);
                            ItemMeta crowbarMeta = crowbar.getItemMeta();
                            crowbarMeta.setDisplayName(C.chat("&bCrowbar &7(Right Click)"));
                            ArrayList<String> crowbarLore = new ArrayList<>();
                            crowbarLore.add(C.chat("&eRight Click on a Monster Spawner to Collect It"));
                            crowbarLore.add(C.chat("&eUses: &r" + value));
                            crowbarMeta.setLore(crowbarLore);
                            crowbar.setItemMeta(crowbarMeta);
                            sender.sendMessage(C.chat(Locale.get().getString("command.crowbar.given").replace("%uses%", String.valueOf(value)).replace("%player%", player.getName())));
                            player.sendMessage(C.chat(Locale.get().getString("command.crowbar.received").replace("%uses%", String.valueOf(value)).replace("%sender%", sender.getName())));
                            if (player.getInventory().firstEmpty() != -1) {
                                player.getInventory().addItem(crowbar);
                            } else {
                                player.getWorld().dropItem(player.getLocation(), crowbar);
                            }
                        } else {
                            sender.sendMessage(C.chat(Locale.get().getString("command.crowbar.usage")));
                        }
                    } catch (NumberFormatException nfe) {
                        sender.sendMessage(C.chat(Locale.get().getString("command.crowbar.usage")));
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("command.crowbar.usage")));
                }
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
        }
        return false;
    }
}
