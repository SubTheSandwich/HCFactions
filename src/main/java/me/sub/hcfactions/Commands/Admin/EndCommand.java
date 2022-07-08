package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Locations.Locations;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.end") || p.hasPermission("hcfactions.admin")) {
                if (args.length == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.end.usage")));
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("setenter")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.end.setenter")));
                        Locations.get().set("end.enter.world", p.getWorld().getName());
                        Locations.get().set("end.enter.x", p.getLocation().getX());
                        Locations.get().set("end.enter.y", p.getLocation().getY());
                        Locations.get().set("end.enter.z", p.getLocation().getZ());
                        Locations.get().set("end.enter.yaw", p.getLocation().getYaw());
                        Locations.get().set("end.enter.pitch", p.getLocation().getPitch());
                        Locations.save();
                    } else if (args[0].equalsIgnoreCase("setexit")) {
                        p.sendMessage(C.chat(Locale.get().getString("command.end.setexit")));
                        Locations.get().set("end.exit.world", p.getWorld().getName());
                        Locations.get().set("end.exit.x", p.getLocation().getX());
                        Locations.get().set("end.exit.y", p.getLocation().getY());
                        Locations.get().set("end.exit.z", p.getLocation().getZ());
                        Locations.get().set("end.exit.yaw", p.getLocation().getYaw());
                        Locations.get().set("end.exit.pitch", p.getLocation().getPitch());
                        Locations.save();
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.end.usage")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.end.usage")));
                }
            } else {
                p.sendMessage(C.chat(Locations.get().getString("primary.no-permission")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
