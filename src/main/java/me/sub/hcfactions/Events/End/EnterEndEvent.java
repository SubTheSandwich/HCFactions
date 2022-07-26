package me.sub.hcfactions.Events.End;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Locations.Locations;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EnterEndEvent implements Listener {

    @EventHandler
    public void onEnter(PlayerPortalEvent e) {
        Player p = e.getPlayer();
        if (e.getCause().equals(PlayerPortalEvent.TeleportCause.END_PORTAL)) {
            Locations locations = new Locations();
            if (e.getFrom().getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                if (Main.getInstance().combatTimer.containsKey(p.getUniqueId())) {
                    e.setCancelled(true);
                    if (!Main.getInstance().messageSent.contains(p.getUniqueId())) {
                        Main.getInstance().messageSent.add(p.getUniqueId());
                        p.sendMessage(C.chat(Locale.get().getString("events.enter-end-combat")));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Main.getInstance().messageSent.remove(p.getUniqueId());
                            }
                        }.runTaskLater(Main.getInstance(), 100);
                    }
                    return;
                }
                if (locations.getStatic().isConfigurationSection("end.enter")) {
                    Location location = new Location(Bukkit.getWorld(locations.getStatic().getString("end.enter.world")), locations.getStatic().getDouble("end.enter.x"), locations.getStatic().getDouble("end.enter.y"), locations.getStatic().getDouble("end.enter.z"), (float) locations.getStatic().getDouble("end.enter.yaw"), (float) locations.getStatic().getDouble("end.enter.pitch"));
                    e.setTo(location);
                }
            }
        }
    }
}
