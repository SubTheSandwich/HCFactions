package me.sub.hcfactions.Commands.Admin;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketTitle;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketEmoteBroadcast;
import com.lunarclient.bukkitapi.title.TitleType;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class LunarCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage(C.chat(Locale.get().getString("command.lunar.usage").replace("%alias%", label)));
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("getall")) {
                    if (Main.getInstance().lunarClientAPI.getPlayersRunningLunarClient().size() != 0) {
                        ArrayList<String> names = new ArrayList<>();
                        for (Player player : Main.getInstance().lunarClientAPI.getPlayersRunningLunarClient()) {
                            names.add(player.getName());
                        }
                        String message = String.join(", ", names);
                        p.sendMessage(C.chat(Locale.get().getString("command.lunar.list").replace("%players%", message)));
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.lunar.none")));
                    }
                } else if (args[0].equalsIgnoreCase("sendtitle")) {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.sendtitle-usage").replace("%alias%", label)));
                } else if (args[0].equalsIgnoreCase("sendsubtitle")) {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.sendsubtitle-usage").replace("%alias%", label)));
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.usage").replace("%alias%", label)));
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("running")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player != null) {
                        if (Main.getInstance().lunarClientAPI.isRunningLunarClient(player)) {
                            p.sendMessage(C.chat(Locale.get().getString("command.lunar.is").replace("%player%", player.getName())));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.lunar.isnt").replace("%player%", player.getName())));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.lunar.not-player")));
                    }
                } else if (args[0].equalsIgnoreCase("sendtitle")) {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.sendtitle-usage").replace("%alias%", label)));
                } else if (args[0].equalsIgnoreCase("sendsubtitle")) {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.sendsubtitle-usage").replace("%alias%", label)));
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.usage").replace("%alias%", label)));
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("emote")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player != null) {
                        try {
                            int id = Integer.parseInt(args[2]);
                            if (id > -1) {
                                if (Main.getInstance().lunarClientAPI.isRunningLunarClient(player)) {
                                    LCPacketEmoteBroadcast emoteBroadcast = new LCPacketEmoteBroadcast(player.getUniqueId(), id);
                                    Main.getInstance().lunarClientAPI.sendPacket(player, emoteBroadcast);
                                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.emoted").replace("%player%", player.getName()).replace("%id%", String.valueOf(id))));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.cant").replace("%player%", player.getName())));
                                }
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.lunar.invalid-id")));
                            }
                        } catch (NumberFormatException nfe) {
                            p.sendMessage(C.chat(Locale.get().getString("command.lunar.invalid-id")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.lunar.not-player")));
                    }
                } else if (args[0].equalsIgnoreCase("sendtitle")) {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.sendtitle-usage").replace("%alias%", label)));
                } else if (args[0].equalsIgnoreCase("sendsubtitle")) {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.sendsubtitle-usage").replace("%alias%", label)));
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.usage").replace("%alias%", label)));
                }
            } else {
                if (args[0].equalsIgnoreCase("sendtitle")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player != null) {
                        try {
                            long displayTime = Long.parseLong(args[2]);
                            long fadeInTime = Long.parseLong(args[3]);
                            long fadeOutTime = Long.parseLong(args[4]);
                            String message = String.join(" ", Arrays.copyOfRange(args, 5, args.length));
                            if (Main.getInstance().lunarClientAPI.isRunningLunarClient(player)) {
                                LCPacketTitle packetTitle = new LCPacketTitle(TitleType.TITLE.name(), C.chat(message), displayTime, fadeInTime, fadeOutTime);
                                Main.getInstance().lunarClientAPI.sendPacket(player, packetTitle);
                                p.sendMessage(C.chat(Locale.get().getString("command.lunar.senttitle").replace("%player%", player.getName()).replace("%message%", message).replace("%fade-in%", String.valueOf(fadeInTime)).replace("%fade-out%", String.valueOf(fadeOutTime)).replace("%display%", String.valueOf(displayTime))));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.lunar.cant-title").replace("%player%", player.getName())));
                            }
                        } catch (NumberFormatException nfe) {
                            p.sendMessage(C.chat(Locale.get().getString("command.lunar.not-valid")));
                        }
                    } else {
                        if (args[1].equalsIgnoreCase("all")) {
                            try {
                                long displayTime = Long.parseLong(args[2]);
                                long fadeInTime = Long.parseLong(args[3]);
                                long fadeOutTime = Long.parseLong(args[4]);
                                String message = String.join(" ", Arrays.copyOfRange(args, 5, args.length));
                                p.sendMessage(C.chat(Locale.get().getString("command.lunar.senttitle-all").replace("%message%", message).replace("%fade-in%", String.valueOf(fadeInTime)).replace("%fade-out%", String.valueOf(fadeOutTime)).replace("%display%", String.valueOf(displayTime))));
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    if (Main.getInstance().lunarClientAPI.isRunningLunarClient(d)) {
                                        LCPacketTitle packetTitle = new LCPacketTitle(TitleType.TITLE.name(), C.chat(message), displayTime, fadeInTime, fadeOutTime);
                                        Main.getInstance().lunarClientAPI.sendPacket(d, packetTitle);
                                    }
                                }
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(C.chat(Locale.get().getString("command.lunar.not-valid")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.lunar.not-player")));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("sendsubtitle")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player != null) {
                        try {
                            long displayTime = Long.parseLong(args[2]);
                            long fadeInTime = Long.parseLong(args[3]);
                            long fadeOutTime = Long.parseLong(args[4]);
                            String message = String.join(" ", Arrays.copyOfRange(args, 5, args.length));
                            if (Main.getInstance().lunarClientAPI.isRunningLunarClient(player)) {
                                LCPacketTitle packetTitle = new LCPacketTitle(TitleType.SUBTITLE.name(), C.chat(message), displayTime, fadeInTime, fadeOutTime);
                                Main.getInstance().lunarClientAPI.sendPacket(player, packetTitle);
                                p.sendMessage(C.chat(Locale.get().getString("command.lunar.sentsubtitle").replace("%player%", player.getName()).replace("%message%", message).replace("%fade-in%", String.valueOf(fadeInTime)).replace("%fade-out%", String.valueOf(fadeOutTime)).replace("%display%", String.valueOf(displayTime))));
                            } else {
                                p.sendMessage(C.chat(Locale.get().getString("command.lunar.cant-subtitle").replace("%player%", player.getName())));
                            }
                        } catch (NumberFormatException nfe) {
                            p.sendMessage(C.chat(Locale.get().getString("command.lunar.not-valid")));
                        }
                    } else {
                        if (args[1].equalsIgnoreCase("all")) {
                            try {
                                long displayTime = Long.parseLong(args[2]);
                                long fadeInTime = Long.parseLong(args[3]);
                                long fadeOutTime = Long.parseLong(args[4]);
                                String message = String.join(" ", Arrays.copyOfRange(args, 5, args.length));
                                p.sendMessage(C.chat(Locale.get().getString("command.lunar.sentsubtitle-all").replace("%message%", message).replace("%fade-in%", String.valueOf(fadeInTime)).replace("%fade-out%", String.valueOf(fadeOutTime)).replace("%display%", String.valueOf(displayTime))));
                                for (Player d : Bukkit.getOnlinePlayers()) {
                                    if (Main.getInstance().lunarClientAPI.isRunningLunarClient(d)) {
                                        LCPacketTitle packetTitle = new LCPacketTitle(TitleType.SUBTITLE.name(), C.chat(message), displayTime, fadeInTime, fadeOutTime);
                                        Main.getInstance().lunarClientAPI.sendPacket(d, packetTitle);
                                    }
                                }
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(C.chat(Locale.get().getString("command.lunar.not-valid")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.lunar.not-player")));
                        }
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.lunar.usage").replace("%alias%", label)));
                }
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
