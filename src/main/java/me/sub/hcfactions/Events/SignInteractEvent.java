package me.sub.hcfactions.Events;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Sign.CustomSign;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SignInteractEvent implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBuy(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                CustomSign sign = new CustomSign((Sign) e.getClickedBlock().getState());
                Players players = new Players(p.getUniqueId().toString());
                Material item = Material.DIRT;
                int amount = 0;
                double cost = 0;
                if (sign.getLine(0).equals(C.chat("&a") + Main.getInstance().getConfig().getStringList("signs.buy").get(0))) {
                    ArrayList<String> lines = new ArrayList<>(Main.getInstance().getConfig().getStringList("signs.buy"));
                    switch (lines.get(1)) {
                        case "%item%":
                            item = Material.matchMaterial(sign.getLine(1));
                            break;
                        case "%amount%":
                            amount = Integer.parseInt(sign.getLine(1));
                            break;
                        case "%cost%":
                            cost = Double.parseDouble(sign.getLine(1));
                            break;
                    }
                    switch (lines.get(2)) {
                        case "%item%":
                            item = Material.matchMaterial(sign.getLine(2));
                            break;
                        case "%amount%":
                            amount = Integer.parseInt(sign.getLine(2));
                            break;
                        case "%cost%":
                            cost = Double.parseDouble(sign.getLine(2));
                            break;
                    }
                    switch (lines.get(3)) {
                        case "%item%":
                            item = Material.matchMaterial(sign.getLine(3));
                            break;
                        case "%amount%":
                            amount = Integer.parseInt(sign.getLine(3));
                            break;
                        case "%cost%":
                            cost = Double.parseDouble(sign.getLine(3));
                            break;
                    }
                    if (players.get().getDouble("balance") >= cost) {
                        if (p.getInventory().firstEmpty() != -1) {
                            ItemStack boughtItem = new ItemStack(item, amount);
                            p.getInventory().addItem(boughtItem);
                            p.updateInventory();
                            players.get().set("balance", players.get().getDouble("balance") - cost);
                            players.save();
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.bought-item").replace("%balance%", String.valueOf(players.get().getDouble("balance"))).replace("%item%", item.name())));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.inventory-full")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("events.sign.cant-buy")));
                    }
                } else if (sign.getLine(0).equals(C.chat("&c") + Main.getInstance().getConfig().getStringList("signs.sell").get(0))) {
                    ArrayList<String> lines = new ArrayList<>(Main.getInstance().getConfig().getStringList("signs.buy"));
                    switch (lines.get(1)) {
                        case "%item%":
                            item = Material.matchMaterial(sign.getLine(1));
                            break;
                        case "%amount%":
                            amount = Integer.parseInt(sign.getLine(1));
                            break;
                        case "%cost%":
                            cost = Double.parseDouble(sign.getLine(1));
                            break;
                    }
                    switch (lines.get(2)) {
                        case "%item%":
                            item = Material.matchMaterial(sign.getLine(2));
                            break;
                        case "%amount%":
                            amount = Integer.parseInt(sign.getLine(2));
                            break;
                        case "%cost%":
                            cost = Double.parseDouble(sign.getLine(2));
                            break;
                    }
                    switch (lines.get(3)) {
                        case "%item%":
                            item = Material.matchMaterial(sign.getLine(3));
                            break;
                        case "%amount%":
                            amount = Integer.parseInt(sign.getLine(3));
                            break;
                        case "%cost%":
                            cost = Double.parseDouble(sign.getLine(3));
                            break;
                    }

                    double soldfor;

                    if (p.getInventory().first(item) != -1) {
                        ItemStack first = p.getInventory().getItem(p.getInventory().first(item));
                        if (first.getAmount() > amount) {
                            first.setAmount(first.getAmount() - amount);
                            p.getInventory().setItem(p.getInventory().first(item), first);
                            p.updateInventory();
                            players.get().set("balance", players.get().getDouble("balance") + cost);
                            players.save();
                            soldfor = cost;

                        } else if (first.getAmount() == amount) {
                            p.getInventory().remove(first);
                            p.updateInventory();
                            players.get().set("balance", players.get().getDouble("balance") + cost);
                            players.save();
                            soldfor = cost;
                        } else {
                            p.getInventory().remove(first);
                            p.updateInventory();
                            double costPerItem = cost / amount;
                            amount = first.getAmount();
                            double costOfItem = costPerItem * amount;
                            soldfor = costOfItem;
                            players.get().set("balance", players.get().getDouble("balance") + costOfItem);
                            players.save();
                        }

                        p.sendMessage(C.chat(Locale.get().getString("events.sign.sold-item").replace("%money-sold-for%", String.valueOf(soldfor)).replace("%amount%", String.valueOf(amount)).replace("%item%", item.name()).replace("%balance%", String.valueOf(players.get().getDouble("balance")))));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("events.sign.cant-sell")));
                    }
                }
            }
        }
    }
}
