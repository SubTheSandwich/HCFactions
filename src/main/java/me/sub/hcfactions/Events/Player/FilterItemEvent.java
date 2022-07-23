package me.sub.hcfactions.Events.Player;

import me.sub.hcfactions.Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class FilterItemEvent implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().blockedItems.get(p.getUniqueId()).contains(e.getItem().getItemStack().getType())) {
            e.setCancelled(true);
        }
    }
}
