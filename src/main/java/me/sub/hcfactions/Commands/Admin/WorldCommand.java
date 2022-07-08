package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("hcfactions.admin") || sender.hasPermission("hcfactions.command.world")) {
            if (args.length == 0) {
                sender.sendMessage(C.chat(Locale.get().getString("command.world.usage")));
            } else if (args.length == 1) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    switch (args[0].toLowerCase()) {
                        case "overworld":
                            World overworld = Bukkit.getWorlds().get(0);
                            Location overLocation = new Location(overworld, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                            p.teleport(overLocation);
                            p.sendMessage(C.chat(Locale.get().getString("command.world.world-change").replace("%world%", overworld.getName())));
                            break;
                        case "nether":
                            World nether = Bukkit.getWorlds().get(1);
                            Location netherLocation = new Location(nether, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                            p.teleport(netherLocation);
                            p.sendMessage(C.chat(Locale.get().getString("command.world.world-change").replace("%world%", nether.getName())));
                            break;
                        case "end":
                            World end = Bukkit.getWorlds().get(2);
                            Location endLocation = new Location(end, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                            p.teleport(endLocation);
                            p.sendMessage(C.chat(Locale.get().getString("command.world.world-change").replace("%world%", end.getName())));
                            break;
                        default:
                            World world = Bukkit.getWorld(args[0]);
                            if (world != null) {
                                Location location = new Location(world, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                                p.teleport(location);
                                p.sendMessage(C.chat(Locale.get().getString("command.world.world-change").replace("%world%", world.getName())));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.world.not-world")));
                            }
                            break;
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
                }
            } else if (args.length == 2) {
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    switch (args[0].toLowerCase()) {
                        case "overworld":
                            World overworld = Bukkit.getWorlds().get(0);
                            Location overLocation = new Location(overworld, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                            player.teleport(overLocation);
                            sender.sendMessage(C.chat(Locale.get().getString("command.world.world-change").replace("%world%", overworld.getName())));
                            break;
                        case "nether":
                            World nether = Bukkit.getWorlds().get(1);
                            Location netherLocation = new Location(nether, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                            player.teleport(netherLocation);
                            sender.sendMessage(C.chat(Locale.get().getString("command.world.world-change").replace("%world%", nether.getName())));
                            break;
                        case "end":
                            World end = Bukkit.getWorlds().get(2);
                            Location endLocation = new Location(end, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                            player.teleport(endLocation);
                            sender.sendMessage(C.chat(Locale.get().getString("command.world.world-change").replace("%world%", end.getName())));
                            break;
                        default:
                            World world = Bukkit.getWorld(args[0]);
                            if (world != null) {
                                Location location = new Location(world, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                                player.teleport(location);
                                sender.sendMessage(C.chat(Locale.get().getString("command.world.world-change").replace("%world%", world.getName())));
                            } else {
                                sender.sendMessage(C.chat(Locale.get().getString("command.world.not-world")));
                            }
                            break;
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("command.world.invalid-player")));
                }
            } else {
                sender.sendMessage(C.chat(Locale.get().getString("command.world.usage")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
        }
        return false;
    }
}
