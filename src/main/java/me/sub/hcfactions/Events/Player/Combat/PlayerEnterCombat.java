package me.sub.hcfactions.Events.Player.Combat;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorderCreateNew;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorderRemove;
import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.math.BigDecimal;

public class PlayerEnterCombat implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player damaged = (Player) e.getEntity();
            if (isInClaim(damaged)) {
                Faction damagedFaction = new Faction(getFactionInClaim(damaged));
                if (damagedFaction.get().getString("type").equals("SAFEZONE")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damaged = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            Players d1 = new Players(damaged.getUniqueId().toString());
            Players d2 = new Players(damager.getUniqueId().toString());
            if (!isInClaim(damaged) && !isInClaim(damager)) {
                if (!Main.getInstance().pvpTimer.containsKey(damaged.getUniqueId()) && !Main.getInstance().pvpTimer.containsKey(damager.getUniqueId()) && !Main.getInstance().frozen.contains(damaged) && !Main.getInstance().frozen.contains(damager) && !Main.getInstance().staff.contains(damaged) && !Main.getInstance().staff.contains(damager)) {
                    if (Main.getInstance().sotwStarted && Main.getInstance().sotwEnabled.contains(damager) && Main.getInstance().sotwEnabled.contains(damaged)) {
                        if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                            if (d1.get().getString("faction").equals("")) {
                                Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Cooldown.tickCombatTimer(damaged.getUniqueId());
                                Cooldown.tickCombatTimer(damager.getUniqueId());
                                if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                    Main.getInstance().logoutTimer.remove(damaged);
                                }
                                if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                    Main.getInstance().homeTimer.remove(damaged);
                                }
                            } else {
                                if (!damaged.equals(damager)) {
                                    e.setCancelled(true);
                                    damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                }
                            }
                        } else {
                            Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                            Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                            Cooldown.tickCombatTimer(damaged.getUniqueId());
                            Cooldown.tickCombatTimer(damager.getUniqueId());
                            if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                Main.getInstance().logoutTimer.remove(damaged);
                            }
                            if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                Main.getInstance().homeTimer.remove(damaged);
                            }
                        }
                    } else {
                        if (!Main.getInstance().sotwStarted) {
                            if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                if (d1.get().getString("faction").equals("")) {
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                } else {
                                    if (!damaged.equals(damager)) {
                                        e.setCancelled(true);
                                        damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                    }
                                }
                            } else {
                                Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Cooldown.tickCombatTimer(damaged.getUniqueId());
                                Cooldown.tickCombatTimer(damager.getUniqueId());
                                File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                                if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                    Main.getInstance().logoutTimer.remove(damaged);
                                }
                                if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                    Main.getInstance().homeTimer.remove(damaged);
                                }
                            }
                        } else {
                            e.setCancelled(true);
                        }
                    }
                } else {
                    e.setCancelled(true);
                }
            } else if (!isInClaim(damaged) && isInClaim(damager)) {
                Faction damagerFaction = new Faction(getFactionInClaim(damager));
                if (!damagerFaction.get().getString("type").equals("SAFEZONE")) {
                    if (!Main.getInstance().pvpTimer.containsKey(damaged.getUniqueId()) && !Main.getInstance().pvpTimer.containsKey(damager.getUniqueId()) && !Main.getInstance().frozen.contains(damaged) && !Main.getInstance().frozen.contains(damager) && !Main.getInstance().staff.contains(damaged) && !Main.getInstance().staff.contains(damager)) {
                        if (Main.getInstance().sotwStarted && Main.getInstance().sotwEnabled.contains(damager) && Main.getInstance().sotwEnabled.contains(damaged)) {
                            if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                if (d1.get().getString("faction").equals("")) {
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                } else {
                                    if (!damaged.equals(damager)) {
                                        e.setCancelled(true);
                                        damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                    }
                                }
                            } else {
                                Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Cooldown.tickCombatTimer(damaged.getUniqueId());
                                Cooldown.tickCombatTimer(damager.getUniqueId());
                                if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                    Main.getInstance().logoutTimer.remove(damaged);
                                }
                                if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                    Main.getInstance().homeTimer.remove(damaged);
                                }
                            }
                        } else {
                            if (!Main.getInstance().sotwStarted) {
                                if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                    if (d1.get().getString("faction").equals("")) {
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    } else {
                                        if (!damaged.equals(damager)) {
                                            e.setCancelled(true);
                                            damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                        }
                                    }
                                } else {
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                    damager.sendMessage(C.chat(Locale.get().getString("events.attack-safezone")));
                }
            } else if (isInClaim(damaged) && !isInClaim(damager)) {
                Faction damagerFaction = new Faction(getFactionInClaim(damaged));
                if (!damagerFaction.get().getString("type").equals("SAFEZONE")) {
                    if (!Main.getInstance().frozen.contains(damaged) && !Main.getInstance().frozen.contains(damager) && !Main.getInstance().staff.contains(damaged) && !Main.getInstance().staff.contains(damager)) {
                        if (!Main.getInstance().pvpTimer.containsKey(damaged.getUniqueId()) && !Main.getInstance().pvpTimer.containsKey(damager.getUniqueId()) &&Main.getInstance().sotwStarted && Main.getInstance().sotwEnabled.contains(damager) && Main.getInstance().sotwEnabled.contains(damaged)) {
                            if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                if (d1.get().getString("faction").equals("")) {
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                                    if (factions != null) {
                                        for (File f : factions) {
                                            YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                            if (file.getString("type").equals("SAFEZONE") && file.isConfigurationSection("claims.0") && file.isConfigurationSection("claims.1")) {

                                            } else if (file.isConfigurationSection("claims.0") && file.getString("type").equals("SAFEZONE")
                                            ) {
                                                 LCPacketWorldBorderCreateNew packet = new LCPacketWorldBorderCreateNew("safezone", damaged.getWorld().getName(), true, false, false, 8525585, file.getDouble("claims.0.sideOne.x"), file.getDouble("claims.0.sideOne.z"), file.getDouble("claims.0.sideTwo.x"), file.getDouble("claims.0.sideTwo.z"));
                                                LCPacketWorldBorderRemove deadPacket = new LCPacketWorldBorderRemove("safezone");
                                                Main.getInstance().lunarClientAPI.sendPacket(damaged, packet);
                                                Main.getInstance().lunarClientAPI.sendPacket(damager, packet);
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        if (!Main.getInstance().combatTimer.containsKey(damager.getUniqueId())) {
                                                            Main.getInstance().lunarClientAPI.sendPacket(damager, deadPacket);
                                                            cancel();
                                                        }
                                                    }
                                                }.runTaskTimer(Main.getInstance(), 0, 1);
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        if (!Main.getInstance().combatTimer.containsKey(damaged.getUniqueId())) {
                                                            Main.getInstance().lunarClientAPI.sendPacket(damaged, deadPacket);
                                                            cancel();
                                                        }
                                                    }
                                                }.runTaskTimer(Main.getInstance(), 0, 1);
                                            }
                                        }
                                    }
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                } else {
                                    if (!damaged.equals(damager)) {
                                        e.setCancelled(true);
                                        damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                    }
                                }
                            } else {
                                Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Cooldown.tickCombatTimer(damaged.getUniqueId());
                                Cooldown.tickCombatTimer(damager.getUniqueId());
                                if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                    Main.getInstance().logoutTimer.remove(damaged);
                                }
                                if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                    Main.getInstance().homeTimer.remove(damaged);
                                }
                            }
                        } else {
                            if (!Main.getInstance().sotwStarted) {
                                if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                    if (d1.get().getString("faction").equals("")) {
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    } else {
                                        if (!damaged.equals(damager)) {
                                            e.setCancelled(true);
                                            damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                        }
                                    }
                                } else {
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                    damager.sendMessage(C.chat(Locale.get().getString("events.attack-safezone")));
                }
            } else {
                Faction damagerFaction = new Faction(getFactionInClaim(damager));
                Faction damagedFaction = new Faction(getFactionInClaim(damaged));
                if (!damagerFaction.get().getString("type").equals("SAFEZONE") && !damagedFaction.get().getString("type").equals("SAFEZONE")) {
                    if (!Main.getInstance().frozen.contains(damaged) && !Main.getInstance().frozen.contains(damager) && !Main.getInstance().staff.contains(damaged) && !Main.getInstance().staff.contains(damager)) {
                        if (!Main.getInstance().pvpTimer.containsKey(damaged.getUniqueId()) && !Main.getInstance().pvpTimer.containsKey(damager.getUniqueId()) &&Main.getInstance().sotwStarted && Main.getInstance().sotwEnabled.contains(damager) && Main.getInstance().sotwEnabled.contains(damaged)) {
                            if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                if (d1.get().getString("faction").equals("")) {
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                } else {
                                    if (!damaged.equals(damager)) {
                                        e.setCancelled(true);
                                        damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                    }
                                }
                            } else {
                                Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Cooldown.tickCombatTimer(damaged.getUniqueId());
                                Cooldown.tickCombatTimer(damager.getUniqueId());
                                if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                    Main.getInstance().logoutTimer.remove(damaged);
                                }
                                if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                    Main.getInstance().homeTimer.remove(damaged);
                                }
                            }
                        } else {
                            if (!Main.getInstance().sotwStarted) {
                                if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                    if (d1.get().getString("faction").equals("")) {
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    } else {
                                        if (!damaged.equals(damager)) {
                                            e.setCancelled(true);
                                            damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                        }
                                    }
                                } else {
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                    damager.sendMessage(C.chat(Locale.get().getString("events.attack-safezone")));
                }
            }
        } else if (e.getEntity() instanceof Villager && e.getDamager() instanceof Player) {
            Villager v = (Villager) e.getEntity();
            Player damager = (Player) e.getDamager();
            Players players = new Players(damager.getUniqueId().toString());
            if (v.getCustomName() != null) {
                String name = C.strip(v.getCustomName());
                boolean inFaction = false;
                if (players.hasFaction()) {
                    for (OfflinePlayer player : players.getFaction().getAllMembers()) {
                        if (player.getName().equalsIgnoreCase(name)) {
                            inFaction = true;
                        }
                    }
                }
                if (inFaction) {
                    e.setCancelled(true);
                    damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", name)));
                }
            }
        } else if (e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
            Player damaged = (Player) e.getEntity();
            Projectile projectile = (Projectile) e.getDamager();
            if (projectile.getShooter() instanceof Player) {
                Player damager = (Player) projectile.getShooter();
                Players d1 = new Players(damaged.getUniqueId().toString());
                Players d2 = new Players(damager.getUniqueId().toString());
                if (!isInClaim(damaged) && !isInClaim(damager)) {
                    if (!Main.getInstance().frozen.contains(damaged) && !Main.getInstance().frozen.contains(damager) && !Main.getInstance().staff.contains(damaged) && !Main.getInstance().staff.contains(damager)) {
                        if (!Main.getInstance().pvpTimer.containsKey(damaged.getUniqueId()) && !Main.getInstance().pvpTimer.containsKey(damager.getUniqueId()) &&Main.getInstance().sotwStarted && Main.getInstance().sotwEnabled.contains(damager) && Main.getInstance().sotwEnabled.contains(damaged)) {
                            if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                if (d1.get().getString("faction").equals("")) {
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                } else {
                                    if (!damaged.equals(damager)) {
                                        e.setCancelled(true);
                                        damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                    }
                                }
                            } else {
                                Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                Cooldown.tickCombatTimer(damaged.getUniqueId());
                                Cooldown.tickCombatTimer(damager.getUniqueId());
                                if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                    Main.getInstance().logoutTimer.remove(damaged);
                                }
                                if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                    damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                    Main.getInstance().homeTimer.remove(damaged);
                                }
                            }
                        } else {
                            if (!Main.getInstance().sotwStarted) {
                                if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                    if (d1.get().getString("faction").equals("")) {
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    } else {
                                        if (!damaged.equals(damager)) {
                                            e.setCancelled(true);
                                            damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                        }
                                    }
                                } else {
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        e.setCancelled(true);
                    }
                } else if (!isInClaim(damaged) && isInClaim(damager)) {
                    Faction damagerFaction = new Faction(getFactionInClaim(damager));
                    if (!damagerFaction.get().getString("type").equals("SAFEZONE")) {
                        if (!Main.getInstance().frozen.contains(damaged) && !Main.getInstance().frozen.contains(damager) && !Main.getInstance().staff.contains(damaged) && !Main.getInstance().staff.contains(damager)) {
                            if (!Main.getInstance().pvpTimer.containsKey(damaged.getUniqueId()) && !Main.getInstance().pvpTimer.containsKey(damager.getUniqueId()) &&Main.getInstance().sotwStarted && Main.getInstance().sotwEnabled.contains(damager) && Main.getInstance().sotwEnabled.contains(damaged)) {
                                if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                    if (d1.get().getString("faction").equals("")) {
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    } else {
                                        if (!damaged.equals(damager)) {
                                            e.setCancelled(true);
                                            damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                        }
                                    }
                                } else {
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                }
                            } else {
                                if (!Main.getInstance().sotwStarted) {
                                    if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                        if (d1.get().getString("faction").equals("")) {
                                            Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                            Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                            Cooldown.tickCombatTimer(damaged.getUniqueId());
                                            Cooldown.tickCombatTimer(damager.getUniqueId());
                                            if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                                damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                                Main.getInstance().logoutTimer.remove(damaged);
                                            }
                                            if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                                damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                                Main.getInstance().homeTimer.remove(damaged);
                                            }
                                        } else {
                                            if (!damaged.equals(damager)) {
                                                e.setCancelled(true);
                                                damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                            }
                                        }
                                    } else {
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    }
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            e.setCancelled(true);
                        }
                    } else {
                        e.setCancelled(true);
                        damager.sendMessage(C.chat(Locale.get().getString("events.attack-safezone")));
                    }
                } else if (isInClaim(damaged) && !isInClaim(damager)) {
                    Faction damagerFaction = new Faction(getFactionInClaim(damaged));
                    if (!damagerFaction.get().getString("type").equals("SAFEZONE")) {
                        if (!Main.getInstance().frozen.contains(damaged) && !Main.getInstance().frozen.contains(damager) && !Main.getInstance().staff.contains(damaged) && !Main.getInstance().staff.contains(damager)) {
                            if (!Main.getInstance().pvpTimer.containsKey(damaged.getUniqueId()) && !Main.getInstance().pvpTimer.containsKey(damager.getUniqueId()) &&Main.getInstance().sotwStarted && Main.getInstance().sotwEnabled.contains(damager) && Main.getInstance().sotwEnabled.contains(damaged)) {
                                if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                    if (d1.get().getString("faction").equals("")) {
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    } else {
                                        if (!damaged.equals(damager)) {
                                            e.setCancelled(true);
                                            damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                        }
                                    }
                                } else {
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                }
                            } else {
                                if (!Main.getInstance().sotwStarted) {
                                    if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                        if (d1.get().getString("faction").equals("")) {
                                            Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                            Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                            Cooldown.tickCombatTimer(damaged.getUniqueId());
                                            Cooldown.tickCombatTimer(damager.getUniqueId());
                                            if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                                damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                                Main.getInstance().logoutTimer.remove(damaged);
                                            }
                                            if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                                damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                                Main.getInstance().homeTimer.remove(damaged);
                                            }
                                        } else {
                                            if (!damaged.equals(damager)) {
                                                e.setCancelled(true);
                                                damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                            }
                                        }
                                    } else {
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    }
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            e.setCancelled(true);
                        }
                    } else {
                        e.setCancelled(true);
                        damager.sendMessage(C.chat(Locale.get().getString("events.attack-safezone")));
                    }
                } else {
                    Faction damagerFaction = new Faction(getFactionInClaim(damager));
                    Faction damagedFaction = new Faction(getFactionInClaim(damaged));
                    if (!damagerFaction.get().getString("type").equals("SAFEZONE") && !damagedFaction.get().getString("type").equals("SAFEZONE")) {
                        if (!Main.getInstance().frozen.contains(damaged) && !Main.getInstance().frozen.contains(damager) && !Main.getInstance().staff.contains(damaged) && !Main.getInstance().staff.contains(damager)) {
                            if (!Main.getInstance().pvpTimer.containsKey(damaged.getUniqueId()) && !Main.getInstance().pvpTimer.containsKey(damager.getUniqueId()) &&Main.getInstance().sotwStarted && Main.getInstance().sotwEnabled.contains(damager) && Main.getInstance().sotwEnabled.contains(damaged)) {
                                if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                    if (d1.get().getString("faction").equals("")) {
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    } else {
                                        if (!damaged.equals(damager)) {
                                            e.setCancelled(true);
                                            damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                        }
                                    }
                                } else {
                                    Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                    Cooldown.tickCombatTimer(damaged.getUniqueId());
                                    Cooldown.tickCombatTimer(damager.getUniqueId());
                                    if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                        Main.getInstance().logoutTimer.remove(damaged);
                                    }
                                    if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                        damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                        Main.getInstance().homeTimer.remove(damaged);
                                    }
                                }
                            } else {
                                if (!Main.getInstance().sotwStarted) {
                                    if (d1.get().getString("faction").equals(d2.get().getString("faction"))) {
                                        if (d1.get().getString("faction").equals("")) {
                                            Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                            Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                            Cooldown.tickCombatTimer(damaged.getUniqueId());
                                            Cooldown.tickCombatTimer(damager.getUniqueId());
                                            if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                                damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                                Main.getInstance().logoutTimer.remove(damaged);
                                            }
                                            if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                                damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                                Main.getInstance().homeTimer.remove(damaged);
                                            }
                                        } else {
                                            if (!damaged.equals(damager)) {
                                                e.setCancelled(true);
                                                damager.sendMessage(C.chat(Locale.get().getString("events.damage").replace("%name%", damaged.getName())));
                                            }
                                        }
                                    } else {
                                        Main.getInstance().combatTimer.put(damaged.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Main.getInstance().combatTimer.put(damager.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.combat")));
                                        Cooldown.tickCombatTimer(damaged.getUniqueId());
                                        Cooldown.tickCombatTimer(damager.getUniqueId());
                                        if (Main.getInstance().logoutTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
                                            Main.getInstance().logoutTimer.remove(damaged);
                                        }
                                        if (Main.getInstance().homeTimer.containsKey(damaged)) {
                                            damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                                            Main.getInstance().homeTimer.remove(damaged);
                                        }
                                    }
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            e.setCancelled(true);
                        }
                    } else {
                        e.setCancelled(true);
                        damager.sendMessage(C.chat(Locale.get().getString("events.attack-safezone")));
                    }
                }
            }
        }
    }

    private String getFactionInClaim(Player p) {
        Block selectedBlock = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0") && file.isConfigurationSection("claims.1")) {

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

    private Boolean isInClaim(Player p) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        Block selectedBlock = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0") && file.isConfigurationSection("claims.1")) {

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
}
