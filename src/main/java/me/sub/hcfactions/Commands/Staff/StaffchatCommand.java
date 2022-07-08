package me.sub.hcfactions.Commands.Staff;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffchatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.staffchat") || p.hasPermission("hcfactions.staff")) {
                if (args.length == 0) {
                    if (!Main.getInstance().staffChat.contains(p)) {
                        p.sendMessage(C.chat(Locale.get().getString("command.staffchat.toggled").replace("%status%", "&aEnabled")));
                        Main.getInstance().staffChat.add(p);
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.staffchat.toggled").replace("%status%", "&cDisabled")));
                        Main.getInstance().staffChat.remove(p);
                    }
                } else {
                    String message = C.chat(StringUtils.join(args, " "));
                    message = C.strip(message);
                    for (Player d : Bukkit.getOnlinePlayers()) {
                        if (d.hasPermission("hcfactions.staff")) {
                            d.sendMessage(C.chat(Locale.get().getString("command.staffchat.message").replace("%name%", p.getName()).replace("%message%", message)));
                        }
                    }
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
