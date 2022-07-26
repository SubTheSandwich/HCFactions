package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Players players = new Players(p.getUniqueId().toString());
            if (players.hasFaction()) {
                if (players.getFaction().get().getDouble("dtr") >= Main.getInstance().getConfig().getDouble("dtr.max") || !players.getFaction().get().getBoolean("regening") && players.getFaction().get().getLong("startregen") == 0) {
                    p.sendMessage(C.chat(Locale.get().getString("command.regen.full")));
                } else if (!players.getFaction().get().getBoolean("regening") && players.getFaction().get().getLong("startregen") != 0) {
                    long newTime = players.getFaction().get().getLong("startregen") - System.currentTimeMillis();
                    String format;
                    if (newTime < 60) {
                        format = "ss 'seconds'";
                    } else {
                        format = "mm 'minutes and 'ss 'seconds'";
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    String timee = simpleDateFormat.format(newTime);
                    p.sendMessage(C.chat(Locale.get().getString("command.regen.freeze").replace("%time%", timee)));
                } else if (players.getFaction().get().getBoolean("regening")) {
                    String message = C.chat(Locale.get().getString("command.regen.regen"));
                    message = message.replace("%dtr%", String.valueOf(players.getFaction().get().getDouble("dtr")));
                    message = message.replace("%dtr-regen-rate%", String.valueOf(Main.getInstance().getConfig().getDouble("dtr.regen.increment")));
                    message = message.replace("%dtr-regen-time%", String.valueOf(Main.getInstance().getConfig().getInt("dtr.regen.delay")));
                    String format = "H 'hour(s) and 'mm 'minute(s)'";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    int timesRan = 0;
                    double dtrAtRan = players.getFaction().get().getDouble("dtr");
                    boolean looping = true;
                    while (looping) {
                        dtrAtRan = dtrAtRan + Main.getInstance().getConfig().getDouble("dtr.regen.increment");
                        timesRan = timesRan + 1;
                        if (dtrAtRan >= players.getFaction().getMaximumDTR()) {
                            looping = false;
                        }
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    int delay = Main.getInstance().getConfig().getInt("dtr.regen.delay");
                    delay = delay * players.getFaction().getAllMembers().size();
                    for (int i = 0; i < timesRan; i++) {
                        calendar.add(Calendar.MINUTE, delay);
                    }
                    String timee = simpleDateFormat.format(calendar.getTimeInMillis());
                    message = message.replace("%dtr-regen-finished-time%", timee);
                    p.sendMessage(message);
                }

            } else {
                p.sendMessage(C.chat(Locale.get().getString("command.regen.no-faction")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
