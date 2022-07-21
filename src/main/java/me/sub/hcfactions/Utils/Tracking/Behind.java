package me.sub.hcfactions.Utils.Tracking;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Behind {
    public static boolean playerBehindPlayer(Player facing, Player behind) {
        double yaw = 2*Math.PI-Math.PI*behind.getLocation().getYaw()/180;
        Vector v = facing.getLocation().toVector().subtract(behind.getLocation().toVector());
        Vector r = new Vector(Math.sin(yaw),0, Math.cos(yaw));
        float theta = r.angle(v);
        return Math.PI / 2 < theta && theta < 3 * Math.PI / 2;
    }
}
