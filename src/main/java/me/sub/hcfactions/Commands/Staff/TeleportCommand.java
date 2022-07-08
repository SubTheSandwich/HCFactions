package me.sub.hcfactions.Commands.Staff;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.command.teleport")) {
                if (args.length == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.teleport.usage").replace("%alias%", alias)));
                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        p.teleport(target);
                        p.sendMessage(C.chat(Locale.get().getString("command.teleport.teleport-player").replace("%player%", target.getDisplayName())));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.teleport.not-player")));
                    }
                } else if (args.length == 2) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        Player executed = Bukkit.getPlayer(args[1]);
                        if (executed != null) {
                            if (Locale.get().getBoolean("command.teleport.safe-check")) {
                                if (isSafeLocation(executed.getLocation())) {
                                    target.teleport(executed);
                                    p.sendMessage(C.chat(Locale.get().getString("command.teleport.teleport-player-to-player").replace("%target%", target.getDisplayName()).replace("%player%", executed.getDisplayName())));
                                    target.sendMessage(C.chat(Locale.get().getString("command.teleport.teleport-player").replace("%player%", executed.getDisplayName())));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.teleport.not-safe").replace("%target%", target.getDisplayName())));
                                }
                            } else {
                                target.teleport(executed);
                                p.sendMessage(C.chat(Locale.get().getString("command.teleport.teleport-player-to-player").replace("%target%", target.getDisplayName()).replace("%player%", executed.getDisplayName())));
                                target.sendMessage(C.chat(Locale.get().getString("command.teleport.teleport-player").replace("%player%", executed.getDisplayName())));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.teleport.not-player")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.teleport.not-player")));
                    }
                } else if (args.length == 3) {
                    try {
                        int posOne = Integer.parseInt(args[0]);
                        int posTwo = Integer.parseInt(args[1]);
                        int posThree = Integer.parseInt(args[2]);
                        p.teleport(new Location(p.getWorld(), posOne, posTwo, posThree));
                        p.sendMessage(C.chat(Locale.get().getString("command.teleport.teleport-location").replace("%x%", String.valueOf(posOne)).replace("%y%", String.valueOf(posTwo)).replace("%z%", String.valueOf(posThree))));
                    } catch (NumberFormatException nfe) {
                        p.sendMessage(C.chat(Locale.get().getString("command.teleport.usage").replace("%alias%", alias)));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.teleport.usage").replace("%alias%", alias)));
                }
            } else {
                sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }

    public static boolean isSafeLocation(Location location) {
        try {
            Block feet = location.getBlock();
            if (!feet.getType().isTransparent() && !feet.getLocation().add(0, 1, 0).getBlock().getType().isTransparent()) {
                return false; // not transparent (will suffocate)
            }
            Block head = feet.getRelative(BlockFace.UP);
            if (!head.getType().isTransparent()) {
                return false; // not transparent (will suffocate)
            }
            Block ground = feet.getRelative(BlockFace.DOWN);
            // returns if the ground is solid or not.
            return ground.getType().isSolid();
        } catch (Exception err) {
            System.out.println("An unknown error occured with determing if the teleport location is safe.");
        }
        return false;
    }
}
