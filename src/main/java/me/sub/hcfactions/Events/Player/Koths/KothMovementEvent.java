package me.sub.hcfactions.Events.Player.Koths;

import me.sub.hcfactions.Files.Koth.Koth;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class KothMovementEvent implements Listener {

    private void tickKothTimer(String kothName, UUID uuid) {
        new BukkitRunnable() {
            int seconds = 0;
            @Override
            public void run() {
                seconds = seconds + 1;
                if (!Main.getInstance().capturingKothFaction.containsValue(uuid)) {
                    cancel();
                }
                if (seconds == 20) {
                    seconds = 0;
                    int time = Main.getInstance().kothTimer.get(kothName);
                    time = time - 1;
                    if (time <= 0) {
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            d.sendMessage(C.chat(Locale.get().getString("events.koth.stop.captured").replace("%name%", kothName)));
                        }
                        Koth koth = new Koth(kothName);
                        if (koth.get().getStringList("rewards").size() != 0) {
                            for (String s : koth.get().getStringList("rewards")) {
                                Bukkit.getServer().dispatchCommand(Main.getInstance().getServer().getConsoleSender(), s.replace("%player%", Bukkit.getPlayer(uuid).getName()));
                            }
                        }
                        Main.getInstance().kothTimer.remove(kothName);
                        Main.getInstance().capturingKothFaction.remove(kothName);
                        cancel();
                    } else {
                        Main.getInstance().kothTimer.put(kothName, time);
                    }
                }
            }
         }.runTaskTimer(Main.getInstance(), 0, 1);
     }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
            if (Main.getInstance().kothTimer.keySet().size() != 0) { //Checks if there is an active koth
                ArrayList<String> koths = new ArrayList<>(Main.getInstance().kothTimer.keySet()); //Gets all keys
                Koth koth = new Koth(koths.get(0)); //Since only one koth can run at a time, it grabs the 0th key, which is the active koth
                if (p.getWorld().getEnvironment().equals(Bukkit.getWorld(koth.get().getString("position.world")).getEnvironment())) { //Checks if the player is in the same world
                    Players players = new Players(p.getUniqueId().toString()); // Creating players varible
                    Cuboid cuboid = new Cuboid(Bukkit.getWorld(koth.get().getString("position.world")), koth.get().getInt("position.sideOne.x"), koth.get().getInt("position.sideOne.y"), koth.get().getInt("position.sideOne.z"), koth.get().getInt("position.sideTwo.x"), koth.get().getInt("position.sideTwo.y"), koth.get().getInt("position.sideTwo.z")); //Constructs a two dimensional cuboid
                    Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN); //Saves block below player
                    for (Block b : cuboid.getBlocks()) { // Loops through all blocks of cuboid
                        if (b.getX() == block.getX() && b.getZ() == block.getZ()) { // Checks if player is within zone
                            if (players.hasFaction() && !Main.getInstance().pvpTimer.containsKey(p.getUniqueId())) { // Checks if the player doesn't have a PvP timer and that they have a faction
                                if (Main.getInstance().capturingKothFaction.containsKey(koths.get(0))) {
                                    if (Bukkit.getPlayer(Main.getInstance().capturingKothFaction.get(koths.get(0))) == null) {
                                        Main.getInstance().capturingKothFaction.remove(koths.get(0));
                                        Main.getInstance().capturingKothFaction.put(koths.get(0), p.getUniqueId());
                                        for (Player d : Bukkit.getOnlinePlayers()) {
                                            d.sendMessage(C.chat(Locale.get().getString("events.koth.control").replace("%faction%", players.getFaction().get().getString("name")).replace("%name%", koths.get(0))));
                                        }
                                        for (Player d : players.getFaction().getAllOnlinePlayers()) {
                                            d.sendMessage(C.chat(Locale.get().getString("events.koth.control-team").replace("%name%", koths.get(0))));
                                        }
                                        tickKothTimer(koths.get(0), p.getUniqueId());
                                        Main.getInstance().kothTimer.put(koths.get(0), koth.get().getInt("time"));
                                    }
                                    return;
                                } else {
                                    Main.getInstance().capturingKothFaction.put(koths.get(0), p.getUniqueId());
                                    Main.getInstance().kothTimer.put(koths.get(0), koth.get().getInt("time"));
                                    for (Player d : Bukkit.getOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("events.koth.control").replace("%faction%", players.getFaction().get().getString("name")).replace("%name%", koths.get(0))));
                                    }
                                    for (Player d : players.getFaction().getAllOnlinePlayers()) {
                                        d.sendMessage(C.chat(Locale.get().getString("events.koth.control-team").replace("%name%", koths.get(0))));
                                    }

                                    tickKothTimer(koths.get(0), p.getUniqueId());
                                    return;
                                }
                            } else {
                                return;
                            }
                        }
                    }
                    if (players.hasFaction() && !Main.getInstance().pvpTimer.containsKey(p.getUniqueId())) {
                        if (Main.getInstance().capturingKothFaction.containsValue(p.getUniqueId())) {
                            Main.getInstance().capturingKothFaction.remove(koths.get(0));
                            Main.getInstance().kothTimer.put(koths.get(0), koth.get().getInt("time"));
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                d.sendMessage(C.chat(Locale.get().getString("events.koth.knock").replace("%faction%", players.getFaction().get().getString("name")).replace("%name%", koths.get(0))));
                            }
                        }
                    }
                }
            }
        }
    }
}
