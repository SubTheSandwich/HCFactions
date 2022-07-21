package me.sub.hcfactions.Commands.Member;

import com.lunarclient.bukkitapi.object.LCWaypoint;
import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Lunar.Lunar;
import me.sub.hcfactions.Files.Messages.Messages;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import me.sub.hcfactions.Utils.Economy.Economy;
import me.sub.hcfactions.Utils.Faction.Claim;
import me.sub.hcfactions.Utils.Faction.FactionInviteHandler;
import me.sub.hcfactions.Utils.Timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class FactionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                ArrayList<String> messages = new ArrayList<>();
                for (String str : Messages.get().getStringList("faction.help.main")) {
                    if (str.contains("%primary%")) {
                        str = str.replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary"));
                    }
                    if (str.contains("%secondary%")) {
                        str = str.replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary"));
                    }
                    if (str.contains("%servername%")) {
                        str = str.replace("%servername%", Main.getInstance().getConfig().getString("server.name"));
                    }
                    if (str.contains("%alias%")) {
                        str = str.replace("%alias%", s);
                    }
                    messages.add(str);
                }
                for (String str : messages) {
                    p.sendMessage(C.chat(str));
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("bypass")) {
                    if (p.hasPermission("hcfactions.command.faction.bypass") || p.hasPermission("hcfactions.admin")) {
                        if (Main.getInstance().bypass.contains(p.getUniqueId())) {
                            Main.getInstance().bypass.remove(p.getUniqueId());
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.bypass.disabled")));
                        } else {
                            Main.getInstance().bypass.add(p.getUniqueId());
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.bypass.enabled")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                    }
                } else if (args[0].equalsIgnoreCase("who")) {
                    Players players = new Players(p.getUniqueId().toString());
                    if (players.get().getString("faction").equals("")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.who.not-in-faction")));
                    } else {
                        for (String msg : Messages.get().getStringList("faction.who.player")) {
                            if (msg.contains("%name%")) {
                                String factionUUID = players.get().getString("faction");
                                Faction f = new Faction(factionUUID);
                                String factionName = f.get().getString("name");
                                msg = msg.replace("%name%", factionName);
                            }
                            String factionUUID = players.get().getString("faction");
                            Faction f = new Faction(factionUUID);

                            int onlinePlayers = 0;
                            int totalPlayers = 0;
                            Player leader = Bukkit.getPlayer(UUID.fromString(f.get().getString("leader")));
                            if (leader != null) {
                                onlinePlayers = onlinePlayers + 1;
                                totalPlayers = totalPlayers + 1;
                            } else {
                                totalPlayers = totalPlayers + 1;
                            }

                            for (String play : f.get().getStringList("coleaders")) {
                                Player player = Bukkit.getPlayer(UUID.fromString(play));
                                if (player != null) {
                                    onlinePlayers = onlinePlayers + 1;
                                    totalPlayers = totalPlayers + 1;
                                } else {
                                    totalPlayers = totalPlayers + 1;
                                }
                            }

                            for (String play : f.get().getStringList("captains")) {
                                Player player = Bukkit.getPlayer(UUID.fromString(play));
                                if (player != null) {
                                    onlinePlayers = onlinePlayers + 1;
                                    totalPlayers = totalPlayers + 1;
                                } else {
                                    totalPlayers = totalPlayers + 1;
                                }
                            }

                            for (String play : f.get().getStringList("members")) {
                                Player player = Bukkit.getPlayer(UUID.fromString(play));
                                if (player != null) {
                                    onlinePlayers = onlinePlayers + 1;
                                    totalPlayers = totalPlayers + 1;
                                } else {
                                    totalPlayers = totalPlayers + 1;
                                }
                            }

                            if (msg.contains("%online%")) {
                                msg = msg.replace("%online%", String.valueOf(onlinePlayers));
                            }

                            if (msg.contains("%players%")) {
                                msg = msg.replace("%players%", String.valueOf(totalPlayers));
                            }

                            if (msg.contains("%home%")) {
                                if (f.get().isConfigurationSection("home")) {
                                    int x = f.get().getInt("home.x");
                                    int z = f.get().getInt("home.z");
                                    msg = msg.replace("%home%", String.valueOf(x) + ", " + String.valueOf(z));
                                } else {
                                    msg = msg.replace("%home%", "None");
                                }
                            }

                            if (msg.contains("%leader%")) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(f.get().getString("leader")));
                                Players offPlayer = new Players(player.getUniqueId().toString());
                                if (player.isOnline()) {
                                    msg = msg.replace("%leader%", "&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                } else {
                                    if (offPlayer.get().getBoolean("deathBanned")) {
                                        msg = msg.replace("%leader%", "&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                    } else {
                                        msg = msg.replace("%leader%", "&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                    }
                                }
                            }


                            if (msg.contains("%coleaders%")) {
                                String result;
                                ArrayList<String> stuff = new ArrayList<>();
                                if (f.get().getStringList("coleaders").size() > 0) {
                                    for (String str : f.get().getStringList("coleaders")) {
                                        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                                        Players offPlayer = new Players(str);
                                        if (player.isOnline()) {
                                            stuff.add("&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                        } else {
                                            if (offPlayer.get().getBoolean("deathBanned")) {
                                                stuff.add("&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            } else {
                                                stuff.add("&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            }
                                        }
                                    }
                                    result = String.join(", ", stuff);
                                    msg = msg.replace("%coleaders%", result);
                                } else {
                                    continue;
                                }
                            }

                            if (msg.contains("%captains%")) {
                                String result;
                                ArrayList<String> stuff = new ArrayList<>();
                                if (f.get().getStringList("captains").size() > 0) {
                                    for (String str : f.get().getStringList("captains")) {
                                        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                                        Players offPlayer = new Players(str);
                                        if (player.isOnline()) {
                                            stuff.add("&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                        } else {
                                            if (offPlayer.get().getBoolean("deathBanned")) {
                                                stuff.add("&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            } else {
                                                stuff.add("&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            }
                                        }
                                    }
                                    result = String.join(", ", stuff);
                                    msg = msg.replace("%captains%", result);
                                } else {
                                    continue;
                                }
                            }

                            if (msg.contains("%members%")) {
                                String result;
                                ArrayList<String> stuff = new ArrayList<>();
                                if (f.get().getStringList("members").size() > 0) {
                                    for (String str : f.get().getStringList("members")) {
                                        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                                        Players offPlayer = new Players(str);
                                        if (player.isOnline()) {
                                            stuff.add("&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                        } else {
                                            if (offPlayer.get().getBoolean("deathBanned")) {
                                                stuff.add("&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            } else {
                                                stuff.add("&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            }
                                        }
                                    }
                                    result = String.join(", ", stuff);
                                    msg = msg.replace("%members%", result);
                                } else {
                                    continue;
                                }
                            }

                            if (msg.contains("%balance%")) {
                                msg = msg.replace("%balance%", String.valueOf(f.get().getInt("balance")));
                            }

                            if (msg.contains("%points%")) {
                                msg = msg.replace("%points%", String.valueOf(f.get().getInt("points")));
                            }

                            if (msg.contains("%kothcaptures%")) {
                                msg = msg.replace("%kothcaptures%", String.valueOf(f.get().getInt("kothcaptures")));
                            }

                            if (msg.contains("%dtr%")) {
                                double dtr = f.get().getDouble("dtr");
                                if (dtr <= 0) {
                                    msg = msg.replace("%dtr%", "&c" + String.valueOf(f.get().getDouble("dtr")));
                                } else if (dtr <= 1) {
                                    msg = msg.replace("%dtr%", "&e" + String.valueOf(f.get().getDouble("dtr")));
                                } else {
                                    msg = msg.replace("%dtr%", "&a" + String.valueOf(f.get().getDouble("dtr")));
                                }
                            }

                            if (msg.contains("%regen%")) {
                                if (f.get().getBoolean("regening")) {
                                    // for now
                                    continue;
                                } else {
                                    continue;
                                }
                            }

                            if (msg.contains("%lives%")) {
                                msg = msg.replace("%lives%", String.valueOf(f.get().getInt("lives")));
                            }

                            if (msg.contains("%announcement%")) {
                                if (f.get().getString("announcement").equals("")) {
                                    continue;
                                } else {
                                    msg = msg.replace("%announcement%", String.valueOf(f.get().getString("announcement")));
                                }
                            }



                            p.sendMessage(C.chat(msg));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("focus")) {
                    Players players = new Players(p.getUniqueId().toString());
                    if (players.get().getString("faction").equals("")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.focus.no-faction")));
                    } else {
                        if (Main.getInstance().focusedPlayer.containsKey(players.get().getString("faction"))) {
                            Player player = Main.getInstance().focusedPlayer.get(players.get().getString("faction"));
                            Players newPlayers = new Players(player.getUniqueId().toString());
                            Faction f = new Faction(newPlayers.get().getString("faction"));
                            Faction otherF = new Faction(players.get().getString("faction"));
                            double dtr = f.get().getDouble("dtr");
                            String msg = "%dtr%";
                            if (dtr <= 0) {
                                msg = msg.replace("%dtr%", "&c" + String.valueOf(f.get().getDouble("dtr")));
                            } else if (dtr <= 1) {
                                msg = msg.replace("%dtr%", "&e" + String.valueOf(f.get().getDouble("dtr")));
                            } else {
                                msg = msg.replace("%dtr%", "&a" + String.valueOf(f.get().getDouble("dtr")));
                            }
                            Faction getName = new Faction(newPlayers.get().getString("faction"));
                            Player leader = Bukkit.getPlayer(UUID.fromString(otherF.get().getString("leader")));
                            ArrayList<String> name = new ArrayList<>();
                            name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.enemy.team-color") + getName.get().getString("name") + "&6]&r " + msg));
                            name.add(C.chat(Lunar.get().getString("lunar.colors.enemy.color") + player.getName()));
                            if (leader != null) {
                                Main.getInstance().lunarClientAPI.overrideNametag(player, name, leader);
                                leader.sendMessage(C.chat(Locale.get().getString("command.faction.focus.cancel-focus").replace("%player%", player.getName())));
                            }

                            for (String play : f.get().getStringList("coleaders")) {
                                Player d = Bukkit.getPlayer(UUID.fromString(play));
                                if (d != null) {
                                    Main.getInstance().lunarClientAPI.overrideNametag(player, name, d);
                                    d.sendMessage(C.chat(Locale.get().getString("command.faction.focus.cancel-focus").replace("%player%", player.getName())));
                                }
                            }

                            for (String play : f.get().getStringList("captains")) {
                                Player d = Bukkit.getPlayer(UUID.fromString(play));
                                if (d != null) {
                                    Main.getInstance().lunarClientAPI.overrideNametag(player, name, d);
                                    d.sendMessage(C.chat(Locale.get().getString("command.faction.focus.cancel-focus").replace("%player%", player.getName())));
                                }
                            }

                            for (String play : f.get().getStringList("members")) {
                                Player d = Bukkit.getPlayer(UUID.fromString(play));
                                if (d != null) {
                                    Main.getInstance().lunarClientAPI.overrideNametag(player, name, d);
                                    d.sendMessage(C.chat(Locale.get().getString("command.faction.focus.cancel-focus").replace("%player%", player.getName())));
                                }
                            }

                            Main.getInstance().focusedPlayer.remove(players.get().getString("faction"), player);
                        } else {
                            if (Main.getInstance().focusedFaction.containsKey(players.get().getString("faction"))) {

                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.focus.not-focusing")));
                            }
                        }
                    }
                } else if (args[0].equalsIgnoreCase("rally")) {
                    Location rallyPoint = p.getLocation();
                    Players players = new Players(p.getUniqueId().toString());
                    Faction f = new Faction(players.get().getString("faction"));
                    ArrayList<Player> onlinePlayers = new ArrayList<>(f.getAllOnlinePlayers());
                    String location = String.valueOf(Cooldown.round(p.getLocation().getX(), 1)) + ", " + String.valueOf(Cooldown.round(p.getLocation().getY(), 1)) + ", " + String.valueOf(Cooldown.round(p.getLocation().getZ(), 1)) + " in World " + p.getLocation().getWorld().getEnvironment().toString();
                    int color = Lunar.get().getInt("lunar.waypoints.rally.color.red") + Lunar.get().getInt("lunar.waypoints.rally.color.green") + Lunar.get().getInt("lunar.waypoints.rally.color.blue");
                    LCWaypoint waypoint = new LCWaypoint(Lunar.get().getString("lunar.waypoints.rally.name"), p.getLocation(), color, true, true);

                    if (Main.getInstance().currentRally.containsKey(players.get().getString("faction"))) {
                        LCWaypoint old = Main.getInstance().currentRally.get(players.get().getString("faction"));
                        Main.getInstance().currentRally.remove(players.get().getString("faction"));
                        for (Player d : onlinePlayers) {
                            Main.getInstance().lunarClientAPI.removeWaypoint(d, old);
                        }
                    }

                    //Fix waypoint not working but overall it works decently well

                    for (Player d : onlinePlayers) {
                        d.sendMessage(C.chat(Locale.get().getString("command.faction.rally.set").replace("%creator%", p.getName()).replace("%location%", location).replace("%time%", String.valueOf(Main.getInstance().getConfig().getInt("settings.rally-expire-time")))));
                        Main.getInstance().lunarClientAPI.sendWaypoint(d, waypoint);
                    }

                    Main.getInstance().currentRally.put(players.get().getString("faction"), waypoint);
                    int time = 20 * (60 * Main.getInstance().getConfig().getInt("settings.rally-expire-time"));

                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            Main.getInstance().currentRally.remove(players.get().getString("faction"), waypoint);
                            for (Player d : onlinePlayers) {
                                Main.getInstance().lunarClientAPI.removeWaypoint(d, waypoint);
                                d.sendMessage(C.chat(Locale.get().getString("command.faction.rally.expired")));
                            }
                        }
                    }.runTaskLater(Main.getInstance(), time);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!Main.getInstance().currentRally.get(players.get().getString("faction")).equals(waypoint)) {
                                Bukkit.getServer().getScheduler().cancelTask(task.getTaskId());
                            }
                            if (!Main.getInstance().currentRally.containsKey(players.get().getString("faction"))) {
                                cancel();
                            }
                        }
                    }.runTaskTimer(Main.getInstance(), 0, 1);
                } else if (args[0].equalsIgnoreCase("sethome")) {
                    Players players = new Players(p.getUniqueId().toString());
                    if (players.hasFaction()) {
                        Faction faction = players.getFaction();
                        String leader = faction.get().getString("leader");
                        ArrayList<String> coleaders = new ArrayList<>(faction.get().getStringList("coleaders"));
                        if (leader.equals(p.getUniqueId().toString()) || coleaders.contains(p.getUniqueId().toString())) {
                            if (getFactionInClaim(p) != null && getFactionInClaim(p).equals(faction.get().getString("uuid"))) {
                                for (Player d : faction.getAllOnlinePlayers()) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.faction.sethome.updated").replace("%name%", p.getName())));
                                }
                                faction.get().set("home.world", p.getWorld().getName());
                                faction.get().set("home.x", p.getLocation().getX());
                                faction.get().set("home.y", p.getLocation().getY());
                                faction.get().set("home.z", p.getLocation().getZ());
                                faction.get().set("home.yaw", p.getLocation().getYaw());
                                faction.get().set("home.pitch", p.getLocation().getPitch());
                                faction.save();
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.sethome.not-home")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.sethome.not-right-role")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.sethome.no-faction")));
                    }
                } else if (args[0].equalsIgnoreCase("home")) {
                    Players players = new Players(p.getUniqueId().toString());
                    if (players.hasFaction()) {
                        Faction faction = players.getFaction();
                        if (faction.get().isConfigurationSection("home")) {
                            if (!Main.getInstance().homeTimer.containsKey(p) || !Main.getInstance().combatTimer.containsKey(p.getUniqueId()) || !Main.getInstance().pvpTimer.containsKey(p.getUniqueId()) || !Main.getInstance().frozen.contains(p) || !Main.getInstance().logoutTimer.containsKey(p)) {
                                if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                    if (getFactionInClaim(p) != null) {
                                        if (getFactionInClaim(p).equals(faction.get().getString("uuid"))) {
                                            Timer.setHomeTimer(p, new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.home")));
                                            p.sendMessage(C.chat(Locale.get().getString("command.faction.home.waiting").replace("%time%", String.valueOf(Main.getInstance().getConfig().getInt("settings.timers.home")))));
                                        } else {
                                            Faction faction1 = new Faction(getFactionInClaim(p));
                                            if (!faction1.get().getString("type").equals("SAFEZONE")) {
                                                if (faction1.get().getString("type").equals("PLAYER")) {
                                                    Timer.setHomeTimer(p, new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.claim-home")));
                                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.home.waiting").replace("%time%", String.valueOf(Main.getInstance().getConfig().getInt("settings.timers.claim-home")))));
                                                } else {
                                                    Timer.setHomeTimer(p, new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.home")));
                                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.home.waiting").replace("%time%", String.valueOf(Main.getInstance().getConfig().getInt("settings.timers.home")))));
                                                }
                                            } else {
                                                p.sendMessage(C.chat(Locale.get().getString("events.teleport-home")));
                                                Location location = new Location(Bukkit.getWorld(faction.get().getString("home.world")), faction.get().getDouble("home.x"), faction.get().getDouble("home.y"), faction.get().getDouble("home.z"), (float) faction.get().getDouble("home.yaw"), (float) faction.get().getDouble("home.pitch"));
                                                p.teleport(location);
                                            }
                                        }
                                    } else {
                                        Timer.setHomeTimer(p, new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.home")));
                                        p.sendMessage(C.chat(Locale.get().getString("command.faction.home.waiting").replace("%time%", String.valueOf(Main.getInstance().getConfig().getInt("settings.timers.home")))));
                                    }
                                } else if (p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                                    Timer.setHomeTimer(p, new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.nether-home")));
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.home.waiting").replace("%time%", String.valueOf(Main.getInstance().getConfig().getInt("settings.timers.nether-home")))));
                                } else {
                                    Timer.setHomeTimer(p, new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.end-home")));
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.home.waiting").replace("%time%", String.valueOf(Main.getInstance().getConfig().getInt("settings.timers.end-home")))));
                                }
                                Timer.tickHomeTimer(p);
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.home.unable")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.home.not-set")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.home.no-faction")));
                    }
                } else if (args[0].equalsIgnoreCase("claim")) {
                    Players players = new Players(p.getUniqueId().toString());
                    if (players.hasFaction()) {
                        if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                            ArrayList<String> coleaders = new ArrayList<>(players.getFaction().get().getStringList("coleaders"));
                            String leader = players.getFaction().get().getString("leader");
                            if (p.getUniqueId().toString().equalsIgnoreCase(leader) || coleaders.contains(p.getUniqueId().toString())) {
                                if (!Main.getInstance().pvpTimer.containsKey(p.getUniqueId())) {
                                    if (p.getLocation().getX() > 0 || p.getLocation().getZ() > 0) {
                                        if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                            if (!players.getFaction().get().isConfigurationSection("claims.0")) {
                                                if (!Main.getInstance().claiming.contains(p.getUniqueId())) {
                                                    Main.getInstance().claiming.add(p.getUniqueId());
                                                    Claim.giveItem(p);
                                                } else {
                                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.already")));
                                                }
                                            } else {
                                                p.sendMessage(C.chat(Locale.get().getString("events.cant-claim-expand")));
                                            }
                                        } else {
                                            p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.cant-claim")));
                                        }
                                    } else {
                                        if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                            if (!players.getFaction().get().isConfigurationSection("claims.0")) {
                                                if (!Main.getInstance().claiming.contains(p.getUniqueId())) {
                                                    Main.getInstance().claiming.add(p.getUniqueId());
                                                    Claim.giveItem(p);
                                                } else {
                                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.already")));
                                                }
                                            } else {
                                                p.sendMessage(C.chat(Locale.get().getString("events.cant-claim-expand")));
                                            }
                                        } else {
                                            p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.cant-claim")));
                                        }
                                    }
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.invalid-rank")));
                            }
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.no-faction")));
                    }
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("claimfor")) {
                    if (p.hasPermission("hcfactions.admin")) {
                        String factionName = args[1];
                        String id = "";
                        boolean valid = false;
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(factionName.toLowerCase(java.util.Locale.ROOT))) {
                                    valid = true;
                                    id = file.getString("uuid");
                                }
                            }
                        }
                        if (valid) {
                            Main.getInstance().claimFor.put(p.getUniqueId(), id);
                            Claim.giveItem(p);
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.claimfor.invalid")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                    }
                } else if (args[0].equalsIgnoreCase("createsystem")) {
                    if (p.hasPermission("hcfactions.admin")) {
                        String factionName = args[1];
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(factionName.toLowerCase(java.util.Locale.ROOT))) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.system-create.already-exists")));
                                    return true;
                                }
                            }
                        }

                        for (Player d : Bukkit.getOnlinePlayers()) {
                            if (d.hasPermission("hcfactions.admin")) {
                                d.sendMessage(C.chat(Locale.get().getString("command.faction.system-create.created").replace("%player%", p.getName()).replace("%name%", factionName)));
                            }
                        }

                        String id = UUID.randomUUID().toString();
                        Faction faction = new Faction(id);
                        faction.setup();
                        faction.get().set("uuid", id);
                        faction.get().set("claims", "");
                        faction.get().set("type", "SYSTEM");
                        faction.get().set("name", factionName);
                        faction.get().set("deathban", true);
                        faction.get().set("color", "GREEN");
                        faction.save();
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                    }
                } else if (args[0].equalsIgnoreCase("setdeathban")) {
                    if (p.hasPermission("hcfactions.admin") || p.hasPermission("hcfactions.command.faction.setcolor")) {
                        String factionName = args[1];
                        String id = "";
                        boolean valid = false;
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(factionName.toLowerCase(java.util.Locale.ROOT))) {
                                    valid = true;
                                    id = file.getString("uuid");
                                }
                            }
                        }
                        if (valid) {
                            Faction faction = new Faction(id);
                            if (!faction.get().getString("type").equals("PLAYER")) {
                                if (faction.get().getBoolean("deathban")) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.setdeathban.set").replace("%faction%", factionName).replace("%deathban%", Main.getInstance().getConfig().getString("claim.not-deathban"))));
                                    faction.get().set("deathban", false);
                                    faction.save();
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.setdeathban.set").replace("%faction%", factionName).replace("%deathban%", Main.getInstance().getConfig().getString("claim.deathban"))));
                                    faction.get().set("deathban", true);
                                    faction.save();
                                }

                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.setdeathban.invalid-type")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.setdeathban.invalid")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                    }
                } else if (args[0].equalsIgnoreCase("create")) {
                    String factionName = args[1];
                    File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                    if (factions != null) {
                        for (File f : factions) {
                            YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                            if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(factionName.toLowerCase(java.util.Locale.ROOT))) {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.create.already-exists")));
                                return true;
                            }
                        }
                    }
                    File f = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/profiles/", p.getUniqueId().toString() + ".yml");
                    YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                    if (!file.getString("faction").equals("")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.attempt-create")));
                        return true;
                    } else {
                        String id = UUID.randomUUID().toString();
                        Faction faction = new Faction(id);
                        Players players = new Players(p.getUniqueId().toString());
                        players.get().set("faction", id);
                        players.save();
                        faction.setup();
                        faction.get().set("uuid", id);
                        faction.get().set("name", args[1]);
                        faction.get().set("deathban", true);
                        faction.get().set("type", "PLAYER");
                        faction.get().set("claims", "");
                        faction.get().set("leader", p.getUniqueId().toString());
                        faction.get().set("coleaders", "");
                        faction.get().set("captains", "");
                        faction.get().set("members", "");
                        faction.get().set("allies", "");
                        faction.get().set("kothcaptures", 0);
                        faction.get().set("dtr", 1.01);
                        faction.get().set("startregen", "");
                        faction.get().set("regening", false);
                        faction.get().set("lives", 0);
                        faction.get().set("balance", 0);
                        faction.get().set("points", 0);
                        faction.get().set("announcement", "");
                        faction.save();
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.create.message")));
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.create.help")));
                        Bukkit.broadcastMessage(C.chat(Locale.get().getString("command.faction.create.broadcast").replace("%faction%", args[1]).replace("%player%", p.getName())));
                    }
                } else if (args[0].equalsIgnoreCase("invite")) {
                    Players players = new Players(p.getUniqueId().toString());
                    if (players.get().getString("faction").equals("")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.invite.not-in-faction")));
                    } else {
                        if (p.getName().toLowerCase(java.util.Locale.ROOT).equals(args[1].toLowerCase(java.util.Locale.ROOT))) {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.invite.not-yourself")));
                            return true;
                        }

                        String factionID = players.get().getString("faction");
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        boolean valid = false;
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("uuid").equals(factionID)) {
                                    if (file.getStringList("coleaders").contains(p.getUniqueId().toString())) {
                                        valid = true;
                                    } else if (file.getStringList("captains").contains(p.getUniqueId().toString())) {
                                        valid = true;
                                    } else if (file.getString("leader").equals(p.getUniqueId().toString())) {
                                        valid = true;
                                    }
                                }
                            }
                        }

                        if (valid) {
                            Player invitedPlayer = Bukkit.getPlayer(args[1]);
                            if (invitedPlayer != null) {
                                ArrayList<String> uuids = new ArrayList<>();
                                Faction f = new Faction(factionID);
                                uuids.addAll(f.get().getStringList("members"));
                                uuids.addAll(f.get().getStringList("captains"));
                                uuids.addAll(f.get().getStringList("coleaders"));
                                uuids.add(f.get().getString("leader"));
                                if (!uuids.contains(invitedPlayer.getUniqueId().toString())) {
                                    if (Main.getInstance().invites.get(invitedPlayer) == null) {
                                        FactionInviteHandler.create(factionID, invitedPlayer);
                                        for (String id : uuids) {
                                            Player play = Bukkit.getPlayer(UUID.fromString(id));
                                            if (play != null) {
                                                play.sendMessage(C.chat(Locale.get().getString("command.faction.invite.broadcast").replace("%name%", invitedPlayer.getName())));
                                                FactionInviteHandler.tickInviteTimer(factionID, invitedPlayer);
                                            }
                                        }
                                        invitedPlayer.sendMessage(C.chat(Locale.get().getString("command.faction.invite.message-to-player").replace("%name%", p.getName()).replace("%faction%", f.get().getString("name"))));
                                    } else if (!Main.getInstance().invites.get(invitedPlayer).contains(factionID)) {
                                        FactionInviteHandler.create(factionID, invitedPlayer);
                                        for (String id : uuids) {
                                            Player play = Bukkit.getPlayer(UUID.fromString(id));
                                            if (play != null) {
                                                play.sendMessage(C.chat(Locale.get().getString("command.faction.invite.broadcast").replace("%name%", invitedPlayer.getName())));
                                                FactionInviteHandler.tickInviteTimer(factionID, invitedPlayer);
                                            }
                                        }
                                    } else {
                                        p.sendMessage(C.chat(Locale.get().getString("command.faction.invite.existing-invite")));
                                    }
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.invite.already-in-team").replace("%player%", invitedPlayer.getName())));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.invite.invalid-player")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.invite.invalid-rank")));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("join")) {
                    String factionName = args[1];
                    boolean validName = false;
                    String uuid = null;
                    File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                    if (factions != null) {
                        for (File f : factions) {
                            YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                            if (file.getString("name").equals(factionName)) {
                                validName = true;
                                uuid = file.getString("uuid");
                            }
                        }
                    }

                    if (validName) {
                        if (Main.getInstance().invites.get(p).contains(uuid)) {
                            Players players = new Players(p.getUniqueId().toString());
                            if (players.get().getString("faction").equals("")) {
                                ArrayList<String> uuids = new ArrayList<>();
                                Faction f = new Faction(uuid);
                                players.get().set("faction", uuid);
                                players.save();
                                ArrayList<String> members = new ArrayList<>(f.get().getStringList("members"));
                                members.add(p.getUniqueId().toString());
                                f.get().set("members", members);
                                f.save();
                                uuids.addAll(f.get().getStringList("members"));
                                uuids.addAll(f.get().getStringList("captains"));
                                uuids.addAll(f.get().getStringList("coleaders"));
                                uuids.add(f.get().getString("leader"));
                                for (String id : uuids) {
                                    Player play = Bukkit.getPlayer(UUID.fromString(id));
                                    if (play != null) {
                                        play.sendMessage(C.chat(Locale.get().getString("command.faction.join.join-successful").replace("%name%", p.getName())));
                                    }
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.join.already-in-faction")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.join.no-invite")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.join.invalid-faction")));
                    }
                } else if (args[0].equalsIgnoreCase("promote")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    Players players = new Players(p.getUniqueId().toString());
                    String factionID = players.get().getString("faction");
                    if (player != null) {
                        Players additionalPlayer = new Players(player.getUniqueId().toString());
                        if (!player.getName().equals(p.getName())) {
                            if (additionalPlayer.get().getString("faction").equals(factionID)) {
                                Faction faction = new Faction(factionID);
                                ArrayList<String> coleaders = new ArrayList<>(faction.get().getStringList("coleaders"));
                                ArrayList<String> captains = new ArrayList<>(faction.get().getStringList("captains"));
                                ArrayList<String> members = new ArrayList<>(faction.get().getStringList("members"));
                                if (members.contains(player.getUniqueId().toString())) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.promote.captain.success").replace("%player%", player.getName())));
                                    player.sendMessage(C.chat(Locale.get().getString("command.faction.promote.captain.promoted")));
                                    members.remove(player.getUniqueId().toString());
                                    captains.add(player.getUniqueId().toString());
                                    faction.get().set("members", members);
                                    faction.get().set("captains", captains);
                                    faction.save();
                                } else if (captains.contains(player.getUniqueId().toString())) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.promote.coleader.success").replace("%player%", player.getName())));
                                    player.sendMessage(C.chat(Locale.get().getString("command.faction.promote.coleader.promoted")));
                                    captains.remove(player.getUniqueId().toString());
                                    coleaders.add(player.getUniqueId().toString());
                                    faction.get().set("captains", captains);
                                    faction.get().set("coleaders", coleaders);
                                    faction.save();
                                } else if (coleaders.contains(player.getUniqueId().toString())) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.promote.cant-promote-further").replace("%alias%", s).replace("%player%", player.getName())));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.promote.not-in-faction")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.promote.same-player")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.promote.invalid-player")));
                    }
                } else if (args[0].equalsIgnoreCase("demote")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    Players players = new Players(p.getUniqueId().toString());
                    String factionID = players.get().getString("faction");
                    if (player != null) {
                        Players additionalPlayer = new Players(player.getUniqueId().toString());
                        if (!player.getName().equals(p.getName())) {
                            if (additionalPlayer.get().getString("faction").equals(factionID)) {
                                Faction faction = new Faction(factionID);
                                ArrayList<String> coleaders = new ArrayList<>(faction.get().getStringList("coleaders"));
                                ArrayList<String> captains = new ArrayList<>(faction.get().getStringList("captains"));
                                ArrayList<String> members = new ArrayList<>(faction.get().getStringList("members"));
                                if (members.contains(player.getUniqueId().toString())) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.demote.cant-demote-further").replace("%alias%", s).replace("%player%", player.getName())));
                                } else if (coleaders.contains(player.getUniqueId().toString())) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.demote.captain.success").replace("%player%", player.getName())));
                                    player.sendMessage(C.chat(Locale.get().getString("command.faction.demote.captain.demoted")));
                                    captains.add(player.getUniqueId().toString());
                                    coleaders.remove(player.getUniqueId().toString());
                                    faction.get().set("captains", captains);
                                    faction.get().set("coleaders", coleaders);
                                    faction.save();
                                } else if (captains.contains(player.getUniqueId().toString())) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.demote.member.success").replace("%player%", player.getName())));
                                    player.sendMessage(C.chat(Locale.get().getString("command.faction.demote.member.demoted")));
                                    members.add(player.getUniqueId().toString());
                                    captains.remove(player.getUniqueId().toString());
                                    faction.get().set("captains", captains);
                                    faction.get().set("members", members);
                                    faction.save();
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.promote.not-in-faction")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.promote.same-player")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.promote.invalid-player")));
                    }
                } else if (args[0].equalsIgnoreCase("focus")) {
                    Players players = new Players(p.getUniqueId().toString());
                    String factionID = players.get().getString("faction");
                    Faction f = new Faction(factionID);
                    if (players.get().getString("faction").equals("")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.focus.no-faction")));
                    } else {
                        Player player = Bukkit.getPlayer(args[1]);
                        if (player != null) {
                            if (Main.getInstance().getConfig().getBoolean("settings.lunar-client.use-api")) {
                                Players additionalPlayer = new Players(player.getUniqueId().toString());
                                if (!player.getName().equals(p.getName())) {
                                    if (!additionalPlayer.get().getString("faction").equals(players.get().getString("faction"))) {
                                        double dtr = f.get().getDouble("dtr");
                                        String msg = "%dtr%";
                                        if (dtr <= 0) {
                                            msg = msg.replace("%dtr%", "&c" + String.valueOf(f.get().getDouble("dtr")));
                                        } else if (dtr <= 1) {
                                            msg = msg.replace("%dtr%", "&e" + String.valueOf(f.get().getDouble("dtr")));
                                        } else {
                                            msg = msg.replace("%dtr%", "&a" + String.valueOf(f.get().getDouble("dtr")));
                                        }
                                        Faction getName = new Faction(additionalPlayer.get().getString("faction"));
                                        Player leader = Bukkit.getPlayer(UUID.fromString(f.get().getString("leader")));
                                        ArrayList<String> name = new ArrayList<>();
                                        name.add(C.chat("&6[" + Lunar.get().getString("lunar.colors.focused.team-color") + getName.get().getString("name") + "&6]&r " + msg));
                                        name.add(C.chat(Lunar.get().getString("lunar.colors.focused.color") + player.getName()));
                                        if (leader != null) {
                                            Main.getInstance().lunarClientAPI.overrideNametag(player, name, leader);
                                            leader.sendMessage(C.chat(Locale.get().getString("command.faction.focus.focused").replace("%player%", player.getName())));
                                        }

                                        for (String play : f.get().getStringList("coleaders")) {
                                            Player d = Bukkit.getPlayer(UUID.fromString(play));
                                            if (d != null) {
                                                Main.getInstance().lunarClientAPI.overrideNametag(player, name, d);
                                                d.sendMessage(C.chat(Locale.get().getString("command.faction.focus.focused").replace("%player%", player.getName())));
                                            }
                                        }

                                        for (String play : f.get().getStringList("captains")) {
                                            Player d = Bukkit.getPlayer(UUID.fromString(play));
                                            if (d != null) {
                                                Main.getInstance().lunarClientAPI.overrideNametag(player, name, d);
                                                d.sendMessage(C.chat(Locale.get().getString("command.faction.focus.focused").replace("%player%", player.getName())));
                                            }
                                        }

                                        for (String play : f.get().getStringList("members")) {
                                            Player d = Bukkit.getPlayer(UUID.fromString(play));
                                            if (d != null) {
                                                Main.getInstance().lunarClientAPI.overrideNametag(player, name, d);
                                                d.sendMessage(C.chat(Locale.get().getString("command.faction.focus.focused").replace("%player%", player.getName())));
                                            }
                                        }

                                        Main.getInstance().focusedPlayer.put(factionID, player);
                                    } else {
                                        p.sendMessage(C.chat(Locale.get().getString("command.faction.focus.in-faction")));
                                    }
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.focus.same-player")));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("primary.lunar-only")));
                            }
                        } else {

                        }
                    }
                } else if (args[0].equalsIgnoreCase("who")) {
                    OfflinePlayer pl = Bukkit.getOfflinePlayer(args[1]);
                    Players players1 = new Players(pl.getUniqueId().toString());
                    if (players1.get().getString("faction") != null) {
                        if (!players1.get().getString("faction").equals("")) {
                            for (String msg : Messages.get().getStringList("faction.who.player")) {
                                if (msg.contains("%name%")) {
                                    String factionUUID = players1.get().getString("faction");
                                    Faction f = new Faction(factionUUID);
                                    String factionName = f.get().getString("name");
                                    msg = msg.replace("%name%", factionName);
                                }
                                String factionUUID = players1.get().getString("faction");
                                Faction f = new Faction(factionUUID);

                                int onlinePlayers = 0;
                                int totalPlayers = 0;
                                Player leader = Bukkit.getPlayer(UUID.fromString(f.get().getString("leader")));
                                if (leader != null) {
                                    onlinePlayers = onlinePlayers + 1;
                                    totalPlayers = totalPlayers + 1;
                                } else {
                                    totalPlayers = totalPlayers + 1;
                                }

                                for (String play : f.get().getStringList("coleaders")) {
                                    Player player = Bukkit.getPlayer(UUID.fromString(play));
                                    if (player != null) {
                                        onlinePlayers = onlinePlayers + 1;
                                        totalPlayers = totalPlayers + 1;
                                    } else {
                                        totalPlayers = totalPlayers + 1;
                                    }
                                }

                                for (String play : f.get().getStringList("captains")) {
                                    Player player = Bukkit.getPlayer(UUID.fromString(play));
                                    if (player != null) {
                                        onlinePlayers = onlinePlayers + 1;
                                        totalPlayers = totalPlayers + 1;
                                    } else {
                                        totalPlayers = totalPlayers + 1;
                                    }
                                }

                                for (String play : f.get().getStringList("members")) {
                                    Player player = Bukkit.getPlayer(UUID.fromString(play));
                                    if (player != null) {
                                        onlinePlayers = onlinePlayers + 1;
                                        totalPlayers = totalPlayers + 1;
                                    } else {
                                        totalPlayers = totalPlayers + 1;
                                    }
                                }

                                if (msg.contains("%online%")) {
                                    msg = msg.replace("%online%", String.valueOf(onlinePlayers));
                                }

                                if (msg.contains("%players%")) {
                                    msg = msg.replace("%players%", String.valueOf(totalPlayers));
                                }

                                if (msg.contains("%home%")) {
                                    if (f.get().isConfigurationSection("home")) {
                                        int x = f.get().getInt("home.x");
                                        int z = f.get().getInt("home.z");
                                        msg = msg.replace("%home%", String.valueOf(x) + ", " + String.valueOf(z));
                                    } else {
                                        msg = msg.replace("%home%", "None");
                                    }
                                }

                                if (msg.contains("%leader%")) {
                                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(f.get().getString("leader")));
                                    Players offPlayer = new Players(player.getUniqueId().toString());
                                    if (player.isOnline()) {
                                        msg = msg.replace("%leader%", "&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                    } else {
                                        if (offPlayer.get().getBoolean("deathBanned")) {
                                            msg = msg.replace("%leader%", "&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                        } else {
                                            msg = msg.replace("%leader%", "&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                        }
                                    }
                                }


                                if (msg.contains("%coleaders%")) {
                                    String result;
                                    ArrayList<String> stuff = new ArrayList<>();
                                    if (f.get().getStringList("coleaders").size() > 0) {
                                        for (String str : f.get().getStringList("coleaders")) {
                                            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                                            Players offPlayer = new Players(str);
                                            if (player.isOnline()) {
                                                stuff.add("&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            } else {
                                                if (offPlayer.get().getBoolean("deathBanned")) {
                                                    stuff.add("&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                } else {
                                                    stuff.add("&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                }
                                            }
                                        }
                                        result = String.join(", ", stuff);
                                        msg = msg.replace("%coleaders%", result);
                                    } else {
                                        continue;
                                    }
                                }

                                if (msg.contains("%captains%")) {
                                    String result;
                                    ArrayList<String> stuff = new ArrayList<>();
                                    if (f.get().getStringList("captains").size() > 0) {
                                        for (String str : f.get().getStringList("captains")) {
                                            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                                            Players offPlayer = new Players(str);
                                            if (player.isOnline()) {
                                                stuff.add("&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            } else {
                                                if (offPlayer.get().getBoolean("deathBanned")) {
                                                    stuff.add("&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                } else {
                                                    stuff.add("&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                }
                                            }
                                        }
                                        result = String.join(", ", stuff);
                                        msg = msg.replace("%captains%", result);
                                    } else {
                                        continue;
                                    }
                                }

                                if (msg.contains("%members%")) {
                                    String result;
                                    ArrayList<String> stuff = new ArrayList<>();
                                    if (f.get().getStringList("members").size() > 0) {
                                        for (String str : f.get().getStringList("members")) {
                                            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                                            Players offPlayer = new Players(str);
                                            if (player.isOnline()) {
                                                stuff.add("&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            } else {
                                                if (offPlayer.get().getBoolean("deathBanned")) {
                                                    stuff.add("&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                } else {
                                                    stuff.add("&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                }
                                            }
                                        }
                                        result = String.join(", ", stuff);
                                        msg = msg.replace("%members%", result);
                                    } else {
                                        continue;
                                    }
                                }

                                if (msg.contains("%balance%")) {
                                    msg = msg.replace("%balance%", String.valueOf(f.get().getInt("balance")));
                                }

                                if (msg.contains("%points%")) {
                                    msg = msg.replace("%points%", String.valueOf(f.get().getInt("points")));
                                }

                                if (msg.contains("%kothcaptures%")) {
                                    msg = msg.replace("%kothcaptures%", String.valueOf(f.get().getInt("kothcaptures")));
                                }

                                if (msg.contains("%dtr%")) {
                                    double dtr = f.get().getDouble("dtr");
                                    if (dtr <= 0) {
                                        msg = msg.replace("%dtr%", "&c" + String.valueOf(f.get().getDouble("dtr")));
                                    } else if (dtr <= 1) {
                                        msg = msg.replace("%dtr%", "&e" + String.valueOf(f.get().getDouble("dtr")));
                                    } else {
                                        msg = msg.replace("%dtr%", "&a" + String.valueOf(f.get().getDouble("dtr")));
                                    }
                                }

                                if (msg.contains("%regen%")) {
                                    if (f.get().getBoolean("regening")) {
                                        // for now
                                        continue;
                                    } else {
                                        continue;
                                    }
                                }

                                if (msg.contains("%lives%")) {
                                    msg = msg.replace("%lives%", String.valueOf(f.get().getInt("lives")));
                                }

                                if (msg.contains("%announcement%")) {
                                    if (f.get().getString("announcement").equals("")) {
                                        continue;
                                    } else {
                                        msg = msg.replace("%announcement%", String.valueOf(f.get().getString("announcement")));
                                    }
                                }



                                p.sendMessage(C.chat(msg));
                            }
                        }
                    } else {
                        String fName = args[1].toLowerCase(java.util.Locale.ROOT);

                        boolean validName = false;
                        String uuid = null;
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(fName)) {
                                    validName = true;
                                    uuid = file.getString("uuid");
                                }
                            }
                        }

                        if (validName) {
                            Faction f = new Faction(uuid);
                            switch (f.get().getString("type")) {
                                case "PLAYER":
                                    for (String msg : Messages.get().getStringList("faction.who.player")) {
                                        if (msg.contains("%name%")) {
                                            String factionName = f.get().getString("name");
                                            msg = msg.replace("%name%", factionName);
                                        }

                                        int onlinePlayers = 0;
                                        int totalPlayers = 0;
                                        Player leader = Bukkit.getPlayer(UUID.fromString(f.get().getString("leader")));
                                        if (leader != null) {
                                            onlinePlayers = onlinePlayers + 1;
                                            totalPlayers = totalPlayers + 1;
                                        } else {
                                            totalPlayers = totalPlayers + 1;
                                        }

                                        for (String play : f.get().getStringList("coleaders")) {
                                            Player player = Bukkit.getPlayer(UUID.fromString(play));
                                            if (player != null) {
                                                onlinePlayers = onlinePlayers + 1;
                                                totalPlayers = totalPlayers + 1;
                                            } else {
                                                totalPlayers = totalPlayers + 1;
                                            }
                                        }

                                        for (String play : f.get().getStringList("captains")) {
                                            Player player = Bukkit.getPlayer(UUID.fromString(play));
                                            if (player != null) {
                                                onlinePlayers = onlinePlayers + 1;
                                                totalPlayers = totalPlayers + 1;
                                            } else {
                                                totalPlayers = totalPlayers + 1;
                                            }
                                        }

                                        for (String play : f.get().getStringList("members")) {
                                            Player player = Bukkit.getPlayer(UUID.fromString(play));
                                            if (player != null) {
                                                onlinePlayers = onlinePlayers + 1;
                                                totalPlayers = totalPlayers + 1;
                                            } else {
                                                totalPlayers = totalPlayers + 1;
                                            }
                                        }

                                        if (msg.contains("%online%")) {
                                            msg = msg.replace("%online%", String.valueOf(onlinePlayers));
                                        }

                                        if (msg.contains("%players%")) {
                                            msg = msg.replace("%players%", String.valueOf(totalPlayers));
                                        }

                                        if (msg.contains("%home%")) {
                                            if (f.get().isConfigurationSection("home")) {
                                                int x = f.get().getInt("home.x");
                                                int z = f.get().getInt("home.z");
                                                msg = msg.replace("%home%", String.valueOf(x) + ", " + String.valueOf(z));
                                            } else {
                                                msg = msg.replace("%home%", "None");
                                            }
                                        }

                                        if (msg.contains("%leader%")) {
                                            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(f.get().getString("leader")));
                                            Players offPlayer = new Players(player.getUniqueId().toString());
                                            if (player.isOnline()) {
                                                msg = msg.replace("%leader%", "&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                            } else {
                                                if (offPlayer.get().getBoolean("deathBanned")) {
                                                    msg = msg.replace("%leader%", "&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                } else {
                                                    msg = msg.replace("%leader%", "&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                }
                                            }
                                        }


                                        if (msg.contains("%coleaders%")) {
                                            String result;
                                            ArrayList<String> stuff = new ArrayList<>();
                                            if (f.get().getStringList("coleaders").size() > 0) {
                                                for (String str : f.get().getStringList("coleaders")) {
                                                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                                                    Players offPlayer = new Players(str);
                                                    Players players = new Players(str);
                                                    if (player.isOnline()) {
                                                        stuff.add("&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                    } else {
                                                        if (players.get().getBoolean("deathBanned")) {
                                                            stuff.add("&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                        } else {
                                                            stuff.add("&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                        }
                                                    }
                                                }
                                                result = String.join(", ", stuff);
                                                msg = msg.replace("%coleaders%", result);
                                            } else {
                                                continue;
                                            }
                                        }

                                        if (msg.contains("%captains%")) {
                                            String result;
                                            ArrayList<String> stuff = new ArrayList<>();
                                            if (f.get().getStringList("captains").size() > 0) {
                                                for (String str : f.get().getStringList("captains")) {
                                                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                                                    Players offPlayer = new Players(str);
                                                    Players players = new Players(str);
                                                    if (player.isOnline()) {
                                                        stuff.add("&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                    } else {
                                                        if (players.get().getBoolean("deathBanned")) {
                                                            stuff.add("&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                        } else {
                                                            stuff.add("&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                        }
                                                    }
                                                }
                                                result = String.join(", ", stuff);
                                                msg = msg.replace("%captains%", result);
                                            } else {
                                                continue;
                                            }
                                        }

                                        if (msg.contains("%members%")) {
                                            String result;
                                            ArrayList<String> stuff = new ArrayList<>();
                                            if (f.get().getStringList("members").size() > 0) {
                                                for (String str : f.get().getStringList("members")) {
                                                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                                                    Players offPlayer = new Players(str);
                                                    Players players = new Players(str);
                                                    if (player.isOnline()) {
                                                        stuff.add("&a" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                    } else {
                                                        if (players.get().getBoolean("deathBanned")) {
                                                            stuff.add("&c" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                        } else {
                                                            stuff.add("&7" + player.getName() + "&7[&e" + offPlayer.get().getInt("kills") + "&7]");
                                                        }
                                                    }
                                                }
                                                result = String.join(", ", stuff);
                                                msg = msg.replace("%members%", result);
                                            } else {
                                                continue;
                                            }
                                        }

                                        if (msg.contains("%balance%")) {
                                            msg = msg.replace("%balance%", String.valueOf(f.get().getInt("balance")));
                                        }

                                        if (msg.contains("%points%")) {
                                            msg = msg.replace("%points%", String.valueOf(f.get().getInt("points")));
                                        }

                                        if (msg.contains("%kothcaptures%")) {
                                            msg = msg.replace("%kothcaptures%", String.valueOf(f.get().getInt("kothcaptures")));
                                        }

                                        if (msg.contains("%dtr%")) {
                                            double dtr = f.get().getDouble("dtr");
                                            if (dtr <= 0) {
                                                msg = msg.replace("%dtr%", "&c" + String.valueOf(f.get().getDouble("dtr")));
                                            } else if (dtr <= 1) {
                                                msg = msg.replace("%dtr%", "&e" + String.valueOf(f.get().getDouble("dtr")));
                                            } else {
                                                msg = msg.replace("%dtr%", "&a" + String.valueOf(f.get().getDouble("dtr")));
                                            }
                                        }

                                        if (msg.contains("%regen%")) {
                                            if (f.get().getBoolean("regening")) {
                                                // for now
                                                continue;
                                            } else {
                                                continue;
                                            }
                                        }

                                        if (msg.contains("%lives%")) {
                                            msg = msg.replace("%lives%", String.valueOf(f.get().getInt("lives")));
                                        }

                                        if (msg.contains("%announcement%")) {
                                            if (f.get().getString("announcement").equals("")) {
                                                continue;
                                            } else {
                                                msg = msg.replace("%announcement%", String.valueOf(f.get().getString("announcement")));
                                            }
                                        }


                                        p.sendMessage(C.chat(msg));
                                    }
                                    break;
                                case "ROAD":
                                    for (String msg : Messages.get().getStringList("faction.who.system")) {
                                        if (msg.contains("%name%")) {
                                            String factionName = C.convertColorCode(f.get().getString("color")) + f.get().getString("name") + " Road";
                                            msg = msg.replace("%name%", factionName);
                                        }

                                        if (msg.contains("%home%")) {
                                            if (f.get().isConfigurationSection("location")) {

                                            } else {
                                                msg = msg.replace("%home%", "None");
                                            }
                                        }


                                        p.sendMessage(C.chat(msg));
                                    }
                                    break;
                                case "KOTH":
                                    for (String msg : Messages.get().getStringList("faction.who.system")) {
                                        if (msg.contains("%name%")) {
                                            String factionName = C.convertColorCode(f.get().getString("color")) + f.get().getString("name") + " KOTH";
                                            msg = msg.replace("%name%", factionName);
                                        }

                                        if (msg.contains("%home%")) {
                                            if (f.get().isConfigurationSection("location")) {

                                            } else {
                                                msg = msg.replace("%home%", "None");
                                            }
                                        }


                                        p.sendMessage(C.chat(msg));
                                    }
                                    break;
                                case "MOUNTAIN":
                                    for (String msg : Messages.get().getStringList("faction.who.system")) {
                                        if (msg.contains("%name%")) {
                                            String factionName = C.convertColorCode(f.get().getString("color")) + f.get().getString("name") + " Mountain";
                                            msg = msg.replace("%name%", factionName);
                                        }

                                        if (msg.contains("%home%")) {
                                            if (f.get().isConfigurationSection("location")) {

                                            } else {
                                                msg = msg.replace("%home%", "None");
                                            }
                                        }


                                        p.sendMessage(C.chat(msg));
                                    }
                                    break;
                                default:
                                    for (String msg : Messages.get().getStringList("faction.who.system")) {
                                        if (msg.contains("%name%")) {
                                            String factionName = C.convertColorCode(f.get().getString("color")) + f.get().getString("name");
                                            msg = msg.replace("%name%", factionName);
                                        }

                                        if (msg.contains("%home%")) {
                                            if (f.get().isConfigurationSection("location")) {

                                            } else {
                                                msg = msg.replace("%home%", "None");
                                            }
                                        }


                                        p.sendMessage(C.chat(msg));
                                    }
                                    break;
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.who.no-existing")));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("deposit") || args[0].equalsIgnoreCase("d")) {
                    try {
                        double value = Double.parseDouble(args[1]);
                        if (isValidNumber(value)) {
                            Players players = new Players(p.getUniqueId().toString());
                            if (!players.get().getString("faction").equals("")) {
                                Economy economy = new Economy(p.getUniqueId().toString());
                                if (economy.has(value)) {
                                    Faction faction = new Faction(players.get().getString("faction"));
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.success").replace("%amount%", String.valueOf(value))));
                                    for (Player d : faction.getAllOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.deposited").replace("%name%", p.getName()).replace("%amount%", String.valueOf(value))));
                                    }
                                    faction.get().set("balance", faction.get().getDouble("balance") + value);
                                    faction.save();
                                    economy.withdrawBalance(value);
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.invalid-amount")));
                                }

                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.no-faction")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.invalid-amount")));
                        }
                    } catch (NumberFormatException e) {
                        if (args[1].equalsIgnoreCase("all")) {
                            Players players = new Players(p.getUniqueId().toString());
                            double value = players.get().getDouble("balance");
                            if (isValidNumber(value)) {
                                if (!players.get().getString("faction").equals("")) {
                                    Faction faction = new Faction(players.get().getString("faction"));
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.success").replace("%amount%", String.valueOf(value))));
                                    for (Player d : faction.getAllOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.deposited").replace("%name%", p.getName()).replace("%amount%", String.valueOf(value))));
                                    }
                                    faction.get().set("balance", faction.get().getDouble("balance") + value);
                                    faction.save();

                                    Economy economy = new Economy(p.getUniqueId().toString());
                                    economy.setBalance(0.0);
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.no-faction")));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.invalid-amount")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.deposit.invalid-amount")));
                        }
                    }
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("settype")) {
                    if (p.hasPermission("hcfactions.admin") || p.hasPermission("hcfactions.command.faction.settype")) {
                        String factionName = args[1];
                        String id = "";
                        boolean valid = false;
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(factionName.toLowerCase(java.util.Locale.ROOT))) {
                                    valid = true;
                                    id = file.getString("uuid");
                                }
                            }
                        }
                        if (valid) {
                            Faction faction = new Faction(id);
                            switch (args[2].toLowerCase()) {
                                case "mountain":
                                    faction.get().set("type", "MOUNTAIN");
                                    faction.save();
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.settype.set").replace("%type%", "MOUNTAIN").replace("%faction%", factionName)));
                                    break;
                                case "koth":
                                    faction.get().set("type", "KOTH");
                                    faction.save();
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.settype.set").replace("%type%", "KOTH").replace("%faction%", factionName)));
                                    break;
                                case "player":
                                    faction.get().set("type", "PLAYER");
                                    faction.save();
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.settype.set").replace("%type%", "PLAYER").replace("%faction%", factionName)));
                                    break;
                                case "system":
                                    faction.get().set("type", "SYSTEM");
                                    faction.save();
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.settype.set").replace("%type%", "SYSTEM").replace("%faction%", factionName)));
                                    break;
                                case "safezone":
                                    faction.get().set("type", "SAFEZONE");
                                    faction.save();
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.settype.set").replace("%type%", "SAFEZONE").replace("%faction%", factionName)));
                                    break;
                                case "road":
                                    faction.get().set("type", "ROAD");
                                    faction.save();
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.settype.set").replace("%type%", "ROAD").replace("%faction%", factionName)));
                                    break;
                                default:
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.settype.invalid-type")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.settype.invalid-faction")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                    }
                } else if (args[0].equalsIgnoreCase("setcolor")) {
                    if (p.hasPermission("hcfactions.admin") || p.hasPermission("hcfactions.command.faction.setcolor")) {
                        String factionName = args[1];
                        String id = "";
                        boolean valid = false;
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(factionName.toLowerCase(java.util.Locale.ROOT))) {
                                    valid = true;
                                    id = file.getString("uuid");
                                }
                            }
                        }
                        if (valid) {
                            Faction faction = new Faction(id);
                            if (C.isValidColor(args[2])) {
                                if (!faction.get().getString("type").equals("PLAYER")) {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.setcolor.set").replace("%faction%", factionName).replace("%set-color%", C.convertColorCode(args[2]))));
                                    faction.get().set("color", args[2].toUpperCase());
                                    faction.save();
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.setcolor.invalid-type")));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.setcolor.invalid-color")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.setcolor.invalid")));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("setdtr")) {
                    if (p.hasPermission("hcfactions.command.faction.setdtr") || p.hasPermission("hcfactions.admin")) {
                        String fName = args[1].toLowerCase(java.util.Locale.ROOT);
                        boolean validName = false;
                        String uuid = null;
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.getString("name").toLowerCase(java.util.Locale.ROOT).equals(fName)) {
                                    validName = true;
                                    uuid = file.getString("uuid");
                                }
                            }
                        }

                        if (validName) {
                            try {
                                double dtr = Double.parseDouble(args[2]);
                                if (isValidDTR(dtr)) {
                                    Faction faction = new Faction(uuid);
                                    faction.get().set("dtr", dtr);
                                    faction.save();
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.setdtr.success").replace("%dtr%", String.valueOf(dtr)).replace("%faction%", faction.get().getString("name"))));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.faction.setdtr.invalid-amount")));
                                    return true;
                                }
                            } catch (NumberFormatException e) {
                                p.sendMessage(C.chat(Locale.get().getString("command.faction.setdtr.invalid-amount")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.setdtr.not-faction")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                    }
                }
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }

    public Boolean isValidDTR(Double v) {
        if (v > -1) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isValidNumber(Double v) {
        String number = String.valueOf(v);
        String[] parts = number.split("\\.");
        String other = parts[1];
        other = other.replace(".", "");
        if (other.length() > 2) {
            return false;
        } else {
            if (v > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    private String getFactionInClaim(Player p) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0") && file.isConfigurationSection("claims.1")) {

                } else if (file.isConfigurationSection("claims.0")) {
                    Location locationOne = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideOne.x"), file.getDouble("claims.0.sideOne.y"), file.getDouble("claims.0.sideOne.z"));
                    Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getDouble("claims.0.sideTwo.x"), file.getDouble("claims.0.sideTwo.y"), file.getDouble("claims.0.sideTwo.z"));
                    Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
                    Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                    for (Block b : cuboid.getBlocks()) {
                        if (block.getX() == b.getX() && block.getZ() == b.getZ()) {
                            return file.getString("uuid");
                        }
                    }
                }
            }
        }

        return null;
    }
}
