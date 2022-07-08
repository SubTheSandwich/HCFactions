package me.sub.hcfactions.Events.Player.Classes;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Class.Classes;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Timer.Timer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class LoadClass implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Classes.hasActiveClass(p)) {
                    if (Main.getInstance().hcfClass.containsKey(p)) {
                        String name = Main.getInstance().hcfClass.get(p).substring(0, 1).toUpperCase(java.util.Locale.ROOT) + Main.getInstance().hcfClass.get(p).substring(1).toLowerCase(java.util.Locale.ROOT);
                        p.sendMessage(C.chat(Locale.get().getString("kit.disabled").replace("%name%", name)));
                        String value = Main.getInstance().hcfClass.get(p);
                        Main.getInstance().hcfClass.remove(p);
                        switch (value) {
                            case "ARCHER":
                                p.removePotionEffect(PotionEffectType.SPEED);
                                p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                                break;
                            case "ROGUE":
                                p.removePotionEffect(PotionEffectType.SPEED);
                                p.removePotionEffect(PotionEffectType.JUMP);
                                p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                                p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                                break;
                            case "MINER":
                                p.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                                p.removePotionEffect(PotionEffectType.INVISIBILITY);
                                break;
                            case "BARD":
                                p.removePotionEffect(PotionEffectType.SPEED);
                                p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                                break;
                        }
                    }
                } else {
                    if (!Main.getInstance().hcfClass.containsKey(p)) {
                        ArrayList<String> items = new ArrayList<>();
                        switch (Classes.getActiveClass(p)) {
                            case "ARCHER":
                                if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                                    p.removePotionEffect(PotionEffectType.SPEED);
                                }
                                if (p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                                    p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                                }
                                Main.getInstance().hcfClass.put(p, "ARCHER");
                                for (String s : Locale.get().getStringList("kit.enabled")) {
                                    if (s.contains("%name%")){
                                        String name = Main.getInstance().hcfClass.get(p).substring(0, 1).toUpperCase(java.util.Locale.ROOT) + Main.getInstance().hcfClass.get(p).substring(1).toLowerCase(java.util.Locale.ROOT);
                                        s = s.replace("%name%", name);
                                    }
                                    if (s.contains("%website%")) {
                                        s = s.replace("%website%", Main.getInstance().getConfig().getString("server.website"));
                                    }

                                    items.add(s);
                                }

                                for (String s : items) {
                                    p.sendMessage(C.chat(s));
                                }

                                Classes.activateEffects(p, "ARCHER");
                                break;
                            case "BARD":
                                if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                                    p.removePotionEffect(PotionEffectType.SPEED);
                                }

                                if (p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                                    p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                                }
                                Main.getInstance().hcfClass.put(p, "BARD");
                                for (String s : Locale.get().getStringList("kit.enabled")) {
                                    if (s.contains("%name%")){
                                        String name = Main.getInstance().hcfClass.get(p).substring(0, 1).toUpperCase(java.util.Locale.ROOT) + Main.getInstance().hcfClass.get(p).substring(1).toLowerCase(java.util.Locale.ROOT);
                                        s = s.replace("%name%", name);
                                    }
                                    if (s.contains("%website%")) {
                                        s = s.replace("%website%", Main.getInstance().getConfig().getString("server.website"));
                                    }

                                    items.add(s);
                                }

                                for (String s : items) {
                                    p.sendMessage(C.chat(s));
                                }

                                Classes.activateEffects(p, "BARD");

                                if (!Main.getInstance().bardEnergy.containsKey(p)) {
                                    Main.getInstance().bardEnergy.put(p, 0.0);
                                    Timer.tickEnergy(p);
                                }
                                break;
                            case "ROGUE":
                                if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                                    p.removePotionEffect(PotionEffectType.SPEED);
                                }
                                if (p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                                    p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                                }
                                if (p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                                    p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                                }
                                if (p.hasPotionEffect(PotionEffectType.JUMP)) {
                                    p.removePotionEffect(PotionEffectType.JUMP);
                                }
                                Main.getInstance().hcfClass.put(p, "ROGUE");
                                for (String s : Locale.get().getStringList("kit.enabled")) {
                                    if (s.contains("%name%")){
                                        String name = Main.getInstance().hcfClass.get(p).substring(0, 1).toUpperCase(java.util.Locale.ROOT) + Main.getInstance().hcfClass.get(p).substring(1).toLowerCase(java.util.Locale.ROOT);
                                        s = s.replace("%name%", name);
                                    }
                                    if (s.contains("%website%")) {
                                        s = s.replace("%website%", Main.getInstance().getConfig().getString("server.website"));
                                    }

                                    items.add(s);
                                }

                                for (String s : items) {
                                    p.sendMessage(C.chat(s));
                                }

                                Classes.activateEffects(p, "ROGUE");
                                break;
                            case "MINER":
                                Main.getInstance().hcfClass.put(p, "MINER");
                                for (String s : Locale.get().getStringList("kit.enabled")) {
                                    if (s.contains("%name%")){
                                        String name = Main.getInstance().hcfClass.get(p).substring(0, 1).toUpperCase(java.util.Locale.ROOT) + Main.getInstance().hcfClass.get(p).substring(1).toLowerCase(java.util.Locale.ROOT);
                                        s = s.replace("%name%", name);
                                    }
                                    if (s.contains("%website%")) {
                                        s = s.replace("%website%", Main.getInstance().getConfig().getString("server.website"));
                                    }

                                    items.add(s);
                                }

                                for (String s : items) {
                                    p.sendMessage(C.chat(s));
                                }

                                Classes.activateEffects(p, "MINER");

                                break;
                            default:
                                System.out.println("An unknown error occured when detecting a player's class.");
                        }
                    } else {
                        if (!Classes.getActiveClass(p).equals(Main.getInstance().hcfClass.get(p))) {
                            Main.getInstance().hcfClass.put(p, Classes.getActiveClass(p));
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0 , 1);
    }
}
