package me.sub.hcfactions.Events.Scoreboard;

import me.sub.hcfactions.Utils.Scoreboard.ScoreHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RemoveScoreboard implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        ScoreHelper.removeScore(e.getPlayer());
    }
}
