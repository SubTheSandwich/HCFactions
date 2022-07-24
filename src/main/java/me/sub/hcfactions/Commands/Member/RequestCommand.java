package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class RequestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage(C.chat(Locale.get().getString("command.request.usage")));
            } else {
                if (!Main.getInstance().requestCooldown.contains(p.getUniqueId())) {
                    String[] split = Arrays.copyOfRange(args, 0, args.length);
                    String request = String.join(" ", split);
                    p.sendMessage(C.chat(Locale.get().getString("command.request.success")));
                    for (Player d : Bukkit.getOnlinePlayers()) {
                        if (d.hasPermission("hcfactions.staff")) {
                            d.sendMessage(C.chat(Locale.get().getString("command.request.broadcast").replace("%sender%", p.getName()).replace("%message%", request)));
                        }
                    }
                    Main.getInstance().requestCooldown.add(p.getUniqueId());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Main.getInstance().requestCooldown.remove(p.getUniqueId());
                        }
                    }.runTaskLater(Main.getInstance(), 600);

                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.request.cooldown")));
                }
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
