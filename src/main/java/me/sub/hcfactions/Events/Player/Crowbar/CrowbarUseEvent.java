package me.sub.hcfactions.Events.Player.Crowbar;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CrowbarUseEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.isCancelled()) {
            Player p = e.getPlayer();
            Action a = e.getAction();
            if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getClickedBlock().getType().equals(Material.MOB_SPAWNER) && p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType().equals(Material.DIAMOND_HOE) && p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(C.chat("&bCrowbar &7(Right Click)")) && p.getInventory().getItemInHand().getItemMeta().getLore() != null && p.getInventory().getItemInHand().getItemMeta().getLore().get(0).equals(C.chat("&eRight Click on a Monster Spawner to Collect It"))) {
                    CreatureSpawner creatureSpawner = (CreatureSpawner) e.getClickedBlock().getState();
                    String uses = C.strip(p.getInventory().getItemInHand().getItemMeta().getLore().get(1));
                    uses = uses.replaceAll("[^0-9]", "");
                    try {
                        int amountOfUses = Integer.parseInt(uses);
                        amountOfUses = amountOfUses - 1;
                        if (amountOfUses == 0) {
                            p.getInventory().setItemInHand(new ItemStack(Material.AIR));
                        } else {
                            ItemStack crowbar = new ItemStack(Material.DIAMOND_HOE);
                            ItemMeta crowbarMeta = crowbar.getItemMeta();
                            crowbarMeta.setDisplayName(C.chat("&bCrowbar &7(Right Click)"));
                            ArrayList<String> crowbarLore = new ArrayList<>();
                            crowbarLore.add(C.chat("&eRight Click on a Monster Spawner to Collect It"));
                            crowbarLore.add(C.chat("&eUses: &r" + amountOfUses));
                            crowbarMeta.setLore(crowbarLore);
                            crowbar.setItemMeta(crowbarMeta);
                            p.getInventory().setItemInHand(crowbar);
                        }

                        e.getClickedBlock().setType(Material.AIR);

                        ItemStack spawner = new ItemStack(Material.MOB_SPAWNER);
                        ItemMeta spawnerMeta = spawner.getItemMeta();
                        ArrayList<String> spawnerLore = new ArrayList<>();
                        spawnerLore.add(C.chat("&eSpawner Type: &b" + creatureSpawner.getSpawnedType().toString()));
                        spawnerMeta.setLore(spawnerLore);
                        spawner.setItemMeta(spawnerMeta);
                        p.sendMessage(C.chat(Locale.get().getString("events.crowbar.used").replace("%spawner%", creatureSpawner.getSpawnedType().toString()).replace("%charges%", String.valueOf(amountOfUses))));
                        p.getWorld().dropItemNaturally(p.getLocation(), spawner);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
            }
        }
    }
}
