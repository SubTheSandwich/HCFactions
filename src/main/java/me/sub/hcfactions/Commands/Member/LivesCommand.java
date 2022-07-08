package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LivesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                Players players = new Players(p.getUniqueId().toString());
                p.sendMessage(C.chat(Locale.get().getString("command.lives.amount").replace("%lives%", String.valueOf(players.get().getInt("lives")))));
            } else {
                sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("send")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    Players livesSender = new Players(p.getUniqueId().toString());
                    if (livesSender.get().getInt("lives") > 0) {
                        OfflinePlayer added = Bukkit.getOfflinePlayer(args[1]);
                        if (added != null) {
                            try {
                                int lives = Integer.parseInt(args[2]);
                                if (lives > 0) {
                                    Players players = new Players(added.getUniqueId().toString());
                                    players.get().set("lives", players.get().getInt("lives") + lives);
                                    players.save();
                                    livesSender.get().set("lives", livesSender.get().getInt("lives") - lives);
                                    livesSender.save();
                                    p.sendMessage(C.chat(Locale.get().getString("command.lives.sent-lives").replace("%lives%", String.valueOf(lives)).replace("%player%", added.getName())));
                                    if (added.isOnline()) {
                                        Player player = Bukkit.getPlayer(added.getUniqueId());
                                        player.sendMessage(C.chat(Locale.get().getString("command.lives.player-added").replace("%lives%", String.valueOf(lives)).replace("%live-sender%", sender.getName())));
                                    }
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.lives.not-number")));
                                }
                            } catch (NumberFormatException numberFormatException) {
                                p.sendMessage(C.chat(Locale.get().getString("command.lives.not-number")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.lives.not-player")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.lives.not-number")));
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
                }
            } else if (args[0].equalsIgnoreCase("add")) {
                if (sender.hasPermission("hcfaction.command.lives")) {
                    OfflinePlayer added = Bukkit.getOfflinePlayer(args[1]);
                    if (added != null) {
                        try {
                            int lives = Integer.parseInt(args[2]);
                            if (lives > 0) {
                                Players players = new Players(added.getUniqueId().toString());
                                players.get().set("lives", players.get().getInt("lives") + lives);
                                players.save();
                                sender.sendMessage(C.chat(Locale.get().getString("command.lives.added-lives").replace("%lives%", String.valueOf(lives)).replace("%player%", added.getName())));
                                if (added.isOnline()) {
                                    Player player = Bukkit.getPlayer(added.getUniqueId());
                                    player.sendMessage(C.chat(Locale.get().getString("command.lives.player-added").replace("%lives%", String.valueOf(lives)).replace("%live-sender%", sender.getName())));
                                }
                            } else {
                                sender.sendMessage(C.chat(Locale.get().getString("command.lives.not-number")));
                            }
                        } catch (NumberFormatException numberFormatException) {
                            sender.sendMessage(C.chat(Locale.get().getString("command.lives.not-number")));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.lives.not-player")));
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (sender.hasPermission("hcfaction.command.lives")) {
                    OfflinePlayer added = Bukkit.getOfflinePlayer(args[1]);
                    if (added != null) {
                        try {
                            int lives = Integer.parseInt(args[2]);
                            if (lives > 0) {
                                Players players = new Players(added.getUniqueId().toString());
                                if (players.get().getInt("lives") > 0) {
                                    if (players.get().getInt("lives") - lives > 0) {
                                        players.get().set("lives", players.get().getInt("lives") - lives);
                                        players.save();
                                        sender.sendMessage(C.chat(Locale.get().getString("command.lives.removed-lives").replace("%lives%", String.valueOf(lives)).replace("%player%", added.getName())));
                                        if (added.isOnline()) {
                                            Player player = Bukkit.getPlayer(added.getUniqueId());
                                            player.sendMessage(C.chat(Locale.get().getString("command.lives.player-removed").replace("%lives%", String.valueOf(lives)).replace("%live-sender%", sender.getName())));
                                        }
                                    } else {
                                        sender.sendMessage(C.chat(Locale.get().getString("command.lives.cant-remove-more").replace("%player%", added.getName())));
                                    }
                                } else {
                                    sender.sendMessage(C.chat(Locale.get().getString("command.lives.cant-remove-more").replace("%player%", added.getName())));
                                }
                            } else {
                                sender.sendMessage(C.chat(Locale.get().getString("command.lives.not-number")));
                            }
                        } catch (NumberFormatException numberFormatException) {
                            sender.sendMessage(C.chat(Locale.get().getString("command.lives.not-number")));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.lives.not-player")));
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
                }
            }
        } else if (args.length == 1){
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (player != null && player.hasPlayedBefore()) {
                Players players = new Players(player.getUniqueId().toString());
                sender.sendMessage(C.chat(Locale.get().getString("command.lives.player-amount").replace("%player%", player.getName()).replace("%lives%", String.valueOf(players.get().getInt("lives")))));
            } else {
                sender.sendMessage(C.chat(Locale.get().getString("command.lives.not-player")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("command.lives.usage")));
        }
        return false;
    }
}
