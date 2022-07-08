package me.sub.hcfactions.Events.End;

import me.sub.hcfactions.Files.Locations.Locations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class LeaveEndEvent implements Listener {

    @EventHandler
    public void onLeave(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (e.getFrom().getEnvironment().equals(World.Environment.THE_END)) {
            if (Locations.get().isConfigurationSection("end.exit")) {
                Location location = new Location(Bukkit.getWorld(Locations.get().getString("end.exit.world")), Locations.get().getDouble("end.exit.x"), Locations.get().getDouble("end.exit.y"), Locations.get().getDouble("end.exit.z"), (float) Locations.get().getDouble("end.exit.yaw") , (float) Locations.get().getDouble("end.exit.pitch"));
                p.teleport(location);
            }
        }
    }
}
