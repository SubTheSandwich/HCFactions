package me.sub.hcfactions.Events.Player.Lunar;

import com.lunarclient.bukkitapi.object.LCWaypoint;
import me.sub.hcfactions.Files.Lunar.Lunar;
import me.sub.hcfactions.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SetupSpawnWaypoint implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (Main.getInstance().getConfig().getBoolean("settings.lunar-client.use-api")) {
            if (Lunar.get().getBoolean("lunar.waypoints.enabled")) {
                if (Lunar.get().getBoolean("lunar.waypoints.spawn.enabled")) {
                    int red = Lunar.get().getInt("lunar.waypoints.spawn.color.red");
                    int green = Lunar.get().getInt("lunar.waypoints.spawn.color.green");
                    int blue = Lunar.get().getInt("lunar.waypoints.spawn.color.blue");
                    int color = red + green + blue;
                    LCWaypoint spawn = new LCWaypoint(Lunar.get().getString("lunar.waypoints.spawn.name"), new Location(Bukkit.getWorlds().get(0), Main.getInstance().getConfig().getDouble("settings.spawn.x"), Main.getInstance().getConfig().getInt("settings.spawn.y"), Main.getInstance().getConfig().getDouble("settings.spawn.z")), color, Lunar.get().getBoolean("lunar.waypoints.spawn.forced"), Lunar.get().getBoolean("lunar.waypoints.spawn.visible"));
                    Main.getInstance().lunarClientAPI.sendWaypoint(e.getPlayer(), spawn);
                    if (e.getPlayer().hasPermission("hcfactions.staff")) {
                        Main.getInstance().lunarClientAPI.giveAllStaffModules(e.getPlayer());
                    }
                }
            }
        }
    }
}
