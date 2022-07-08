package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            switch (label.toLowerCase()) {
                case "gmc":
                    if (p.hasPermission("hcfactions.command.gamemode.creative") || p.hasPermission("hcfactions.admin")) {
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.success").replace("%gamemode%", p.getGameMode().toString())));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.no-permission").replace("%gamemode%", "CREATIVE")));
                    }
                    break;

                case "gms":
                    if (p.hasPermission("hcfactions.command.gamemode.survival") || p.hasPermission("hcf.admin")) {
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.success").replace("%gamemode%", p.getGameMode().toString())));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.no-permission").replace("%gamemode%", "SURVIVAL")));
                    }
                    break;

                case "gma":
                    if (p.hasPermission("hcfactions.command.gamemode.adventure") || p.hasPermission("hcf.admin")) {
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.success").replace("%gamemode%", p.getGameMode().toString())));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.no-permission").replace("%gamemode%", "ADVENTURE")));
                    }
                    break;

                default:
                    if (p.hasPermission("hcfactions.command.gamemode") || p.hasPermission("hcf.admin")) {
                        if (args.length == 0) {
                            p.sendMessage(C.chat(Locale.get().getString("command.gamemode.usage").replace("%alias%", label)));
                        } else if (args.length == 1) {
                            String gamemode = args[0].toLowerCase();
                            String actualGamemode = args[0].toUpperCase();
                            if (gamemode.equals("creative") || gamemode.equals("c")) {
                                if (p.hasPermission("hcfactions.command.gamemode.creative")) {
                                    p.setGameMode(GameMode.CREATIVE);
                                    p.sendMessage(C.chat(Locale.get().getString("command.gamemode.success").replace("%gamemode%", p.getGameMode().toString())));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.gamemode.no-permission").replace("%gamemode%", actualGamemode)));
                                }
                            } else if (gamemode.equals("adventure") || gamemode.equals("a")) {
                                if (p.hasPermission("hcfactions.command.gamemode.adventure")) {
                                    p.setGameMode(GameMode.ADVENTURE);
                                    p.sendMessage(C.chat(Locale.get().getString("command.gamemode.success").replace("%gamemode%", p.getGameMode().toString())));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.gamemode.no-permission").replace("%gamemode%", actualGamemode)));
                                }
                            } else if (gamemode.equals("survival") || gamemode.equals("s")) {
                                if (p.hasPermission("hcfactions.command.gamemode.survival")) {
                                    p.setGameMode(GameMode.SURVIVAL);
                                    p.sendMessage(C.chat(Locale.get().getString("command.gamemode.success").replace("%gamemode%", p.getGameMode().toString())));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.gamemode.no-permission").replace("%gamemode%", actualGamemode)));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.gamemode.invalid-gamemode")));
                            }
                        } else if (args.length == 2) {
                            String gamemode = args[0].toLowerCase();
                            String actualGamemode = args[0].toUpperCase();
                            Player player = Bukkit.getPlayer(args[1]);
                            if (player != null) {
                                if (gamemode.equals("creative") || gamemode.equals("c")) {
                                    if (p.hasPermission("hcfactions.command.gamemode.creative") || p.hasPermission("hcf.admin")) {
                                        player.setGameMode(GameMode.CREATIVE);
                                        player.sendMessage(C.chat(Locale.get().getString("command.gamemode.success").replace("%gamemode%", p.getGameMode().toString())));
                                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.updated-player-gamemode").replace("%gamemode%", player.getGameMode().toString()).replace("%player%", player.getName())));
                                    } else {
                                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.no-permission").replace("%gamemode%", actualGamemode)));
                                    }
                                } else if (gamemode.equals("adventure") || gamemode.equals("a")) {
                                    if (p.hasPermission("hcfactions.command.gamemode.adventure") || p.hasPermission("hcf.admin")) {
                                        player.setGameMode(GameMode.ADVENTURE);
                                        player.sendMessage(C.chat(Locale.get().getString("command.gamemode.success").replace("%gamemode%", p.getGameMode().toString())));
                                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.updated-player-gamemode").replace("%gamemode%", player.getGameMode().toString()).replace("%player%", player.getName())));
                                    } else {
                                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.no-permission").replace("%gamemode%", actualGamemode)));
                                    }
                                } else if (gamemode.equals("survival") || gamemode.equals("s") || p.hasPermission("hcf.admin")) {
                                    if (p.hasPermission("hcfactions.command.gamemode.survival")) {
                                        player.setGameMode(GameMode.SURVIVAL);
                                        player.sendMessage(C.chat(Locale.get().getString("command.gamemode.success").replace("%gamemode%", p.getGameMode().toString())));
                                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.updated-player-gamemode").replace("%gamemode%", player.getGameMode().toString()).replace("%player%", player.getName())));
                                    } else {
                                        p.sendMessage(C.chat(Locale.get().getString("command.gamemode.no-permission").replace("%gamemode%", actualGamemode)));
                                    }
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.gamemode.invalid-gamemode")));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.gamemode.not-player")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.gamemode.usage").replace("%alias%", label)));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                    }
                    break;

            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
