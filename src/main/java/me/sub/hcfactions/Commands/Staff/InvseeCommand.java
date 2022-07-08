package me.sub.hcfactions.Commands.Staff;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.invsee") || p.hasPermission("hcfactions.staff")) {
                if (args.length == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.invsee.usage").replace("%alias%", label)));
                } else if (args.length == 1) {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player != null) {
                        p.openInventory(player.getInventory());
                        p.sendMessage(C.chat(Locale.get().getString("command.invsee.opening").replace("%player%", player.getName())));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.invsee.not-player")));
                    }
                 } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.invsee.usage").replace("%alias%", label)));
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
