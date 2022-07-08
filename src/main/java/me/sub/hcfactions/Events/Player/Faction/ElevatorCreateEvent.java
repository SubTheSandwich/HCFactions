package me.sub.hcfactions.Events.Player.Faction;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class ElevatorCreateEvent implements Listener {

    @EventHandler
    public void onCreate(SignChangeEvent e) {
        if (!e.isCancelled()) {
            Player p = e.getPlayer();
            if (e.getLine(0).equalsIgnoreCase("[Elevator]")) {
                if (e.getLine(1).equalsIgnoreCase("up") || e.getLine(1).equalsIgnoreCase("down")) {
                    e.setLine(0, C.chat("&9[Elevator]"));
                } else {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.sign.elevator.invalid-type")));
                }
            }
        }
    }
}
