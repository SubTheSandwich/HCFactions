package me.sub.hcfactions.Utils.Timer;

import me.sub.hcfactions.Commands.Staff.TeleportCommand;
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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class Timer {

    public static void tickStuckTimer(UUID p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.getInstance().stuckTimer.containsKey(p) && Bukkit.getPlayer(p) != null) {
                    int time = Main.getInstance().stuckTimer.get(p);
                    time = time - 1;
                    if (time > 0) {
                        Main.getInstance().stuckTimer.put(p, time);
                    } else {
                        Player player = Bukkit.getPlayer(p);
                        Main.getInstance().stuckTimer.remove(p);
                        Main.getInstance().stuckLocation.remove(p);
                        loop:
                        for (int x = player.getLocation().getBlockX() - 15; x <= player.getLocation().getBlockX() + 15; x++) {
                            for (int z = player.getLocation().getBlockZ() - 15; z <= player.getLocation().getBlockZ() + 15; z++) {
                                Location location = new Location(player.getWorld(), x, player.getLocation().getBlockY(), z);
                                if (getFactionInLocation(location) == null && TeleportCommand.isSafeLocation(location)) {
                                    player.teleport(location);
                                    break loop;
                                }
                            }
                        }
                        player.sendMessage(C.chat(Locale.get().getString("command.faction.stuck.success")));
                        cancel();
                    }
                } else {
                    Main.getInstance().stuckTimer.remove(p);
                    Main.getInstance().stuckLocation.remove(p);
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    public static void tickCustomTimer(String timer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.getInstance().customTimers.containsKey(timer)) {
                    int time = Main.getInstance().customTimers.get(timer);
                    if (!Main.getInstance().customTimersPaused.get(timer)) {
                        time = time - 1;
                        if (time <= 0) {
                            Main.getInstance().customTimers.remove(timer);
                            cancel();
                        } else {
                            Main.getInstance().customTimers.put(timer, time);
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

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

    public static void tickArcherTag(UUID uuid) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.getInstance().archerTag.containsKey(uuid)) {
                    BigDecimal num = new BigDecimal("0.05");
                    BigDecimal time = Main.getInstance().archerTag.get(uuid);
                    time = time.subtract(num);
                    if (time.doubleValue() <= 0) {
                        if (Bukkit.getPlayer(uuid) != null) {
                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("archer.expired")));
                        }
                        Main.getInstance().archerTag.remove(uuid);
                        cancel();
                    } else {
                        Main.getInstance().archerTag.put(uuid, time);
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public static void tickGoppleTimer(UUID uuid) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getPlayer(uuid) != null) {
                    int time = Main.getInstance().goppleTimer.get(uuid);
                    if (Main.getInstance().goppleTimer.containsKey(uuid)) {
                        time = time - 1;
                        if (time <= 0) {
                            Main.getInstance().goppleTimer.remove(uuid);
                            Players players = new Players(uuid.toString());
                            players.get().set("savedTimers.goppleTimer", 0);
                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("events.gopple.expired")));
                            players.save();
                            cancel();
                        } else {
                            Main.getInstance().goppleTimer.put(uuid, time);
                        }
                    } else {
                        cancel();
                    }
                } else {
                    Players players = new Players(uuid.toString());
                    players.get().set("savedTimers.goppleTimer", Main.getInstance().goppleTimer.get(uuid));
                    players.save();
                    Main.getInstance().goppleTimer.remove(uuid);
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
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

    private static String getFactionInClaim(Player p) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims")) {
                    for (String s : file.getConfigurationSection("claims").getKeys(false)) {
                        Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + s + ".world")), file.getDouble("claims." + s + ".sideOne.x"), file.getDouble("claims." + s + ".sideOne.y"), file.getDouble("claims." + s + ".sideOne.z"));
                        Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + s + ".world")), file.getDouble("claims." + s + ".sideTwo.x"), file.getDouble("claims." + s + ".sideTwo.y"), file.getDouble("claims." + s + ".sideTwo.z"));
                        Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                        if (cuboid.contains(p.getLocation())) {
                            return file.getString("uuid");
                        }
                    }
                }
            }
        }

        return null;
    }

    private static String getFactionInLocation(Location p) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims")) {
                    for (String s : file.getConfigurationSection("claims").getKeys(false)) {
                        Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + s + ".world")), file.getDouble("claims." + s + ".sideOne.x"), file.getDouble("claims." + s + ".sideOne.y"), file.getDouble("claims." + s + ".sideOne.z"));
                        Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + s + ".world")), file.getDouble("claims." + s + ".sideTwo.x"), file.getDouble("claims." + s + ".sideTwo.y"), file.getDouble("claims." + s + ".sideTwo.z"));
                        Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                        if (cuboid.contains(p)) {
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
                                Main.getInstance().timers.remove(Timers.SOTW);
                            } else {
                                setTimer(Timers.SOTW, time);
                            }
                        }
                    }
                    if (timers.contains(Timers.EOTW)) {
                        int time = Main.getInstance().timers.get(Timers.EOTW);
                        time = time - 1;
                        if (time == 0) {
                            Main.getInstance().eotwStarted = true;
                            Bukkit.broadcastMessage(C.chat(Locale.get().getString("command.eotw.commenced")));
                            Main.getInstance().timers.remove(Timers.EOTW);
                        } else {
                            setTimer(Timers.EOTW, time);
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
            @Override
            public void run() {
                if (Classes.hasActiveClass(p) && Classes.isInBard(p)) {
                    if (!Main.getInstance().bardEnergy.containsKey(p)) {
                        Main.getInstance().bardEnergy.put(p, 0.0);
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
