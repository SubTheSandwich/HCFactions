package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class ProfileCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                p.sendMessage(C.chat(Locale.get().getString("command.profile.usage")));
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("view")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    Players players = new Players(player.getUniqueId().toString());
                    if (players.exists()) {
                        Inventory inventory = Bukkit.createInventory(null, 54, C.chat("&eProfile Viewer"));
                        if (players.get().getString("color") == null) {
                            players.get().set("color", "WHITE");
                            players.save();
                        }
                        ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) C.convertIntegerCode(players.get().getString("color")));
                        ItemMeta borderMeta = border.getItemMeta();
                        borderMeta.setDisplayName(" ");
                        border.setItemMeta(borderMeta);
                        for (int i = 0; i < 54; i++) {
                             if (i < 10 || i == 17 || i == 18 || i == 26 || i == 27 || i == 35 || i == 36 || i >= 44) {
                                inventory.setItem(i, border);
                            }
                        }

                        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                        skullMeta.setOwner(player.getName());
                        skullMeta.setDisplayName(C.chat("&b" + player.getName() + "&e's Profile"));
                        playerHead.setItemMeta(skullMeta);
                        inventory.setItem(22, playerHead);

                        ItemStack kills = new ItemStack(Material.DIAMOND_SWORD);
                        ItemMeta killsMeta = kills.getItemMeta();
                        killsMeta.setDisplayName(C.chat("&eKills: &c" + players.get().getInt("kills")));
                        kills.setItemMeta(killsMeta);
                        inventory.setItem(30, kills);

                        ItemStack deaths = new ItemStack(Material.DIAMOND_AXE);
                        ItemMeta deathsMeta = deaths.getItemMeta();
                        deathsMeta.setDisplayName(C.chat("&eDeaths: &c" + players.get().getInt("deaths")));
                        deaths.setItemMeta(deathsMeta);
                        inventory.setItem(32, deaths);

                        ItemStack ores = new ItemStack(Material.STONE);
                        ItemMeta oresMeta = ores.getItemMeta();
                        ArrayList<String> oresLore = new ArrayList<>();
                        oresMeta.setDisplayName(C.chat("&eOres Mines: "));
                        oresLore.add(C.chat(" &bDiamonds&7: &f" + players.get().getInt("ores.diamonds")));
                        oresLore.add(C.chat(" &eGold&7: &f" + players.get().getInt("ores.gold")));
                        oresLore.add(C.chat(" &fIron&7: &f" + players.get().getInt("ores.iron")));
                        oresLore.add(C.chat(" &8Coal&7: &f" + players.get().getInt("ores.coal")));
                        oresLore.add(C.chat(" &9Lapis&7: &f" + players.get().getInt("ores.lapis")));
                        oresLore.add(C.chat(" &cRedstone&7: &f" + players.get().getInt("ores.redstone")));
                        oresLore.add(C.chat(" &aEmeralds&7: &f" + players.get().getInt("ores.emeralds")));
                        oresLore.add(C.chat(" &fQuartz&7: &f" + players.get().getInt("ores.quartz")));
                        oresMeta.setLore(oresLore);
                        ores.setItemMeta(oresMeta);
                        inventory.setItem(31, ores);
                        p.openInventory(inventory);
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.profile.usage")));
                    }
                } else if (args[0].equalsIgnoreCase("setcolor")) {
                    if (C.isValidColor(args[1])) {
                        Players players = new Players(p.getUniqueId().toString());
                        players.get().set("color", args[1].toUpperCase());
                        players.save();
                        p.sendMessage(C.chat(Locale.get().getString("command.profile.colored").replace("%color-name%", args[1].toUpperCase()).replace("%color%", C.convertColorCode(args[1]))));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.profile.usage")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.profile.usage")));
                }
            } else if (args.length == 0) {
                Players players = new Players(((OfflinePlayer) p).getUniqueId().toString());
                if (players.exists()) {
                    Inventory inventory = Bukkit.createInventory(null, 54, C.chat("&eProfile Viewer"));
                    if (players.get().getString("color") == null) {
                        players.get().set("color", "WHITE");
                        players.save();
                    }
                    ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) C.convertIntegerCode(players.get().getString("color")));
                    ItemMeta borderMeta = border.getItemMeta();
                    borderMeta.setDisplayName(" ");
                    border.setItemMeta(borderMeta);
                    for (int i = 0; i < 54; i++) {
                        if (i < 10 || i == 17 || i == 18 || i == 26 || i == 27 || i == 35 || i == 36 || i >= 44) {
                            inventory.setItem(i, border);
                        }
                    }

                    ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                    SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                    skullMeta.setOwner(((OfflinePlayer) p).getName());
                    skullMeta.setDisplayName(C.chat("&b" + ((OfflinePlayer) p).getName() + "&e's Profile"));
                    playerHead.setItemMeta(skullMeta);
                    inventory.setItem(22, playerHead);

                    ItemStack kills = new ItemStack(Material.DIAMOND_SWORD);
                    ItemMeta killsMeta = kills.getItemMeta();
                    killsMeta.setDisplayName(C.chat("&eKills: &c" + players.get().getInt("kills")));
                    kills.setItemMeta(killsMeta);
                    inventory.setItem(30, kills);

                    ItemStack deaths = new ItemStack(Material.DIAMOND_AXE);
                    ItemMeta deathsMeta = deaths.getItemMeta();
                    deathsMeta.setDisplayName(C.chat("&eDeaths: &c" + players.get().getInt("deaths")));
                    deaths.setItemMeta(deathsMeta);
                    inventory.setItem(32, deaths);

                    ItemStack ores = new ItemStack(Material.STONE);
                    ItemMeta oresMeta = ores.getItemMeta();
                    ArrayList<String> oresLore = new ArrayList<>();
                    oresMeta.setDisplayName(C.chat("&eOres Mines: "));
                    oresLore.add(C.chat(" &bDiamonds&7: &f" + players.get().getInt("ores.diamonds")));
                    oresLore.add(C.chat(" &eGold&7: &f" + players.get().getInt("ores.gold")));
                    oresLore.add(C.chat(" &fIron&7: &f" + players.get().getInt("ores.iron")));
                    oresLore.add(C.chat(" &8Coal&7: &f" + players.get().getInt("ores.coal")));
                    oresLore.add(C.chat(" &9Lapis&7: &f" + players.get().getInt("ores.lapis")));
                    oresLore.add(C.chat(" &cRedstone&7: &f" + players.get().getInt("ores.redstone")));
                    oresLore.add(C.chat(" &aEmeralds&7: &f" + players.get().getInt("ores.emeralds")));
                    oresLore.add(C.chat(" &fQuartz&7: &f" + players.get().getInt("ores.quartz")));
                    oresMeta.setLore(oresLore);
                    ores.setItemMeta(oresMeta);
                    inventory.setItem(31, ores);
                    p.openInventory(inventory);
                }
            } else {
                p.sendMessage(C.chat(Locale.get().getString("command.profile.usage")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
