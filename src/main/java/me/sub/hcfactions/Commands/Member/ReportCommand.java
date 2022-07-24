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

public class ReportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0 || args.length == 1) {
                p.sendMessage(C.chat(Locale.get().getString("command.report.usage")));
            } else {
                Player player = Bukkit.getPlayer(args[0]);
                if (player != null) {
                    if (!player.getName().equals(p.getName())) {
                        if (!Main.getInstance().reportCooldown.contains(p.getUniqueId())) {
                            String[] split = Arrays.copyOfRange(args, 1, args.length);
                            String report = String.join(" ", split);
                            p.sendMessage(C.chat(Locale.get().getString("command.report.success")));
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                if (d.hasPermission("hcfactions.staff")) {
                                    d.sendMessage(C.chat(Locale.get().getString("command.report.broadcast").replace("%reporter%", p.getName()).replace("%reported%", player.getName()).replace("%reason%", report)));
                                }
                            }
                            Main.getInstance().reportCooldown.add(p.getUniqueId());
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Main.getInstance().reportCooldown.remove(p.getUniqueId());
                                }
                            }.runTaskLater(Main.getInstance(), 600);
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.report.cooldown")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.report.self")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.report.usage")));
                }
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
