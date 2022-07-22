package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Files.Reclaims.Reclaims;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Deathban.Deathban;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ReclaimCommand implements CommandExecutor {

    // no clue why they dont work. ig saving does not work? fix laterrrrrr

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (Reclaims.get().getStringList("reclaimed").contains(p.getUniqueId().toString())) {
                    p.sendMessage(C.chat(Locale.get().getString("command.reclaim.already-used")));
                } else {
                    Deathban deathban = new Deathban(p.getUniqueId().toString());
                    String rank = deathban.getRank();
                    if (!rank.equals("DEFAULT")) {
                        System.out.println(Reclaims.get().getStringList("reclaimed"));
                        System.out.println(Reclaims.get());
                        ArrayList<String> reclaimed = new ArrayList<>(Reclaims.get().getStringList("reclaimed"));
                        reclaimed.add(p.getUniqueId().toString());
                        Reclaims.get().set("reclaimed", reclaimed);
                        Reclaims.save();
                        ArrayList<String> commands = new ArrayList<>();
                        for (String s : Reclaims.get().getStringList("reclaim." + rank + ".commands")) {
                            if (s.contains("%player%")) {
                                s = s.replace("%player%", p.getName());
                            }
                            commands.add(s);
                        }
                        for (String s : commands) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
                        }
                        Bukkit.broadcastMessage(C.chat(Reclaims.get().getString("reclaim." + rank + ".broadcast").replace("%player%", p.getName())));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.reclaim.no-reclaim")));
                    }
                }
            } else if (args.length == 1) {
                if (p.hasPermission("hcfactions.admin") || p.hasPermission("hcfactions.command.reclaim")) {
                    p.sendMessage(C.chat(Locale.get().getString("command.reclaim.usage")));
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                }
            } else if (args.length == 2) {
                if (p.hasPermission("hcfactions.admin") || p.hasPermission("hcfactions.command.reclaim")) {
                    if (args[0].equalsIgnoreCase("reset")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                        Players players = new Players(offlinePlayer.getUniqueId().toString());
                        if (players.exists()) {
                            if (Reclaims.get().getStringList("reclaimed").contains(p.getUniqueId().toString())) {
                                p.sendMessage(C.chat(Locale.get().getString("command.reclaim.reset").replace("%player%", offlinePlayer.getName())));
                                ArrayList<String> play = new ArrayList<>(Reclaims.get().getStringList("reclaimed"));
                                play.remove(offlinePlayer.getUniqueId().toString());
                                Reclaims.get().set("reclaimed", play);
                                Reclaims.save();
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.reclaim.usage")));
                            }
                        } else {
                            if (args[1].equalsIgnoreCase("all")) {
                                p.sendMessage(C.chat(Locale.get().getString("command.reclaim.reset-all")));
                                ArrayList<String> play = new ArrayList<>(Reclaims.get().getStringList("reclaimed"));
                                Reclaims.get().set("reclaimed", play);
                                Reclaims.save();
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.reclaim.usage")));
                            }
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.reclaim.usage")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                }
            } else {
                p.sendMessage(C.chat(Locale.get().getString("command.reclaim.other-use")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
