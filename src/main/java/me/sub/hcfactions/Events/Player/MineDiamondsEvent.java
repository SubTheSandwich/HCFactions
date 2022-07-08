package me.sub.hcfactions.Events.Player;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MineDiamondsEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.isCancelled() && e.getBlock().getType() == Material.DIAMOND_ORE && !e.getBlock().hasMetadata("Diamond")) {
            int diamonds = 0;
            for (int x = -5; x < 5; x++) {
                for (int y = -5; y < 5; y++) {
                    for (int z = -5; z < 5; z++) {
                        Block block = e.getBlock().getLocation().add(x, y, z).getBlock();
                        if (block.getType() == Material.DIAMOND_ORE) {
                            diamonds++;
                            block.setMetadata("Diamond", new FixedMetadataValue(Main.getInstance(), true));
                        }
                    }
                }
            }
            if (diamonds != 1) {
                for (Player d : Bukkit.getOnlinePlayers()) {
                    Players players = new Players(d.getUniqueId().toString());
                    if (players.get().getBoolean("settings.foundDiamonds")) {
                        d.sendMessage(C.chat(Locale.get().getString("events.diamond-mine.multiple").replace("%player%", e.getPlayer().getName()).replace("%amount%", String.valueOf(diamonds))));
                    }
                }
            } else {
                for (Player d : Bukkit.getOnlinePlayers()) {
                    Players players = new Players(d.getUniqueId().toString());
                    if (players.get().getBoolean("settings.foundDiamonds")) {
                        d.sendMessage(C.chat(Locale.get().getString("events.diamond-mine.one").replace("%player%", e.getPlayer().getName())));
                    }
                }
            }

        }
    }
}
