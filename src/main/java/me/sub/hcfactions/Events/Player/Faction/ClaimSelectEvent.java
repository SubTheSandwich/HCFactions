package me.sub.hcfactions.Events.Player.Faction;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Faction.Claim;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClaimSelectEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (Main.getInstance().claiming.contains(p.getUniqueId())) {
            if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null) {
                if (Claim.getItemName().equals(p.getItemInHand().getItemMeta().getDisplayName()) && Claim.getItemMaterial().equals(p.getItemInHand().getType())) {
                    if (a.equals(Action.LEFT_CLICK_BLOCK)) {
                        Block block = e.getClickedBlock();
                        if (Claim.isValidSelectedBlock(block.getLocation())) {
                            Claim.claimSelectOne(p, block.getLocation());
                            e.setCancelled(true);
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.invalid-position")));
                            e.setCancelled(true);
                        }
                    } else if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
                        Block block = e.getClickedBlock();
                        if (Claim.isValidSelectedBlock(block.getLocation())) {
                            Claim.claimSelectTwo(p, block.getLocation());
                            e.setCancelled(true);
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.invalid-position")));
                            e.setCancelled(true);
                        }
                    } else if (a.equals(Action.LEFT_CLICK_AIR)) {
                        if (p.isSneaking()) {
                            Claim.executeClaim(p);
                        }
                    } else if (a.equals(Action.RIGHT_CLICK_AIR)) {
                        if (p.isSneaking()) {
                            Claim.cancelClaim(p);
                        }
                    }
                }
            }
        } else if (Main.getInstance().claimFor.containsKey(p.getUniqueId())) {
            if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null) {
                if (Claim.getItemName().equals(p.getItemInHand().getItemMeta().getDisplayName()) && Claim.getItemMaterial().equals(p.getItemInHand().getType())) {
                    if (a.equals(Action.LEFT_CLICK_BLOCK)) {
                        Block block = e.getClickedBlock();
                        Claim.claimSelectOne(p, block.getLocation());
                        e.setCancelled(true);
                    } else if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
                        Block block = e.getClickedBlock();
                        Claim.claimSelectTwo(p, block.getLocation());
                        e.setCancelled(true);
                    } else if (a.equals(Action.LEFT_CLICK_AIR)) {
                        if (p.isSneaking()) {
                            Claim.executeClaimfor(p);
                        }
                    } else if (a.equals(Action.RIGHT_CLICK_AIR)) {
                        if (p.isSneaking()) {
                            Claim.cancelClaim(p);
                        }
                    }
                }
            }
        }
    }
}
