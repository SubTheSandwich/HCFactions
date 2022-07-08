package me.sub.hcfactions.Events.Brew;

import me.sub.hcfactions.Main.Main;
import org.bukkit.block.BrewingStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BrewSpeedEvent implements Listener {

    // Check if time is actually faster

    @EventHandler
    public void onBrew(BrewEvent e) {
        startUpdate((BrewingStand) e.getBlock().getState(), Main.getInstance().getConfig().getInt("listeners.brewing-speed-multiplier"));
    }

    private void startUpdate(final BrewingStand tile, final double increase) {
        (new BukkitRunnable() {
            public void run() {
                if (tile.getBrewingTime() > 0) {
                    tile.setBrewingTime((short)(tile.getBrewingTime() - increase));
                    tile.update();
                } else {
                    cancel();
                }
            }
        }).runTaskTimer(Main.getInstance(), 1L, 1L);
    }

}
