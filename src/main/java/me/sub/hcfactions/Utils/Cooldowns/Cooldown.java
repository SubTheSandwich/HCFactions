package me.sub.hcfactions.Utils.Cooldowns;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Class.Classes;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class Cooldown {

    public static void tickArcherSpeedCooldown(UUID p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int time = Main.getInstance().archerSpeedCooldown.get(p);
                if (Bukkit.getPlayer(p) != null) {
                    Player player = Bukkit.getPlayer(p);
                    if (Classes.hasActiveClass(player) && Classes.isInArcher(player)) {
                        if (time == 0) {
                            player.sendMessage(C.chat(Locale.get().getString("archer.speed-cooldown-expired")));
                            Main.getInstance().archerSpeedCooldown.remove(p);
                            cancel();
                        } else {
                            time = time - 1;
                            Main.getInstance().archerSpeedCooldown.put(p, time);
                        }

                    }
                } else {
                    if (time == 0) {
                        Main.getInstance().archerSpeedCooldown.remove(p);
                        cancel();
                    } else {
                        time = time - 1;
                        Main.getInstance().archerSpeedCooldown.put(p, time);
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    public static void tickArcherJumpCooldown(UUID p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int time = Main.getInstance().archerJumpCooldown.get(p);
                if (Bukkit.getPlayer(p) != null) {
                    Player player = Bukkit.getPlayer(p);
                    if (Classes.hasActiveClass(player) && Classes.isInArcher(player)) {
                        if (time == 0) {
                            player.sendMessage(C.chat(Locale.get().getString("archer.jump-cooldown-expired")));
                            Main.getInstance().archerJumpCooldown.remove(p);
                            cancel();
                        } else {
                            time = time - 1;
                            Main.getInstance().archerJumpCooldown.put(p, time);
                        }

                    }
                } else {
                    if (time == 0) {
                        Main.getInstance().archerJumpCooldown.remove(p);
                        cancel();
                    } else {
                        time = time - 1;
                        Main.getInstance().archerJumpCooldown.put(p, time);
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    public static void tickRogueSpeedCooldown(UUID p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int time = Main.getInstance().rogueSpeedCooldown.get(p);
                if (Bukkit.getPlayer(p) != null) {
                    Player player = Bukkit.getPlayer(p);
                    if (Classes.hasActiveClass(player) && Classes.isInRogue(player)) {
                        if (time == 0) {
                            player.sendMessage(C.chat(Locale.get().getString("rogue.speed-cooldown-expired")));
                            Main.getInstance().rogueSpeedCooldown.remove(p);
                            cancel();
                        } else {
                            time = time - 1;
                            Main.getInstance().rogueSpeedCooldown.put(p, time);
                        }
                    }
                } else {
                    if (time == 0) {
                        Main.getInstance().rogueSpeedCooldown.remove(p);
                        cancel();
                    } else {
                        time = time - 1;
                        Main.getInstance().rogueSpeedCooldown.put(p, time);
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    public static void tickRogueJumpCooldown(UUID p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int time = Main.getInstance().rogueJumpCooldown.get(p);
                if (Bukkit.getPlayer(p) != null) {
                    Player player = Bukkit.getPlayer(p);
                    if (Classes.hasActiveClass(player) && Classes.isInRogue(player)) {
                        if (time == 0) {
                            player.sendMessage(C.chat(Locale.get().getString("rogue.jump-cooldown-expired")));
                            Main.getInstance().rogueJumpCooldown.remove(p);
                            cancel();
                        } else {
                            time = time - 1;
                            Main.getInstance().rogueJumpCooldown.put(p, time);
                        }
                    }
                } else {
                    if (time == 0) {
                        Main.getInstance().rogueJumpCooldown.remove(p);
                        cancel();
                    } else {
                        time = time - 1;
                        Main.getInstance().rogueJumpCooldown.put(p, time);
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    public static void tickEnderpearlCooldown(UUID p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                BigDecimal time = Main.getInstance().enderpearlCooldown.get(p);
                if (Bukkit.getPlayer(p) != null) {
                    Player player = Bukkit.getPlayer(p);
                    if (time.doubleValue() <= 0.0) {
                        player.sendMessage(C.chat(Locale.get().getString("enderpearl.cooldown-expired")));
                        Main.getInstance().enderpearlCooldown.remove(p);
                        cancel();
                    } else {
                        BigDecimal num = new BigDecimal("0.05");
                        time = time.subtract(num);
                        Main.getInstance().enderpearlCooldown.put(p, time);
                    }
                } else {
                    if (time.doubleValue() <= 0.0) {
                        Main.getInstance().enderpearlCooldown.remove(p);
                        cancel();
                    } else {
                        BigDecimal num = new BigDecimal("0.05");
                        time = time.subtract(num);
                        Main.getInstance().enderpearlCooldown.put(p, time);
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public static void tickCombatTimer(UUID p) {
        if (!Main.getInstance().ticking.contains(p)) {
            Main.getInstance().ticking.add(p);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (Bukkit.getPlayer(p) != null) {
                        Player player = Bukkit.getPlayer(p);
                        BigDecimal time = Main.getInstance().combatTimer.get(p);
                        if (Main.getInstance().combatTimer.containsKey(p)) {
                            if (time.doubleValue() <= 0) {
                                player.sendMessage(C.chat(Locale.get().getString("combat.cooldown-expired")));
                                Main.getInstance().combatTimer.remove(p);
                                Main.getInstance().ticking.remove(p);
                                cancel();
                            } else {
                                BigDecimal num = new BigDecimal("0.05");
                                time = time.subtract(num);
                                Main.getInstance().combatTimer.put(p, time);
                            }
                        } else {
                            Main.getInstance().combatTimer.remove(p);
                            cancel();
                        }
                    } else {
                        BigDecimal time = Main.getInstance().combatTimer.get(p);
                        if (Main.getInstance().combatTimer.containsKey(p)) {
                            if (time.doubleValue() <= 0) {
                                Main.getInstance().combatTimer.remove(p);
                                Main.getInstance().ticking.remove(p);
                                cancel();
                            } else {
                                BigDecimal num = new BigDecimal("0.05");
                                time = time.subtract(num);
                                Main.getInstance().combatTimer.put(p, time);
                            }
                        } else {
                            Main.getInstance().combatTimer.remove(p);
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0, 1);
        }
    }

    public static void tickBackstabCooldown(UUID p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int time = Main.getInstance().rogueBackstabCooldown.get(p);
                if (Bukkit.getPlayer(p) != null) {
                    Player player = Bukkit.getPlayer(p);
                    if (Classes.hasActiveClass(player) && Classes.isInRogue(player)) {
                        if (time == 0) {
                            player.sendMessage(C.chat(Locale.get().getString("rogue.backstab-cooldown-expired")));
                            Main.getInstance().rogueBackstabCooldown.remove(p);
                            cancel();
                        } else {
                            time = time - 1;
                            Main.getInstance().rogueBackstabCooldown.put(p, time);
                        }

                    }
                } else {
                    if (time == 0) {
                        Main.getInstance().rogueBackstabCooldown.remove(p);
                        cancel();
                    } else {
                        time = time - 1;
                        Main.getInstance().rogueBackstabCooldown.put(p, time);
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
