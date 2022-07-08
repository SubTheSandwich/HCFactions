package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcf.command.revive") || p.hasPermission("hcfactions.admin")) {
                if (args.length == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.revive.usage")));
                } else if (args.length == 1) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                    if (player != null) {
                        Players players = new Players(player.getUniqueId().toString());
                        if (players.get().getBoolean("deathBanned")) {
                            if (!Main.getInstance().revived.contains(player.getUniqueId())) {
                                Main.getInstance().revived.add(player.getUniqueId());
                                players.get().set("deathBanned", false);
                                players.get().set("bannedTill", 0);
                                players.get().set("startDeathban", 0);
                                players.save();
                                p.sendMessage(C.chat(Locale.get().getString("command.revive.revived").replace("%player%", player.getName())));
                                if (!players.get().getString("faction").equals("")) {
                                    Faction faction = new Faction(players.get().getString("faction"));
                                    double dtr = Main.getInstance().getConfig().getDouble("dtr.max") - 1;
                                    if (faction.get().getDouble("dtr") < dtr) {
                                        faction.get().set("dtr", faction.get().getDouble("dtr") + 1);
                                        faction.save();
                                    } else {
                                        faction.get().set("dtr", dtr);
                                        faction.save();
                                    }
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.revive.already-revived").replace("%player%", player.getName())));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.revive.not-deathbanned").replace("%player%", player.getName())));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.revive.invalid-player")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.revive.usage")));
                }
            } else {
                p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
