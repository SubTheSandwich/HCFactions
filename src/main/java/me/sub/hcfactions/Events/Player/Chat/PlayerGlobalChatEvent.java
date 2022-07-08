package me.sub.hcfactions.Events.Player.Chat;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import me.sub.hcfactions.Utils.Timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.math.BigDecimal;

public class PlayerGlobalChatEvent implements Listener {

    // Finish chat

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        String before = C.chat(e.getMessage());
        String message = C.strip(before);
        if (message.startsWith("!")) {
            if (!Main.getInstance().disabledChat) {
                if (Main.getInstance().chatSlow == 0) {
                    String format;
                    message = message.replace("!", "");
                    Players players = new Players(p.getUniqueId().toString());
                    Faction f = new Faction(players.get().getString("faction"));
                    for (Player d : Bukkit.getOnlinePlayers()) {
                        Players old = new Players(d.getUniqueId().toString());
                        if (old.get().getBoolean("settings.publicChat")) {
                            if (old.get().getString("faction").equals(players.get().getString("faction"))) {
                                if (old.get().getString("faction").equals("")) {
                                    d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", "*").replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                } else {
                                    d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.teammate")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                }
                            } else {
                                if (players.hasFaction()) {
                                    d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                } else {
                                    d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", "*").replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                }
                            }
                        }
                    }
                } else {
                    if (!p.hasPermission("hcfactions.chat.bypass") || p.hasPermission("hcfactions.staff")) {
                        if (!Main.getInstance().chatSlowPlayer.containsKey(p.getUniqueId())) {
                            message = message.replace("!", "");
                            Players players = new Players(p.getUniqueId().toString());
                            Faction f = new Faction(players.get().getString("faction"));
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                Players old = new Players(d.getUniqueId().toString());
                                if (old.get().getBoolean("settings.publicChat")) {
                                    if (old.get().getString("faction").equals(players.get().getString("faction"))) {
                                        if (old.get().getString("faction").equals("")) {
                                            d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.none")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                        } else {
                                            d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.teammate")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                        }
                                    } else {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    }
                                }
                            }
                            Main.getInstance().chatSlowPlayer.put(p.getUniqueId(), new BigDecimal(Main.getInstance().chatSlow));
                            Timer.tickChatSlow(p.getUniqueId());
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("events.cant-chat").replace("%time%", String.valueOf(Cooldown.round(Main.getInstance().chatSlowPlayer.get(p.getUniqueId()).doubleValue(), 1)))));
                        }
                    } else {
                        message = message.replace("!", "");
                        Players players = new Players(p.getUniqueId().toString());
                        Faction f = new Faction(players.get().getString("faction"));
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players old = new Players(d.getUniqueId().toString());
                            if (old.get().getBoolean("settings.publicChat")) {
                                if (old.get().getString("faction").equals(players.get().getString("faction"))) {
                                    if (old.get().getString("faction").equals("")) {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.none")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    } else {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.teammate")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    }
                                } else {
                                    d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                }
                            }
                        }
                    }
                }
            } else {
                if (p.hasPermission("hcfactions.chat.bypass") || p.hasPermission("hcfactions.staff")) {
                    message = message.replace("!", "");
                    Players players = new Players(p.getUniqueId().toString());
                    Faction f = new Faction(players.get().getString("faction"));
                    for (Player d : Bukkit.getOnlinePlayers()) {
                        Players old = new Players(d.getUniqueId().toString());
                        if (old.get().getBoolean("settings.publicChat")) {
                            if (old.get().getString("faction").equals(players.get().getString("faction"))) {
                                if (old.get().getString("faction").equals("")) {
                                    d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.none")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                } else {
                                    d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.teammate")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                }
                            } else {
                                d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                            }
                        }
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("events.chat-disabled")));
                }
            }
        } else if (message.startsWith("@")) {

        } else {
            if (Main.getInstance().staffChat.contains(p)) {
                for (Player d : Bukkit.getOnlinePlayers()) {
                    if (d.hasPermission("hcfactions.staff")) {
                        d.sendMessage(C.chat(Locale.get().getString("command.staffchat.message").replace("%name%", p.getName()).replace("%message%", message)));
                    }
                }
            } else if (Main.getInstance().factionChat.contains(p)) {

            } else if (Main.getInstance().allyChat.contains(p)) {

            } else {
                if (!Main.getInstance().disabledChat) {
                    if (Main.getInstance().chatSlow == 0) {
                        message = message.replace("!", "");
                        Players players = new Players(p.getUniqueId().toString());
                        Faction f = new Faction(players.get().getString("faction"));
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players old = new Players(d.getUniqueId().toString());
                            if (old.get().getBoolean("settings.publicChat")) {
                                if (old.get().getString("faction").equals(players.get().getString("faction"))) {
                                    if (old.get().getString("faction").equals("")) {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", "*").replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    } else {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.teammate")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    }
                                } else {
                                    if (players.hasFaction()) {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    } else {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", "*").replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    }
                                }
                            }
                        }
                    } else {
                        if (!Main.getInstance().chatSlowPlayer.containsKey(p.getUniqueId())) {
                            message = message.replace("!", "");
                            Players players = new Players(p.getUniqueId().toString());
                            Faction f = new Faction(players.get().getString("faction"));
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                Players old = new Players(d.getUniqueId().toString());
                                if (old.get().getBoolean("settings.publicChat")) {
                                    if (old.get().getString("faction").equals(players.get().getString("faction"))) {
                                        if (old.get().getString("faction").equals("")) {
                                            d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", "*").replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                        } else {
                                            d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.teammate")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                        }
                                    } else {
                                        if (players.hasFaction()) {
                                            d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                        } else {
                                            d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", "*").replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                        }
                                    }
                                }
                            }
                            if (!p.hasPermission("hcfactions.chat.bypass") || !p.hasPermission("hcfactions.staff")) {
                                Main.getInstance().chatSlowPlayer.put(p.getUniqueId(), new BigDecimal(Main.getInstance().chatSlow));
                                Timer.tickChatSlow(p.getUniqueId());
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("events.cant-chat").replace("%time%", String.valueOf(Main.getInstance().chatSlowPlayer.get(p.getUniqueId()).doubleValue()))));
                        }
                    }
                } else {
                    if (p.hasPermission("hcfactions.chat.bypass") || p.hasPermission("hcfactions.staff")) {
                        message = message.replace("!", "");
                        Players players = new Players(p.getUniqueId().toString());
                        Faction f = new Faction(players.get().getString("faction"));
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players old = new Players(d.getUniqueId().toString());
                            if (old.get().getBoolean("settings.publicChat")) {
                                if (old.get().getString("faction").equals(players.get().getString("faction"))) {
                                    if (old.get().getString("faction").equals("")) {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", "*").replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    } else {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.teammate")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    }
                                } else {
                                    if (players.hasFaction()) {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", f.get().getString("name")).replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    } else {
                                        d.sendMessage(C.chat(Locale.get().getString("chat.format.public.faction").replace("%color%", Locale.get().getString("chat.format.public.colors.enemy")).replace("%name%", "*").replace("%display%", p.getDisplayName()).replace("%message%", message).replace("%prefix%", Main.getInstance().getChat().getPlayerPrefix(p)).replace("%suffix%", Main.getInstance().getChat().getPlayerSuffix(p))));
                                    }
                                }
                            }
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("events.chat-disabled")));
                    }
                }
            }
        }

    }
}
