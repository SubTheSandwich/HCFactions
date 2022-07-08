package me.sub.hcfactions.Events.Mapkit;

import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MapkitClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(C.chat(Main.getInstance().getConfig().getString("settings.mapkit.inventory-name")))) {
            e.setCancelled(true);
        }
    }

}
