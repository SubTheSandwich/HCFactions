package me.sub.hcfactions.Events.Scoreboard;

import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Scoreboard.ScoreHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RemoveScoreboard implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        ScoreHelper.removeScore(p);
        Main.getInstance().claimFor.remove(p.getUniqueId());
        Main.getInstance().randomlyGeneratedMaterial.remove(p.getUniqueId());
        Main.getInstance().mappedLocations.remove(p.getUniqueId());
        Main.getInstance().stuckLocation.remove(p.getUniqueId());
        Main.getInstance().bypass.remove(p.getUniqueId());
        Main.getInstance().selectingMountain.remove(p.getUniqueId());
        Main.getInstance().mountainPositionTwo.remove(p.getUniqueId());
        Main.getInstance().mountainPositionOne.remove(p.getUniqueId());
        Main.getInstance().posClaimTwo.remove(p.getUniqueId());
        Main.getInstance().posClaimOne.remove(p.getUniqueId());
        Main.getInstance().claiming.remove(p.getUniqueId());
    }
}
