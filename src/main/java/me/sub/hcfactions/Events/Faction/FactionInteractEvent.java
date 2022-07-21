package me.sub.hcfactions.Events.Faction;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Mountain.Mountain;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

import java.io.File;

public class FactionInteractEvent implements Listener {

    private String getFactionInClaim(Player p, Location selectedBlock) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0") && file.isConfigurationSection("claims.1")) {
                    for (String fileNumber : file.getConfigurationSection("claims").getKeys(false)) {
                        if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NORMAL) && p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return file.getString("uuid");
                                }
                            }
                        } else if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NETHER) && p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return file.getString("uuid");
                                }
                            }
                        }
                    }
                } else if (file.isConfigurationSection("claims.0")) {
                    if (Bukkit.getWorld(file.getString("claims.0.world")).getEnvironment().equals(World.Environment.NORMAL) && p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                        Location locationOne = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideOne.x"), file.getDouble("claims.0.sideOne.y"), file.getDouble("claims.0.sideOne.z"));
                        Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideTwo.x"), file.getDouble("claims.0.sideTwo.y"), file.getDouble("claims.0.sideTwo.z"));
                        Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                        for (Block b : cuboid.getBlocks()) {
                            if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                return file.getString("uuid");
                            }
                        }
                    } else if (Bukkit.getWorld(file.getString("claims.0.world")).getEnvironment().equals(World.Environment.NETHER) && p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                        Location locationOne = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideOne.x"), file.getDouble("claims.0.sideOne.y"), file.getDouble("claims.0.sideOne.z"));
                        Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideTwo.x"), file.getDouble("claims.0.sideTwo.y"), file.getDouble("claims.0.sideTwo.z"));
                        Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                        for (Block b : cuboid.getBlocks()) {
                            if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                return file.getString("uuid");
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private Boolean isInClaim(Player p, Location selectedBlock) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0") && file.isConfigurationSection("claims.1")) {
                    for (String fileNumber : file.getConfigurationSection("claims").getKeys(false)) {
                        if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NORMAL) && p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return true;
                                }
                            }
                        } else if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NETHER) && p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return true;
                                }
                            }
                        }
                    }
                } else if (file.isConfigurationSection("claims.0")) {
                    if (Bukkit.getWorld(file.getString("claims.0.world")).getEnvironment().equals(World.Environment.NORMAL) && p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                        Location locationOne = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideOne.x"), file.getDouble("claims.0.sideOne.y"), file.getDouble("claims.0.sideOne.z"));
                        Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideTwo.x"), file.getDouble("claims.0.sideTwo.y"), file.getDouble("claims.0.sideTwo.z"));
                        Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                        for (Block b : cuboid.getBlocks()) {
                            if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                return true;
                            }
                        }
                    } else if (Bukkit.getWorld(file.getString("claims.0.world")).getEnvironment().equals(World.Environment.NETHER) && p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                        Location locationOne = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideOne.x"), file.getDouble("claims.0.sideOne.y"), file.getDouble("claims.0.sideOne.z"));
                        Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideTwo.x"), file.getDouble("claims.0.sideTwo.y"), file.getDouble("claims.0.sideTwo.z"));
                        Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                        for (Block b : cuboid.getBlocks()) {
                            if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
            if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (!(e.getClickedBlock().getState() instanceof Sign)) {
                    if (isInClaim(p, e.getClickedBlock().getLocation())) {
                        Players players = new Players(p.getUniqueId().toString());
                        Faction faction = new Faction(getFactionInClaim(p, e.getClickedBlock().getLocation()));
                        if (players.hasFaction()) {
                            if (!players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                e.setCancelled(true);
                                if (faction.get().getString("color") != null) {
                                    p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                                }
                            }
                        } else {
                            e.setCancelled(true);
                            if (faction.get().getString("color") != null) {
                                p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void br(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (!isInClaim(p, e.getBlock().getLocation())) {
                int limit = Main.getInstance().getConfig().getInt("worlds.default.warzone-build-limit");
                if (e.getBlock().getLocation().getX() >= 0 && e.getBlock().getLocation().getZ() >= 0) {
                    if (e.getBlock().getLocation().getX() <= limit && e.getBlock().getLocation().getZ() <= limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                } else if (e.getBlock().getLocation().getX() <= 0 && e.getBlock().getLocation().getZ() <= 0) {
                    if (e.getBlock().getLocation().getX() >= -limit && e.getBlock().getLocation().getZ() >= -limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                } else if (e.getBlock().getLocation().getX() >= 0 && e.getBlock().getLocation().getZ() <= 0) {
                    if (e.getBlock().getLocation().getX() <= limit && e.getBlock().getLocation().getZ() >= -limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                } else if (e.getBlock().getLocation().getX() <= 0 && e.getBlock().getLocation().getZ() >= 0) {
                    if (e.getBlock().getLocation().getX() >= -limit && e.getBlock().getLocation().getZ() <= limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                }
            }
        } else if (p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
            if (!isInClaim(p, e.getBlock().getLocation())) {
                int limit = Main.getInstance().getConfig().getInt("worlds.nether.warzone-build-limit");
                if (e.getBlock().getLocation().getX() >= 0 && e.getBlock().getLocation().getZ() >= 0) {
                    if (e.getBlock().getLocation().getX() <= limit && e.getBlock().getLocation().getZ() <= limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                } else if (e.getBlock().getLocation().getX() <= 0 && e.getBlock().getLocation().getZ() <= 0) {
                    if (e.getBlock().getLocation().getX() >= -limit && e.getBlock().getLocation().getZ() >= -limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                } else if (e.getBlock().getLocation().getX() >= 0 && e.getBlock().getLocation().getZ() <= 0) {
                    if (e.getBlock().getLocation().getX() <= limit && e.getBlock().getLocation().getZ() >= -limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                } else if (e.getBlock().getLocation().getX() <= 0 && e.getBlock().getLocation().getZ() >= 0) {
                    if (e.getBlock().getLocation().getX() >= -limit && e.getBlock().getLocation().getZ() <= limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void br(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (!isInClaim(p, e.getBlock().getLocation())) {
                int limit = Main.getInstance().getConfig().getInt("worlds.default.warzone-build-limit");
                if (e.getBlock().getLocation().getX() >= 0 && e.getBlock().getLocation().getZ() >= 0) {
                    if (e.getBlock().getLocation().getX() <= limit && e.getBlock().getLocation().getZ() <= limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                } else if (e.getBlock().getLocation().getX() <= 0 && e.getBlock().getLocation().getZ() <= 0) {
                    if (e.getBlock().getLocation().getX() >= -limit && e.getBlock().getLocation().getZ() >= -limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                } else if (e.getBlock().getLocation().getX() >= 0 && e.getBlock().getLocation().getZ() <= 0) {
                    if (e.getBlock().getLocation().getX() <= limit && e.getBlock().getLocation().getZ() >= -limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                } else if (e.getBlock().getLocation().getX() <= 0 && e.getBlock().getLocation().getZ() >= 0) {
                    if (e.getBlock().getLocation().getX() >= -limit && e.getBlock().getLocation().getZ() <= limit) {
                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", Main.getInstance().getConfig().getString("claim.warzone.name"))));
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void breakEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
            if (isInClaim(p, e.getBlock().getLocation())) {
                Players players = new Players(p.getUniqueId().toString());
                Faction faction = new Faction(getFactionInClaim(p, e.getBlock().getLocation()));
                if (players.hasFaction()) {
                    if (!players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                        if (!faction.get().getString("type").equals("MOUNTAIN") || faction.get().getString("mountain") == null) {
                            e.setCancelled(true);
                            if (faction.get().getString("color") != null) {
                                if (faction.get().getString("type").equals("KOTH")) {
                                    p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH")));
                                } else if (faction.get().getString("type").equals("ROAD")) {
                                    p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road")));
                                } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                    p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain")));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                                }

                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                            }
                        } else {
                            Mountain mountain = new Mountain(faction.get().getString("mountain"));
                            if (e.getBlock().getType().equals(Material.matchMaterial(mountain.get().getString("block")))) {
                                e.setCancelled(false);
                            } else {
                                e.setCancelled(true);
                                if (faction.get().getString("color") != null) {
                                    if (faction.get().getString("type").equals("KOTH")) {
                                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH")));
                                    } else if (faction.get().getString("type").equals("ROAD")) {
                                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road")));
                                    } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain")));
                                    } else {
                                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                                    }
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                                }
                            }
                        }
                    }
                } else {
                    e.setCancelled(true);
                    if (faction.get().getString("color") != null) {
                        if (faction.get().getString("type").equals("KOTH")) {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH")));
                        } else if (faction.get().getString("type").equals("ROAD")) {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road")));
                        } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain")));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                    }
                }
            }
        }
    }

    @EventHandler
    public void placeEvent(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
            if (isInClaim(p, e.getBlock().getLocation())) {
                Players players = new Players(p.getUniqueId().toString());
                Faction faction = new Faction(getFactionInClaim(p, e.getBlock().getLocation()));
                if (players.hasFaction()) {
                    if (!players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                        e.setCancelled(true);
                        if (faction.get().getString("color") != null) {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                        }
                    }
                } else {
                    e.setCancelled(true);
                    if (faction.get().getString("color") != null) {
                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                    }
                }
            }
        }
    }

    @EventHandler
    public void bed(PlayerBedEnterEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void bed(PlayerShearEntityEvent e) {
        Player p = e.getPlayer();
        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
            if (isInClaim(p, e.getEntity().getLocation())) {
                Players players = new Players(p.getUniqueId().toString());
                Faction faction = new Faction(getFactionInClaim(p, e.getEntity().getLocation()));
                if (players.hasFaction()) {
                    if (!players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                        e.setCancelled(true);
                        if (faction.get().getString("color") != null) {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                        }
                    }
                } else {
                    e.setCancelled(true);
                    if (faction.get().getString("color") != null) {
                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                    }
                }
            }
        }
    }

    @EventHandler
    public void bed(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
            if (isInClaim(p, e.getBlockClicked().getLocation())) {
                Players players = new Players(p.getUniqueId().toString());
                Faction faction = new Faction(getFactionInClaim(p, e.getBlockClicked().getLocation()));
                if (players.hasFaction()) {
                    if (!players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                        e.setCancelled(true);
                        if (faction.get().getString("color") != null) {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                        }
                    }
                } else {
                    e.setCancelled(true);
                    if (faction.get().getString("color") != null) {
                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"))));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("events.faction.deny").replace("%faction%", faction.get().getString("name"))));
                    }
                }
            }
        }
    }


}
