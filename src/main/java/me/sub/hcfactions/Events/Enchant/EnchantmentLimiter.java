package me.sub.hcfactions.Events.Enchant;

import me.sub.hcfactions.Main.Main;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantmentLimiter implements Listener {

    @EventHandler
    public void onEnchant(EnchantItemEvent e) {
        for (String s : Main.getInstance().getConfig().getConfigurationSection("settings.limits.enchantments").getKeys(false)) {
            if (Enchantment.getByName(s) != null) {
                Enchantment enchantment = Enchantment.getByName(s);
                if (e.getEnchantsToAdd().containsKey(enchantment)) {
                    if (Main.getInstance().getConfig().getInt("settings.limits.enchantments." + s) != 0) {
                        e.getEnchantsToAdd().put(enchantment, Main.getInstance().getConfig().getInt("settings.limits.enchantments." + s));
                    } else {
                        e.getEnchantsToAdd().remove(enchantment);
                    }
                }
            }
        }
    }
}
