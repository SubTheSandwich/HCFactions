package me.sub.hcfactions.Events.Player.Classes.Archer;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Class.Classes;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArcherSpeedEffect implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (e.getPlayer().getItemInHand() != null) {
            if (p.getItemInHand().getType().equals(Material.SUGAR)) {
                if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)) {
                    if (Classes.isInArcher(p)) {
                        if (!Main.getInstance().archerSpeedCooldown.containsKey(p.getUniqueId())) {
                            ItemStack i = p.getInventory().getItemInHand();
                            if ((i.getAmount() - 1) == 0) {
                                p.getInventory().remove(p.getInventory().getItemInHand());
                                p.updateInventory();
                            } else {
                                i.setAmount(i.getAmount() - 1);
                                p.getInventory().setItemInHand(i);
                                p.updateInventory();
                            }
                            Main.getInstance().archerSpeedCooldown.put(p.getUniqueId(), 40);
                            p.removePotionEffect(PotionEffectType.SPEED);
                            PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 200, 3);
                            p.addPotionEffect(speed);
                            Cooldown.tickArcherSpeedCooldown(p.getUniqueId());
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("archer.speed-cooldown").replace("%time%", String.valueOf(Main.getInstance().archerSpeedCooldown.get(p.getUniqueId())))));
                        }
                    }
                }
            }
        }
    }
}
