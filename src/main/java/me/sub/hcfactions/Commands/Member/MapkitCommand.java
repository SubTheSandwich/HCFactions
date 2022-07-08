package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MapkitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Inventory inventory = Bukkit.createInventory(null, 54, C.chat(Main.getInstance().getConfig().getString("settings.mapkit.inventory-name")));
            for (int i = 0; i < 54; i++) {
                if (i % 2 == 0) {
                    if (i == 22) {
                        //chestplate
                        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                        ItemMeta meta = item.getItemMeta();
                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL"), true);
                        item.setItemMeta(meta);
                        inventory.setItem(i, item);
                    } else if (i == 40) {
                        //boots
                        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
                        ItemMeta meta = item.getItemMeta();
                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL"), true);
                        item.setItemMeta(meta);
                        inventory.setItem(i, item);
                    } else {
                        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(C.chat("&d"));
                        item.setItemMeta(meta);
                        inventory.setItem(i, item);
                    }
                } else {
                    if (i == 13) {
                        //helmet
                        ItemStack item = new ItemStack(Material.DIAMOND_HELMET, 1);
                        ItemMeta meta = item.getItemMeta();
                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL"), true);
                        item.setItemMeta(meta);
                        inventory.setItem(i, item);
                    } else if (i == 21) {
                        //sword
                        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
                        ItemMeta meta = item.getItemMeta();
                        meta.addEnchant(Enchantment.DAMAGE_ALL, Main.getInstance().getConfig().getInt("settings.limits.enchantments.DAMAGE_ALL") , true);

                        if (Main.getInstance().getConfig().getInt("settings.limits.enchantments.KNOCKBACK") != 0) {
                            meta.addEnchant(Enchantment.KNOCKBACK, Main.getInstance().getConfig().getInt("settings.limits.enchantments.KNOCKBACK"), true);
                        }

                        item.setItemMeta(meta);
                        inventory.setItem(i, item);
                    } else if (i == 23) {
                        //bow
                        ItemStack item = new ItemStack(Material.BOW, 1);
                        ItemMeta meta = item.getItemMeta();
                        meta.addEnchant(Enchantment.ARROW_DAMAGE, Main.getInstance().getConfig().getInt("settings.limits.enchantments.ARROW_DAMAGE"), true);
                        item.setItemMeta(meta);
                        inventory.setItem(i, item);
                    } else if (i == 31) {
                        //leggings
                        ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                        ItemMeta meta = item.getItemMeta();
                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL"), true);
                        item.setItemMeta(meta);
                        inventory.setItem(i, item);
                    } else {
                        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 11);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(C.chat("&d"));
                        item.setItemMeta(meta);
                        inventory.setItem(i, item);
                    }
                }
            }

            p.openInventory(inventory);
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
