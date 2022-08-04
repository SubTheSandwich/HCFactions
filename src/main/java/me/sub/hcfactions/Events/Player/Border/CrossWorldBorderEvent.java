package me.sub.hcfactions.Events.Player.Border;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class CrossWorldBorderEvent implements Listener {

    @EventHandler
    public void onEnter(VehicleEnterEvent e) {
        if (e.getEntered() instanceof Player) {
            Player p = (Player) e.getEntered();
            if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                if (e.getVehicle().getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.border") || e.getVehicle().getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                } else if (e.getVehicle().getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.border") || e.getVehicle().getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                }
            } else if (p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                if (e.getVehicle().getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getVehicle().getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                } else if (e.getVehicle().getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getVehicle().getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.border.cross")));
                }
            }
        }
    }

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
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (e.getBlock().getX() > Main.getInstance().getConfig().getInt("worlds.default.border") || e.getBlock().getZ() >  Main.getInstance().getConfig().getInt("worlds.default.border")) {
                e.setCancelled(true);
            } else if (e.getBlock().getX() < -Main.getInstance().getConfig().getInt("worlds.default.border") || e.getBlock().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.border")) {
                e.setCancelled(true);
            }
        } else if (p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
            if (e.getBlock().getX() > Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getBlock().getZ() >  Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                e.setCancelled(true);
            } else if (e.getBlock().getX() < -Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getBlock().getZ() < -Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (e.getBlock().getX() > Main.getInstance().getConfig().getInt("worlds.default.border") || e.getBlock().getZ() >  Main.getInstance().getConfig().getInt("worlds.default.border")) {
                e.setCancelled(true);
            } else if (e.getBlock().getX() < -Main.getInstance().getConfig().getInt("worlds.default.border") || e.getBlock().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.border")) {
                e.setCancelled(true);
            }
        } else if (p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
            if (e.getBlock().getX() > Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getBlock().getZ() >  Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                e.setCancelled(true);
            } else if (e.getBlock().getX() < -Main.getInstance().getConfig().getInt("worlds.nether.border") || e.getBlock().getZ() < -Main.getInstance().getConfig().getInt("worlds.nether.border")) {
                e.setCancelled(true);
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
