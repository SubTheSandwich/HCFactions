package me.sub.hcfactions.Events.Logger;

import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatLoggerCreate implements Listener {

    @EventHandler
    public void onLog(PlayerQuitEvent e) {
        int time = Main.getInstance().getConfig().getInt("settings.combat-logger-time") * 20;
        Player p = e.getPlayer();
        Players players = new Players(p.getUniqueId().toString());
        Main.getInstance().logoutContents.put(p.getUniqueId(), p.getInventory().getContents());
        Main.getInstance().logoutArmorContents.put(p.getUniqueId(), p.getInventory().getArmorContents());
        if (Main.getInstance().pvpTimer.containsKey(p.getUniqueId())) {
            players.get().set("savedTimers.pvpTimer", Main.getInstance().pvpTimer.get(p.getUniqueId()));
            players.save();
        }
        if (Main.getInstance().combatTimer.containsKey(p.getUniqueId())) {
            Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
            v.setCanPickupItems(false);
            v.setHealth(20);
            v.setCustomName(C.chat("&e" + p.getName()));
            v.setCustomNameVisible(true);
            v.getEquipment().setArmorContents(p.getInventory().getArmorContents());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline()) {
                        v.remove();
                    }
                }
            }.runTaskLater(Main.getInstance(), time);
        }
    }
}
