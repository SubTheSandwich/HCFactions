package me.sub.hcfactions.Utils.Timer;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Class.Classes;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class Timer {

    public static void setChatSlow(UUID p, BigDecimal time) {
        Main.getInstance().chatSlowPlayer.put(p, time);
    }

    public static void setHomeTimer(Player p, BigDecimal time) {
        Main.getInstance().homeTimer.put(p, time);
    }

    public static void setPvPTimer(UUID p, int time) {
        Main.getInstance().pvpTimer.put(p, time);
    }

    public static void tickPvPTimer(UUID p) {
        Players players = new Players(p.toString());
        new BukkitRunnable() {
            int second = 0;
            @Override
            public void run() {
                second = second + 1;
                if (second == 20) {
                    second = 0;
                    if (Main.getInstance().pvpTimer.containsKey(p)) {
                        Player player = Bukkit.getPlayer(p);
                        int time = Main.getInstance().pvpTimer.get(p);
                        if (player != null) {
                            if (getFactionInClaim(player) != null) {
                                if (!new Faction(getFactionInClaim(player)).get().getString("type").equals("SAFEZONE")) {
                                    if (time <= 0) {
                                        cancel();
                                    } else {
                                        time = time - 1;
                                        setPvPTimer(p, time);
                                    }
                                } else {
                                    if (time <= 0) {
                                        players.get().set("savedTimers.pvpTimer", null);
                                        players.save();
                                        cancel();
                                    }
                                }
                            } else {
                                if (time <= 0) {
                                    players.get().set("savedTimers.pvpTimer", null);
                                    players.save();
                                    cancel();
                                } else {
                                    time = time - 1;
                                    setPvPTimer(p, time);
                                }
                            }
                        } else {
                            players.get().set("savedTimers.pvpTimer", time);
                            players.save();
                            Main.getInstance().pvpTimer.remove(p);
                            cancel();
                        }
                    } else {
                        cancel();
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }


    public static void tickHomeTimer(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                BigDecimal time = Main.getInstance().homeTimer.get(p);
                if (Main.getInstance().homeTimer.containsKey(p)) {
                    if (p.isOnline()) {
                        if (time.doubleValue() <= 0) {
                            Main.getInstance().homeTimer.remove(p);
                            p.sendMessage(C.chat(Locale.get().getString("events.teleport-home")));
                            Faction faction = new Players(p.getUniqueId().toString()).getFaction();
                            Location location = new Location(Bukkit.getWorld(faction.get().getString("home.world")), faction.get().getDouble("home.x"), faction.get().getDouble("home.y"), faction.get().getDouble("home.z"), (float) faction.get().getDouble("home.yaw"), (float) faction.get().getDouble("home.pitch"));
                            p.teleport(location);
                            cancel();
                        } else {
                            BigDecimal num = new BigDecimal("0.05");
                            time = time.subtract(num);
                            setHomeTimer(p, time);
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public static void tickChatSlow(UUID p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.getInstance().chatSlowPlayer.containsKey(p)) {
                    BigDecimal time = Main.getInstance().chatSlowPlayer.get(p);
                    if (Bukkit.getPlayer(p) != null) {
                        Player player = Bukkit.getPlayer(p);
                        if (time.doubleValue() <= 0) {
                            Main.getInstance().chatSlowPlayer.remove(p);
                            player.sendMessage(C.chat(Locale.get().getString("events.chat-now")));
                            cancel();
                        } else {
                            BigDecimal num = new BigDecimal("0.05");
                            time = time.subtract(num);
                            setChatSlow(p, time);
                        }
                    } else {
                        if (time.doubleValue() <= 0) {
                            Main.getInstance().chatSlowPlayer.remove(p);
                            cancel();
                        } else {
                            BigDecimal num = new BigDecimal("0.05");
                            time = time.subtract(num);
                            setChatSlow(p, time);
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public static void tickLogoutTimer(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                BigDecimal time = Main.getInstance().logoutTimer.get(p);
                if (Main.getInstance().logoutTimer.containsKey(p)) {
                    if (p.isOnline()) {
                        if (time.doubleValue() <= 0) {
                            Main.getInstance().logoutTimer.remove(p);
                            Main.getInstance().ticking.remove(p.getUniqueId());
                            Main.getInstance().combatTimer.remove(p.getUniqueId());
                            p.kickPlayer(C.chat(Locale.get().getString("command.logout.success")));
                            cancel();
                        } else {
                            BigDecimal num = new BigDecimal("0.05");
                            time = time.subtract(num);
                            setLogoutTimer(p, time);
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public static void setAppleTimer(UUID p, BigDecimal v) {
        Main.getInstance().appleTimer.put(p, v);
    }

    public static String getFactionInClaim(Player p) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0") && file.isConfigurationSection("claims.1")) {

                } else if (file.isConfigurationSection("claims.0")) {
                    Location locationOne = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideOne.x"), file.getDouble("claims.0.sideOne.y"), file.getDouble("claims.0.sideOne.z"));
                    Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideTwo.x"), file.getDouble("claims.0.sideTwo.y"), file.getDouble("claims.0.sideTwo.z"));
                    Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
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

    public static void tickAppleTimer(UUID p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.getInstance().appleTimer.containsKey(p)) {
                    BigDecimal time = Main.getInstance().appleTimer.get(p);
                    if (Bukkit.getPlayer(p) != null) {
                        Player player = Bukkit.getPlayer(p);
                        if (time.doubleValue() <= 0) {
                            Main.getInstance().appleTimer.remove(p);
                            player.sendMessage(C.chat(Locale.get().getString("events.apple.expired")));
                            cancel();
                        } else {
                            BigDecimal num = new BigDecimal("0.05");
                            time = time.subtract(num);
                            setAppleTimer(p, time);
                        }
                    } else {
                        if (time.doubleValue() <= 0) {
                            Main.getInstance().appleTimer.remove(p);
                            cancel();
                        } else {
                            BigDecimal num = new BigDecimal("0.05");
                            time = time.subtract(num);
                            setAppleTimer(p, time);
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public static void setEffectTimer(OfflinePlayer p, BigDecimal v) {
        Main.getInstance().effectCooldown.put(p, v);
    }

    public static void tickEffectTimer(OfflinePlayer p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                BigDecimal time = Main.getInstance().effectCooldown.get(p);
                if (Main.getInstance().effectCooldown.containsKey(p)) {
                    if (p.isOnline()) {
                        Player player = Bukkit.getPlayer(p.getUniqueId());
                        if (time.doubleValue() <= 0) {
                            Main.getInstance().effectCooldown.remove(p);
                            player.sendMessage(C.chat(Locale.get().getString("bard.can-use")));
                            cancel();
                        } else {
                            BigDecimal num = new BigDecimal("0.05");
                            time = time.subtract(num);
                            setEffectTimer(p, time);
                        }
                    } else {
                        if (time.doubleValue() <= 0) {
                            Main.getInstance().effectCooldown.remove(p);
                            cancel();
                        } else {
                            BigDecimal num = new BigDecimal("0.05");
                            time = time.subtract(num);
                            setEffectTimer(p, time);
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public static void setLogoutTimer(Player p, BigDecimal v) {
        Main.getInstance().logoutTimer.put(p, v);
    }


    public static void trackTimers() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.getInstance().timers != null) {
                    ArrayList<Timers> timers = new ArrayList<>(Main.getInstance().timers.keySet());
                    if (timers.contains(Timers.SOTW) && Main.getInstance().sotwStarted) {
                        int time = Main.getInstance().timers.get(Timers.SOTW);
                        if (!Main.getInstance().sotwPaused) {
                            time = time - 1;
                            if (time == 0) {
                                Main.getInstance().sotwStarted = false;
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.sotw.ended")));
                                }
                            } else {
                                setTimer(Timers.SOTW, time);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }



    public static Integer getTimer(Timers timer) {
        return Main.getInstance().timers.get(timer);
    }

    public static void setTimer(Timers timer, Integer time) {
        Main.getInstance().timers.put(timer, time);
    }

    public static void tickEnergy(Player p) {
        new BukkitRunnable() {
            double bardEnergy = 0.0;
            @Override
            public void run() {
                if (Classes.hasActiveClass(p) && Classes.isInBard(p)) {
                    if (!Main.getInstance().bardEnergy.containsKey(p)) {
                        Main.getInstance().bardEnergy.put(p, bardEnergy);
                    } else {
                        if (Main.getInstance().bardEnergy.get(p) < 100.0) {
                            double energy = Main.getInstance().bardEnergy.get(p) + 1.0;
                            Main.getInstance().bardEnergy.put(p, energy);
                        }
                    }
                } else {
                    Main.getInstance().bardEnergy.remove(p);
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

}
