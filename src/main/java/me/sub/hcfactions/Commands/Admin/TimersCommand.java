package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import me.sub.hcfactions.Utils.Timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TimersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("hcfactions.command.timers") || sender.hasPermission("hcfactions.admin")) {
            if (args.length == 0) {
                sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    sender.sendMessage(C.chat(Locale.get().getString("command.timers.list")));
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
                }
            } else if (args.length == 4) {
                ArrayList<String> timers = new ArrayList<>();
                timers.add("enderpearl"); timers.add("gopple"); timers.add("apple");
                timers.add("pvptimer"); timers.add("bardeffect"); timers.add("bardenergy");
                timers.add("logout"); timers.add("starting"); timers.add("stuck");
                timers.add("archermark"); timers.add("backstab"); timers.add("combat");
                timers.add("home"); timers.add("archerjump"); timers.add("archerspeed");
                timers.add("roguejump"); timers.add("roguespeed");
                if (args[0].equalsIgnoreCase("set")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    Players targetPlayers = new Players(target.getUniqueId().toString());
                    if (target.isOnline()) {
                        if (timers.contains(args[2].toLowerCase(java.util.Locale.ROOT))) {
                            try {
                                int time = Integer.parseInt(args[3]);
                                if (time > 0) {
                                    String timer = args[2].toLowerCase(java.util.Locale.ROOT);
                                    switch (timer) {
                                        case "enderpearl":
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            if (Main.getInstance().enderpearlCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().enderpearlCooldown.put(target.getUniqueId(), new BigDecimal(time));
                                            } else {
                                                Main.getInstance().enderpearlCooldown.put(target.getUniqueId(), new BigDecimal(time));
                                                Cooldown.tickEnderpearlCooldown(target.getUniqueId());
                                            }

                                            break;
                                        case "gopple":
                                            if (Main.getInstance().goppleTimer.containsKey(target.getUniqueId())) {
                                                Main.getInstance().goppleTimer.put(target.getUniqueId(), time);
                                            } else {
                                                Main.getInstance().goppleTimer.put(target.getUniqueId(), time);
                                                Timer.tickGoppleTimer(target.getUniqueId());
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "apple":
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            if (Main.getInstance().appleTimer.containsKey(target.getUniqueId())) {
                                                Main.getInstance().appleTimer.put(target.getUniqueId(), new BigDecimal(time));
                                            } else {
                                                Main.getInstance().appleTimer.put(target.getUniqueId(), new BigDecimal(time));
                                                Timer.tickAppleTimer(target.getUniqueId());
                                            }
                                            break;
                                        case "pvptimer":
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            if (Main.getInstance().pvpTimer.containsKey(target.getUniqueId())) {
                                                Main.getInstance().pvpTimer.put(target.getUniqueId(), time);
                                            } else {
                                                Main.getInstance().pvpTimer.put(target.getUniqueId(), time);
                                                Timer.tickPvPTimer(target.getUniqueId());
                                            }
                                            break;
                                        case "bardeffect":
                                            if (Main.getInstance().effectCooldown.containsKey(target)) {
                                                Main.getInstance().effectCooldown.put(target, new BigDecimal(time));
                                            } else {
                                                Main.getInstance().effectCooldown.put(target, new BigDecimal(time));
                                                Timer.tickEffectTimer(target);
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "bardenergy":
                                            if (Main.getInstance().bardEnergy.containsKey(target)) {
                                                Main.getInstance().bardEnergy.put(target, (double) time);
                                            } else {
                                                Main.getInstance().bardEnergy.put(target, (double) time);
                                                Timer.tickEnergy(target);
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "logout":
                                            if (Main.getInstance().logoutTimer.containsKey(target)) {
                                                Main.getInstance().logoutTimer.put(target, new BigDecimal(time));
                                            } else {
                                                Main.getInstance().logoutTimer.put(target, new BigDecimal(time));
                                                Timer.tickLogoutTimer(target);
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "starting":
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "stuck":
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "archermark":
                                            if (Main.getInstance().archerTag.containsKey(target.getUniqueId())) {
                                                Main.getInstance().archerTag.put(target.getUniqueId(), new BigDecimal(time));
                                            } else {
                                                Main.getInstance().archerTag.put(target.getUniqueId(), new BigDecimal(time));
                                                Timer.tickArcherTag(target.getUniqueId());
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "backstab":
                                            if (Main.getInstance().rogueBackstabCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().rogueBackstabCooldown.put(target.getUniqueId(), time);
                                            } else {
                                                Main.getInstance().rogueBackstabCooldown.put(target.getUniqueId(), time);
                                                Cooldown.tickBackstabCooldown(target.getUniqueId());
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "combat":
                                            if (Main.getInstance().combatTimer.containsKey(target.getUniqueId())) {
                                                Main.getInstance().combatTimer.put(target.getUniqueId(), new BigDecimal(time));
                                            } else {
                                                Main.getInstance().combatTimer.put(target.getUniqueId(), new BigDecimal(time));
                                                Cooldown.tickCombatTimer(target.getUniqueId());
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "home":
                                            if (Main.getInstance().homeTimer.containsKey(target)) {
                                                Main.getInstance().homeTimer.put(target, new BigDecimal(time));
                                            } else {
                                                Main.getInstance().homeTimer.put(target, new BigDecimal(time));
                                                Timer.tickHomeTimer(target);
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "archerjump":
                                            if (Main.getInstance().archerJumpCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().archerJumpCooldown.put(target.getUniqueId(), time);
                                            } else {
                                                Main.getInstance().archerJumpCooldown.put(target.getUniqueId(), time);
                                                Cooldown.tickArcherJumpCooldown(target.getUniqueId());
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "archerspeed":
                                            if (Main.getInstance().archerSpeedCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().archerSpeedCooldown.put(target.getUniqueId(), time);
                                            } else {
                                                Main.getInstance().archerSpeedCooldown.put(target.getUniqueId(), time);
                                                Cooldown.tickArcherSpeedCooldown(target.getUniqueId());
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "roguejump":
                                            if (Main.getInstance().rogueJumpCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().rogueJumpCooldown.put(target.getUniqueId(), time);
                                            } else {
                                                Main.getInstance().rogueJumpCooldown.put(target.getUniqueId(), time);
                                                Cooldown.tickRogueJumpCooldown(target.getUniqueId());
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "roguespeed":
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            if (Main.getInstance().rogueSpeedCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().rogueSpeedCooldown.put(target.getUniqueId(), time);
                                            } else {
                                                Main.getInstance().rogueSpeedCooldown.put(target.getUniqueId(), time);
                                                Cooldown.tickRogueSpeedCooldown(target.getUniqueId());
                                            }
                                            break;
                                    }
                                } else {
                                    sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
                                }
                            } catch (NumberFormatException e) {
                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
                            }
                        } else {
                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.wrong")));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    Players targetPlayers = new Players(target.getUniqueId().toString());
                    if (target.isOnline()) {
                        if (timers.contains(args[2].toLowerCase(java.util.Locale.ROOT))) {
                            try {
                                int time = Integer.parseInt(args[3]);
                                if (time > 0) {
                                    String timer = args[2].toLowerCase(java.util.Locale.ROOT);
                                    switch (timer) {
                                        case "enderpearl":
                                            if (Main.getInstance().enderpearlCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().enderpearlCooldown.put(target.getUniqueId(), Main.getInstance().enderpearlCooldown.get(target.getUniqueId()).subtract(new BigDecimal(time)));
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }

                                            break;
                                        case "gopple":
                                            if (Main.getInstance().goppleTimer.containsKey(target.getUniqueId())) {
                                                Main.getInstance().goppleTimer.put(target.getUniqueId(), Main.getInstance().goppleTimer.get(target.getUniqueId()) - time);
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            break;
                                        case "apple":
                                            if (Main.getInstance().appleTimer.containsKey(target.getUniqueId())) {
                                                Main.getInstance().appleTimer.put(target.getUniqueId(), Main.getInstance().appleTimer.get(target.getUniqueId()).subtract(new BigDecimal(time)));
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            break;
                                        case "pvptimer":
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            if (Main.getInstance().pvpTimer.containsKey(target.getUniqueId())) {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                                Main.getInstance().pvpTimer.put(target.getUniqueId(), Main.getInstance().pvpTimer.get(target.getUniqueId()) - time);
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            break;
                                        case "bardeffect":
                                            if (Main.getInstance().effectCooldown.containsKey(target)) {
                                                Main.getInstance().effectCooldown.put(target, Main.getInstance().effectCooldown.get(target).subtract(new BigDecimal(time)));
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            //sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "bardenergy":
                                            if (Main.getInstance().bardEnergy.containsKey(target)) {
                                                Main.getInstance().bardEnergy.put(target, Main.getInstance().bardEnergy.get(target) - time);
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            //sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "logout":
                                            if (Main.getInstance().logoutTimer.containsKey(target)) {
                                                Main.getInstance().logoutTimer.put(target, Main.getInstance().logoutTimer.get(target).subtract(new BigDecimal(time)));
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            //sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "starting":
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "stuck":
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "archermark":
                                            if (Main.getInstance().archerTag.containsKey(target.getUniqueId())) {
                                                Main.getInstance().archerTag.put(target.getUniqueId(), Main.getInstance().archerTag.get(target.getUniqueId()).subtract(new BigDecimal(time)));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "backstab":
                                            if (Main.getInstance().rogueBackstabCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().rogueBackstabCooldown.put(target.getUniqueId(), Main.getInstance().rogueBackstabCooldown.get(target.getUniqueId()) - time);
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            //sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "combat":
                                            if (Main.getInstance().combatTimer.containsKey(target.getUniqueId())) {
                                                Main.getInstance().combatTimer.put(target.getUniqueId(), Main.getInstance().combatTimer.get(target.getUniqueId()).subtract(new BigDecimal(time)));
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            //sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "home":
                                            if (Main.getInstance().homeTimer.containsKey(target)) {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                                Main.getInstance().homeTimer.put(target, Main.getInstance().homeTimer.get(target).subtract(new BigDecimal(time)));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            break;
                                        case "archerjump":
                                            if (Main.getInstance().archerJumpCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().archerJumpCooldown.put(target.getUniqueId(), Main.getInstance().archerJumpCooldown.get(target.getUniqueId()) - time);
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            //sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "archerspeed":
                                            if (Main.getInstance().archerSpeedCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().archerSpeedCooldown.put(target.getUniqueId(), Main.getInstance().archerSpeedCooldown.get(target.getUniqueId()) - time);
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            //sender.sendMessage(C.chat(Locale.get().getString("command.timers.started").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            break;
                                        case "roguejump":
                                            if (Main.getInstance().rogueJumpCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().rogueJumpCooldown.put(target.getUniqueId(), Main.getInstance().rogueJumpCooldown.get(target.getUniqueId()) - time);
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            break;
                                        case "roguespeed":
                                            if (Main.getInstance().rogueSpeedCooldown.containsKey(target.getUniqueId())) {
                                                Main.getInstance().rogueSpeedCooldown.put(target.getUniqueId(), Main.getInstance().rogueSpeedCooldown.get(target.getUniqueId()) - time);
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.stopped").replace("%timer%", timer).replace("%name%", target.getName()).replace("%time%", String.valueOf(time))));
                                            } else {
                                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.not-running").replace("%name%", target.getName())));
                                            }
                                            break;
                                    }
                                } else {
                                    sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
                                }
                            } catch (NumberFormatException e) {
                                sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
                            }
                        } else {
                            sender.sendMessage(C.chat(Locale.get().getString("command.timers.wrong")));
                        }
                    } else {
                        sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
                    }
                } else {
                    sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
                }
            } else {
                sender.sendMessage(C.chat(Locale.get().getString("command.timers.usage").replace("%alias%", label)));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
        }
        return false;
    }
}
