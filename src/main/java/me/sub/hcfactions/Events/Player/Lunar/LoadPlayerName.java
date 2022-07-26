package me.sub.hcfactions.Events.Player.Lunar;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Lunar.Lunar;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Files.Staff.Staff;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;


public class LoadPlayerName implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Staff.get().getBoolean("staff.enabled")) {
            if (p.hasPermission("hcfactions.staff")) {
                p.performCommand("h");
            }
        }
        Players players = new Players(p.getUniqueId().toString());
        if (Main.getInstance().getConfig().getBoolean("settings.lunar-client.use-api")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    ArrayList<Player> playerArrayList = new ArrayList<>(Bukkit.getOnlinePlayers());
                    for (Player d : playerArrayList) {
                        ArrayList<String> name = new ArrayList<>();
                        Players other = new Players(d.getUniqueId().toString());
                        if (!Main.getInstance().staff.contains(d)) {
                            if (!other.get().getString("faction").equals("")) {
                                Faction f1 = new Faction(other.get().getString("faction"));
                                double dtr = f1.get().getDouble("dtr");
                                String msg = "%dtr%";
                                if (dtr <= 0) {
                                    msg = msg.replace("%dtr%", "&c" + String.valueOf(f1.get().getDouble("dtr")));
                                } else if (dtr <= 1) {
                                    msg = msg.replace("%dtr%", "&e" + String.valueOf(f1.get().getDouble("dtr")));
                                } else {
                                    msg = msg.replace("%dtr%", "&a" + String.valueOf(f1.get().getDouble("dtr")));
                                }
                                if (other.get().getString("faction").equals(players.get().getString("faction"))) {
                                    if (other.getFaction().get().getBoolean("regening")) {
                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.teammate.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25B2" + "&r&6]"));
                                        name.add(C.chat(Lunar.get().getString("lunar.colors.teammate.color") + d.getName()));
                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                    } else if (!other.getFaction().get().getString("startregen").equalsIgnoreCase("")) {
                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.teammate.team-color") + f1.get().getString("name") + " " + msg + "&c&l\u25AA" + "&r&6]"));
                                        name.add(C.chat(Lunar.get().getString("lunar.colors.teammate.color") + d.getName()));
                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                    } else {
                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.teammate.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25C4" + "&r&6]"));
                                        name.add(C.chat(Lunar.get().getString("lunar.colors.teammate.color") + d.getName()));
                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                    }
                                } else {
                                    if (other.getFaction().get().getBoolean("regening")) {
                                        if (!Main.getInstance().archerTag.containsKey(d.getUniqueId())) {
                                            name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25B2" + "&r&6]"));
                                            name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                            Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                        } else {
                                            name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25B2" + "&r&6]"));
                                            name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                            Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                        }
                                    } else if (!other.getFaction().get().getString("startregen").equalsIgnoreCase("")) {
                                        if (!Main.getInstance().archerTag.containsKey(d.getUniqueId())) {
                                            name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&c&l\u25AA" + "&r&6]"));
                                            name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                            Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                        } else {
                                            name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&c&l\u25AA" + "&r&6]"));
                                            name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                            Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                        }
                                    } else {
                                        if (!Main.getInstance().archerTag.containsKey(d.getUniqueId())) {
                                            name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25C4" + "&r&6]"));
                                            name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                            Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                        } else {
                                            name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25C4" + "&r&6]"));
                                            name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                            Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                        }
                                    }
                                }
                            } else {
                                if (!Main.getInstance().archerTag.containsKey(d.getUniqueId())) {
                                    name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + "*&6]"));
                                    name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                    Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                } else {
                                    name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + "*&6]"));
                                    name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                    Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                }
                            }
                        } else {
                            name.add(C.chat("&7[Mod Mode]"));
                            if (other.get().getString("faction").equals(players.get().getString("faction")) && other.hasFaction()) {
                                name.add(C.chat(Lunar.get().getString("lunar.colors.teammate.color") + d.getName()));
                                Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                           } else {
                               name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                          }
                        }
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0, 1);
        }
    }
}
