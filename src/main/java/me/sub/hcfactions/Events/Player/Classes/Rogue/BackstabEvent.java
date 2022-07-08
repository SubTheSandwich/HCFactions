package me.sub.hcfactions.Events.Player.Classes.Rogue;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Class.Classes;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import me.sub.hcfactions.Utils.Tracking.Behind;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BackstabEvent implements Listener {

    @EventHandler
    public void onClick(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damaged = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if (Classes.hasActiveClass(damager) && Classes.isInRogue(damager)) {
                if (damager.getItemInHand().getType().equals(Material.GOLD_SWORD)) {
                    ItemStack item = damager.getItemInHand();
                    if (Behind.playerBehindPlayer(damager, damaged)) {
                        if (!Main.getInstance().rogueBackstabCooldown.containsKey(damager.getUniqueId())) {
                            damager.sendMessage(C.chat(Locale.get().getString("rogue.backstab-succeed").replace("%player-hit%", damaged.getName())));
                            Main.getInstance().rogueBackstabCooldown.put(damager.getUniqueId(), 15);
                            Cooldown.tickBackstabCooldown(damager.getUniqueId());
                            damager.getInventory().setItemInHand(new ItemStack(Material.AIR));
                            PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 60, 2);
                            damager.removePotionEffect(PotionEffectType.SLOW);
                            damager.addPotionEffect(effect);
                            e.setDamage(5);
                            damager.updateInventory();
                        } else {
                            damager.sendMessage(C.chat(Locale.get().getString("rogue.backstab-cooldown").replace("%time%", String.valueOf(Main.getInstance().rogueBackstabCooldown.get(damager.getUniqueId())))));
                        }
                    } else {
                        damager.sendMessage(C.chat(Locale.get().getString("rogue.backstab-failed")));
                    }
                }
            }
        }
    }
}
