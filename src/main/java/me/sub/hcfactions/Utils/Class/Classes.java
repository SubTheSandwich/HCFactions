package me.sub.hcfactions.Utils.Class;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Classes {
    public static Boolean hasActiveClass(Player p) {
        if (isInArcher(p)) {
            return true;
        } else if (isInBard(p)) {
            return true;
        } else if (isInRogue(p)) {
            return true;
        } else if (isInMiner(p)) {
            return true;
        } else {
            return false;
        }
    }

    public static void activateEffects(Player p, String className) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.getInstance().hcfClass.containsKey(p)) {
                    if (p.isOnline()) {
                        switch (className) {
                            case "ARCHER":
                                if (Main.getInstance().archerSpeedCooldown.get(p) == null) {
                                    PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 100000, 2);
                                    p.addPotionEffect(speed);
                                }
                                if (Main.getInstance().archerSpeedCooldown.get(p) != null && Main.getInstance().archerSpeedCooldown.get(p) <= 30) {
                                    PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 100000, 2);
                                    p.addPotionEffect(speed);
                                }
                                PotionEffect r = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000, 1);
                                p.addPotionEffect(r);
                                break;
                            case "BARD":
                                PotionEffect s = new PotionEffect(PotionEffectType.SPEED, 100000, 1);
                                PotionEffect f = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000, 0);
                                p.addPotionEffect(s);
                                p.addPotionEffect(f);

                                ArrayList<String> Notitems = new ArrayList<>(Main.getInstance().getConfig().getConfigurationSection("kits.bard.items").getKeys(false));
                                ArrayList<String> items = new ArrayList<>();
                                for (String d : Notitems) {
                                    if (Main.getInstance().getConfig().getBoolean("kits.bard.items." + d + ".hold")) {
                                        items.add(d);
                                    }
                                }

                                for (String str : items) {
                                    if (Main.getInstance().getConfig().getString("kits.bard.items." + str + ".item").equals(p.getItemInHand().getType().toString())) {
                                        String config = "kits.bard.items." + str;
                                        PotionEffect effect = new PotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")), Main.getInstance().getConfig().getInt(config + ".potionDuration"), Main.getInstance().getConfig().getInt(config + ".potionAmplifier"));
                                        Players players = new Players(p.getUniqueId().toString());
                                        if (players.hasFaction()) {
                                            Faction faction = new Faction(players.get().getString("faction"));
                                            for (Player d : faction.getAllOnlinePlayers()) {
                                                if (d.getUniqueId().equals(p.getUniqueId())) {
                                                    if (Main.getInstance().getConfig().getBoolean(config + ".applyOnBard")) {
                                                        if (d.hasPotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")))) {
                                                            int amplifier = 0;
                                                            for (PotionEffect ef : d.getActivePotionEffects()) {
                                                                if (ef.getType().equals(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")))) {
                                                                    amplifier = ef.getAmplifier();
                                                                }
                                                            }
                                                            if (Main.getInstance().getConfig().getInt(config + ".potionAmplifier") > amplifier) {
                                                                d.removePotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")));
                                                            }
                                                        }
                                                        d.addPotionEffect(effect);
                                                    }
                                                } else {
                                                    if (p.getLocation().distance(d.getLocation()) <= Main.getInstance().getConfig().getInt("kits.bard.effect-range")) {
                                                        if (d.hasPotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")))) {
                                                            int amplifier = 0;
                                                            for (PotionEffect ef : d.getActivePotionEffects()) {
                                                                if (ef.getType().equals(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")))) {
                                                                    amplifier = ef.getAmplifier();
                                                                }
                                                            }
                                                            if (Main.getInstance().getConfig().getInt(config + ".potionAmplifier") > amplifier) {
                                                                d.removePotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")));
                                                            }
                                                        }
                                                        d.addPotionEffect(effect);
                                                    }
                                                }
                                            }
                                        } else {
                                            if (Main.getInstance().getConfig().getBoolean(config + ".applyOnBard")) {
                                                if (p.hasPotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")))) {
                                                    int amplifier = 0;
                                                    for (PotionEffect ef :p.getActivePotionEffects()) {
                                                        if (ef.getType().equals(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")))) {
                                                            amplifier = ef.getAmplifier();
                                                        }
                                                    }
                                                    if (Main.getInstance().getConfig().getInt(config + ".potionAmplifier") > amplifier) {
                                                        p.removePotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")));
                                                    }
                                                }
                                                p.addPotionEffect(effect);
                                            }
                                        }
                                        break;
                                    }
                                }
                                return;
                            case "ROGUE":
                                PotionEffect sp = new PotionEffect(PotionEffectType.SPEED, 100000, 2);
                                PotionEffect fir = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000, 0);
                                PotionEffect j = new PotionEffect(PotionEffectType.JUMP, 100000, 1);
                                PotionEffect r1 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000, 0);
                                p.addPotionEffect(sp);
                                p.addPotionEffect(fir);
                                p.addPotionEffect(j);
                                p.addPotionEffect(r1);
                                break;
                            case "MINER":
                                PotionEffect h = new PotionEffect(PotionEffectType.FAST_DIGGING, 100000, 1);
                                PotionEffect fi = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000, 0);
                                PotionEffect n = new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 0);
                                p.addPotionEffect(h);
                                p.addPotionEffect(fi);
                                p.addPotionEffect(n);
                                if (p.getLocation().getY() <= Main.getInstance().getConfig().getInt("kits.miner.invis-level")) {
                                    PotionEffect i = new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 0);
                                    p.addPotionEffect(i);
                                } else {
                                    p.removePotionEffect(PotionEffectType.INVISIBILITY);
                                }
                                break;
                        }
                    } else {
                        cancel();
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public static String getActiveClass(Player p) {
        if (isInArcher(p)) {
            return "ARCHER";
        } else if (isInBard(p)) {
            return "BARD";
        } else if (isInRogue(p)) {
            return "ROGUE";
        } else if (isInMiner(p)) {
            return "MINER";
        } else {
            return null;
        }
    }

    public static Boolean isInArcher(Player p) {
        if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {
            if (p.getInventory().getHelmet().getType().equals(Material.LEATHER_HELMET) && p.getInventory().getChestplate().getType().equals(Material.LEATHER_CHESTPLATE) && p.getInventory().getLeggings().getType().equals(Material.LEATHER_LEGGINGS) && p.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static Boolean isInBard(Player p) {
        if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {
            if (p.getInventory().getHelmet().getType().equals(Material.GOLD_HELMET) && p.getInventory().getChestplate().getType().equals(Material.GOLD_CHESTPLATE) && p.getInventory().getLeggings().getType().equals(Material.GOLD_LEGGINGS) && p.getInventory().getBoots().getType().equals(Material.GOLD_BOOTS)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static Boolean isInRogue(Player p) {
        if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {
            if (p.getInventory().getHelmet().getType().equals(Material.CHAINMAIL_HELMET) && p.getInventory().getChestplate().getType().equals(Material.CHAINMAIL_CHESTPLATE) && p.getInventory().getLeggings().getType().equals(Material.CHAINMAIL_LEGGINGS) && p.getInventory().getBoots().getType().equals(Material.CHAINMAIL_BOOTS)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static Boolean isInMiner(Player p) {
        if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {
            if (p.getInventory().getHelmet().getType().equals(Material.IRON_HELMET) && p.getInventory().getChestplate().getType().equals(Material.IRON_CHESTPLATE) && p.getInventory().getLeggings().getType().equals(Material.IRON_LEGGINGS) && p.getInventory().getBoots().getType().equals(Material.IRON_BOOTS)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }



}
