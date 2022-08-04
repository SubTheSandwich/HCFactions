package me.sub.hcfactions.Events.Player.Lunar;

import me.sub.hcfactions.Files.Staff.Staff;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;


public class LoadPlayerName implements Listener {

    public static void setPlayerNameTag(Player player, String name) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(player);
            boolean gameProfileExists = false;
            try {
                Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            try {
                Class.forName("com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            if (!gameProfileExists) {
                Field nameField = entityPlayer.getClass().getSuperclass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(entityPlayer, name);
            } else {
                Object profile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
                Field ff = profile.getClass().getDeclaredField("name");
                ff.setAccessible(true);
                ff.set(profile, name);
            }
            if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
                Collection<? extends Player> players = (Collection<? extends Player>) Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
                for (Player p : players) {
                    p.hidePlayer(player);
                    p.showPlayer(player);
                }
            } else {
                Player[] players = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null));
                for (Player p : players) {
                    p.hidePlayer(player);
                    p.showPlayer(player);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Staff.get().getBoolean("staff.enabled")) {
            if (p.hasPermission("hcfactions.staff")) {
                p.performCommand("h");
            }
        }
        setPlayerNameTag(p, C.chat("&e") + p.getName());
/*        if (Main.getInstance().getConfig().getBoolean("settings.lunar-client.use-api")) {
            if (Main.getInstance().lunarClientAPI.isRunningLunarClient(p)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player d : Bukkit.getOnlinePlayers()) {
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
                                                if (players.hasFaction() && Main.getInstance().focusedPlayer.get(players.get().getString("faction")) != null && Main.getInstance().focusedPlayer.get(players.get().getString("faction")).equals(d)) {
                                                    name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25B2" + "&r&6]"));
                                                    name.add(C.chat(Lunar.get().getString("lunar.colors.focused.color") + d.getName()));
                                                    Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                } else {
                                                    if (players.hasFaction() && Main.getInstance().focusedFaction.get(players.get().getString("faction")) != null && Main.getInstance().focusedFaction.get(players.get().getString("faction")).equals(other.get().getString("faction"))) {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.focused.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25B2" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    } else {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25B2" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    }
                                                }
                                            } else {
                                                if (players.hasFaction() && Main.getInstance().focusedPlayer.get(players.get().getString("faction")) != null && Main.getInstance().focusedPlayer.get(players.get().getString("faction")).equals(d)) {
                                                    name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25B2" + "&r&6]"));
                                                    name.add(C.chat(Lunar.get().getString("lunar.colors.focused.color") + d.getName()));
                                                    Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                } else {
                                                    if (players.hasFaction() && Main.getInstance().focusedFaction.get(players.get().getString("faction")) != null && Main.getInstance().focusedFaction.get(players.get().getString("faction")).equals(other.get().getString("faction"))) {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.focused.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25B2" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    } else {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25B2" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    }
                                                }
                                            }
                                        } else if (!other.getFaction().get().getString("startregen").equalsIgnoreCase("")) {
                                            if (!Main.getInstance().archerTag.containsKey(d.getUniqueId())) {
                                                if (players.hasFaction() && Main.getInstance().focusedPlayer.get(players.get().getString("faction")) != null && Main.getInstance().focusedPlayer.get(players.get().getString("faction")).equals(d)) {
                                                    name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&c&l\u25AA" + "&r&6]"));
                                                    name.add(C.chat(Lunar.get().getString("lunar.colors.focused.color") + d.getName()));
                                                    Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                } else {
                                                    if (players.hasFaction() && Main.getInstance().focusedFaction.get(players.get().getString("faction")) != null && Main.getInstance().focusedFaction.get(players.get().getString("faction")).equals(other.get().getString("faction"))) {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.focused.team-color") + f1.get().getString("name") + " " + msg + "&c&l\u25AA" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    } else {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&c&l\u25AA" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    }
                                                }
                                            } else {
                                                if (players.hasFaction() && Main.getInstance().focusedPlayer.get(players.get().getString("faction")) != null && Main.getInstance().focusedPlayer.get(players.get().getString("faction")).equals(d)) {
                                                    name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&c&l\u25AA" + "&r&6]"));
                                                    name.add(C.chat(Lunar.get().getString("lunar.colors.focused.color") + d.getName()));
                                                    Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                } else {
                                                    if (players.hasFaction() && Main.getInstance().focusedFaction.get(players.get().getString("faction")) != null && Main.getInstance().focusedFaction.get(players.get().getString("faction")).equals(other.get().getString("faction"))) {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.focused.team-color") + f1.get().getString("name") + " " + msg + "&c&l\u25AA" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    } else {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&c&l\u25AA" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    }
                                                }
                                            }
                                        } else {
                                            if (!Main.getInstance().archerTag.containsKey(d.getUniqueId())) {
                                                if (players.hasFaction() && Main.getInstance().focusedPlayer.get(players.get().getString("faction")) != null && Main.getInstance().focusedPlayer.get(players.get().getString("faction")).equals(d)) {
                                                    name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25C4" + "&r&6]"));
                                                    name.add(C.chat(Lunar.get().getString("lunar.colors.focused.color") + d.getName()));
                                                    Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                } else {
                                                    if (players.hasFaction() && Main.getInstance().focusedFaction.get(players.get().getString("faction")) != null && Main.getInstance().focusedFaction.get(players.get().getString("faction")).equals(other.get().getString("faction"))) {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.focused.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25C4" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    } else {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25C4" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    }
                                                }
                                            } else {
                                                if (players.hasFaction() && Main.getInstance().focusedPlayer.get(players.get().getString("faction")) != null && Main.getInstance().focusedPlayer.get(players.get().getString("faction")).equals(d)) {
                                                    name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25C4" + "&r&6]"));
                                                    name.add(C.chat(Lunar.get().getString("lunar.colors.focused.color") + d.getName()));
                                                    Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                } else {
                                                    if (players.hasFaction() && Main.getInstance().focusedFaction.get(players.get().getString("faction")) != null && Main.getInstance().focusedFaction.get(players.get().getString("faction")).equals(other.get().getString("faction"))) {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.focused.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25C4" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    } else {
                                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + f1.get().getString("name") + " " + msg + "&a&l\u25C4" + "&r&6]"));
                                                        name.add(C.chat(Lunar.get().getString("lunar.colors.mark.color") + d.getName()));
                                                        Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (!Main.getInstance().archerTag.containsKey(d.getUniqueId())) {
                                        if (players.hasFaction() && Main.getInstance().focusedPlayer.get(players.get().getString("faction")) != null && Main.getInstance().focusedPlayer.get(players.get().getString("faction")).equals(d)) {
                                            name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + "*&6]"));
                                            name.add(C.chat(Lunar.get().getString("lunar.colors.focused.color") + d.getName()));
                                            Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                        } else {
                                            name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + "*&6]"));
                                            name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + d.getName()));
                                            Main.getInstance().lunarClientAPI.overrideNametag(d, name, p);
                                        }
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
        }*/
    }
}
