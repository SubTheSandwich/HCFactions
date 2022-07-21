package me.sub.hcfactions.Events.Player.Mountain;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MountainSelectEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (Main.getInstance().selectingMountain.containsKey(e.getPlayer().getUniqueId())) {
            Player p = e.getPlayer();
            Action a = e.getAction();
            if (p.getItemInHand() != null && p.getItemInHand().getItemMeta().getDisplayName().equals(C.chat("&eMountain Selector Wand"))) {
                e.setCancelled(true);
                if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
                    Main.getInstance().mountainPositionTwo.put(p.getUniqueId(), e.getClickedBlock().getLocation());
                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.select.selected").replace("%position%", "2").replace("%x%", String.valueOf(e.getClickedBlock().getLocation().getBlockX())).replace("%y%", String.valueOf(e.getClickedBlock().getLocation().getBlockY())).replace("%z%", String.valueOf(e.getClickedBlock().getLocation().getBlockZ()))));
                } else if (a.equals(Action.LEFT_CLICK_BLOCK)) {
                    Main.getInstance().mountainPositionOne.put(p.getUniqueId(), e.getClickedBlock().getLocation());
                    p.sendMessage(C.chat(Locale.get().getString("command.mountain.select.selected").replace("%position%", "1").replace("%x%", String.valueOf(e.getClickedBlock().getLocation().getBlockX())).replace("%y%", String.valueOf(e.getClickedBlock().getLocation().getBlockY())).replace("%z%", String.valueOf(e.getClickedBlock().getLocation().getBlockZ()))));
                }
            }
        }
    }
}
