package me.sub.hcfactions.Commands.Staff;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Messages.Messages;
import me.sub.hcfactions.Files.Staff.Staff;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class FreezeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.freeze") || p.hasPermission("hcfactions.staff")) {
                if (Staff.get().getBoolean("staff.enabled")) {
                    if (args.length == 0) {
                        p.sendMessage(C.chat(Locale.get().getString("command.freeze.usage").replace("%alias%", label)));
                    } else if (args.length == 1) {
                        Player frozenPlayer = Bukkit.getPlayer(args[0]);
                        if (frozenPlayer != null) {
                            if (frozenPlayer.equals(p)) {
                                p.sendMessage(C.chat(Locale.get().getString("command.freeze.not-you")));
                                return true;
                            }

                            if (!Main.getInstance().frozen.contains(frozenPlayer)) {
                                p.sendMessage(C.chat(Locale.get().getString("command.freeze.froze-player").replace("%player%", frozenPlayer.getName())));
                                Main.getInstance().frozen.add(frozenPlayer);
                                new BukkitRunnable() {
                                    int time = 0;
                                    @Override
                                    public void run() {
                                        time = time + 1;
                                        if (Main.getInstance().frozen.contains(frozenPlayer)) {
                                            if (time == 100) {
                                                ArrayList<String> msgs = new ArrayList<>();
                                                for (String s : Messages.get().getStringList("staff.frozen")) {
                                                    if (s.contains("%teamspeak%")) {
                                                        s = s.replace("%teamspeak%", Main.getInstance().getConfig().getString("server.teamspeak"));
                                                    }
                                                    if (s.contains("%discord%")) {
                                                        s = s.replace("%discord%", Main.getInstance().getConfig().getString("server.discord"));
                                                    }

                                                    msgs.add(s);
                                                }

                                                for (String s : msgs) {
                                                    frozenPlayer.sendMessage(C.chat(s));
                                                }
                                            }
                                        } else {
                                            cancel();
                                        }
                                    }
                                }.runTaskTimer(Main.getInstance(), 0, 1);
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.freeze.unfroze-player").replace("%player%", frozenPlayer.getName())));
                                frozenPlayer.sendMessage(C.chat(Locale.get().getString("command.freeze.unfrozen")));
                                Main.getInstance().frozen.remove(frozenPlayer);
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.freeze.not-player")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.freeze.usage").replace("%alias%", label)));
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
