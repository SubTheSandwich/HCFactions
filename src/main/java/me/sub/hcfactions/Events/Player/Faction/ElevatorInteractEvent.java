package me.sub.hcfactions.Events.Player.Faction;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Sign.CustomSign;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class ElevatorInteractEvent implements Listener {

    @EventHandler
    public void onTouch(PlayerInteractEvent e) {
        if (!e.isCancelled()) {
            Player p = e.getPlayer();
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getState() instanceof Sign) {
                CustomSign sign = new CustomSign((Sign) e.getClickedBlock().getState());
                if (sign.getLine(0).equals(C.chat("&9[Elevator]"))) {
                    if (sign.getLine(1).equalsIgnoreCase("up")) {
                        boolean tped = false;
                        for (int i = 0; i < 255; i++) {
                            if (e.getClickedBlock().getY() < i && isSafeBlock(new Location(e.getClickedBlock().getWorld(), e.getClickedBlock().getLocation().getBlockX(), i, e.getClickedBlock().getLocation().getBlockZ())) && isSafeBlock(new Location(e.getClickedBlock().getWorld(), e.getClickedBlock().getLocation().getBlockX(), (i + 1), e.getClickedBlock().getLocation().getBlockZ()))) {
                                p.teleport(new Location(e.getPlayer().getWorld(), e.getClickedBlock().getLocation().getBlockX() + 0.5, i, e.getClickedBlock().getLocation().getBlockZ() + 0.5, p.getLocation().getYaw(), p.getLocation().getPitch()));
                                tped = true;
                                break;
                            }
                        }
                        if (!tped) {
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.elevator.no-valid-location")));
                        }
                    } else if (sign.getLine(1).equalsIgnoreCase("down")) {
                        boolean tped = false;
                        for (int i = 256; i >= 0; i--) {
                            if (i < e.getClickedBlock().getY() && isSafeBlock(new Location(e.getClickedBlock().getWorld(), e.getClickedBlock().getLocation().getBlockX(), i, e.getClickedBlock().getLocation().getBlockZ())) && isSafeBlock(new Location(e.getClickedBlock().getWorld(), e.getClickedBlock().getLocation().getBlockX(), (i - 1), e.getClickedBlock().getLocation().getBlockZ()))) {
                                p.teleport(new Location(e.getPlayer().getWorld(), e.getClickedBlock().getLocation().getBlockX() + 0.5, (i - 1), e.getClickedBlock().getLocation().getBlockZ() + 0.5, p.getLocation().getYaw(), p.getLocation().getPitch()));
                                tped = true;
                                break;
                            }
                        }
                        if (!tped) {
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.elevator.no-valid-location")));
                        }
                    }
                }
            }
        }
    }

    private boolean isSafeBlock(Location location) {
        ArrayList<Material> validMaterials = new ArrayList<>();
        validMaterials.add(Material.SIGN);
        validMaterials.add(Material.AIR);
        validMaterials.add(Material.SIGN_POST);
        validMaterials.add(Material.WALL_SIGN);
        for (Material m : validMaterials) {
            if (location.getWorld().getBlockAt(location).getType().equals(m)) {
                return true;
            }
         }
        return false;
    }
}
