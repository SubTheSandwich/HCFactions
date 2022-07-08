package me.sub.hcfactions.Events.Player.Items;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigDecimal;

public class EnderpearlEvent implements Listener {

    @EventHandler
    public void onPearl(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (p.getItemInHand().getType().equals(Material.ENDER_PEARL)) {
                if (!Main.getInstance().enderpearlCooldown.containsKey(p.getUniqueId())) {
                    if (p.getGameMode() != GameMode.CREATIVE) {
                        double time = Main.getInstance().getConfig().getDouble("settings.timers.enderpearl");
                        BigDecimal extendedTime = new BigDecimal(time);
                        Main.getInstance().enderpearlCooldown.put(p.getUniqueId(), extendedTime);
                        Cooldown.tickEnderpearlCooldown(p.getUniqueId());
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("enderpearl.cooldown").replace("%time%", String.valueOf(Main.getInstance().enderpearlCooldown.get(p.getUniqueId()).doubleValue()))));
                    e.setCancelled(true);
                }
            }
        }
    }
}
