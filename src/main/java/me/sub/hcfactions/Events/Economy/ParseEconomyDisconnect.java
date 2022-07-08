package me.sub.hcfactions.Events.Economy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ParseEconomyDisconnect implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
       // Player p = e.getPlayer();
       // String uuid = p.getUniqueId().toString();
        //Players players = new Players(uuid);
        //players.get().set("balance", Main.getEconomy().getBalance(p));
       // players.save();
    }
}
