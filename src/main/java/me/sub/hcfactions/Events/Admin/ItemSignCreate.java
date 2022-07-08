package me.sub.hcfactions.Events.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.ArrayList;

public class ItemSignCreate implements Listener {

    @EventHandler
    public void change(SignChangeEvent e) {
        Player p = e.getPlayer();
        if (e.getLine(0).equals(Main.getInstance().getConfig().getStringList("signs.buy").get(0))) {
            if (p.hasPermission("hcfactions.admin")) {
                ArrayList<String> lines = new ArrayList<>(Main.getInstance().getConfig().getStringList("signs.buy"));
                e.setLine(0, C.chat("&a" + Main.getInstance().getConfig().getStringList("signs.buy").get(0)));
                switch (lines.get(1)) {
                    case "%item%":
                        Material material = Material.matchMaterial(e.getLine(1));
                        if (material != null) {
                            e.setLine(1, e.getLine(1).toUpperCase());
                        } else {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-material")));
                            return;
                        }
                        break;
                    case "%amount%":
                        try {
                            int amount = Integer.parseInt(e.getLine(1));
                            if (amount > 0) {
                                e.setLine(1, e.getLine(1));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                            return;
                        }
                        break;
                    case "%cost%":
                        try {
                            double cost = Double.parseDouble(e.getLine(1));
                            if (cost > 0) {
                                e.setLine(1, e.getLine(1));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                            return;
                        }
                        break;
                    default:
                        e.setCancelled(true);
                        p.sendMessage(C.chat(Locale.get().getString("events.sign.not-valid").replace("%line%", "1")));
                        return;
                }
                switch (lines.get(2)) {
                    case "%item%":
                        Material material = Material.matchMaterial(e.getLine(2));
                        if (material != null) {
                            e.setLine(2, e.getLine(2).toUpperCase());
                        } else {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-material")));
                            return;
                        }
                        break;
                    case "%amount%":
                        try {
                            int amount = Integer.parseInt(e.getLine(2));
                            if (amount > 0) {
                                e.setLine(2, e.getLine(2));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                            return;
                        }
                        break;
                    case "%cost%":
                        try {
                            double cost = Double.parseDouble(e.getLine(2));
                            if (cost > 0) {
                                e.setLine(2, e.getLine(2));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                            return;
                        }
                        break;
                    default:
                        e.setCancelled(true);
                        p.sendMessage(C.chat(Locale.get().getString("events.sign.not-valid").replace("%line%", "2")));
                        return;
                }

                switch (lines.get(3)) {
                    case "%item%":
                        Material material = Material.matchMaterial(e.getLine(3));
                        if (material != null) {
                            e.setLine(3, e.getLine(3).toUpperCase());
                        } else {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-material")));
                            return;
                        }
                        break;
                    case "%amount%":
                        try {
                            int amount = Integer.parseInt(e.getLine(3));
                            if (amount > 0) {
                                e.setLine(3, e.getLine(3));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                            return;
                        }
                        break;
                    case "%cost%":
                        try {
                            double cost = Double.parseDouble(e.getLine(3));
                            if (cost > 0) {
                                e.setLine(3, e.getLine(3));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                            return;
                        }
                        break;
                    default:
                        e.setCancelled(true);
                        p.sendMessage(C.chat(Locale.get().getString("events.sign.not-valid").replace("%line%", "2")));
                        return;
                }
                p.sendMessage(C.chat(Locale.get().getString("events.sign.created")));
            } else {
                e.setCancelled(true);
                p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            }
        } else if (e.getLine(0).equals(Main.getInstance().getConfig().getStringList("signs.sell").get(0))) {
            if (p.hasPermission("hcfactions.admin")) {
                ArrayList<String> lines = new ArrayList<>(Main.getInstance().getConfig().getStringList("signs.sell"));
                e.setLine(0, C.chat("&c" + Main.getInstance().getConfig().getStringList("signs.sell").get(0)));
                switch (lines.get(1)) {
                    case "%item%":
                        Material material = Material.matchMaterial(e.getLine(1));
                        if (material != null) {
                            e.setLine(1, e.getLine(1).toUpperCase());
                        } else {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-material")));
                            return;
                        }
                        break;
                    case "%amount%":
                        try {
                            int amount = Integer.parseInt(e.getLine(1));
                            if (amount > 0) {
                                e.setLine(1, e.getLine(1));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                            return;
                        }
                        break;
                    case "%cost%":
                        try {
                            double cost = Double.parseDouble(e.getLine(1));
                            if (cost > 0) {
                                e.setLine(1, e.getLine(1));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                            return;
                        }
                        break;
                    default:
                        e.setCancelled(true);
                        p.sendMessage(C.chat(Locale.get().getString("events.sign.not-valid").replace("%line%", "1")));
                        return;
                }
                switch (lines.get(2)) {
                    case "%item%":
                        Material material = Material.matchMaterial(e.getLine(2));
                        if (material != null) {
                            e.setLine(2, e.getLine(2).toUpperCase());
                        } else {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-material")));
                            return;
                        }
                        break;
                    case "%amount%":
                        try {
                            int amount = Integer.parseInt(e.getLine(2));
                            if (amount > 0) {
                                e.setLine(2, e.getLine(2));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                            return;
                        }
                        break;
                    case "%cost%":
                        try {
                            double cost = Double.parseDouble(e.getLine(2));
                            if (cost > 0) {
                                e.setLine(2, e.getLine(2));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                            return;
                        }
                        break;
                    default:
                        e.setCancelled(true);
                        p.sendMessage(C.chat(Locale.get().getString("events.sign.not-valid").replace("%line%", "2")));
                        return;
                }

                switch (lines.get(3)) {
                    case "%item%":
                        Material material = Material.matchMaterial(e.getLine(3));
                        if (material != null) {
                            e.setLine(3, e.getLine(3).toUpperCase());
                        } else {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-material")));
                            return;
                        }
                        break;
                    case "%amount%":
                        try {
                            int amount = Integer.parseInt(e.getLine(3));
                            if (amount > 0) {
                                e.setLine(3, e.getLine(3));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-amount")));
                            return;
                        }
                        break;
                    case "%cost%":
                        try {
                            double cost = Double.parseDouble(e.getLine(3));
                            if (cost > 0) {
                                e.setLine(3, e.getLine(3));
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            e.setCancelled(true);
                            p.sendMessage(C.chat(Locale.get().getString("events.sign.not-price")));
                            return;
                        }
                        break;
                    default:
                        e.setCancelled(true);
                        p.sendMessage(C.chat(Locale.get().getString("events.sign.not-valid").replace("%line%", "2")));
                        return;
                }
                p.sendMessage(C.chat(Locale.get().getString("events.sign.created")));
            } else {
                e.setCancelled(true);
                p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            }
        }
    }
}
