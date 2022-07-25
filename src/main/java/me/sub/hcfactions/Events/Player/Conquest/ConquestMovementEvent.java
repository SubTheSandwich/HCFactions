package me.sub.hcfactions.Events.Player.Conquest;
// Line 469 of this class produces a null exception error. Possible solution is just checking if the player's UUID is a value.
import me.sub.hcfactions.Files.Conquest.Conquest;
import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ConquestMovementEvent implements Listener {

    private void tickColorTimer(UUID uuid) {
        new BukkitRunnable() {
            int seconds = 0;
            int time = 30;
            final Players players = new Players(uuid.toString());
            final Faction faction = players.getFaction();
            @Override
            public void run() {
                seconds = seconds + 1;
                if (Main.getInstance().conquestTimer.keySet().size() != 0) {
                    if (Bukkit.getPlayer(uuid) != null) {
                        Conquest conquest = new Conquest(new ArrayList<>(Main.getInstance().conquestTimer.keySet()).get(0));
                        if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).containsValue(uuid)) {
                            if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED").equals(uuid)) {
                                if (seconds == 20) {
                                    seconds = 0;
                                    time = time - 1;
                                    if (time <= 0) {
                                        time = 30;
                                        HashMap<String, Integer> colorTimer = new HashMap<>();
                                        colorTimer.put("RED", 30);
                                        colorTimer.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                        colorTimer.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                        colorTimer.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                        Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), colorTimer);
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capped").replace("%zone%", "&cRed").replace("%name%", new Players(uuid.toString()).getFaction().get().getString("name"))));
                                        }
                                        if (Main.getInstance().conquestPoints.containsKey(faction.get().getString("uuid"))) {
                                            Main.getInstance().conquestPoints.put(faction.get().getString("uuid"), Main.getInstance().conquestPoints.get(faction.get().getString("uuid")) + 1);
                                        } else {
                                            Main.getInstance().conquestPoints.put(faction.get().getString("uuid"), 1);
                                        }
                                        int points = Main.getInstance().conquestPoints.get(faction.get().getString("uuid"));
                                        if (points >= Main.getInstance().getConfig().getInt("settings.conquest-max-points")) {
                                            Main.getInstance().conquestPoints.clear();
                                            Main.getInstance().conquestTimer.clear();
                                            Main.getInstance().capturingColorFaction.clear();
                                            for (Player p : Bukkit.getOnlinePlayers()) {
                                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.captured").replace("%name%", conquest.get().getString("name")).replace("%faction%", faction.get().getString("name"))));
                                            }
                                            cancel();
                                        }
                                    } else {
                                        HashMap<String, Integer> colorTimer = new HashMap<>();
                                        colorTimer.put("RED", time);
                                        colorTimer.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                        colorTimer.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                        colorTimer.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                        Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), colorTimer);
                                        if (time == 25) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&cRed").replace("%time%", String.valueOf(time))));
                                        } else if (time == 20) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&cRed").replace("%time%", String.valueOf(time))));
                                        } else if (time == 15) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&cRed").replace("%time%", String.valueOf(time))));
                                        } else if (time == 10) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&cRed").replace("%time%", String.valueOf(time))));
                                        } else if (time == 5) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&cRed").replace("%time%", String.valueOf(time))));
                                        }
                                    }
                                }
                            } else if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN").equals(uuid)) {
                                if (seconds == 20) {
                                    seconds = 0;
                                    time = time - 1;
                                    if (time <= 0) {
                                        time = 30;
                                        HashMap<String, Integer> colorTimer = new HashMap<>();
                                        colorTimer.put("GREEN", 30);
                                        colorTimer.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                        colorTimer.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                        colorTimer.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                        Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), colorTimer);
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capped").replace("%zone%", "&aGreen").replace("%name%", new Players(uuid.toString()).getFaction().get().getString("name"))));
                                        }
                                        if (Main.getInstance().conquestPoints.containsKey(faction.get().getString("uuid"))) {
                                            Main.getInstance().conquestPoints.put(faction.get().getString("uuid"), Main.getInstance().conquestPoints.get(faction.get().getString("uuid")) + 1);
                                        } else {
                                            Main.getInstance().conquestPoints.put(faction.get().getString("uuid"), 1);
                                        }
                                        if (Main.getInstance().conquestPoints.get(faction.get().getString("uuid")) >= Main.getInstance().getConfig().getInt("settings.conquest-max-points")) {
                                            Main.getInstance().conquestPoints.clear();
                                            Main.getInstance().conquestTimer.clear();
                                            Main.getInstance().capturingColorFaction.clear();
                                            for (Player p : Bukkit.getOnlinePlayers()) {
                                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.captured").replace("%name%", conquest.get().getString("name")).replace("%faction%", faction.get().getString("name"))));
                                            }
                                            cancel();
                                        }
                                    } else {
                                        HashMap<String, Integer> colorTimer = new HashMap<>();
                                        colorTimer.put("GREEN", time);
                                        colorTimer.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                        colorTimer.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                        colorTimer.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                        Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), colorTimer);
                                        if (time == 25) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&aGreen").replace("%time%", String.valueOf(time))));
                                        } else if (time == 20) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&aGreen").replace("%time%", String.valueOf(time))));
                                        } else if (time == 15) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&aGreen").replace("%time%", String.valueOf(time))));
                                        } else if (time == 10) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&aGreen").replace("%time%", String.valueOf(time))));
                                        } else if (time == 5) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&aGreen").replace("%time%", String.valueOf(time))));
                                        }
                                    }
                                }
                            } else if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW").equals(uuid)) {
                                if (seconds == 20) {
                                    seconds = 0;
                                    time = time - 1;
                                    if (time <= 0) {
                                        time = 30;
                                        HashMap<String, Integer> colorTimer = new HashMap<>();
                                        colorTimer.put("YELLOW", 30);
                                        colorTimer.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                        colorTimer.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                        colorTimer.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                        Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), colorTimer);
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capped").replace("%zone%", "&eYellow").replace("%name%", new Players(uuid.toString()).getFaction().get().getString("name"))));
                                        }
                                        if (Main.getInstance().conquestPoints.containsKey(faction.get().getString("uuid"))) {
                                            Main.getInstance().conquestPoints.put(faction.get().getString("uuid"), Main.getInstance().conquestPoints.get(faction.get().getString("uuid")) + 1);
                                        } else {
                                            Main.getInstance().conquestPoints.put(faction.get().getString("uuid"), 1);
                                        }
                                        if (Main.getInstance().conquestPoints.get(faction.get().getString("uuid")) >= Main.getInstance().getConfig().getInt("settings.conquest-max-points")) {
                                            Main.getInstance().conquestPoints.clear();
                                            Main.getInstance().conquestTimer.clear();
                                            Main.getInstance().capturingColorFaction.clear();
                                            for (Player p : Bukkit.getOnlinePlayers()) {
                                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.captured").replace("%name%", conquest.get().getString("name")).replace("%faction%", faction.get().getString("name"))));
                                            }
                                            cancel();
                                        }
                                    } else {
                                        HashMap<String, Integer> colorTimer = new HashMap<>();
                                        colorTimer.put("YELLOW", time);
                                        colorTimer.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                        colorTimer.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                        colorTimer.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                        Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), colorTimer);
                                        if (time == 25) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&eYellow").replace("%time%", String.valueOf(time))));
                                        } else if (time == 20) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&eYellow").replace("%time%", String.valueOf(time))));
                                        } else if (time == 15) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&eYellow").replace("%time%", String.valueOf(time))));
                                        } else if (time == 10) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&eYellow").replace("%time%", String.valueOf(time))));
                                        } else if (time == 5) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&eYellow").replace("%time%", String.valueOf(time))));
                                        }
                                    }
                                }
                            } else if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE").equals(uuid)) {
                                if (seconds == 20) {
                                    seconds = 0;
                                    time = time - 1;
                                    if (time <= 0) {
                                        time = 30;
                                        HashMap<String, Integer> colorTimer = new HashMap<>();
                                        colorTimer.put("BLUE", 30);
                                        colorTimer.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                        colorTimer.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                        colorTimer.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                        Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), colorTimer);
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capped").replace("%zone%", "&9Blue").replace("%name%", new Players(uuid.toString()).getFaction().get().getString("name"))));
                                        }
                                        if (Main.getInstance().conquestPoints.containsKey(faction.get().getString("uuid"))) {
                                            Main.getInstance().conquestPoints.put(faction.get().getString("uuid"), Main.getInstance().conquestPoints.get(faction.get().getString("uuid")) + 1);
                                        } else {
                                            Main.getInstance().conquestPoints.put(faction.get().getString("uuid"), 1);
                                        }
                                        if (Main.getInstance().conquestPoints.get(faction.get().getString("uuid")) >= Main.getInstance().getConfig().getInt("settings.conquest-max-points")) {
                                            Main.getInstance().conquestPoints.clear();
                                            Main.getInstance().conquestTimer.clear();
                                            Main.getInstance().capturingColorFaction.clear();
                                            for (Player p : Bukkit.getOnlinePlayers()) {
                                                p.sendMessage(C.chat(Locale.get().getString("command.conquest.captured").replace("%name%", conquest.get().getString("name")).replace("%faction%", faction.get().getString("name"))));
                                            }
                                            cancel();
                                        }
                                    } else {
                                        HashMap<String, Integer> colorTimer = new HashMap<>();
                                        colorTimer.put("BLUE", time);
                                        colorTimer.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                        colorTimer.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                        colorTimer.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                        Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), colorTimer);
                                        if (time == 25) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&9Blue").replace("%time%", String.valueOf(time))));
                                        } else if (time == 20) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&9Blue").replace("%time%", String.valueOf(time))));
                                        } else if (time == 15) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&9Blue").replace("%time%", String.valueOf(time))));
                                        } else if (time == 10) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&9Blue").replace("%time%", String.valueOf(time))));
                                        } else if (time == 5) {
                                            Bukkit.getPlayer(uuid).sendMessage(C.chat(Locale.get().getString("command.conquest.zone-capping").replace("%zone%", "&9Blue").replace("%time%", String.valueOf(time))));
                                        }
                                    }
                                }
                            } else {
                                cancel();
                            }
                        } else {
                            cancel();
                        }
                    } else {
                        cancel();
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().conquestTimer.keySet().size() != 0) {
            Conquest conquest = new Conquest(new ArrayList<>(Main.getInstance().conquestTimer.keySet()).get(0));
            if (new Players(p.getUniqueId().toString()).hasFaction() && !Main.getInstance().pvpTimer.containsKey(p.getUniqueId())) {
                Faction faction = new Players(p.getUniqueId().toString()).getFaction();
                if (conquest.getColorCuboid("RED").contains(p.getLocation())) {
                    if (!Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED").equals(p.getUniqueId())) {
                        UUID uuid = Main.getInstance().randomGeneratedUUIDConquest;
                        if (!Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED").equals(uuid)) {
                            if (Bukkit.getPlayer(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED")) != null) {
                                Player capturingPlayer = Bukkit.getPlayer(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                                if (!conquest.getColorCuboid("RED").contains(capturingPlayer.getLocation())) {
                                    Players knocked = new Players(capturingPlayer.getUniqueId().toString());
                                    HashMap<String, UUID> play = new HashMap<>();
                                    for (Player d : Bukkit.getOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&cRed").replace("%faction%", knocked.getFaction().get().getString("name"))));
                                    }
                                    play.put("RED", p.getUniqueId());
                                    play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                                    play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                                    play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                                    Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                                    HashMap<String, Integer> time = new HashMap<>();
                                    time.put("RED", 30);
                                    time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                    time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                    time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                    Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                                    for (Player d : faction.getAllOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&cRed")));
                                    }
                                    for (Player d : Bukkit.getOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&cRed").replace("%faction%", faction.get().getString("name"))));
                                    }
                                    tickColorTimer(p.getUniqueId());
                                }
                            } else {
                                Players knocked = new Players(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED").toString());
                                HashMap<String, UUID> play = new HashMap<>();
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&cRed").replace("%faction%", knocked.getFaction().get().getString("name"))));
                                }
                                HashMap<String, Integer> time = new HashMap<>();
                                time.put("RED", 30);
                                time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                                play.put("RED", p.getUniqueId());
                                play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                                play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                                play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                                Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                                for (Player d : faction.getAllOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&cRed")));
                                }
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&cRed").replace("%faction%", faction.get().getString("name"))));
                                }
                                tickColorTimer(p.getUniqueId());
                            }
                        } else {
                            HashMap<String, UUID> play = new HashMap<>();
                            play.put("RED", p.getUniqueId());
                            play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                            play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                            play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                            HashMap<String, Integer> time = new HashMap<>();
                            time.put("RED", 30);
                            time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                            time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                            time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                            for (Player d : faction.getAllOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&cRed")));
                            }
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&cRed").replace("%faction%", faction.get().getString("name"))));
                            }
                            tickColorTimer(p.getUniqueId());
                        }
                    }
                } else if (conquest.getColorCuboid("YELLOW").contains(p.getLocation())) {
                    if (!Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW").equals(p.getUniqueId())) {
                        UUID uuid = Main.getInstance().randomGeneratedUUIDConquest;
                        if (!Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW").equals(uuid)) {
                            if (Bukkit.getPlayer(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW")) != null) {
                                Player capturingPlayer = Bukkit.getPlayer(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                                if (!conquest.getColorCuboid("YELLOW").contains(capturingPlayer.getLocation())) {
                                    Players knocked = new Players(capturingPlayer.getUniqueId().toString());
                                    HashMap<String, UUID> play = new HashMap<>();
                                    for (Player d : Bukkit.getOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&eYellow").replace("%faction%", knocked.getFaction().get().getString("name"))));
                                    }
                                    play.put("YELLOW", p.getUniqueId());
                                    play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                                    play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                                    play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                                    Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                                    HashMap<String, Integer> time = new HashMap<>();
                                    time.put("YELLOW", 30);
                                    time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                    time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                    time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                    Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                                    for (Player d : faction.getAllOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&eYellow")));
                                    }
                                    for (Player d : Bukkit.getOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&eYellow").replace("%faction%", faction.get().getString("name"))));
                                    }
                                    tickColorTimer(p.getUniqueId());
                                }
                            } else {
                                Players knocked = new Players(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW").toString());
                                HashMap<String, UUID> play = new HashMap<>();
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&eYellow").replace("%faction%", knocked.getFaction().get().getString("name"))));
                                }
                                play.put("YELLOW", p.getUniqueId());
                                play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                                play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                                play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                                Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                                HashMap<String, Integer> time = new HashMap<>();
                                time.put("YELLOW", 30);
                                time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                                for (Player d : faction.getAllOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&eYellow")));
                                }
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&eYellow").replace("%faction%", faction.get().getString("name"))));
                                }
                                tickColorTimer(p.getUniqueId());
                            }
                        } else {
                            HashMap<String, UUID> play = new HashMap<>();
                            play.put("YELLOW", p.getUniqueId());
                            play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                            play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                            play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                            HashMap<String, Integer> time = new HashMap<>();
                            time.put("YELLOW", 30);
                            time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                            time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                            time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                            Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                            for (Player d : faction.getAllOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&eYellow")));
                            }
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&eYellow").replace("%faction%", faction.get().getString("name"))));
                            }
                            tickColorTimer(p.getUniqueId());
                        }
                    }
                } else if (conquest.getColorCuboid("BLUE").contains(p.getLocation())) {
                    if (!Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE").equals(p.getUniqueId())) {
                        UUID uuid = Main.getInstance().randomGeneratedUUIDConquest;
                        if (!Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE").equals(uuid)) {
                            if (Bukkit.getPlayer(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE")) != null) {
                                Player capturingPlayer = Bukkit.getPlayer(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                                if (!conquest.getColorCuboid("BLUE").contains(capturingPlayer.getLocation())) {
                                    Players knocked = new Players(capturingPlayer.getUniqueId().toString());
                                    HashMap<String, UUID> play = new HashMap<>();
                                    for (Player d : Bukkit.getOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&9Blue").replace("%faction%", knocked.getFaction().get().getString("name"))));
                                    }
                                    play.put("BLUE", p.getUniqueId());
                                    play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                                    play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                                    play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                                    Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                                    HashMap<String, Integer> time = new HashMap<>();
                                    time.put("BLUE", 30);
                                    time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                    time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                    time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                    Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                                    for (Player d : faction.getAllOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&9Blue")));
                                    }
                                    for (Player d : Bukkit.getOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&9Blue").replace("%faction%", faction.get().getString("name"))));
                                    }
                                    tickColorTimer(p.getUniqueId());
                                }
                            } else {
                                Players knocked = new Players(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE").toString());
                                HashMap<String, UUID> play = new HashMap<>();
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&9Blue").replace("%faction%", knocked.getFaction().get().getString("name"))));
                                }
                                play.put("BLUE", p.getUniqueId());
                                play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                                play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                                play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                                HashMap<String, Integer> time = new HashMap<>();
                                time.put("BLUE", 30);
                                time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                                Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                                Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                                for (Player d : faction.getAllOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&9Blue")));
                                }
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&9Blue").replace("%faction%", faction.get().getString("name"))));
                                }
                                tickColorTimer(p.getUniqueId());
                            }
                        } else {
                            HashMap<String, UUID> play = new HashMap<>();
                            play.put("BLUE", p.getUniqueId());
                            play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                            play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                            play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                            HashMap<String, Integer> time = new HashMap<>();
                            time.put("BLUE", 30);
                            time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                            time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                            time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                            Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                            for (Player d : faction.getAllOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&9Blue")));
                            }
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&9Blue").replace("%faction%", faction.get().getString("name"))));
                            }
                            tickColorTimer(p.getUniqueId());
                        }
                    }
                } else if (conquest.getColorCuboid("GREEN").contains(p.getLocation())) {
                    if (!Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN").equals(p.getUniqueId())) {
                        UUID uuid = Main.getInstance().randomGeneratedUUIDConquest;
                        if (!Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN").equals(uuid)) {
                            if (Bukkit.getPlayer(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN")) != null) {
                                Player capturingPlayer = Bukkit.getPlayer(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                                if (!conquest.getColorCuboid("GREEN").contains(capturingPlayer.getLocation())) {
                                    Players knocked = new Players(capturingPlayer.getUniqueId().toString());
                                    HashMap<String, UUID> play = new HashMap<>();
                                    for (Player d : Bukkit.getOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&9Blue").replace("%faction%", knocked.getFaction().get().getString("name"))));
                                    }
                                    play.put("GREEN", p.getUniqueId());
                                    play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                                    play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                                    play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                                    HashMap<String, Integer> time = new HashMap<>();
                                    time.put("GREEN", 30);
                                    time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                    time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                    time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                    Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                                    Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                                    for (Player d : faction.getAllOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&aGreen")));
                                    }
                                    for (Player d : Bukkit.getOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&aGreen").replace("%faction%", faction.get().getString("name"))));
                                    }
                                    tickColorTimer(p.getUniqueId());
                                }
                            } else {
                                Players knocked = new Players(Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN").toString());
                                HashMap<String, UUID> play = new HashMap<>();
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&aGreen").replace("%faction%", knocked.getFaction().get().getString("name"))));
                                }
                                play.put("GREEN", p.getUniqueId());
                                play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                                play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                                play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                                HashMap<String, Integer> time = new HashMap<>();
                                time.put("GREEN", 30);
                                time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                                time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                                time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                                Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                                Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                                for (Player d : faction.getAllOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&aGreen")));
                                }
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&aGreen").replace("%faction%", faction.get().getString("name"))));
                                }
                                tickColorTimer(p.getUniqueId());
                            }
                        } else {
                            HashMap<String, UUID> play = new HashMap<>();
                            play.put("GREEN", p.getUniqueId());
                            play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                            play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                            play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                            HashMap<String, Integer> time = new HashMap<>();
                            time.put("GREEN", 30);
                            time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                            time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                            time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                            Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                            Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                            for (Player d : faction.getAllOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.control-team").replace("%zone%", "&aGreen")));
                            }
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.control").replace("%zone%", "&aGreen").replace("%faction%", faction.get().getString("name"))));
                            }
                            tickColorTimer(p.getUniqueId());
                        }
                    }
                } else {
                    if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).containsValue(p.getUniqueId())) {
                        if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED").equals(p.getUniqueId())) {
                            UUID uuid = Main.getInstance().randomGeneratedUUIDConquest;
                            Players knocked = new Players(p.getUniqueId().toString());
                            HashMap<String, UUID> play = new HashMap<>();
                            HashMap<String, Integer> time = new HashMap<>();
                            time.put("RED", 30);
                            time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                            time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                            time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                            play.put("RED", uuid);
                            play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                            play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                            play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.knock").replace("%zone%", "&cRed").replace("%faction%", knocked.getFaction().get().getString("name"))));
                            }
                        } else if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN").equals(p.getUniqueId())) {
                            UUID uuid = Main.getInstance().randomGeneratedUUIDConquest;
                            Players knocked = new Players(p.getUniqueId().toString());
                            HashMap<String, UUID> play = new HashMap<>();
                            HashMap<String, Integer> time = new HashMap<>();
                            time.put("GREEN", 30);
                            time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                            time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                            time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                            Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                            play.put("GREEN", uuid);
                            play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                            play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                            play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                            Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.knock").replace("%zone%", "&aGreen").replace("%faction%", knocked.getFaction().get().getString("name"))));
                            }
                        } else if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW").equals(p.getUniqueId())) {
                            UUID uuid = Main.getInstance().randomGeneratedUUIDConquest;
                            Players knocked = new Players(p.getUniqueId().toString());
                            HashMap<String, UUID> play = new HashMap<>();
                            HashMap<String, Integer> time = new HashMap<>();
                            time.put("YELLOW", 30);
                            time.put("BLUE", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("BLUE"));
                            time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                            time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                            play.put("YELLOW", uuid);
                            play.put("BLUE", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE"));
                            play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                            play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.knock").replace("%zone%", "&eYellow").replace("%faction%", knocked.getFaction().get().getString("name"))));
                            }
                        } else if (Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("BLUE").equals(p.getUniqueId())) {
                            UUID uuid = Main.getInstance().randomGeneratedUUIDConquest;
                            Players knocked = new Players(p.getUniqueId().toString());
                            HashMap<String, UUID> play = new HashMap<>();
                            HashMap<String, Integer> time = new HashMap<>();
                            time.put("BLUE", 30);
                            time.put("RED", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("RED"));
                            time.put("YELLOW", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("YELLOW"));
                            time.put("GREEN", Main.getInstance().conquestTimer.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().conquestTimer.put(conquest.get().getString("uuid"), time);
                            play.put("BLUE ", uuid);
                            play.put("RED", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("RED"));
                            play.put("YELLOW", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("YELLOW"));
                            play.put("GREEN", Main.getInstance().capturingColorFaction.get(conquest.get().getString("uuid")).get("GREEN"));
                            Main.getInstance().capturingColorFaction.put(conquest.get().getString("uuid"), play);
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("command.conquest.knock").replace("%zone%", "&9Blue").replace("%faction%", knocked.getFaction().get().getString("name"))));
                            }
                        }
                    }
                }
            }
        }
    }
}
