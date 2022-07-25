package me.sub.hcfactions.Events.Deathban;


import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Deathban.Deathban;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DeathbanJoinEvent implements Listener {

    @EventHandler
    public void playerLifeuse(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Players players = new Players(p.getUniqueId().toString());
        Deathban deathban = new Deathban(p.getUniqueId().toString());
        int lives = players.get().getInt("lives");
        if (deathban.hasDeathban()) {
            if (players.hasFaction()) {
                Faction faction = new Faction(players.get().getString("faction"));
                int factionLives = faction.get().getInt("lives");
                if (factionLives != 0) {
                    factionLives = factionLives - 1;
                    faction.get().set("lives", factionLives);
                    faction.save();
                    deathban.removeDeathban();
                    p.sendMessage(C.chat(Locale.get().getString("events.deathban.life-use.faction").replace("%lives%", String.valueOf(factionLives))));
                    p.spigot().respawn();
                    return;
                }
                if (lives != 0) {
                    lives = lives - 1;
                    players.get().set("lives", lives);
                    players.save();
                    deathban.removeDeathban();
                    p.sendMessage(C.chat(Locale.get().getString("events.deathban.life-use.player").replace("%lives%", String.valueOf(lives))));
                    p.spigot().respawn();
                    return;
                }

                long deathbanLength = deathban.getDeathban();
                if (deathbanLength - System.currentTimeMillis() > 0) {
                    Date date = new Date(deathbanLength);
                    String format = "MM'/'dd'/'yyyy 'at' HH':'mm':'ss";
                    DateFormat dateFormat = new SimpleDateFormat(format);
                    String formattedText = dateFormat.format(date);
                    ArrayList<String> text = new ArrayList<>();
                    for (String s : Locale.get().getStringList("events.deathban.message")) {
                        if (s.contains("%time%")) {
                            s = s.replace("%time%", formattedText);
                        }
                        text.add(s);
                    }
                    String message = String.join("\n", text);
                    p.kickPlayer(C.chat(message));
                } else {
                    deathban.removeDeathban();
                    p.sendMessage(C.chat(Locale.get().getString("events.deathban.expired")));
                    p.spigot().respawn();
                }
            } else {
                if (lives != 0) {
                    lives = lives - 1;
                    players.get().set("lives", lives);
                    players.save();
                    deathban.removeDeathban();
                    p.sendMessage(C.chat(Locale.get().getString("events.deathban.life-use.player").replace("%lives%", String.valueOf(lives))));
                    p.spigot().respawn();
                    return;
                }

                long deathbanLength = deathban.getDeathban();
                if (deathbanLength - System.currentTimeMillis() > 0) {
                    Date date = new Date(deathbanLength);
                    String format = "MM'/'dd'/'yyyy 'at' HH':'mm':'ss";
                    DateFormat dateFormat = new SimpleDateFormat(format);
                    String formattedText = dateFormat.format(date);
                    ArrayList<String> text = new ArrayList<>();
                    for (String s : Locale.get().getStringList("events.deathban.message")) {
                        if (s.contains("%time%")) {
                            s = s.replace("%time%", formattedText);
                        }
                        text.add(s);
                    }
                    String message = String.join("\n", text);
                    p.kickPlayer(C.chat(message));
                } else {
                    deathban.removeDeathban();
                    p.sendMessage(C.chat(Locale.get().getString("events.deathban.expired")));
                    p.spigot().respawn();
                }
            }
        } else {
            if (Main.getInstance().revived.contains(p.getUniqueId())) {
                if (Main.getInstance().savedInventoryArmorDeath.containsKey(p.getUniqueId()) && Main.getInstance().savedInventoryContentsDeath.containsKey(p.getUniqueId())) {
                    p.sendMessage(C.chat(Locale.get().getString("command.revive.player-revive")));
                    p.getInventory().setContents(Main.getInstance().savedInventoryContentsDeath.get(p.getUniqueId()));
                    p.getInventory().setArmorContents(Main.getInstance().savedInventoryArmorDeath.get(p.getUniqueId()));
                    Main.getInstance().revived.remove(p.getUniqueId());
                    Main.getInstance().savedInventoryContentsDeath.remove(p.getUniqueId());
                    Main.getInstance().savedInventoryArmorDeath.remove(p.getUniqueId());
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.revive.error")));
                }
            }
        }
    }
}
