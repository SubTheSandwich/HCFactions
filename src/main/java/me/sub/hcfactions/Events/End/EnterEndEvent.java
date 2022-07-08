package me.sub.hcfactions.Events.End;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Locations.Locations;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EnterEndEvent implements Listener {

    @EventHandler
    public void onEnter(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
            if (Main.getInstance().combatTimer.containsKey(p.getUniqueId())) {
                e.setCancelled(true);
                p.sendMessage(C.chat(Locale.get().getString("events.enter-end-combat")));
                return;
            }
            if (Locations.get().isConfigurationSection("end.enter")) {
                Location location = new Location(Bukkit.getWorld(Locations.get().getString("end.enter.world")), Locations.get().getDouble("end.enter.x"), Locations.get().getDouble("end.enter.y"), Locations.get().getDouble("end.enter.z"), (float) Locations.get().getDouble("end.enter.yaw") , (float) Locations.get().getDouble("end.enter.pitch"));
                p.teleport(location);
            }
        }
    }
}
