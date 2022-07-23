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
            Locations locations = new Locations();
            if (locations.getStatic().isConfigurationSection("end.exit")) {
                Location location = new Location(Bukkit.getWorld(locations.getStatic().getString("end.exit.world")), locations.getStatic().getDouble("end.exit.x"), locations.getStatic().getDouble("end.exit.y"), locations.getStatic().getDouble("end.exit.z"), (float) locations.getStatic().getDouble("end.exit.yaw") , (float) locations.getStatic().getDouble("end.exit.pitch"));
                p.teleport(location);
            }
        }
    }
}
