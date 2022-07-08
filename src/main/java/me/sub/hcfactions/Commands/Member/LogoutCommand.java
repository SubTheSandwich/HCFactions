package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Timer.Timer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class LogoutCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!Main.getInstance().logoutTimer.containsKey(p)) {
                p.sendMessage(C.chat(Locale.get().getString("command.logout.message").replace("%time%", String.valueOf(Main.getInstance().getConfig().getInt("settings.timers.logout")))));
                BigDecimal time = BigDecimal.valueOf(Main.getInstance().getConfig().getDouble("settings.timers.logout"));
                Timer.setLogoutTimer(p, time);
                Timer.tickLogoutTimer(p);
            } else {
                p.sendMessage(C.chat(Locale.get().getString("command.logout.running")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
