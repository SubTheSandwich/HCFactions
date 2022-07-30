package me.sub.hcfactions.Events.Player.Settings;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SettingsClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Players players = new Players(p.getUniqueId().toString());
        if (e.getView().getTitle().equals(C.chat(Main.getInstance().getConfig().getString("settings.settings.inventory-name")))) {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(C.chat(Main.getInstance().getConfig().getString("settings.settings.items.death-messages.name").replace("%status%", (players.get().getBoolean("settings.deathMessages")) ? Main.getInstance().getConfig().getString("settings.settings.enabled") : Main.getInstance().getConfig().getString("settings.settings.disabled"))))) {
                if (players.get().getBoolean("settings.deathMessages")) {
                    players.get().set("settings.deathMessages", false);
                    players.save();
                } else {
                    players.get().set("settings.deathMessages", true);
                    players.save();
                }
                p.sendMessage(C.chat(Locale.get().getString("events.settings.changed").replace("%status%", (players.get().getBoolean("settings.deathMessages")) ? Main.getInstance().getConfig().getString("settings.settings.lower-enabled") : Main.getInstance().getConfig().getString("settings.settings.lower-disabled")).replace("%type%", "death messages")));
                p.closeInventory();
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(C.chat(Main.getInstance().getConfig().getString("settings.settings.items.scoreboard.name").replace("%status%", (players.get().getBoolean("settings.showScoreboard")) ? Main.getInstance().getConfig().getString("settings.settings.enabled") : Main.getInstance().getConfig().getString("settings.settings.disabled"))))) {
                if (players.get().getBoolean("settings.showScoreboard")) {
                    players.get().set("settings.showScoreboard", false);
                    players.save();
                } else {
                    players.get().set("settings.showScoreboard", true);
                    players.save();
                }
                p.sendMessage(C.chat(Locale.get().getString("events.settings.changed").replace("%status%", (players.get().getBoolean("settings.showScoreboard")) ? Main.getInstance().getConfig().getString("settings.settings.lower-enabled") : Main.getInstance().getConfig().getString("settings.settings.lower-disabled")).replace("%type%", "scoreboard")));
                p.closeInventory();
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(C.chat(Main.getInstance().getConfig().getString("settings.settings.items.foundDiamonds.name").replace("%status%", (players.get().getBoolean("settings.foundDiamonds")) ? Main.getInstance().getConfig().getString("settings.settings.enabled") : Main.getInstance().getConfig().getString("settings.settings.disabled"))))) {
                if (players.get().getBoolean("settings.foundDiamonds")) {
                    players.get().set("settings.foundDiamonds", false);
                    players.save();
                } else {
                    players.get().set("settings.foundDiamonds", true);
                    players.save();
                }
                p.sendMessage(C.chat(Locale.get().getString("events.settings.changed").replace("%status%", (players.get().getBoolean("settings.foundDiamonds")) ? Main.getInstance().getConfig().getString("settings.settings.lower-enabled") : Main.getInstance().getConfig().getString("settings.settings.lower-disabled")).replace("%type%", "found diamonds notifications")));
                p.closeInventory();
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(C.chat(Main.getInstance().getConfig().getString("settings.settings.items.publicChat.name").replace("%status%", (players.get().getBoolean("settings.publicChat")) ? Main.getInstance().getConfig().getString("settings.settings.enabled") : Main.getInstance().getConfig().getString("settings.settings.disabled"))))) {
                if (players.get().getBoolean("settings.publicChat")) {
                    players.get().set("settings.publicChat", false);
                    players.save();
                } else {
                    players.get().set("settings.publicChat", true);
                    players.save();
                }
                p.sendMessage(C.chat(Locale.get().getString("events.settings.changed").replace("%status%", (players.get().getBoolean("settings.publicChat")) ? Main.getInstance().getConfig().getString("settings.settings.lower-enabled") : Main.getInstance().getConfig().getString("settings.settings.lower-disabled")).replace("%type%", "public chat")));
                p.closeInventory();
            }
        }
    }
}
