package me.sub.hcfactions.Events.Player;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
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
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;
import java.util.ArrayList;

public class FactionMultimoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        ArrayList<String> newClaim = new ArrayList<>();
        if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ() || e.getFrom().getY() != e.getTo().getY()) {
                if (e.getTo().getX() > 0 && e.getTo().getZ() > 0) {
                    if (e.getTo().getX() >= Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getX() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getZ() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }

                    } else if (e.getTo().getZ() >= Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getZ() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getX() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getX() <= Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getZ() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getZ() <= Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getX() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else {
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.isConfigurationSection("claims.0")) {
                                    for (String fileNumber : file.getConfigurationSection("claims").getKeys(false)) {
                                        if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NORMAL)) {
                                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                                            for (Block b : cuboid.getBlocks()) {
                                                Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
                                                if (block.getX() == b.getX() && block.getZ() == b.getZ()) {
                                                    if (!Main.getInstance().currentFactionLocation.containsKey(p.getUniqueId())) {
                                                        Main.getInstance().currentFactionLocation.put(p.getUniqueId(), file.getString("uuid"));
                                                        if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        } else {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        }
                                                    } else {
                                                        if (!file.getString("uuid").equals(Main.getInstance().currentFactionLocation.get(p.getUniqueId()))) {
                                                            Faction faction = new Faction(Main.getInstance().currentFactionLocation.get(p.getUniqueId()));
                                                            Main.getInstance().currentFactionLocation.put(p.getUniqueId(), file.getString("uuid"));
                                                            if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            } else {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (Main.getInstance().currentFactionLocation.containsKey(p.getUniqueId())) {
                                if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                    String factionID = Main.getInstance().currentFactionLocation.get(p.getUniqueId());
                                    Main.getInstance().currentFactionLocation.remove(p.getUniqueId());
                                    Faction faction = new Faction(factionID);
                                    if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    } else {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (e.getTo().getX() < 0 && e.getTo().getZ() < 0) {
                    if (e.getTo().getX() <= -Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getX() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getZ() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getZ() >= -Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getX() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getZ() <= -Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getZ() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getX() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getX() >= -Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getZ() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else {
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.isConfigurationSection("claims.0")) {
                                    for (String fileNumber : file.getConfigurationSection("claims").getKeys(false)) {
                                        if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NORMAL)) {
                                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                                            for (Block b : cuboid.getBlocks()) {
                                                Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
                                                if (block.getX() == b.getX() && block.getZ() == b.getZ()) {
                                                    if (!Main.getInstance().currentFactionLocation.containsKey(p.getUniqueId())) {
                                                        Main.getInstance().currentFactionLocation.put(p.getUniqueId(), file.getString("uuid"));
                                                        if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        } else {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        }
                                                    } else {
                                                        if (!file.getString("uuid").equals(Main.getInstance().currentFactionLocation.get(p.getUniqueId()))) {
                                                            Faction faction = new Faction(Main.getInstance().currentFactionLocation.get(p.getUniqueId()));
                                                            Main.getInstance().currentFactionLocation.put(p.getUniqueId(), file.getString("uuid"));
                                                            if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            } else {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (Main.getInstance().currentFactionLocation.containsKey(p.getUniqueId())) {
                                if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                    String factionID = Main.getInstance().currentFactionLocation.get(p.getUniqueId());
                                    Main.getInstance().currentFactionLocation.remove(p.getUniqueId());
                                    Faction faction = new Faction(factionID);
                                    if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    } else {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (e.getTo().getX() < 0 && e.getTo().getZ() > 0) {
                    if (e.getTo().getX() >= -Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getZ() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getX() <= -Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getX() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getZ() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getZ() <= Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getX() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getZ() >= Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getZ() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getX() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else {
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.isConfigurationSection("claims.0")) {
                                    for (String fileNumber : file.getConfigurationSection("claims").getKeys(false)) {
                                        if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NORMAL)) {
                                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                                            for (Block b : cuboid.getBlocks()) {
                                                Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
                                                if (block.getX() == b.getX() && block.getZ() == b.getZ()) {
                                                    if (!Main.getInstance().currentFactionLocation.containsKey(p.getUniqueId())) {
                                                        Main.getInstance().currentFactionLocation.put(p.getUniqueId(), file.getString("uuid"));
                                                        if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        } else {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        }
                                                    } else {
                                                        if (!file.getString("uuid").equals(Main.getInstance().currentFactionLocation.get(p.getUniqueId()))) {
                                                            Faction faction = new Faction(Main.getInstance().currentFactionLocation.get(p.getUniqueId()));
                                                            Main.getInstance().currentFactionLocation.put(p.getUniqueId(), file.getString("uuid"));
                                                            if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            } else {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (Main.getInstance().currentFactionLocation.containsKey(p.getUniqueId())) {
                                if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                    String factionID = Main.getInstance().currentFactionLocation.get(p.getUniqueId());
                                    Main.getInstance().currentFactionLocation.remove(p.getUniqueId());
                                    Faction faction = new Faction(factionID);
                                    if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    } else {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (e.getTo().getX() > 0 && e.getTo().getZ() < 0) {
                    if (e.getTo().getZ() >= -Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getX() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getZ() <= -Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getZ() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getX() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getX() <= Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else if (e.getTo().getX() >= Main.getInstance().getConfig().getInt("worlds.default.warzone") && e.getFrom().getX() < Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                        if (e.getFrom().getZ() > -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                if (s.contains("%leave-name%")) {
                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                }
                                if (s.contains("%isdeathban-leave%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }
                                if (s.contains("%enter-name%")) {
                                    s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                }
                                if (s.contains("%isdeathban-enter%")) {
                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                    } else {
                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                    }
                                }

                                newClaim.add(C.chat(s));
                            }

                            for (String s : newClaim) {
                                p.sendMessage(s);
                            }
                        }
                    } else {
                        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
                        if (factions != null) {
                            for (File f : factions) {
                                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                                if (file.isConfigurationSection("claims.0")) {
                                    for (String fileNumber : file.getConfigurationSection("claims").getKeys(false)) {
                                        if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NORMAL)) {
                                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                                            for (Block b : cuboid.getBlocks()) {
                                                Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
                                                if (block.getX() == b.getX() && block.getZ() == b.getZ()) {
                                                    if (!Main.getInstance().currentFactionLocation.containsKey(p.getUniqueId())) {
                                                        Main.getInstance().currentFactionLocation.put(p.getUniqueId(), file.getString("uuid"));
                                                        if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        } else {
                                                            ArrayList<String> sector = new ArrayList<>();
                                                            Players players = new Players(p.getUniqueId().toString());
                                                            for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                if (s.contains("%leave-name%")) {
                                                                    s = s.replace("%leave-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                                                }
                                                                if (s.contains("%isdeathban-leave%")) {
                                                                    if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }
                                                                if (s.contains("%enter-name%")) {
                                                                    if (file.getString("color") != null) {
                                                                        if (C.isValidColor(file.getString("color"))) {
                                                                            if (file.getString("type").equals("KOTH")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                            } else if (file.getString("type").equals("ROAD")) {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                            } else {
                                                                                s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                        }
                                                                    } else {
                                                                        if (players.hasFaction()) {
                                                                            if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                        }
                                                                    }
                                                                }
                                                                if (s.contains("%isdeathban-enter%")) {
                                                                    if (file.getBoolean("deathban")) {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                    } else {
                                                                        s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                    }
                                                                }

                                                                sector.add(C.chat(s));
                                                            }

                                                            for (String s : sector) {
                                                                p.sendMessage(s);
                                                            }
                                                        }
                                                    } else {
                                                        if (!file.getString("uuid").equals(Main.getInstance().currentFactionLocation.get(p.getUniqueId()))) {
                                                            Faction faction = new Faction(Main.getInstance().currentFactionLocation.get(p.getUniqueId()));
                                                            Main.getInstance().currentFactionLocation.put(p.getUniqueId(), file.getString("uuid"));
                                                            if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            } else {
                                                                ArrayList<String> sector = new ArrayList<>();
                                                                Players players = new Players(p.getUniqueId().toString());
                                                                for (String s : Locale.get().getStringList("move.new-claim")) {
                                                                    if (s.contains("%leave-name%")) {
                                                                        if (faction.get().getString("color") != null) {
                                                                            if (C.isValidColor(faction.get().getString("color"))) {
                                                                                if (faction.get().getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                                                } else if (faction.get().getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-leave%")) {
                                                                        if (faction.get().getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }
                                                                    if (s.contains("%enter-name%")) {
                                                                        if (file.getString("color") != null) {
                                                                            if (C.isValidColor(file.getString("color"))) {
                                                                                if (file.getString("type").equals("KOTH")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " KOTH");
                                                                                } else if (file.getString("type").equals("ROAD")) {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name") + " Road");
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", C.convertColorCode(file.getString("color")) + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", "&c" + file.getString("name"));
                                                                            }
                                                                        } else {
                                                                            if (players.hasFaction()) {
                                                                                if (players.getFaction().get().getString("uuid").equals(file.getString("uuid"))) {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.teammate") + file.getString("name"));
                                                                                } else {
                                                                                    s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                                }
                                                                            } else {
                                                                                s = s.replace("%enter-name%", Locale.get().getString("chat.format.public.colors.enemy") + file.getString("name"));
                                                                            }
                                                                        }
                                                                    }
                                                                    if (s.contains("%isdeathban-enter%")) {
                                                                        if (file.getBoolean("deathban")) {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                                        } else {
                                                                            s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                                        }
                                                                    }

                                                                    sector.add(C.chat(s));
                                                                }

                                                                for (String s : sector) {
                                                                    p.sendMessage(s);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (Main.getInstance().currentFactionLocation.containsKey(p.getUniqueId())) {
                                if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                    String factionID = Main.getInstance().currentFactionLocation.get(p.getUniqueId());
                                    Main.getInstance().currentFactionLocation.remove(p.getUniqueId());
                                    Faction faction = new Faction(factionID);
                                    if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.wilderness.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    } else {
                                        ArrayList<String> sector = new ArrayList<>();
                                        Players players = new Players(p.getUniqueId().toString());
                                        for (String s : Locale.get().getStringList("move.new-claim")) {
                                            if (s.contains("%enter-name%")) {
                                                s = s.replace("%enter-name%", Main.getInstance().getConfig().getString("claim.warzone.name"));
                                            }
                                            if (s.contains("%isdeathban-enter%")) {
                                                if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-enter%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }
                                            if (s.contains("%leave-name%")) {
                                                if (faction.get().getString("color") != null) {
                                                    if (C.isValidColor(faction.get().getString("color"))) {
                                                        if (faction.get().getString("type").equals("KOTH")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                                        } else if (faction.get().getString("type").equals("ROAD")) {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                                        } else {
                                                            s = s.replace("%leave-name%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", "&c" + faction.get().getString("name"));
                                                    }
                                                } else {
                                                    if (players.hasFaction()) {
                                                        if (players.getFaction().get().getString("uuid").equals(faction.get().getString("uuid"))) {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.teammate") + faction.get().getString("name"));
                                                        } else {
                                                            s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                        }
                                                    } else {
                                                        s = s.replace("%leave-name%", Locale.get().getString("chat.format.public.colors.enemy") + faction.get().getString("name"));
                                                    }
                                                }
                                            }
                                            if (s.contains("%isdeathban-leave%")) {
                                                if (faction.get().getBoolean("deathban")) {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.deathban"));
                                                } else {
                                                    s = s.replace("%isdeathban-leave%", Main.getInstance().getConfig().getString("claim.not-deathban"));
                                                }
                                            }

                                            sector.add(C.chat(s));
                                        }

                                        for (String s : sector) {
                                            p.sendMessage(s);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
