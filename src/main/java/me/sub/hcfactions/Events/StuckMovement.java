package me.sub.hcfactions.Events;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class StuckMovement implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
            if (Main.getInstance().stuckLocation.containsKey(p.getUniqueId())) {
                if ((Main.getInstance().stuckLocation.get(p.getUniqueId()).distance(e.getTo()) + 1) > 5) {
                    Main.getInstance().stuckLocation.remove(p.getUniqueId());
                    Main.getInstance().stuckTimer.remove(p.getUniqueId());
                    p.sendMessage(C.chat(Locale.get().getString("command.faction.stuck.cancelled")));
                }
            }
        }
    }
}
