package me.sub.hcfactions.Events.Player;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;

public class CombatMoveEvent implements Listener {

    @EventHandler
    public void onPvPTimer(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
            if (Main.getInstance().pvpTimer.containsKey(p.getUniqueId())) {
                if (getFactionInClaim(e.getTo()) != null && new Faction(getFactionInClaim(e.getTo())).get().getString("type").equals("PLAYER")) {
                    if (Bukkit.getWorld(new Faction(getFactionInClaim(e.getTo())).get().getString("claims.0.world")).getEnvironment().equals(p.getWorld().getEnvironment())) {
                        p.sendMessage(C.chat(Locale.get().getString("events.pvptimer-claim")));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
            if (Main.getInstance().combatTimer.containsKey(p.getUniqueId())) {
                if (getFactionInClaim(e.getTo()) != null && new Faction(getFactionInClaim(e.getTo())).get().getString("type").equals("SAFEZONE")) {
                    if (Bukkit.getWorld(new Faction(getFactionInClaim(e.getTo())).get().getString("claims.0.world")).getEnvironment().equals(p.getWorld().getEnvironment())) {
                        p.sendMessage(C.chat(Locale.get().getString("events.combat-safezone")));
                        e.setCancelled(true);
                        p.teleport(e.getFrom());
                    }
                }
            }
        }
    }


    private static String getFactionInClaim(Location loc) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0") && file.isConfigurationSection("claims.1")) {

                } else if (file.isConfigurationSection("claims.0")) {
                    Location locationOne = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideOne.x"), file.getDouble("claims.0.sideOne.y"), file.getDouble("claims.0.sideOne.z"));
                    Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideTwo.x"), file.getDouble("claims.0.sideTwo.y"), file.getDouble("claims.0.sideTwo.z"));
                    Block block = loc.getBlock().getRelative(BlockFace.DOWN);
                    Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                    for (Block b : cuboid.getBlocks()) {
                        if (block.getX() == b.getX() && block.getZ() == b.getZ()) {
                            return file.getString("uuid");
                        }
                    }
                }
            }
        }

        return null;
    }
}
