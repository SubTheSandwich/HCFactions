package me.sub.hcfactions.Utils.Color;

import org.bukkit.ChatColor;

import java.util.Locale;

public class C {
    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String strip(String s) {
        return ChatColor.stripColor(s);
    }

    public static int convertIntegerCode(String s) {
        s = s.toLowerCase(Locale.ROOT);
        switch (s) {
            case "black":
                return 15;
            case "yellow":
            case "gold":
                return 4;
            case "light_purple":
                return 2;
            case "red":
            case "dark_red":
                return 14;
            case "aqua":
            case "blue":
                return 3;
            case "green":
            case "dark_green":
                return 13;
            case "gray":
                return 8;
            case "dark_aqua":
                return 9;
            case "dark_blue":
                return 11;
            case "dark_gray":
                return 7;
            default:
                return 0;
        }
    }

    public static String convertColorCode(String s) {
        s = s.toLowerCase(Locale.ROOT);
        switch (s) {
            case "purple":
                return "&5";
            case "black":
                return "&0";
            case "yellow":
                return "&e";
            case "light_purple":
                return "&d";
            case "red":
                return "&c";
            case "aqua":
                return "&b";
            case "green":
                return "&a";
            case "blue":
                return "&9";
            case "gray":
                return "&7";
            case "dark_aqua":
                return "&3";
            case "dark_green":
                return "&2";
            case "dark_blue":
                return "&1";
            case "dark_red":
                return "&4";
            case "gold":
                return "&6";
            case "dark_gray":
                return "&8";
            case "white":
                return "&f";
            default:
                return null;
        }
    }

    public static Boolean isValidColor(String s) {
        s = s.toLowerCase(Locale.ROOT);
        switch (s) {
            case "purple":
            case "black":
            case "yellow":
            case "light_purple":
            case "red":
            case "aqua":
            case "green":
            case "blue":
            case "gray":
            case "dark_purple":
            case "dark_aqua":
            case "dark_green":
            case "dark_blue":
            case "dark_red":
            case "gold":
            case "dark_gray":
            case "white":
                return true;
            default:
                return false;
        }
    }
}
