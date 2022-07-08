package me.sub.hcfactions.Events.Player.Faction;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HomeEvents implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!e.isCancelled()) {
            if (e.getEntity() instanceof Player) {
                Player damaged = (Player) e.getEntity();
                if (Main.getInstance().homeTimer.containsKey(damaged)) {
                    damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                    Main.getInstance().homeTimer.remove(damaged);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {
        if (!e.isCancelled()) {
            if (e.getEntity() instanceof Player) {
                Player damaged = (Player) e.getEntity();
                if (Main.getInstance().homeTimer.containsKey(damaged)) {
                    damaged.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
                    Main.getInstance().homeTimer.remove(damaged);
                }
            }
        }
    }
}
