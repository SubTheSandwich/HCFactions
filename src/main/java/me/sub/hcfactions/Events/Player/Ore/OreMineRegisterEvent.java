package me.sub.hcfactions.Events.Player.Ore;

import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class OreMineRegisterEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Players players = new Players(p.getUniqueId().toString());
        if (!e.isCancelled() && e.getBlock().getType() == Material.DIAMOND_ORE && !e.getBlock().hasMetadata("Diamonds")) {
            Block block = e.getBlock();
            block.setMetadata("Diamonds", new FixedMetadataValue(Main.getInstance(), true));
            players.get().set("ores.diamonds", players.get().getInt("ores.diamonds") + 1);
            players.save();
        } else if (!e.isCancelled() && e.getBlock().getType() == Material.IRON_ORE && !e.getBlock().hasMetadata("Iron")) {
            Block block = e.getBlock();
            block.setMetadata("Iron", new FixedMetadataValue(Main.getInstance(), true));
            players.get().set("ores.iron", players.get().getInt("ores.iron") + 1);
            players.save();
        } else if (!e.isCancelled() && e.getBlock().getType() == Material.GOLD_ORE && !e.getBlock().hasMetadata("Gold")) {
            Block block = e.getBlock();
            block.setMetadata("Gold", new FixedMetadataValue(Main.getInstance(), true));
            players.get().set("ores.gold", players.get().getInt("ores.gold") + 1);
            players.save();
        } else if (!e.isCancelled() && e.getBlock().getType() == Material.COAL_ORE && !e.getBlock().hasMetadata("Coal")) {
            Block block = e.getBlock();
            block.setMetadata("Coal", new FixedMetadataValue(Main.getInstance(), true));
            players.get().set("ores.coal", players.get().getInt("ores.coal") + 1);
            players.save();
        } else if (!e.isCancelled() && e.getBlock().getType() == Material.LAPIS_ORE && !e.getBlock().hasMetadata("Lapis")) {
            Block block = e.getBlock();
            block.setMetadata("Lapis", new FixedMetadataValue(Main.getInstance(), true));
            players.get().set("ores.lapis", players.get().getInt("ores.lapis") + 1);
            players.save();
        } else if (!e.isCancelled() && e.getBlock().getType() == Material.REDSTONE_ORE && !e.getBlock().hasMetadata("Redstone")) {
            Block block = e.getBlock();
            block.setMetadata("Redstone", new FixedMetadataValue(Main.getInstance(), true));
            players.get().set("ores.redstone", players.get().getInt("ores.redstone") + 1);
            players.save();
        } else if (!e.isCancelled() && e.getBlock().getType() == Material.EMERALD_ORE && !e.getBlock().hasMetadata("Emerald")) {
            Block block = e.getBlock();
            block.setMetadata("Emerald", new FixedMetadataValue(Main.getInstance(), true));
            players.get().set("ores.emeralds", players.get().getInt("ores.emeralds") + 1);
            players.save();
        } else if (!e.isCancelled() && e.getBlock().getType() == Material.QUARTZ_ORE && !e.getBlock().hasMetadata("Quartz")) {
            Block block = e.getBlock();
            block.setMetadata("Quartz", new FixedMetadataValue(Main.getInstance(), true));
            players.get().set("ores.quartz", players.get().getInt("ores.quartz") + 1);
            players.save();
        }
    }
}
