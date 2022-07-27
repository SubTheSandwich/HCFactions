package me.sub.hcfactions.Events.Player.Consume;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Timer.Timer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AppleConsumeEvent implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (e.getItem().getType().equals(Material.GOLDEN_APPLE) && e.getItem().getDurability() == 0) {
            if (!Main.getInstance().appleTimer.containsKey(p.getUniqueId())) {
                Timer.setAppleTimer(p.getUniqueId(), new BigDecimal(Main.getInstance().getConfig().getInt("settings.timers.gapple")));
                Timer.tickAppleTimer(p.getUniqueId());
            } else {
                p.sendMessage(C.chat(Locale.get().getString("events.apple.cooldown").replace("%time%", String.valueOf(Main.getInstance().appleTimer.get(p.getUniqueId()).doubleValue()))));
                e.setCancelled(true);
            }
        } else if (e.getItem().getType().equals(Material.GOLDEN_APPLE) && e.getItem().getDurability() == 1) {
            if (!Main.getInstance().goppleTimer.containsKey(p.getUniqueId())) {
                Main.getInstance().goppleTimer.put(p.getUniqueId(), Main.getInstance().getConfig().getInt("settings.timers.gopple"));
                Timer.tickGoppleTimer(p.getUniqueId());
            } else {
                Calendar calender = Calendar.getInstance();
                calender.clear();
                calender.add(Calendar.SECOND, Main.getInstance().goppleTimer.get(p.getUniqueId()));
                String format;
                if (Main.getInstance().goppleTimer.get(p.getUniqueId()) >= 3600) {
                    format = "H 'hours' m 'minutes and' s 'seconds'";
                } else if (Main.getInstance().goppleTimer.get(p.getUniqueId()) < 3600 && Main.getInstance().goppleTimer.get(p.getUniqueId()) >= 60) {
                    format = "m 'minutes and' s 'seconds'";
                } else {
                    format = "s 'seconds'";
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                String timee = simpleDateFormat.format(calender.getTimeInMillis());
                p.sendMessage(C.chat(Locale.get().getString("events.gopple.cooldown").replace("%time%", timee)));
                e.setCancelled(true);
            }
        }
    }
}
