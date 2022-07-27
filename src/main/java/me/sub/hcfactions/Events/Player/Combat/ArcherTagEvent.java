package me.sub.hcfactions.Events.Player.Combat;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Class.Classes;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.util.UUID;

public class ArcherTagEvent implements Listener {

    private void tickArcherTag(UUID uuid) {
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

    @EventHandler
    public void archer(EntityDamageByEntityEvent e) {
        if (!e.isCancelled()) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                Player damaged = (Player) e.getEntity();
                if (Main.getInstance().archerTag.containsKey(damaged.getUniqueId())) {
                    double addedDamage = e.getDamage() * 0.25;
                    e.setDamage(e.getDamage() + addedDamage);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!e.isCancelled()) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
                Player damaged = (Player) e.getEntity();
                if (e.getDamager() instanceof Arrow) {
                    Arrow arrow = (Arrow) e.getDamager();
                    if (arrow.getShooter() instanceof Player) {
                        Player damager = (Player) arrow.getShooter();
                        if (Classes.hasActiveClass(damager) && Classes.isInArcher(damager)) {
                            if (!Classes.isInArcher(damaged)) {
                                damager.sendMessage(C.chat(Locale.get().getString("archer.tag").replace("%range%", String.valueOf(Cooldown.round(damaged.getLocation().distance(damager.getLocation()), 2))).replace("%time%", "10").replace("%hearts%", String.valueOf(e.getDamage()))));
                                if (!Main.getInstance().archerTag.containsKey(damaged.getUniqueId())) {
                                    Main.getInstance().archerTag.put(damaged.getUniqueId(), new BigDecimal(10));
                                    tickArcherTag(damaged.getUniqueId());
                                } else {
                                    Main.getInstance().archerTag.put(damaged.getUniqueId(), new BigDecimal(10));
                                }
                            } else {
                                damager.sendMessage(C.chat(Locale.get().getString("archer.cannot-tag").replace("%hearts%", String.valueOf(e.getDamage()))));
                            }
                        }
                    }
                }
            }
        }
    }
}
