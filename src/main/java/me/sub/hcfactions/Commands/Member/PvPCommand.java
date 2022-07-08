package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage(C.chat(Locale.get().getString("command.pvp.usage")));
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("enable")) {
                    if (Main.getInstance().pvpTimer.containsKey(p.getUniqueId())) {
                        p.sendMessage(C.chat(Locale.get().getString("command.pvp.enabled")));
                        Main.getInstance().pvpTimer.remove(p.getUniqueId());
                        Players players = new Players(p.getUniqueId().toString());
                        players.get().set("savedTimers.pvpTimer", 0);
                        players.save();
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.pvp.none")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.pvp.usage")));
                }
            } else {
                p.sendMessage(C.chat(Locale.get().getString("command.pvp.usage")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
