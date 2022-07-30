package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            settingsInventory(p);
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }


    private void settingsInventory(Player p) {
        Players players = new Players(p.getUniqueId().toString());
        Inventory inventory = Bukkit.createInventory(null, 27, C.chat(Main.getInstance().getConfig().getString("settings.settings.inventory-name")));

        ItemStack deathMessages = new ItemStack(Material.matchMaterial(Main.getInstance().getConfig().getString("settings.settings.items.death-messages.item")), 1, (byte) Main.getInstance().getConfig().getInt("settings.settings.items.death-messages.byte"));
        ItemMeta deathMeta = deathMessages.getItemMeta();
        deathMeta.setDisplayName(C.chat(Main.getInstance().getConfig().getString("settings.settings.items.death-messages.name").replace("%status%", (players.get().getBoolean("settings.deathMessages")) ? Main.getInstance().getConfig().getString("settings.settings.enabled") : Main.getInstance().getConfig().getString("settings.settings.disabled"))));
        deathMessages.setItemMeta(deathMeta);

        ItemStack scoreboard = new ItemStack(Material.matchMaterial(Main.getInstance().getConfig().getString("settings.settings.items.scoreboard.item")), 1, (byte) Main.getInstance().getConfig().getInt("settings.settings.items.showScoreboard.byte"));
        ItemMeta scoreboardMeta = scoreboard.getItemMeta();
        scoreboardMeta.setDisplayName(C.chat(Main.getInstance().getConfig().getString("settings.settings.items.scoreboard.name").replace("%status%", (players.get().getBoolean("settings.showScoreboard")) ? Main.getInstance().getConfig().getString("settings.settings.enabled") : Main.getInstance().getConfig().getString("settings.settings.disabled"))));
        scoreboard.setItemMeta(scoreboardMeta);

        ItemStack diamonds = new ItemStack(Material.matchMaterial(Main.getInstance().getConfig().getString("settings.settings.items.foundDiamonds.item")), 1, (byte) Main.getInstance().getConfig().getInt("settings.settings.items.foundDiamonds.byte"));
        ItemMeta diamondsMeta = diamonds.getItemMeta();
        diamondsMeta.setDisplayName(C.chat(Main.getInstance().getConfig().getString("settings.settings.items.foundDiamonds.name").replace("%status%", (players.get().getBoolean("settings.foundDiamonds")) ? Main.getInstance().getConfig().getString("settings.settings.enabled") : Main.getInstance().getConfig().getString("settings.settings.disabled"))));
        diamonds.setItemMeta(diamondsMeta);

        ItemStack chat = new ItemStack(Material.matchMaterial(Main.getInstance().getConfig().getString("settings.settings.items.publicChat.item")), 1, (byte) Main.getInstance().getConfig().getInt("settings.settings.items.publicChat.byte"));
        ItemMeta chatMeta = chat.getItemMeta();
        chatMeta.setDisplayName(C.chat(Main.getInstance().getConfig().getString("settings.settings.items.publicChat.name").replace("%status%", (players.get().getBoolean("settings.publicChat")) ? Main.getInstance().getConfig().getString("settings.settings.enabled") : Main.getInstance().getConfig().getString("settings.settings.disabled"))));
        chat.setItemMeta(chatMeta);

        ItemStack other = new ItemStack(Material.matchMaterial(Main.getInstance().getConfig().getString("settings.settings.items.other.item")), 1, (byte) Main.getInstance().getConfig().getInt("settings.settings.items.other.byte"));
        ItemMeta otherMeta = other.getItemMeta();
        otherMeta.setDisplayName(C.chat(Main.getInstance().getConfig().getString("settings.settings.items.other.name")));
        other.setItemMeta(otherMeta);

        for (int i = 0; i < 27; i++) {
            if (i == Main.getInstance().getConfig().getInt("settings.settings.items.death-messages.slot")) {
                inventory.setItem(i, deathMessages);
            } else if (i == Main.getInstance().getConfig().getInt("settings.settings.items.scoreboard.slot")) {
                inventory.setItem(i, scoreboard);
            } else if (i == Main.getInstance().getConfig().getInt("settings.settings.items.foundDiamonds.slot")) {
                inventory.setItem(i, diamonds);
            } else if (i == Main.getInstance().getConfig().getInt("settings.settings.items.publicChat.slot")) {
                inventory.setItem(i, chat);
            } else {
                inventory.setItem(i, other);
            }
        }


        p.openInventory(inventory);
    }
}
