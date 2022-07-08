package me.sub.hcfactions.Events.Staff;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Staff.Staff;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Random;

public class StaffEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().staff.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().staff.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLoss(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Main.getInstance().staff.contains(p)) {
                e.setCancelled(true);
                e.setFoodLevel(20);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (e.getRightClicked() instanceof Player) {
            Player rightClicked = (Player) e.getRightClicked();
            if (Main.getInstance().staff.contains(p)) {
                String identifier = null;
                for (String item : Staff.get().getConfigurationSection("staff.items").getKeys(false)) {
                    String name = Staff.get().getString("staff.items." + item + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")));
                    if (C.chat(name).equals(p.getItemInHand().getItemMeta().getDisplayName()) && p.getItemInHand().getType().toString().equals(Staff.get().getString("staff.items." + item + ".item"))) {
                        identifier = Staff.get().getString("staff.items." + item + ".action");
                    }
                }

                if (identifier != null) {
                    switch (identifier) {
                        case "UNHANDLED":
                            break;
                        case "INSPECTOR":
                            p.performCommand("invsee " + rightClicked.getName());
                            break;
                        case "FREEZE":
                            p.performCommand("ss " + rightClicked.getName());
                            break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMove(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getType().equals(InventoryType.PLAYER)) {
            if (Main.getInstance().staff.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (Main.getInstance().staff.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().staff.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Main.getInstance().staff.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Main.getInstance().staff.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(C.chat(Main.getInstance().getConfig().getString("settings.online-staff.inventory-name")))) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                String player = C.strip(e.getCurrentItem().getItemMeta().getDisplayName());
                p.performCommand("teleport " + player);
                p.closeInventory();
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().staff.contains(p) || Main.getInstance().vanished.contains(p)) {
            String identifier = null;
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if (p.getItemInHand().getType() != Material.AIR && p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null) {
                    for (String item : Staff.get().getConfigurationSection("staff.items").getKeys(false)) {
                        String name = Staff.get().getString("staff.items." + item + ".name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary").replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary")));
                        if (C.chat(name).equals(p.getItemInHand().getItemMeta().getDisplayName()) && p.getItemInHand().getType().toString().equals(Staff.get().getString("staff.items." + item + ".item"))) {
                            identifier = Staff.get().getString("staff.items." + item + ".action");
                        }
                    }
                    if (identifier != null) {
                        switch (identifier) {
                            case "UNHANDLED":
                                break;
                            case "VANISH_OFF":
                                p.performCommand("vanish");
                                ItemStack item = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items.vanish-visible.item")), 1, (byte) Staff.get().getInt("staff.items.vanish-visible.data"));
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(C.chat(Staff.get().getString("staff.items.vanish-visible.name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary")).replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary"))));
                                ArrayList<String> lore = new ArrayList<>();
                                for (String s : Staff.get().getStringList("staff.items.vanish-visible.lore")) {
                                    lore.add(C.chat(s));
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                int slot = Staff.get().getInt("staff.items.vanish-visible.slot") - 1;
                                ItemStack vanis = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items.vanish-hidden.item")), 1, (byte) Staff.get().getInt("staff.items.vanish-hidden.data"));
                                ItemMeta vanisMeta = vanis.getItemMeta();
                                vanisMeta.setDisplayName(C.chat(Staff.get().getString("staff.items.vanish-hidden.name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary")).replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary"))));
                                ArrayList<String> vanisLore = new ArrayList<>();
                                for (String s : Staff.get().getStringList("staff.items.vanish-hidden.lore")) {
                                    vanisLore.add(C.chat(s));
                                }
                                vanisMeta.setLore(vanisLore);
                                vanis.setItemMeta(vanisMeta);
                                p.getInventory().removeItem(vanis);
                                p.getInventory().setItem(slot, item);
                                p.updateInventory();
                                break;
                            case "RANDOM_TP":
                                ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                                players.remove(p);
                                if (players.size() != 0) {
                                    Random rnd = new Random();
                                    int playe = rnd.nextInt(players.size());
                                    p.performCommand("teleport " + players.get(playe).getName());
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.staff.no-players")));
                                }
                                break;
                            case "VANISH_ON":
                                p.performCommand("vanish");
                                ItemStack ite = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items.vanish-hidden.item")), 1, (byte) Staff.get().getInt("staff.items.vanish-hidden.data"));
                                ItemMeta met = ite.getItemMeta();
                                met.setDisplayName(C.chat(Staff.get().getString("staff.items.vanish-hidden.name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary")).replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary"))));
                                ArrayList<String> lor = new ArrayList<>();
                                for (String s : Staff.get().getStringList("staff.items.vanish-hidden.lore")) {
                                    lor.add(C.chat(s));
                                }
                                met.setLore(lor);
                                ite.setItemMeta(met);

                                ItemStack vanish = new ItemStack(Material.getMaterial(Staff.get().getString("staff.items.vanish-visible.item")), 1, (byte) Staff.get().getInt("staff.items.vanish-visible.data"));
                                ItemMeta vanishMeta = vanish.getItemMeta();
                                vanishMeta.setDisplayName(C.chat(Staff.get().getString("staff.items.vanish-visible.name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary")).replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary"))));
                                ArrayList<String> vanishLore = new ArrayList<>();
                                for (String s : Staff.get().getStringList("staff.items.vanish-visible.lore")) {
                                    vanishLore.add(C.chat(s));
                                }
                                vanishMeta.setLore(vanishLore);
                                vanish.setItemMeta(vanishMeta);
                                int slo = Staff.get().getInt("staff.items.vanish-hidden.slot") - 1;
                                p.getInventory().removeItem(vanish);
                                p.getInventory().setItem(slo, ite);
                                p.updateInventory();
                                break;
                            case "ONLINE_STAFF":
                                Inventory i = Bukkit.createInventory(null, 54, C.chat(Main.getInstance().getConfig().getString("settings.online-staff.inventory-name")));

                                ArrayList<ItemStack> staff = new ArrayList<>();

                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    if (player.hasPermission("hcfactions.staff")) {
                                        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                                        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                                        skullMeta.setOwner(player.getName());
                                        skullMeta.setDisplayName(C.chat(Locale.get().getString("events.online-staff.skull-name").replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary")).replace("%player%", player.getName()).replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary"))));
                                        ArrayList<String> l = new ArrayList<>();
                                        for (String s : Locale.get().getStringList("events.online-staff.skull-lore")) {
                                            if (s.contains("%primary%")) {
                                                s = s.replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary"));
                                            }

                                            if (s.contains("%secondary%")) {
                                                s = s.replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary"));
                                            }

                                            if (s.contains("%gamemode%")) {
                                                String gamemode = player.getGameMode().toString().substring(0, 1).toUpperCase() + player.getGameMode().toString().substring(1).toLowerCase(java.util.Locale.ROOT);
                                                switch (p.getGameMode()) {
                                                    case ADVENTURE:
                                                        s = s.replace("%gamemode%", "&c" + gamemode);
                                                        break;
                                                    case CREATIVE:
                                                        s = s.replace("%gamemode%", "&a" + gamemode);
                                                        break;

                                                    case SURVIVAL:
                                                        s = s.replace("%gamemode%", "&7" + gamemode);
                                                        break;
                                                    default:
                                                        s = s.replace("%gamemode%", "&b" + gamemode);
                                                }
                                            }

                                            if (s.contains("%vanished%")) {
                                                if (Main.getInstance().vanished.contains(player)) {
                                                    s = s.replace("%vanished%", "&aEnabled");
                                                } else {
                                                    s = s.replace("%vanished%", "&cDisabled");
                                                }
                                            }

                                            if (s.contains("%staff-mode%")) {
                                                if (Main.getInstance().staff.contains(player)) {
                                                    s = s.replace("%staff-mode%", "&aEnabled");
                                                } else {
                                                    s = s.replace("%staff-mode%", "&cDisabled");
                                                }
                                            }

                                            l.add(C.chat(s));
                                        }

                                        skullMeta.setLore(l);
                                        skull.setItemMeta(skullMeta);
                                        staff.add(skull);
                                    }
                                }

                                for (ItemStack skull : staff) {
                                    i.addItem(skull);
                                }
                                p.openInventory(i);
                                break;
                        }
                    }
                }
            } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getClickedBlock().getType().equals(Material.CHEST) || e.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) {
                    e.setCancelled(true);
                    p.sendMessage(C.chat(Locale.get().getString("events.chest")));
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    Inventory i = Bukkit.createInventory(null, chest.getInventory().getSize());
                    i.setContents(chest.getInventory().getContents());
                    p.openInventory(i);
                } else if (e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {

                }
            } else if (e.getAction().equals(Action.PHYSICAL)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPick(PlayerPickupItemEvent e) {
        if (Main.getInstance().staff.contains(e.getPlayer()) || Main.getInstance().vanished.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
