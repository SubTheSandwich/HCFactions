package me.sub.hcfactions.Events.Brew;

import me.sub.hcfactions.Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BrewItemEvent implements Listener {

    @EventHandler
    public void onBrew(BrewEvent e) {
        if (!e.isCancelled()) {
            ArrayList<String> blockedTypes = new ArrayList<>(Main.getInstance().getConfig().getStringList("potions.blocked-types"));
            ArrayList<String> restrictedTypes = new ArrayList<>(Main.getInstance().getConfig().getConfigurationSection("potions.restricted-types").getKeys(false));
            new BukkitRunnable() {
                @Override
                public void run() {
                    ItemStack[] contents = e.getContents().getContents();
                    for (ItemStack i : contents) {
                        if (isPotion(i)) {
                            Potion potion = Potion.fromItemStack(i);
                            if (blockedTypes.contains(potion.getType().toString())) {
                                e.getContents().clear();
                            } else if (restrictedTypes.contains(potion.getType().toString())) {
                                if (potion.getLevel() >= Main.getInstance().getConfig().getInt("potions.restricted-types." + potion.getType().toString() + ".max-level")) {
                                    e.getContents().clear();
                                }
                            }
                        }
                    }
                }
            }.runTaskLater(Main.getInstance(), 5);

        }
    }

    private boolean isPotion(ItemStack item) {
        try {
            Potion.fromItemStack(item);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
