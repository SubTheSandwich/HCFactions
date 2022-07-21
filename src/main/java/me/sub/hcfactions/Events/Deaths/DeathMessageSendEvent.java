package me.sub.hcfactions.Events.Deaths;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import me.sub.hcfactions.Utils.Deathban.Deathban;
import me.sub.hcfactions.Utils.Economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DeathMessageSendEvent implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Villager) {
            Villager v = (Villager) e.getEntity();
            if (v.getCustomName() != null) {
                String name = C.strip(v.getCustomName());
                Players players = new Players(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
                int newDeaths = players.get().getInt("deaths") + 1;
                players.get().set("deaths", newDeaths);
                players.save();
                Main.getInstance().loggedDeath.add(Bukkit.getOfflinePlayer(name).getUniqueId());
                for (ItemStack i : Main.getInstance().logoutArmorContents.get(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                    if (i != null) {
                        v.getWorld().dropItemNaturally(v.getLocation(), i);
                    }
                }
                for (ItemStack i : Main.getInstance().logoutContents.get(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                    if (i != null) {
                        v.getWorld().dropItemNaturally(v.getLocation(), i);
                    }
                }
                if (v.getKiller() == null) {
                    for (Player d : Bukkit.getOnlinePlayers()) {
                        Players settings = new Players(d.getUniqueId().toString());
                        if (settings.get().getBoolean("settings.deathMessages")) {
                            d.sendMessage(C.chat(Locale.get().getString("deathmessage.logger.unknown").replace("%dead%", name).replace("%dead_kills%", String.valueOf(players.get().getInt("kills")))));
                        }
                    }
                } else {
                    Players players2 = new Players(v.getKiller().getUniqueId().toString());
                    int newKills = players2.get().getInt("kills") + 1;
                    players2.get().set("kills", newKills);
                    players2.save();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Players players1 = new Players(p.getUniqueId().toString());
                        if (players1.get().getBoolean("settings.deathMessages")) {
                            p.sendMessage(C.chat(Locale.get().getString("deathmessage.logger.player").replace("%dead%", name).replace("%dead_kills%", String.valueOf(players.get().getInt("kills"))).replace("%killer%", p.getName()).replace("%killer_kills%", String.valueOf(newKills))));
                        }
                    }
                }

                if (v.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || v.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                    if (containsClaim(v.getLocation())) {
                        Faction faction = new Faction(getFactionInBlockClaim(v.getLocation()));
                        if (faction.get().getBoolean("deathban")) {
                            Deathban deathban = new Deathban(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
                            deathban.createDeathban();
                        }
                    } else {
                        if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {;
                            Deathban deathban = new Deathban(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
                            deathban.createDeathban();
                        }
                    }
                } else if (v.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || v.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                    if (containsClaim(v.getLocation())) {
                        Faction faction = new Faction(getFactionInBlockClaim(v.getLocation()));
                        if (faction.get().getBoolean("deathban")) {
                            Deathban deathban = new Deathban(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
                            deathban.createDeathban();
                        }
                    } else {
                        if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                            Deathban deathban = new Deathban(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
                            deathban.createDeathban();
                        }
                    }
                } else {
                    if (containsClaim(v.getLocation())) {
                        Faction faction = new Faction(getFactionInBlockClaim(v.getLocation()));
                        if (faction.get().getBoolean("deathban")) {
                            Deathban deathban = new Deathban(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
                            deathban.createDeathban();
                        }
                    } else {
                        if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                            Deathban deathban = new Deathban(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
                            deathban.createDeathban();
                        }
                    }
                }

                v.remove();
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        Player p = e.getEntity();
        p.getWorld().strikeLightningEffect(p.getLocation());
        if (Main.getInstance().combatTimer.containsKey(p.getUniqueId())) {
            Main.getInstance().combatTimer.remove(p.getUniqueId());
            Main.getInstance().ticking.remove(p.getUniqueId());
        }



        String msg = "";

        Players players = new Players(p.getUniqueId().toString());
        int newDeaths = players.get().getInt("deaths") + 1;
        players.get().set("deaths", newDeaths);
        players.save();

        Main.getInstance().savedInventoryArmorDeath.put(p.getUniqueId(), p.getInventory().getArmorContents());
        Main.getInstance().savedInventoryContentsDeath.put(p.getUniqueId(), p.getInventory().getContents());



        switch (p.getLastDamageCause().getCause()) {
            case ENTITY_ATTACK:
                Entity entity = p.getLastDamageCause().getEntity();
                if (entity instanceof Player) {
                    Player killer = e.getEntity().getKiller();
                    if (killer != null) {

                        Economy deadPlayer = new Economy(p.getUniqueId().toString());
                        Economy killerPlayer = new Economy(killer.getUniqueId().toString());

                        if (deadPlayer.getBalance() > 0) {
                            double deposited = deadPlayer.getBalance();
                            killerPlayer.depositBalance(deadPlayer.getBalance());
                            deadPlayer.withdrawBalance(deposited);
                            killer.sendMessage(C.chat(Locale.get().getString("deathmessage.money-earn").replace("%player%", p.getName()).replace("%money%", String.valueOf(Cooldown.round(deposited, 1)))));
                        }


                        Players kill = new Players(killer.getUniqueId().toString());
                        int newKills = kill.get().getInt("kills") + 1;
                        kill.get().set("kills", newKills);
                        kill.save();
                        msg = Locale.get().getString("deathmessage.entity-attack.player");
                        msg = msg.replace("%dead%", p.getName());
                        msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                        msg = msg.replace("%killer%", killer.getName());
                        msg = msg.replace("%killer_kills%", String.valueOf(newKills));
                        if (killer.getItemInHand() != null) {
                            if (killer.getItemInHand().getType().equals(Material.AIR)) {
                                msg = Locale.get().getString("deathmessage.entity-attack.player-noitem");
                                msg = msg.replace("%dead%", p.getName());
                                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                                msg = msg.replace("%killer%", killer.getName());
                                msg = msg.replace("%killer_kills%", String.valueOf(newKills));
                            } else {
                                if (killer.getItemInHand().getItemMeta().getDisplayName() != null) {
                                    msg = msg.replace("%item%", killer.getInventory().getItemInHand().getItemMeta().getDisplayName());
                                } else {
                                    msg = msg.replace("%item%", killer.getInventory().getItemInHand().getType().toString().replace("_", " "));
                                }
                            }
                        } else {
                            msg = Locale.get().getString("deathmessage.entity-attack.player-noitem");
                            msg = msg.replace("%dead%", p.getName());
                            msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                            msg = msg.replace("%killer%", killer.getName());
                            msg = msg.replace("%killer_kills%", String.valueOf(newKills));
                        }
                    } else {
                        String str = entity.getType().toString();
                        String output = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase(java.util.Locale.ROOT);
                        msg = C.chat(Locale.get().getString("deathmessage.entity-attack.entity").replace("%dead%", p.getName()).replace("%dead_kills%", String.valueOf(players.get().getInt("kills"))).replace("%killer%", output));
                    }
                } else {
                    String str = entity.getType().toString();
                    String output = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase(java.util.Locale.ROOT);
                    msg = C.chat(Locale.get().getString("deathmessage.entity-attack.entity").replace("%dead%", p.getName()).replace("%dead_kills%", String.valueOf(players.get().getInt("kills"))).replace("%killer%", output));
                }
                break;
            case FALL:
                msg = Locale.get().getString("deathmessage.fall");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case ENTITY_EXPLOSION:
                msg = Locale.get().getString("deathmessage.entity-explosion");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case BLOCK_EXPLOSION:
                msg = Locale.get().getString("deathmessage.block-explosion");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case SUFFOCATION:
                msg = Locale.get().getString("deathmessage.suffocation");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case FIRE:
                msg = Locale.get().getString("deathmessage.fire");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case LAVA:
                msg = Locale.get().getString("deathmessage.lava");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case VOID:
                msg = Locale.get().getString("deathmessage.void");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case WITHER:
                msg = Locale.get().getString("deathmessage.wither");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case MAGIC:
                msg = Locale.get().getString("deathmessage.magic");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case CUSTOM:
                msg = Locale.get().getString("deathmessage.custom");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case POISON:
                msg = Locale.get().getString("deathmessage.poison");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case THORNS:
                msg = Locale.get().getString("deathmessage.thorns");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case CONTACT:
                msg = Locale.get().getString("deathmessage.contact");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case MELTING:
                msg = Locale.get().getString("deathmessage.melting");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case SUICIDE:
                msg = Locale.get().getString("deathmessage.suicide");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case DROWNING:
                msg = Locale.get().getString("deathmessage.drowning");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case FIRE_TICK:
                msg = Locale.get().getString("deathmessage.fire-tick");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case LIGHTNING:
                msg = Locale.get().getString("deathmessage.lighting");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case STARVATION:
                msg = Locale.get().getString("deathmessage.starvation");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case FALLING_BLOCK:
                msg = Locale.get().getString("deathmessage.falling_block");
                msg = msg.replace("%dead%", p.getName());
                msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                break;
            case PROJECTILE:
                Player killer = e.getEntity().getKiller();
                if (killer != null) {
                    Players killerConfig = new Players(killer.getUniqueId().toString());
                    msg = Locale.get().getString("deathmessage.projectile.player");
                    msg = msg.replace("%dead%", p.getName());
                    msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                    msg = msg.replace("%killer_kills%", String.valueOf(killerConfig.get().getInt("kills")));
                    msg = msg.replace("%killer%", killer.getName());
                    if (killer.getItemInHand() != null) {
                        if (killer.getItemInHand().getItemMeta().getDisplayName() != null) {
                            msg = msg.replace("%item%", killer.getItemInHand().getItemMeta().getDisplayName());
                        } else {
                            msg = msg.replace("%item%", killer.getItemInHand().getType().toString().replace("_", ""));
                        }
                        double distance = Cooldown.round(killer.getLocation().distance(p.getLocation()), 1);
                        msg = msg.replace("%distance%", String.valueOf(distance));
                    } else {
                        msg = Locale.get().getString("deathmessage.projectile.player-noitem");
                        msg = msg.replace("%dead%", p.getName());
                        msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                        msg = msg.replace("%killer_kills%", String.valueOf(killerConfig.get().getInt("kills")));
                        msg = msg.replace("%killer%", killer.getName());
                    }
                } else {
                    msg = Locale.get().getString("deathmessage.projectile.entity");;
                    msg = msg.replace("%dead%", p.getName());
                    msg = msg.replace("%dead_kills%", String.valueOf(players.get().getInt("kills")));
                }
                break;
        }

        if (!players.get().getString("faction").equals("")) {
            Faction faction = new Faction(players.get().getString("faction"));
            if (faction.get().getInt("dtr") != -0.99) {
                int dtr = faction.get().getInt("dtr");
                dtr = dtr - 1;
                faction.get().set("dtr", dtr);
                faction.save();
            }
        }

        for (Player d : Bukkit.getOnlinePlayers()) {
            Players settings = new Players(d.getUniqueId().toString());
            if (settings.get().getBoolean("settings.deathMessages")) {
                if (!msg.equals("")) {
                    d.sendMessage(C.chat(msg));
                }
            }
        }

        if (p.getLocation().getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
            if (containsClaim(p.getLocation())) {
                Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                if (faction.get().getBoolean("deathban")) {
                    ArrayList<String> tell = new ArrayList<>();
                    Deathban deathban = new Deathban(p.getUniqueId().toString());
                    deathban.createDeathban();
                    for (String s : Locale.get().getStringList("events.deathban.message")) {
                        if (s.contains("%time%")) {
                            Date date = new Date(deathban.getDeathban());
                            String format = "MM'/'dd'/'yyyy 'at' HH':'mm':'ss";
                            DateFormat dateFormat = new SimpleDateFormat(format);
                            String formattedDate = dateFormat.format(date);
                            s = s.replace("%time%", formattedDate);
                        }

                        tell.add(s);
                    }
                    String formatted = String.join("\n", tell);
                    p.kickPlayer(C.chat(formatted));
                }
            } else {
                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                    ArrayList<String> tell = new ArrayList<>();
                    Deathban deathban = new Deathban(p.getUniqueId().toString());
                    deathban.createDeathban();
                    for (String s : Locale.get().getStringList("events.deathban.message")) {
                        if (s.contains("%time%")) {
                            Date date = new Date(deathban.getDeathban());
                            String format = "MM'/'dd'/'yyyy 'at' HH':'mm':'ss";
                            DateFormat dateFormat = new SimpleDateFormat(format);
                            String formattedDate = dateFormat.format(date);
                            s = s.replace("%time%", formattedDate);
                        }

                        tell.add(s);
                    }
                    String formatted = String.join("\n", tell);
                    p.kickPlayer(C.chat(formatted));
                }
            }
        } else if (p.getLocation().getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || p.getLocation().getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
            if (containsClaim(p.getLocation())) {
                Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                if (faction.get().getBoolean("deathban")) {
                    ArrayList<String> tell = new ArrayList<>();
                    Deathban deathban = new Deathban(p.getUniqueId().toString());
                    deathban.createDeathban();
                    for (String s : Locale.get().getStringList("events.deathban.message")) {
                        if (s.contains("%time%")) {
                            Date date = new Date(deathban.getDeathban());
                            String format = "MM'/'dd'/'yyyy 'at' HH':'mm':'ss";
                            DateFormat dateFormat = new SimpleDateFormat(format);
                            String formattedDate = dateFormat.format(date);
                            s = s.replace("%time%", formattedDate);
                        }

                        tell.add(s);
                    }
                    String formatted = String.join("\n", tell);
                    p.kickPlayer(C.chat(formatted));
                }
            } else {
                if (Main.getInstance().getConfig().getBoolean("claim.wilderness.deathban")) {
                    ArrayList<String> tell = new ArrayList<>();
                    Deathban deathban = new Deathban(p.getUniqueId().toString());
                    deathban.createDeathban();
                    for (String s : Locale.get().getStringList("events.deathban.message")) {
                        if (s.contains("%time%")) {
                            Date date = new Date(deathban.getDeathban());
                            String format = "MM'/'dd'/'yyyy 'at' HH':'mm':'ss";
                            DateFormat dateFormat = new SimpleDateFormat(format);
                            String formattedDate = dateFormat.format(date);
                            s = s.replace("%time%", formattedDate);
                        }

                        tell.add(s);
                    }
                    String formatted = String.join("\n", tell);
                    p.kickPlayer(C.chat(formatted));
                }
            }
        } else {
            if (containsClaim(p.getLocation())) {
                Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                if (faction.get().getBoolean("deathban")) {
                    ArrayList<String> tell = new ArrayList<>();
                    Deathban deathban = new Deathban(p.getUniqueId().toString());
                    deathban.createDeathban();
                    for (String s : Locale.get().getStringList("events.deathban.message")) {
                        if (s.contains("%time%")) {
                            Date date = new Date(deathban.getDeathban());
                            String format = "MM'/'dd'/'yyyy 'at' HH':'mm':'ss";
                            DateFormat dateFormat = new SimpleDateFormat(format);
                            String formattedDate = dateFormat.format(date);
                            s = s.replace("%time%", formattedDate);
                        }

                        tell.add(s);
                    }
                    String formatted = String.join("\n", tell);
                    p.kickPlayer(C.chat(formatted));
                }
            } else {
                if (Main.getInstance().getConfig().getBoolean("claim.warzone.deathban")) {
                    ArrayList<String> tell = new ArrayList<>();
                    Deathban deathban = new Deathban(p.getUniqueId().toString());
                    deathban.createDeathban();
                    for (String s : Locale.get().getStringList("events.deathban.message")) {
                        if (s.contains("%time%")) {
                            Date date = new Date(deathban.getDeathban());
                            String format = "MM'/'dd'/'yyyy 'at' HH':'mm':'ss";
                            DateFormat dateFormat = new SimpleDateFormat(format);
                            String formattedDate = dateFormat.format(date);
                            s = s.replace("%time%", formattedDate);
                        }

                        tell.add(s);
                    }
                    String formatted = String.join("\n", tell);
                    p.kickPlayer(C.chat(formatted));
                }
            }
        }
    }

    private String getFactionInBlockClaim(Location selectedBlock) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0")) {
                    for (String fileNumber : file.getConfigurationSection("claims").getKeys(false)) {
                        if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(selectedBlock.getWorld().getEnvironment())) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return file.getString("uuid");
                                }
                            }
                        } else if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NETHER) && selectedBlock.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return file.getString("uuid");
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    private String getFactionInClaim(Location selectedBlock) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0")) {
                    for (String fileNumber : file.getConfigurationSection("claims").getKeys(false)) {
                        if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(selectedBlock.getWorld().getEnvironment())) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return file.getString("uuid");
                                }
                            }
                        } else if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NETHER) && selectedBlock.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return file.getString("uuid");
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static Boolean containsClaim(Location selectedBlock) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0")) {
                    for (String fileNumber : file.getConfigurationSection("claims").getKeys(false)) {
                        if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NORMAL) && selectedBlock.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return true;
                                }
                            }
                        } else if (Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")).getEnvironment().equals(World.Environment.NETHER) && selectedBlock.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                            Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideOne.x"), file.getDouble("claims." + fileNumber + ".sideOne.y"), file.getDouble("claims." + fileNumber + ".sideOne.z"));
                            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + fileNumber + ".world")), file.getDouble("claims." + fileNumber + ".sideTwo.x"), file.getDouble("claims." + fileNumber + ".sideTwo.y"), file.getDouble("claims." + fileNumber + ".sideTwo.z"));
                            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                            for (Block b : cuboid.getBlocks()) {
                                if (selectedBlock.getX() == b.getX() && selectedBlock.getZ() == b.getZ()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
