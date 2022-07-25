package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.admin") || p.hasPermission("hcfactions.command.feed")) {
                if (args.length == 0) {
                    p.setFoodLevel(20);
                    p.setSaturation(20);
                    p.sendMessage(C.chat(Locale.get().getString("command.feed.feed")));
                } else if (args.length == 1) {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player != null) {
                        player.setFoodLevel(20);
                        player.setSaturation(20);
                        player.sendMessage(C.chat(Locale.get().getString("command.feed.feed")));
                        p.sendMessage(C.chat(Locale.get().getString("command.feed.feed-other").replace("%player%", player.getName())));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.feed.usage")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.feed.usage")));
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
