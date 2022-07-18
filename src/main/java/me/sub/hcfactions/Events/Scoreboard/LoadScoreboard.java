package me.sub.hcfactions.Events.Scoreboard;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorderCreateNew;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Class.Classes;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import me.sub.hcfactions.Utils.Scoreboard.ScoreHelper;
import me.sub.hcfactions.Utils.Timer.Timer;
import me.sub.hcfactions.Utils.Timer.Timers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class LoadScoreboard implements Listener {

    @EventHandler
    public void onLoad(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        Players players = new Players(id.toString());
        if (!players.exists()) {
            players.setup();
            players.get().set("uuid", id.toString());
            players.get().set("kills", 0);
            players.get().set("deaths", 0);
            players.get().set("faction", "");
            players.get().set("deathBanned", false);
            players.get().set("bannedTill", 0);
            players.get().set("balance", Main.getInstance().getConfig().getInt("welcome.starting-balance"));
            players.get().set("settings.deathMessages", true);
            players.get().set("settings.foundDiamonds", true);
            players.get().set("settings.publicChat", true);
            players.get().set("settings.showScoreboard", true);
            Timer.setPvPTimer(p.getUniqueId(), Main.getInstance().getConfig().getInt("settings.timers.pvp"));
            Timer.tickPvPTimer(p.getUniqueId());
            players.save();
            if (Main.getInstance().getConfig().getBoolean("welcome.starting-balance-message")) {
                p.sendMessage(C.chat(Locale.get().getString("welcome.starting-balance-message")));
            }
        }
        if (!Main.getInstance().sotwStarted) {
            if (players.get().getInt("savedTimers.pvpTimer") != 0) {
                Timer.setPvPTimer(p.getUniqueId(), players.get().getInt("savedTimers.pvpTimer"));
                Timer.tickPvPTimer(p.getUniqueId());
            }
        }

       // Economy economy = Main.getEconomy();
        //EconomyResponse r = economy.depositPlayer(p, players.get().getDouble("balance"));
        //if (!r.transactionSuccess()) {
           // System.out.println(C.chat("&cAn internal error occured when parsing the economy of " + p.getName()));
       // }


        ScoreHelper helper = ScoreHelper.createScore(p);

        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<String> lines = new ArrayList<>();
                for (String s : Main.getInstance().getConfig().getStringList("scoreboard.lines")) {
                    if (s.contains("%custom-timers%")) {
                        s = s.replace("%custom-timers%", "");
                        if (Main.getInstance().customTimers.size() > 0) {
                            ArrayList<String> customTimers = new ArrayList<>();
                            for (String timer : Main.getInstance().customTimers.keySet()) {
                                String textFormat = Main.getInstance().getConfig().getString("scoreboard.custom-timer-format");
                                int time = Main.getInstance().customTimers.get(timer);
                                Calendar calender = Calendar.getInstance();
                                calender.clear();
                                calender.add(Calendar.SECOND, time);
                                String format = "HH:mm:ss";
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                                String timee = simpleDateFormat.format(calender.getTimeInMillis());
                                textFormat = textFormat.replace("%name%", timer);
                                textFormat = textFormat.replace("%color%", C.convertColorCode(me.sub.hcfactions.Files.Timers.Timers.get().getString("timers." + timer + ".color")));
                                textFormat = textFormat.replace("%time%", timee);
                                customTimers.add(textFormat);
                            }
                            lines.addAll(customTimers);
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%has_active_koth%>")) {
                        if (Main.getInstance().kothTimer.keySet().size() != 0) {
                            s = s.replace("<display=%has_active_koth%>", "");
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            int time = Main.getInstance().kothTimer.get(keys.get(0));
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%time%", String.valueOf(timee));
                            s = s.replace("%koth-name%", keys.get(0));
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_pvptimer_timer%>")) {
                        if (Main.getInstance().pvpTimer.containsKey(p.getUniqueId())) {
                            s = s.replace("<display=%player_has_pvptimer_timer%>", "");
                            int time = Main.getInstance().pvpTimer.get(p.getUniqueId());
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%player_pvptimer_timer%", String.valueOf(timee));
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_apple_timer%>")) {
                        if (Main.getInstance().appleTimer.containsKey(p.getUniqueId())) {
                            s = s.replace("<display=%player_has_apple_timer%>", "");
                            double time = Cooldown.round(Main.getInstance().appleTimer.get(p.getUniqueId()).doubleValue(), 1);
                            String newFormat = String.valueOf(time) + "s";
                            s = s.replace("%player_apple_timer%", newFormat);
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_pearl_timer%>")) {
                        if (Main.getInstance().enderpearlCooldown.containsKey(p.getUniqueId())) {
                            double time = Cooldown.round(Main.getInstance().enderpearlCooldown.get(p.getUniqueId()).doubleValue(), 1);
                            String newFormat = String.valueOf(time) + "s";
                            s = s.replace("<display=%player_has_pearl_timer%>", "");
                            s = s.replace("%player_pearl_timer%", newFormat);
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_combat_timer%>")) {
                        if (Main.getInstance().combatTimer.containsKey(p.getUniqueId())) {
                            double time = Cooldown.round(Main.getInstance().combatTimer.get(p.getUniqueId()).doubleValue(), 1);
                            String newFormat = String.valueOf(time) + "s";
                            s = s.replace("<display=%player_has_combat_timer%>", "");
                            s = s.replace("%player_combat_timer%", newFormat);
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_logout_timer%>")) {
                        if (Main.getInstance().logoutTimer.containsKey(p)) {
                            double time = Cooldown.round(Main.getInstance().logoutTimer.get(p).doubleValue(), 1);
                            String newFormat = String.valueOf(time) + "s";
                            s = s.replace("<display=%player_has_logout_timer%>", "");
                            s = s.replace("%player_logout_timer%", newFormat);
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%server_has_sotw_timer%>")) {
                        if (Main.getInstance().sotwStarted) {
                            s = s.replace("<display=%server_has_sotw_timer%>", "");
                            int time = Timer.getTimer(Timers.SOTW);
                            Calendar cl = Calendar.getInstance();
                            cl.clear();
                            cl.add(Calendar.SECOND, time);
                            String format;
                            if (time > 3600) {
                                format = "HH:mm:ss";
                            } else if (time > 60 && time < 3599) {
                                format = "mm:ss";
                            } else {
                                format = "ss";
                            }

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(cl.getTimeInMillis());
                            s = s.replace("%server_sotw_timer%", String.valueOf(timee));


                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_home_timer%>")) {
                        if (Main.getInstance().homeTimer.containsKey(p)) {
                            double time = Cooldown.round(Main.getInstance().homeTimer.get(p).doubleValue(), 1);
                            String newFormat = String.valueOf(time) + "s";
                            s = s.replace("<display=%player_has_home_timer%>", "");
                            s = s.replace("%player_home_timer%", newFormat);
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%in_staff_mode%>")) {
                        if (Main.getInstance().staff.contains(p) || Main.getInstance().vanished.contains(p)) {
                            s = s.replace("<display=%in_staff_mode%>", "");
                            if (s.contains("%gamemode%")) {
                                String gamemode = p.getGameMode().toString().substring(0, 1).toUpperCase() + p.getGameMode().toString().substring(1).toLowerCase(java.util.Locale.ROOT);
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

                            if (s.contains("%bypass%")) {
                                if (Main.getInstance().bypass.contains(p.getUniqueId())) {
                                    s = s.replace("%bypass%", "&aEnabled");
                                } else {
                                    s = s.replace("%bypass%", "&cDisabled");
                                }
                            }

                            if (s.contains("%vanished%")) {
                                if (Main.getInstance().vanished.contains(p)) {
                                    s = s.replace("%vanished%", "&aEnabled");
                                } else {
                                    s = s.replace("%vanished%", "&cDisabled");
                                }
                            }

                            if (s.contains("%online%")) {
                                s = s.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                            }
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%has_active_class%>")) {
                        if (Main.getInstance().hcfClass.get(p) != null) {
                            if (Classes.isInBard(p)) {
                                s = s.replace("<display=%has_active_class%>", "");
                                s = s.replace("%active_class%", "Bard");
                            } else if (Classes.isInArcher(p)) {
                                if (Main.getInstance().archerSpeedCooldown.containsKey(p.getUniqueId()) || Main.getInstance().archerJumpCooldown.containsKey(p.getUniqueId())) {
                                    s = s.replace("<display=%has_active_class%>", "");
                                    s = s.replace("%active_class%", "Archer");
                                } else {
                                    continue;
                                }
                            } else if (Classes.isInRogue(p)) {
                                if (Main.getInstance().rogueSpeedCooldown.containsKey(p.getUniqueId()) || Main.getInstance().rogueJumpCooldown.containsKey(p.getUniqueId()) || Main.getInstance().rogueBackstabCooldown.containsKey(p.getUniqueId())) {
                                    s = s.replace("<display=%has_active_class%>", "");
                                    s = s.replace("%active_class%", "Rogue");
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_bard-effect_timer%>")) {
                        if (Main.getInstance().hcfClass.get(p) != null && Classes.isInBard(p) && Main.getInstance().effectCooldown.containsKey(p)) {
                            s = s.replace("<display=%player_has_bard-effect_timer%>", "");
                            double time = Cooldown.round(Main.getInstance().effectCooldown.get(p).doubleValue(), 1);
                            String newFormat = String.valueOf(time) + "s";
                            s = s.replace("%player_bard-effect_timer%", newFormat);
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%is_bard%>")) {
                        if (Main.getInstance().hcfClass.get(p) != null && Classes.isInBard(p)) {
                            s = s.replace("<display=%is_bard%>", "");
                            s = s.replace("%bard_energy%", String.valueOf(Main.getInstance().bardEnergy.get(p)));
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_speed-effect_timer%>")) {
                        if (Main.getInstance().hcfClass.get(p) != null && Classes.isInArcher(p) && Main.getInstance().archerSpeedCooldown.get(p.getUniqueId()) != null) {
                            s = s.replace("<display=%player_has_speed-effect_timer%>", "");
                            s = s.replace("%player_speed-effect_timer%", String.valueOf(Main.getInstance().archerSpeedCooldown.get(p.getUniqueId()) + "s"));
                        } else if (Main.getInstance().hcfClass.get(p) != null && Classes.isInRogue(p) && Main.getInstance().rogueSpeedCooldown.get(p.getUniqueId()) != null) {
                            s = s.replace("<display=%player_has_speed-effect_timer%>", "");
                            s = s.replace("%player_speed-effect_timer%", String.valueOf(Main.getInstance().rogueSpeedCooldown.get(p.getUniqueId()) + "s"));
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_jump-effect_timer%>")) {
                        if (Main.getInstance().hcfClass.get(p) != null && Classes.isInArcher(p) && Main.getInstance().archerJumpCooldown.get(p.getUniqueId()) != null) {
                            s = s.replace("<display=%player_has_jump-effect_timer%>", "");
                            s = s.replace("%player_jump-effect_timer%", String.valueOf(Main.getInstance().archerJumpCooldown.get(p.getUniqueId()) + "s"));
                        } else if (Main.getInstance().hcfClass.get(p) != null && Classes.isInRogue(p) && Main.getInstance().rogueJumpCooldown.get(p.getUniqueId()) != null) {
                            s = s.replace("<display=%player_has_jump-effect_timer%>", "");
                            s = s.replace("%player_jump-effect_timer%", String.valueOf(Main.getInstance().rogueJumpCooldown.get(p.getUniqueId()) + "s"));
                        } else {
                            continue;
                        }
                    }

                    if (s.contains("<display=%player_has_backstab_timer%>")) {
                        if (Main.getInstance().hcfClass.get(p) != null && Classes.isInRogue(p) && Main.getInstance().rogueBackstabCooldown.get(p.getUniqueId()) != null) {
                            s = s.replace("<display=%player_has_backstab_timer%>", "");
                            s = s.replace("%player_backstab_timer%", String.valueOf(Main.getInstance().rogueBackstabCooldown.get(p.getUniqueId())));
                        } else {
                            continue;
                        }
                    }

                    if (players.get().getBoolean("settings.showScoreboard")) {
                        if (!s.equals("")) {
                            lines.add(s);
                        }
                    }
                }

                if (lines.size() > 2) {
                    helper.setTitle(Main.getInstance().getConfig().getString("scoreboard.title"));
                    helper.setSlotsFromList(lines);
                } else {
                    lines.clear();
                    helper.setTitle(Main.getInstance().getConfig().getString("scoreboard.title"));
                    helper.setSlotsFromList(lines);
                }

            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }
}
