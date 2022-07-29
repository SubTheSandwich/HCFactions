package me.sub.hcfactions.Events.Player.Tab;

import io.github.thatkawaiisam.ziggurat.Ziggurat;
import io.github.thatkawaiisam.ziggurat.ZigguratAdapter;
import io.github.thatkawaiisam.ziggurat.ZigguratCommons;
import io.github.thatkawaiisam.ziggurat.utils.BufferedTabObject;
import io.github.thatkawaiisam.ziggurat.utils.TabColumn;
import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Koth.Koth;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Files.Tab.Tab;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class LoadTab implements Listener {

    public static String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    private static String getDirection(double rot) {
        if (0 <= rot && rot < 22.5) {
            return "N";
        } else if (22.5 <= rot && rot < 67.5) {
            return "NE";
        } else if (67.5 <= rot && rot < 112.5) {
            return "E";
        } else if (112.5 <= rot && rot < 157.5) {
            return "SE";
        } else if (157.5 <= rot && rot < 202.5) {
            return "S";
        } else if (202.5 <= rot && rot < 247.5) {
            return "SW";
        } else if (247.5 <= rot && rot < 292.5) {
            return "W";
        } else if (292.5 <= rot && rot < 337.5) {
            return "NW";
        } else if (337.5 <= rot && rot < 360.0) {
            return "N";
        } else {
            return null;
        }
    }

    private static String getFactionInClaim(Location loc) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0")) {
                    for (String s : file.getConfigurationSection("claims").getKeys(false)) {
                        Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + s + ".world")), file.getDouble("claims." + s + ".sideOne.x"), file.getDouble("claims." + s + ".sideOne.y"), file.getDouble("claims." + s + ".sideOne.z"));
                        Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + s + ".world")), file.getDouble("claims." + s + ".sideTwo.x"), file.getDouble("claims." + s + ".sideTwo.y"), file.getDouble("claims." + s + ".sideTwo.z"));
                        Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                        if (cuboid.contains(loc)) {
                            return file.getString("uuid");
                        }
                    }
                }
            }
        }

        if (loc.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (loc.getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || loc.getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                return Main.getInstance().getConfig().getString("claim.wilderness.name");
            } else if (loc.getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || loc.getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                return Main.getInstance().getConfig().getString("claim.wilderness.name");
            } else {
                return Main.getInstance().getConfig().getString("claim.warzone.name");
            }
        } else if (loc.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
            if (loc.getX() > Main.getInstance().getConfig().getInt("worlds.nether.warzone") || loc.getZ() > Main.getInstance().getConfig().getInt("worlds.nether.warzone")) {
                return Main.getInstance().getConfig().getString("claim.wilderness.name");
            } else if (loc.getX() < -Main.getInstance().getConfig().getInt("worlds.nether.warzone") || loc.getZ() < -Main.getInstance().getConfig().getInt("worlds.nether.warzone")) {
                return Main.getInstance().getConfig().getString("claim.wilderness.name");
            } else {
                return Main.getInstance().getConfig().getString("claim.warzone.name");
            }
        }

        return null;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Players players = new Players(p.getUniqueId().toString());
        new Ziggurat(Main.getInstance(), new ZigguratAdapter() {
            @Override
            public Set<BufferedTabObject> getSlots(Player player) {
                Set<BufferedTabObject> toReturn = new HashSet<>();

                //Top Left hand corner
                Tab tab = new Tab();
                for (String s : tab.get().getConfigurationSection("tab.left").getKeys(false)) {
                    int lineNumber = Integer.parseInt(s);
                    s = tab.get().getString("tab.left." + s);
                    if (s.contains("%faction-home-location%")) {
                        if (players.hasFaction() &&  players.getFaction().get().isConfigurationSection("home")) {
                            String format = "%x%, %z%";
                            format = format.replace("%x%", String.valueOf(players.getFaction().get().getInt("home.x")));
                            format = format.replace("%z%", String.valueOf(players.getFaction().get().getInt("home.z")));
                            s = s.replace("%faction-home-location%", format);
                        } else {
                            s = s.replace("%faction-home-location%", "None");
                        }
                    }
                    if (s.contains("%kills%")) {
                        s = s.replace("%kills%", String.valueOf(players.get().getInt("kills")));
                    }
                    if (s.contains("%deaths%")) {
                        s = s.replace("%deaths%", String.valueOf(players.get().getInt("deaths")));
                    }
                    if (s.contains("%faction-in%")) {
                        if (getFactionInClaim(p.getLocation()) != null) {
                            Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                            if (faction.exists()) {
                                if (faction.get().getString("color") != null) {
                                    if (faction.get().getString("type").equals("KOTH")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                    } else if (faction.get().getString("type").equals("ROAD")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                    } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain");
                                    } else {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                    }
                                } else {
                                    if (faction.get().getString("uuid").equals(players.get().getString("faction"))) {
                                        s = s.replace("%faction-in%", "&a" + faction.get().getString("name"));
                                    } else {
                                        s = s.replace("%faction-in%", "&c" + faction.get().getString("name"));
                                    }
                                }
                            } else {
                                s = s.replace("%faction-in%", getFactionInClaim(p.getLocation()));
                            }
                        } else {
                            s = s.replace("%faction-in%", "None");
                        }
                    }
                    if (s.contains("%x%")) {
                        s = s.replace("%x%", String.valueOf(p.getLocation().getBlockX()));
                    }
                    if (s.contains("%z%")) {
                        s = s.replace("%z%", String.valueOf(p.getLocation().getBlockZ()));
                    }
                    if (s.contains("%facing%")) {
                        s = s.replace("%facing%", getCardinalDirection(p));
                    }
                    if (s.contains("%online%")) {
                        s = s.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    }
                    if (s.contains("%max-players%")) {
                        s = s.replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                    }
                    if (s.contains("%protection-max%")) {
                        s = s.replace("%protection-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL")));
                    }
                    if (s.contains("%sharpness-max%")) {
                        s = s.replace("%sharpness-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.DAMAGE_ALL")));
                    }
                    if (s.contains("%active-koth%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            s = s.replace("%active-koth%", new ArrayList<>(Main.getInstance().kothTimer.keySet()).get(0));
                        } else {
                            s = s.replace("%active-koth%", "&7");
                        }
                    }
                    if (s.contains("%koth-time%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            int time = Main.getInstance().kothTimer.get(keys.get(0));
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%time%", timee);
                        } else {
                            s = s.replace("%koth-time%", "&7");
                        }
                    }
                    if (s.contains("%koth-location%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            Koth koth = new Koth(keys.get(0));
                            if (koth.get().isConfigurationSection("location")) {

                            } else {
                                s = s.replace("%koth-location%", "None");
                            }
                        } else {
                            s = s.replace("%koth-location%", "&7");
                        }
                    }
                    if (s.contains("%team-list%")) {
                        HashMap<Faction, Integer> track = new HashMap<>();
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players players = new Players(d.getUniqueId().toString());
                            if (players.hasFaction()) {
                                track.put(players.getFaction(), players.getFaction().getAllOnlinePlayers().size());
                            }
                        }
                        Map<Faction, Integer> sortedMap = track.entrySet().stream()
                                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> { throw new AssertionError(); },
                                        LinkedHashMap::new
                                ));
                        if (sortedMap.size() != 0) {
                            if (sortedMap.size() <= lineNumber - 1) {
                                Faction foundFaction = null;
                                for (Faction faction : sortedMap.keySet()) {
                                    if (sortedMap.get(faction) == lineNumber - 1) {
                                        foundFaction = faction;
                                    }
                                }
                                if (foundFaction != null) {
                                    String format = tab.get().getString("team-list-format");
                                    format = format.replace("%name%", foundFaction.get().getString("name"));
                                    format = format.replace("%online-members%", String.valueOf(foundFaction.getAllOnlinePlayers().size()));
                                    s = s.replace("%team-list%", format);
                                } else {
                                    s = s.replace("%team-list%", "&7");
                                }
                            } else {
                                s = s.replace("%team-list%", "&7");
                            }
                        } else {
                            s = s.replace("%team-list%", "&7");
                        }
                    }
                    toReturn.add(
                            new BufferedTabObject()
                                    //Text
                                    .text(s)
                                    //Column
                                    .column(TabColumn.LEFT)
                                    //Slot
                                    .slot(lineNumber)
                                    //Ping (little buggy with 1.7 clients but defaults to 0 if thats the case
                                    .ping(999)
                                    //Texture (need to get skin sig and key
                                    .skin(ZigguratCommons.defaultTexture)
                    );
                }

                for (String s : tab.get().getConfigurationSection("tab.middle").getKeys(false)) {
                    int lineNumber = Integer.parseInt(s);
                    s = tab.get().getString("tab.middle." + s);
                    if (s.contains("%faction-home-location%")) {
                        if (players.hasFaction() &&  players.getFaction().get().isConfigurationSection("home")) {
                            String format = "%x%, %z%";
                            format = format.replace("%x%", String.valueOf(players.getFaction().get().getInt("home.x")));
                            format = format.replace("%z%", String.valueOf(players.getFaction().get().getInt("home.z")));
                            s = s.replace("%faction-home-location%", format);
                        } else {
                            s = s.replace("%faction-home-location%", "None");
                        }
                    }
                    if (s.contains("%kills%")) {
                        s = s.replace("%kills%", String.valueOf(players.get().getInt("kills")));
                    }
                    if (s.contains("%deaths%")) {
                        s = s.replace("%deaths%", String.valueOf(players.get().getInt("deaths")));
                    }
                    if (s.contains("%faction-in%")) {
                        if (getFactionInClaim(p.getLocation()) != null) {
                            Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                            if (faction.exists()) {
                                if (faction.get().getString("color") != null) {
                                    if (faction.get().getString("type").equals("KOTH")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                    } else if (faction.get().getString("type").equals("ROAD")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                    } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain");
                                    } else {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                    }
                                } else {
                                    if (faction.get().getString("uuid").equals(players.get().getString("faction"))) {
                                        s = s.replace("%faction-in%", "&a" + faction.get().getString("name"));
                                    } else {
                                        s = s.replace("%faction-in%", "&c" + faction.get().getString("name"));
                                    }
                                }
                            } else {
                                s = s.replace("%faction-in%", getFactionInClaim(p.getLocation()));
                            }
                        } else {
                            s = s.replace("%faction-in%", "None");
                        }
                    }
                    if (s.contains("%x%")) {
                        s = s.replace("%x%", String.valueOf(p.getLocation().getBlockX()));
                    }
                    if (s.contains("%z%")) {
                        s = s.replace("%z%", String.valueOf(p.getLocation().getBlockZ()));
                    }
                    if (s.contains("%facing%")) {
                        s = s.replace("%facing%", getCardinalDirection(p));
                    }
                    if (s.contains("%online%")) {
                        s = s.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    }
                    if (s.contains("%max-players%")) {
                        s = s.replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                    }
                    if (s.contains("%protection-max%")) {
                        s = s.replace("%protection-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL")));
                    }
                    if (s.contains("%sharpness-max%")) {
                        s = s.replace("%sharpness-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.DAMAGE_ALL")));
                    }
                    if (s.contains("%active-koth%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            s = s.replace("%active-koth%", new ArrayList<>(Main.getInstance().kothTimer.keySet()).get(0));
                        } else {
                            s = s.replace("%active-koth%", "&7");
                        }
                    }
                    if (s.contains("%koth-time%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            int time = Main.getInstance().kothTimer.get(keys.get(0));
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%time%", timee);
                        } else {
                            s = s.replace("%koth-time%", "&7");
                        }
                    }
                    if (s.contains("%koth-location%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            Koth koth = new Koth(keys.get(0));
                            if (koth.get().isConfigurationSection("location")) {

                            } else {
                                s = s.replace("%koth-location%", "None");
                            }
                        } else {
                            s = s.replace("%koth-location%", "&7");
                        }
                    }
                    if (s.contains("%team-list%")) {
                        HashMap<Faction, Integer> track = new HashMap<>();
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players players = new Players(d.getUniqueId().toString());
                            if (players.hasFaction()) {
                                track.put(players.getFaction(), players.getFaction().getAllOnlinePlayers().size());
                            }
                        }
                        Map<Faction, Integer> sortedMap = track.entrySet().stream()
                                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> { throw new AssertionError(); },
                                        LinkedHashMap::new
                                ));
                        if (sortedMap.size() != 0) {
                            if (sortedMap.size() <= lineNumber - 1) {
                                Faction foundFaction = null;
                                for (Faction faction : sortedMap.keySet()) {
                                    if (sortedMap.get(faction) == lineNumber - 1) {
                                        foundFaction = faction;
                                    }
                                }
                                if (foundFaction != null) {
                                    String format = tab.get().getString("team-list-format");
                                    format = format.replace("%name%", foundFaction.get().getString("name"));
                                    format = format.replace("%online-members%", String.valueOf(foundFaction.getAllOnlinePlayers().size()));
                                    s = s.replace("%team-list%", format);
                                } else {
                                    s = s.replace("%team-list%", "&7");
                                }
                            } else {
                                s = s.replace("%team-list%", "&7");
                            }
                        } else {
                            s = s.replace("%team-list%", "&7");
                        }
                    }
                    toReturn.add(
                            new BufferedTabObject()
                                    //Text
                                    .text(s)
                                    //Column
                                    .column(TabColumn.MIDDLE)
                                    //Slot
                                    .slot(lineNumber)
                                    //Ping (little buggy with 1.7 clients but defaults to 0 if thats the case
                                    .ping(999)
                                    //Texture (need to get skin sig and key
                                    .skin(ZigguratCommons.defaultTexture)
                    );
                }

                for (String s : tab.get().getConfigurationSection("tab.right").getKeys(false)) {
                    int lineNumber = Integer.parseInt(s);
                    s = tab.get().getString("tab.right." + s);
                    if (s.contains("%faction-home-location%")) {
                        if (players.hasFaction() &&  players.getFaction().get().isConfigurationSection("home")) {
                            String format = "%x%, %z%";
                            format = format.replace("%x%", String.valueOf(players.getFaction().get().getInt("home.x")));
                            format = format.replace("%z%", String.valueOf(players.getFaction().get().getInt("home.z")));
                            s = s.replace("%faction-home-location%", format);
                        } else {
                            s = s.replace("%faction-home-location%", "None");
                        }
                    }
                    if (s.contains("%kills%")) {
                        s = s.replace("%kills%", String.valueOf(players.get().getInt("kills")));
                    }
                    if (s.contains("%deaths%")) {
                        s = s.replace("%deaths%", String.valueOf(players.get().getInt("deaths")));
                    }
                    if (s.contains("%faction-in%")) {
                        if (getFactionInClaim(p.getLocation()) != null) {
                            Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                            if (faction.exists()) {
                                if (faction.get().getString("color") != null) {
                                    if (faction.get().getString("type").equals("KOTH")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                    } else if (faction.get().getString("type").equals("ROAD")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                    } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain");
                                    } else {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                    }
                                } else {
                                    if (faction.get().getString("uuid").equals(players.get().getString("faction"))) {
                                        s = s.replace("%faction-in%", "&a" + faction.get().getString("name"));
                                    } else {
                                        s = s.replace("%faction-in%", "&c" + faction.get().getString("name"));
                                    }
                                }
                            } else {
                                s = s.replace("%faction-in%", getFactionInClaim(p.getLocation()));
                            }
                        } else {
                            s = s.replace("%faction-in%", "None");
                        }
                    }
                    if (s.contains("%x%")) {
                        s = s.replace("%x%", String.valueOf(p.getLocation().getBlockX()));
                    }
                    if (s.contains("%z%")) {
                        s = s.replace("%z%", String.valueOf(p.getLocation().getBlockZ()));
                    }
                    if (s.contains("%facing%")) {
                        s = s.replace("%facing%", getCardinalDirection(p));
                    }
                    if (s.contains("%online%")) {
                        s = s.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    }
                    if (s.contains("%max-players%")) {
                        s = s.replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                    }
                    if (s.contains("%protection-max%")) {
                        s = s.replace("%protection-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL")));
                    }
                    if (s.contains("%sharpness-max%")) {
                        s = s.replace("%sharpness-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.DAMAGE_ALL")));
                    }
                    if (s.contains("%active-koth%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            s = s.replace("%active-koth%", new ArrayList<>(Main.getInstance().kothTimer.keySet()).get(0));
                        } else {
                            s = s.replace("%active-koth%", "&7");
                        }
                    }
                    if (s.contains("%koth-time%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            int time = Main.getInstance().kothTimer.get(keys.get(0));
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%time%", timee);
                        } else {
                            s = s.replace("%koth-time%", "&7");
                        }
                    }
                    if (s.contains("%koth-location%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            Koth koth = new Koth(keys.get(0));
                            if (koth.get().isConfigurationSection("location")) {

                            } else {
                                s = s.replace("%koth-location%", "None");
                            }
                        } else {
                            s = s.replace("%koth-location%", "&7");
                        }
                    }
                    if (s.contains("%team-list%")) {
                        HashMap<Faction, Integer> track = new HashMap<>();
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players players = new Players(d.getUniqueId().toString());
                            if (players.hasFaction()) {
                                track.put(players.getFaction(), players.getFaction().getAllOnlinePlayers().size());
                            }
                        }
                        Map<Faction, Integer> sortedMap = track.entrySet().stream()
                                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> { throw new AssertionError(); },
                                        LinkedHashMap::new
                                ));
                        if (sortedMap.size() != 0) {
                            if (sortedMap.size() <= lineNumber - 1) {
                                Faction foundFaction = null;
                                for (Faction faction : sortedMap.keySet()) {
                                    if (sortedMap.get(faction) == lineNumber - 1) {
                                        foundFaction = faction;
                                    }
                                }
                                if (foundFaction != null) {
                                    String format = tab.get().getString("team-list-format");
                                    format = format.replace("%name%", foundFaction.get().getString("name"));
                                    format = format.replace("%online-members%", String.valueOf(foundFaction.getAllOnlinePlayers().size()));
                                    s = s.replace("%team-list%", format);
                                } else {
                                    s = s.replace("%team-list%", "&7");
                                }
                            } else {
                                s = s.replace("%team-list%", "&7");
                            }
                        } else {
                            s = s.replace("%team-list%", "&7");
                        }
                    }
                    toReturn.add(
                            new BufferedTabObject()
                                    //Text
                                    .text(s)
                                    //Column
                                    .column(TabColumn.RIGHT)
                                    //Slot
                                    .slot(lineNumber)
                                    //Ping (little buggy with 1.7 clients but defaults to 0 if thats the case
                                    .ping(999)
                                    //Texture (need to get skin sig and key
                                    .skin(ZigguratCommons.defaultTexture)
                    );
                }

                for (String s : tab.get().getConfigurationSection("tab.farright").getKeys(false)) {
                    int lineNumber = Integer.parseInt(s);
                    s = tab.get().getString("tab.farright." + s);
                    if (s.contains("%faction-home-location%")) {
                        if (players.hasFaction() &&  players.getFaction().get().isConfigurationSection("home")) {
                            String format = "%x%, %z%";
                            format = format.replace("%x%", String.valueOf(players.getFaction().get().getInt("home.x")));
                            format = format.replace("%z%", String.valueOf(players.getFaction().get().getInt("home.z")));
                            s = s.replace("%faction-home-location%", format);
                        } else {
                            s = s.replace("%faction-home-location%", "None");
                        }
                    }
                    if (s.contains("%kills%")) {
                        s = s.replace("%kills%", String.valueOf(players.get().getInt("kills")));
                    }
                    if (s.contains("%deaths%")) {
                        s = s.replace("%deaths%", String.valueOf(players.get().getInt("deaths")));
                    }
                    if (s.contains("%faction-in%")) {
                        if (getFactionInClaim(p.getLocation()) != null) {
                            Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                            if (faction.exists()) {
                                if (faction.get().getString("color") != null) {
                                    if (faction.get().getString("type").equals("KOTH")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                    } else if (faction.get().getString("type").equals("ROAD")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                    } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain");
                                    } else {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                    }
                                } else {
                                    if (faction.get().getString("uuid").equals(players.get().getString("faction"))) {
                                        s = s.replace("%faction-in%", "&a" + faction.get().getString("name"));
                                    } else {
                                        s = s.replace("%faction-in%", "&c" + faction.get().getString("name"));
                                    }
                                }
                            } else {
                                s = s.replace("%faction-in%", getFactionInClaim(p.getLocation()));
                            }
                        } else {
                            s = s.replace("%faction-in%", "None");
                        }
                    }
                    if (s.contains("%x%")) {
                        s = s.replace("%x%", String.valueOf(p.getLocation().getBlockX()));
                    }
                    if (s.contains("%z%")) {
                        s = s.replace("%z%", String.valueOf(p.getLocation().getBlockZ()));
                    }
                    if (s.contains("%facing%")) {
                        s = s.replace("%facing%", getCardinalDirection(p));
                    }
                    if (s.contains("%online%")) {
                        s = s.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    }
                    if (s.contains("%max-players%")) {
                        s = s.replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                    }
                    if (s.contains("%protection-max%")) {
                        s = s.replace("%protection-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL")));
                    }
                    if (s.contains("%sharpness-max%")) {
                        s = s.replace("%sharpness-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.DAMAGE_ALL")));
                    }
                    if (s.contains("%active-koth%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            s = s.replace("%active-koth%", new ArrayList<>(Main.getInstance().kothTimer.keySet()).get(0));
                        } else {
                            s = s.replace("%active-koth%", "&7");
                        }
                    }
                    if (s.contains("%koth-time%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            int time = Main.getInstance().kothTimer.get(keys.get(0));
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%time%", timee);
                        } else {
                            s = s.replace("%koth-time%", "&7");
                        }
                    }
                    if (s.contains("%koth-location%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            Koth koth = new Koth(keys.get(0));
                            if (koth.get().isConfigurationSection("location")) {

                            } else {
                                s = s.replace("%koth-location%", "None");
                            }
                        } else {
                            s = s.replace("%koth-location%", "&7");
                        }
                    }
                    if (s.contains("%team-list%")) {
                        HashMap<Faction, Integer> track = new HashMap<>();
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players players = new Players(d.getUniqueId().toString());
                            if (players.hasFaction()) {
                                track.put(players.getFaction(), players.getFaction().getAllOnlinePlayers().size());
                            }
                        }
                        Map<Faction, Integer> sortedMap = track.entrySet().stream()
                                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> { throw new AssertionError(); },
                                        LinkedHashMap::new
                                ));
                        if (sortedMap.size() != 0) {
                            if (sortedMap.size() <= lineNumber - 1) {
                                Faction foundFaction = null;
                                for (Faction faction : sortedMap.keySet()) {
                                    if (sortedMap.get(faction) == lineNumber - 1) {
                                        foundFaction = faction;
                                    }
                                }
                                if (foundFaction != null) {
                                    String format = tab.get().getString("team-list-format");
                                    format = format.replace("%name%", foundFaction.get().getString("name"));
                                    format = format.replace("%online-members%", String.valueOf(foundFaction.getAllOnlinePlayers().size()));
                                    s = s.replace("%team-list%", format);
                                } else {
                                    s = s.replace("%team-list%", "&7");
                                }
                            } else {
                                s = s.replace("%team-list%", "&7");
                            }
                        } else {
                            s = s.replace("%team-list%", "&7");
                        }
                    }
                    toReturn.add(
                            new BufferedTabObject()
                                    //Text
                                    .text(s)
                                    //Column
                                    .column(TabColumn.FAR_RIGHT)
                                    //Slot
                                    .slot(lineNumber)
                                    //Ping (little buggy with 1.7 clients but defaults to 0 if thats the case
                                    .ping(999)
                                    //Texture (need to get skin sig and key
                                    .skin(ZigguratCommons.defaultTexture)
                    );
                }


                return toReturn;
            }

            @Override
            public String getFooter() {
                return null;
            }

            @Override
            public String getHeader() {
                return null;
            }
        });
    }
}
