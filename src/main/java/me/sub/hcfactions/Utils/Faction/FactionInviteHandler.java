package me.sub.hcfactions.Utils.Faction;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class FactionInviteHandler {

    public static void create(String faction, Player invited) {
        ArrayList<String> invites = new ArrayList<>();
        if (Main.getInstance().invites.get(invited) != null) {
            invites.addAll(Main.getInstance().invites.get(invited));
            invites.add(faction);
            Main.getInstance().invites.put(invited, invites);
        } else {
            invites.add(faction);
            Main.getInstance().invites.put(invited, invites);
        }
    }

    public static void tickInviteTimer(String faction, Player invited) {
        new BukkitRunnable() {
            int timeRan = 0;
            @Override
            public void run() {
                if (timeRan == 60) {
                    ArrayList<String> uuids = new ArrayList<>();
                    Faction f = new Faction(faction);
                    uuids.addAll(f.get().getStringList("members"));
                    uuids.addAll(f.get().getStringList("captains"));
                    uuids.addAll(f.get().getStringList("coleaders"));
                    uuids.add(f.get().getString("leader"));
                    for (String id : uuids) {
                        if (Bukkit.getPlayer(UUID.fromString(id)) != null) {
                            Player send = Bukkit.getPlayer(UUID.fromString(id));
                            send.sendMessage(C.chat(Locale.get().getString("command.faction.invite.invited-expired").replace("%name%", invited.getName())));
                        }
                    }
                    ArrayList<String> invites = new ArrayList<>(Main.getInstance().invites.get(invited));
                    invites.remove(faction);
                    Main.getInstance().invites.put(invited, invites);
                    cancel();
                } else {
                    Players players = new Players(invited.getUniqueId().toString());
                    if (players.get().getString("faction").equals(faction)) {
                        ArrayList<String> invites = new ArrayList<>(Main.getInstance().invites.get(invited));
                        invites.remove(faction);
                        Main.getInstance().invites.put(invited, invites);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }
}
