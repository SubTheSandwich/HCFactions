package me.sub.hcfactions.Events.Logger;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Deathban.Deathban;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoggerRemove implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Entity entity : p.getWorld().getEntities()) {
            if (entity instanceof Villager) {
                Villager v = (Villager) entity;
                if (C.strip(v.getCustomName()).equalsIgnoreCase(p.getName())) {
                    double health = v.getHealth();
                    p.teleport(v.getLocation());
                    if (Main.getInstance().loggedDeath.contains(p.getUniqueId())) {
                        ArrayList<String> tell = new ArrayList<>();
                        Main.getInstance().loggedDeath.remove(p.getUniqueId());
                        Deathban deathban = new Deathban(p.getUniqueId().toString());
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
                        p.getInventory().clear();
                        p.setExp(0);
                        String formatted = String.join("\n", tell);
                        p.kickPlayer(C.chat(formatted));
                        v.remove();
                    } else {
                        p.setHealth(health);
                        v.remove();
                    }
                }

                Main.getInstance().logoutContents.remove(p.getUniqueId());
                Main.getInstance().logoutArmorContents.remove(p.getUniqueId());

                return;
            }
        }

        if (Main.getInstance().loggedDeath.contains(p.getUniqueId())) {
            ArrayList<String> tell = new ArrayList<>();
            Main.getInstance().loggedDeath.remove(p.getUniqueId());
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
            p.getInventory().clear();
            p.setExp(0);
            String formatted = String.join("\n", tell);
            p.kickPlayer(C.chat(formatted));
        }
    }
}
