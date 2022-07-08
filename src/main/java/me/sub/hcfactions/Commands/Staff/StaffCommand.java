package me.sub.hcfactions.Commands.Staff;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Staff.Staff;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StaffCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.staff") || p.hasPermission("hcfactions.staff")) {
                if (Staff.get().getBoolean("staff.enabled")) {
                    if (args.length == 0) {
                        if (!Main.getInstance().staff.contains(p)) {
                            Main.getInstance().staff.add(p);
                            p.sendMessage(C.chat(Locale.get().getString("command.staff.enabled")));
                            Main.getInstance().savedInventoryArmorStaff.put(p, p.getInventory().getArmorContents());
                            Main.getInstance().savedInventoryContentsStaff.put(p, p.getInventory().getContents());
                            Main.getInstance().savedGameModeStaff.put(p, p.getGameMode());
                            Main.getInstance().savedHealthStaff.put(p, p.getHealth());
                            Main.getInstance().savedHungerStaff.put(p, p.getFoodLevel());
                            p.setGameMode(GameMode.CREATIVE);
                            p.setHealth(20);
                            p.setFoodLevel(20);
                            p.getInventory().clear();
                            p.getInventory().setHelmet(null);
                            p.getInventory().setChestplate(null);
                            p.getInventory().setLeggings(null);
                            p.getInventory().setBoots(null);
                            for (String s : Staff.get().getConfigurationSection("staff.items").getKeys(false)) {
                                if (s.equals("vanish-hidden")) {
                                    if (Staff.get().getString("staff.items." + s + ".permission").equals("")) {
                                        if (Main.getInstance().vanished.contains(p)) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    } else {
                                        if (p.hasPermission(Staff.get().getString("staff.items." + s + ".permission"))) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    }
                                } else if (s.equals("vanish-visible")) {
                                    if (Staff.get().getString("staff.items." + s + ".permission").equals("")) {
                                        if (!Main.getInstance().vanished.contains(p)) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    } else {
                                        if (p.hasPermission(Staff.get().getString("staff.items." + s + ".permission"))) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    }
                                } else {
                                    if (Staff.get().getString("staff.items." + s + ".permission").equals("")) {
                                        ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                        ItemMeta meta = item.getItemMeta();
                                        meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                        ArrayList<String> lore = new ArrayList<>();
                                        for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                            lore.add(C.chat(str));
                                        }
                                        meta.setLore(lore);
                                        item.setItemMeta(meta);
                                        int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                        p.getInventory().setItem(slot, item);
                                    } else {
                                        if (p.hasPermission(Staff.get().getString("staff.items." + s + ".permission"))) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    }
                                }
                            }
                        } else {
                            Main.getInstance().staff.remove(p);
                            p.sendMessage(C.chat(Locale.get().getString("command.staff.disabled")));
                            p.getInventory().clear();
                            p.getInventory().setArmorContents(null);
                            p.getInventory().setContents(Main.getInstance().savedInventoryContentsStaff.get(p));
                            p.getInventory().setArmorContents(Main.getInstance().savedInventoryArmorStaff.get(p));
                            p.setHealth(Main.getInstance().savedHealthStaff.get(p));
                            p.setFoodLevel(Main.getInstance().savedHungerStaff.get(p));
                            Main.getInstance().savedInventoryContentsStaff.remove(p);
                            Main.getInstance().savedInventoryArmorStaff.remove(p);
                            Main.getInstance().savedHungerStaff.remove(p);
                            Main.getInstance().savedHealthStaff.remove(p);
                            p.setGameMode(Main.getInstance().savedGameModeStaff.get(p));
                            Main.getInstance().savedGameModeStaff.remove(p);
                        }
                    } else if (args.length == 1) {
                        if (p.hasPermission("hcfactions.command.staff.others")) {
                            Player player = Bukkit.getPlayer(args[0]);
                            if (player != null) {
                                if (!Main.getInstance().staff.contains(player)) {
                                    Main.getInstance().staff.add(player);
                                    player.sendMessage(C.chat(Locale.get().getString("command.staff.enabled")));
                                    p.sendMessage(C.chat(Locale.get().getString("command.staff.staff-other-enabled").replace("%player%", player.getName())));
                                    Main.getInstance().savedInventoryArmorStaff.put(player, player.getInventory().getArmorContents());
                                    Main.getInstance().savedInventoryContentsStaff.put(player, player.getInventory().getContents());
                                    Main.getInstance().savedGameModeStaff.put(player, player.getGameMode());
                                    Main.getInstance().savedHealthStaff.put(player, player.getHealth());
                                    Main.getInstance().savedHungerStaff.put(player, player.getFoodLevel());
                                    player.setGameMode(GameMode.CREATIVE);
                                    player.setHealth(20);
                                    player.setFoodLevel(20);
                                    player.getInventory().clear();
                                    player.getInventory().setHelmet(null);
                                    player.getInventory().setChestplate(null);
                                    player.getInventory().setLeggings(null);
                                    player.getInventory().setBoots(null);
                                    for (String s : Staff.get().getConfigurationSection("staff.items").getKeys(false)) {
                                        if (s.equals("vanish-hidden")) {
                                            if (Staff.get().getString("staff.items." + s + ".permission").equals("")) {
                                                if (Main.getInstance().vanished.contains(player)) {
                                                    ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                                    ItemMeta meta = item.getItemMeta();
                                                    meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                                    ArrayList<String> lore = new ArrayList<>();
                                                    for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                        lore.add(C.chat(str));
                                                    }
                                                    meta.setLore(lore);
                                                    item.setItemMeta(meta);
                                                    int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                                    player.getInventory().setItem(slot, item);
                                                }
                                            } else {
                                                if (player.hasPermission(Staff.get().getString("staff.items." + s + ".permission"))) {
                                                    ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                                    ItemMeta meta = item.getItemMeta();
                                                    meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                                    ArrayList<String> lore = new ArrayList<>();
                                                    for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                        lore.add(C.chat(str));
                                                    }
                                                    meta.setLore(lore);
                                                    item.setItemMeta(meta);
                                                    int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                                    player.getInventory().setItem(slot, item);
                                                }
                                            }
                                        } else if (s.equals("vanish-visible")) {
                                            if (Staff.get().getString("staff.items." + s + ".permission").equals("")) {
                                                if (!Main.getInstance().vanished.contains(player)) {
                                                    ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                                    ItemMeta meta = item.getItemMeta();
                                                    meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                                    ArrayList<String> lore = new ArrayList<>();
                                                    for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                        lore.add(C.chat(str));
                                                    }
                                                    meta.setLore(lore);
                                                    item.setItemMeta(meta);
                                                    int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                                    player.getInventory().setItem(slot, item);
                                                }
                                            } else {
                                                if (p.hasPermission(Staff.get().getString("staff.items." + s + ".permission"))) {
                                                    ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                                    ItemMeta meta = item.getItemMeta();
                                                    meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                                    ArrayList<String> lore = new ArrayList<>();
                                                    for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                        lore.add(C.chat(str));
                                                    }
                                                    meta.setLore(lore);
                                                    item.setItemMeta(meta);
                                                    int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                                    player.getInventory().setItem(slot, item);
                                                }
                                            }
                                        } else {
                                            if (Staff.get().getString("staff.items." + s + ".permission").equals("")) {
                                                ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                                ItemMeta meta = item.getItemMeta();
                                                meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                                ArrayList<String> lore = new ArrayList<>();
                                                for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                    lore.add(C.chat(str));
                                                }
                                                meta.setLore(lore);
                                                item.setItemMeta(meta);
                                                int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                                player.getInventory().setItem(slot, item);
                                            } else {
                                                if (player.hasPermission(Staff.get().getString("staff.items." + s + ".permission"))) {
                                                    ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                                    ItemMeta meta = item.getItemMeta();
                                                    meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                                    ArrayList<String> lore = new ArrayList<>();
                                                    for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                        lore.add(C.chat(str));
                                                    }
                                                    meta.setLore(lore);
                                                    item.setItemMeta(meta);
                                                    int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                                    player.getInventory().setItem(slot, item);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Main.getInstance().staff.remove(player);
                                    player.sendMessage(C.chat(Locale.get().getString("command.staff.disabled")));
                                    p.sendMessage(C.chat(Locale.get().getString("command.staff.staff-other-disabled").replace("%player%", player.getName())));
                                    player.getInventory().clear();
                                    player.getInventory().setArmorContents(null);
                                    player.getInventory().setContents(Main.getInstance().savedInventoryContentsStaff.get(player));
                                    player.getInventory().setArmorContents(Main.getInstance().savedInventoryArmorStaff.get(player));
                                    player.setHealth(Main.getInstance().savedHealthStaff.get(player));
                                    player.setFoodLevel(Main.getInstance().savedHungerStaff.get(player));
                                    Main.getInstance().savedInventoryContentsStaff.remove(player);
                                    Main.getInstance().savedInventoryArmorStaff.remove(player);
                                    Main.getInstance().savedHungerStaff.remove(player);
                                    Main.getInstance().savedHealthStaff.remove(player);
                                    player.setGameMode(Main.getInstance().savedGameModeStaff.get(player));
                                    Main.getInstance().savedGameModeStaff.remove(player);
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.staff.invalid-player")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                        }
                    } else {
                        if (!Main.getInstance().staff.contains(p)) {
                            Main.getInstance().staff.add(p);
                            p.sendMessage(C.chat(Locale.get().getString("command.staff.enabled")));
                            Main.getInstance().savedInventoryArmorStaff.put(p, p.getInventory().getArmorContents());
                            Main.getInstance().savedInventoryContentsStaff.put(p, p.getInventory().getContents());
                            Main.getInstance().savedGameModeStaff.put(p, p.getGameMode());
                            Main.getInstance().savedHealthStaff.put(p, p.getHealth());
                            Main.getInstance().savedHungerStaff.put(p, p.getFoodLevel());
                            p.setGameMode(GameMode.CREATIVE);
                            p.setHealth(20);
                            p.setFoodLevel(20);
                            p.getInventory().clear();
                            p.getInventory().setHelmet(null);
                            p.getInventory().setChestplate(null);
                            p.getInventory().setLeggings(null);
                            p.getInventory().setBoots(null);
                            for (String s : Staff.get().getConfigurationSection("staff.items").getKeys(false)) {
                                if (s.equals("vanish-hidden")) {
                                    if (Staff.get().getString("staff.items." + s + ".permission").equals("")) {
                                        if (Main.getInstance().vanished.contains(p)) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    } else {
                                        if (p.hasPermission(Staff.get().getString("staff.items." + s + ".permission"))) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    }
                                } else if (s.equals("vanish-visible")) {
                                    if (Staff.get().getString("staff.items." + s + ".permission").equals("")) {
                                        if (!Main.getInstance().vanished.contains(p)) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    } else {
                                        if (p.hasPermission(Staff.get().getString("staff.items." + s + ".permission"))) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    }
                                } else {
                                    if (Staff.get().getString("staff.items." + s + ".permission").equals("")) {
                                        ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                        ItemMeta meta = item.getItemMeta();
                                        meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                        ArrayList<String> lore = new ArrayList<>();
                                        for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                            lore.add(C.chat(str));
                                        }
                                        meta.setLore(lore);
                                        item.setItemMeta(meta);
                                        int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                        p.getInventory().setItem(slot, item);
                                    } else {
                                        if (p.hasPermission(Staff.get().getString("staff.items." + s + ".permission"))) {
                                            ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items." + s + ".item")), 1, (byte) Staff.get().getInt("staff.items." + s + ".data"));
                                            ItemMeta meta = item.getItemMeta();
                                            meta.setDisplayName(C.chat(Staff.get().getString("staff.items." + s + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")))));
                                            ArrayList<String> lore = new ArrayList<>();
                                            for (String str : Staff.get().getStringList("staff.items." + s + ".lore")) {
                                                lore.add(C.chat(str));
                                            }
                                            meta.setLore(lore);
                                            item.setItemMeta(meta);
                                            int slot = Staff.get().getInt("staff.items." + s + ".slot") - 1;
                                            p.getInventory().setItem(slot, item);
                                        }
                                    }
                                }
                            }
                        } else {
                            Main.getInstance().staff.remove(p);
                            p.sendMessage(C.chat(Locale.get().getString("command.staff.disabled")));
                            p.getInventory().clear();
                            p.getInventory().setArmorContents(null);
                            p.getInventory().setContents(Main.getInstance().savedInventoryContentsStaff.get(p));
                            p.getInventory().setArmorContents(Main.getInstance().savedInventoryArmorStaff.get(p));
                            p.setHealth(Main.getInstance().savedHealthStaff.get(p));
                            p.setFoodLevel(Main.getInstance().savedHungerStaff.get(p));
                            Main.getInstance().savedInventoryContentsStaff.remove(p);
                            Main.getInstance().savedInventoryArmorStaff.remove(p);
                            Main.getInstance().savedHungerStaff.remove(p);
                            Main.getInstance().savedHealthStaff.remove(p);
                            p.setGameMode(Main.getInstance().savedGameModeStaff.get(p));
                            Main.getInstance().savedGameModeStaff.remove(p);
                        }
                    }
                }
            } else {
                p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
