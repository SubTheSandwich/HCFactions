package me.sub.hcfactions.Events.Furnace;

import me.sub.hcfactions.Main.Main;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class FurnaceSmelt implements Listener {

    @EventHandler
    public void onSmelt(FurnaceBurnEvent e) {
        startUpdate((Furnace) e.getBlock().getState(), Main.getInstance().getConfig().getDouble("listeners.furnace-speed-multiplier"));
    }

    private void startUpdate(final Furnace tile, final double increase) {
        (new BukkitRunnable() {
            public void run() {
                if (tile.getCookTime() > 0 || tile.getBurnTime() > 0) {
                    tile.setCookTime((short)(tile.getCookTime() + increase));
                    tile.update();
                } else {
                    cancel();
                }
            }
        }).runTaskTimer(Main.getInstance(), 1L, 1L);
    }
}
