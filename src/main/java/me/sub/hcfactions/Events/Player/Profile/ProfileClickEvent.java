package me.sub.hcfactions.Events.Player.Profile;

import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ProfileClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(C.chat("&eProfile Viewer"))) {
            e.setCancelled(true);
        }
    }
}
