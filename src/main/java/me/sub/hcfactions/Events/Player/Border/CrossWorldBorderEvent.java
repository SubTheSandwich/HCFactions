package me.sub.hcfactions.Events.Player.Border;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CrossWorldBorderEvent implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (e.getTo().getX() != e.getFrom().getX() || e.getTo().getZ() != e.getFrom().getZ() || e.getTo().getY() != e.getFrom().getY()) {
            if (e.getTo().getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                if (e.getTo().getX() > Main.getInstance().getConfig().getInt("worlds.default.border") || e.getTo().getZ() > Main.getInstance().getConfig().getInt("worlds.default.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                } else if (e.getTo().getX() < -Main.getInstance().getConfig().getInt("worlds.default.border") || e.getTo().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                }
            } else if (e.getTo().getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                if (e.getTo().getX() > Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getTo().getZ() > Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                } else if (e.getTo().getX() < -Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getTo().getZ() < -Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getTo().getX() != e.getFrom().getX() || e.getTo().getZ() != e.getFrom().getZ() || e.getTo().getY() != e.getFrom().getY()) {
            if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                if (e.getTo().getX() > Main.getInstance().getConfig().getInt("worlds.default.border") || e.getTo().getZ() > Main.getInstance().getConfig().getInt("worlds.default.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                } else if (e.getTo().getX() < -Main.getInstance().getConfig().getInt("worlds.default.border") || e.getTo().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                }
            } else if (p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                if (e.getTo().getX() > Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getTo().getZ() > Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                } else if (e.getTo().getX() < -Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getTo().getZ() < -Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                }
            }
        }
    }
}
