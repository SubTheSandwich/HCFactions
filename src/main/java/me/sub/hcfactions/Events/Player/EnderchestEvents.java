package me.sub.hcfactions.Events.Player;

import me.sub.hcfactions.Main.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderchestEvents implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType().equals(Material.ENDER_CHEST)) {
            if (Main.getInstance().getConfig().getBoolean("settings.disable-enderchest")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(CraftItemEvent e) {
        if (e.getRecipe().getResult().getType().equals(Material.ENDER_CHEST)) {
            if (Main.getInstance().getConfig().getBoolean("settings.disable-enderchest")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
            if (Main.getInstance().getConfig().getBoolean("settings.disable-enderchest")) {
                e.setCancelled(true);
            }
        }
    }
}
