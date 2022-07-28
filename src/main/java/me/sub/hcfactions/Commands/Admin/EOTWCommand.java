package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Timer.Timer;
import me.sub.hcfactions.Utils.Timer.Timers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EOTWCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("hcfactions.command.admin") || sender.hasPermission("hcfactions.admin")) {
            if (args.length == 0) {
                sender.sendMessage(C.chat(Locale.get().getString("command.eotw.usage")));
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("start")) {
                    Timer.setTimer(Timers.EOTW, 600);
                    Bukkit.broadcastMessage(C.chat(Locale.get().getString("command.eotw.start")));
                } else if (args[0].equalsIgnoreCase("stop")) {
                    Main.getInstance().timers.remove(Timers.EOTW);
                    Bukkit.broadcastMessage(C.chat(Locale.get().getString("command.eotw.stop")));
                    Main.getInstance().eotwStarted = false;
                } else if (args[0].equalsIgnoreCase("commence")) {
                    if (sender instanceof Player) {
                        sender.sendMessage(C.chat(Locale.get().getString("primary.not-console")));
                    } else {
                        Main.getInstance().eotwStarted = true;
                        Bukkit.broadcastMessage(C.chat(Locale.get().getString("command.eotw.commenced")));
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("command.eotw.usage")));
                }
            } else {
                sender.sendMessage(C.chat(Locale.get().getString("command.eotw.usage")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
        }
        return false;
    }
}
