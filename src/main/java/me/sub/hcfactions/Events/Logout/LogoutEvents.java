package me.sub.hcfactions.Events.Logout;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LogoutEvents implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().homeTimer.containsKey(p)) {
            if (e.getTo().getX() != e.getFrom().getX() || e.getTo().getY() != e.getFrom().getY() || e.getTo().getZ() != e.getFrom().getZ()) {
                Main.getInstance().homeTimer.remove(p);
                p.sendMessage(C.chat(Locale.get().getString("command.faction.home.cancelled")));
            }
        }
        if (Main.getInstance().logoutTimer.containsKey(p)) {
            if (e.getTo().getX() != e.getFrom().getX() || e.getTo().getY() != e.getFrom().getY() || e.getTo().getZ() != e.getFrom().getZ()) {
                Main.getInstance().logoutTimer.remove(p);
                p.sendMessage(C.chat(Locale.get().getString("command.logout.cancelled")));
            }
        }
    }
}
