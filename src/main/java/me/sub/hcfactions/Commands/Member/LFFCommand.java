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

import java.util.ArrayList;

public class LFFCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!Main.getInstance().lffCooldown.containsKey(p)) {
                ArrayList<String> msgs = new ArrayList<>();
                ArrayList<String> classe = new ArrayList<>();
                if (args.length != 0) {
                    for (String s : args) {
                        String str = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                        if (!classe.contains("&b" + str) && !classe.contains("&6" + str) && !classe.contains("&c" + str) && !classe.contains("&7" + str)) {
                            if (s.equalsIgnoreCase("DIAMOND")) {
                                classe.add("&b" + str);
                            } else if (s.equalsIgnoreCase("BARD")) {
                                classe.add("&6" + str);
                            } else if (s.equalsIgnoreCase("ARCHER")) {
                                classe.add("&c" + str);
                            } else if (s.toUpperCase().equals("ROGUE")) {
                                classe.add("&7" + str);
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.lff.not-good-class").replace("%class%", s)));
                                return true;
                            }
                        }
                    }
                } else {
                    classe.add("None");
                }

                Main.getInstance().lffCooldown.put(p, Main.getInstance().getConfig().getInt("settings.timers.lff"));

                String classes = String.join(Locale.get().getString("command.lff.color") + ", ", classe);
                for (String s : Locale.get().getStringList("command.lff.format")) {
                    if (s.contains("%player%")) {
                        s = s.replace("%player%", p.getName());
                    }
                    if (s.contains("%classes%")) {
                        s = s.replace("%classes%", classes);
                    }

                    msgs.add(s);
                }

                String msg = String.join("\n", msgs);
                Bukkit.broadcastMessage(C.chat(msg));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int time = Main.getInstance().lffCooldown.get(p);
                        time = time - 1;
                        if (time == 0) {
                            Main.getInstance().lffCooldown.remove(p);
                            p.sendMessage(C.chat(Locale.get().getString("command.lff.expired")));
                            cancel();
                        } else {
                            Main.getInstance().lffCooldown.put(p, time);
                        }
                    }
                }.runTaskTimer(Main.getInstance(), 0, 20);
            } else {
                p.sendMessage(C.chat(Locale.get().getString("command.lff.not-expired").replace("%time%", String.valueOf(Main.getInstance().lffCooldown.get(p)))));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
