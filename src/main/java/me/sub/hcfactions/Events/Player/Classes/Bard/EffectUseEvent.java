package me.sub.hcfactions.Events.Player.Classes.Bard;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Class.Classes;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Timer.Timer;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EffectUseEvent implements Listener {

    @EventHandler
    public void onEffect(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)) {
            if (Classes.isInBard(p)) {
                if (!Main.getInstance().effectCooldown.containsKey(p)) {
                    ArrayList<String> Notitems = new ArrayList<>(Main.getInstance().getConfig().getConfigurationSection("kits.bard.items").getKeys(false));
                    ArrayList<String> items = new ArrayList<>();
                    for (String d : Notitems) {
                        if (!Main.getInstance().getConfig().getBoolean("kits.bard.items." + d + ".hold")) {
                            items.add(d);
                        }
                    }

                    String config = "";

                    for (String str : items) {
                        if (Main.getInstance().getConfig().getString("kits.bard.items." + str + ".item").equals(p.getItemInHand().getType().toString())) {
                            config = "kits.bard.items." + str;
                            PotionEffect effect = new PotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")), Main.getInstance().getConfig().getInt(config + ".potionDuration"), Main.getInstance().getConfig().getInt(config + ".potionAmplifier"));
                            Players players = new Players(p.getUniqueId().toString());
                            if (Main.getInstance().bardEnergy.get(p) >= Main.getInstance().getConfig().getInt(config + ".energy")) {
                                if (p.getItemInHand().getAmount() != 1) {
                                    p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                                } else {
                                    p.getInventory().setItemInHand(new ItemStack(Material.AIR));
                                }
                                if (!Main.getInstance().getConfig().getBoolean(config + ".otherFaction")) {
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
                                                if (p.getLocation().distance(d.getLocation()) <= Main.getInstance().getConfig().getDouble("kits.bard.effect-range")) {
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
                                } else {
                                    for (Entity entity : p.getNearbyEntities(Main.getInstance().getConfig().getDouble("kits.bard.effect-range"), Main.getInstance().getConfig().getDouble("kits.bard.effect-range") , Main.getInstance().getConfig().getDouble("kits.bard.effect-range"))) {
                                        if (entity instanceof Player) {
                                            Player target = (Player) entity;
                                            Players targets = new Players(target.getUniqueId().toString());
                                            if (!targets.getFaction().equals(players.getFaction())) {
                                                if (target.hasPotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")))) {
                                                    int amplifier = 0;
                                                    for (PotionEffect ef : target.getActivePotionEffects()) {
                                                        if (ef.getType().equals(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")))) {
                                                            amplifier = ef.getAmplifier();
                                                        }
                                                    }
                                                    if (Main.getInstance().getConfig().getInt(config + ".potionAmplifier") > amplifier) {
                                                        target.removePotionEffect(PotionEffectType.getByName(Main.getInstance().getConfig().getString(config + ".potionEffect")));
                                                    }
                                                }
                                                target.addPotionEffect(effect);
                                            }
                                        }
                                    }
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("bard.not-enough")));
                                return;
                            }
                        }
                    }

                    if (!config.equals("")) {
                        double bardEnergy = Main.getInstance().bardEnergy.get(p);
                        bardEnergy = bardEnergy - Main.getInstance().getConfig().getInt(config + ".energy");
                        String effect = Locale.get().getString("bard.used-effect");
                        effect = effect.replace("%effect%", Main.getInstance().getConfig().getString(config + ".displayName"));
                        effect = effect.replace("%energy%", String.valueOf(Main.getInstance().getConfig().getInt(config + ".energy")));
                        effect = effect.replace("%remaining-energy%", String.valueOf(bardEnergy));
                        Main.getInstance().bardEnergy.put(p, bardEnergy);
                        p.sendMessage(C.chat(effect));
                        Timer.setEffectTimer(p, new BigDecimal(Main.getInstance().getConfig().getInt("kits.bard.effect-cooldown")));
                        Timer.tickEffectTimer(p);
                    }

                } else {
                    p.sendMessage(C.chat(Locale.get().getString("bard.cannot-use").replace("%time%", String.valueOf(Main.getInstance().effectCooldown.get(p)))));
                }
            }
        }
    }
}
