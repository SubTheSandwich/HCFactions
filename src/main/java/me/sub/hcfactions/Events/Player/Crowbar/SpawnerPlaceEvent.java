package me.sub.hcfactions.Events.Player.Crowbar;

import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpawnerPlaceEvent implements Listener {

    public void setSpawner(Block block, EntityType ent) {
        BlockState blockState = block.getState();
        CreatureSpawner spawner = ((CreatureSpawner) blockState);
        spawner.setSpawnedType(ent);
        blockState.update();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!e.isCancelled()) {
            Player p = e.getPlayer();
            Block block = e.getBlockPlaced();
            if (block != null && p.getItemInHand() != null) {
                if (block.getType().equals(Material.MOB_SPAWNER) && p.getItemInHand().getType().equals(Material.MOB_SPAWNER) && p.getInventory().getItemInHand().getItemMeta().getLore() != null) {
                    if (p.getInventory().getItemInHand().getItemMeta().getLore().get(0).contains(C.chat("&eSpawner Type: &b"))) {
                        String itemName = C.chat(p.getInventory().getItemInHand().getItemMeta().getLore().get(0));
                        String spawnerType = itemName.replace(C.chat("&eSpawner Type: &b"), "").toUpperCase();
                        setSpawner(block, EntityType.valueOf(spawnerType));
                    }
                }
            }
        }
    }
}
