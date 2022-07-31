package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Messages.Messages;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        for (String s : Messages.get().getStringList("help.main")) {
            if (s.contains("%primary%")) {
                s = s.replace("%primary%", Main.getInstance().getConfig().getString("server.color.primary"));
            }
            if (s.contains("%secondary%")) {
                s = s.replace("%secondary%", Main.getInstance().getConfig().getString("server.color.secondary"));
            }
            if (s.contains("%servername%")) {
                s = s.replace("%servername%", Main.getInstance().getConfig().getString("server.name"));
            }
            if (s.contains("%map%")) {
                s = s.replace("%map%", String.valueOf(Main.getInstance().getConfig().getInt("server.map-number")));
            }
            if (s.contains("%mapstartdate%")) {
                s = s.replace("%mapstartdate%", Main.getInstance().getConfig().getString("server.start-date"));
            }
            if (s.contains("%border%")) {
                s = s.replace("%border%", Main.getInstance().getConfig().getString("worlds.default.border"));
            }
            if (s.contains("%warzone%")) {
                s = s.replace("%warzone%", String.valueOf(Main.getInstance().getConfig().getInt("worlds.default.warzone")));
            }
            if (s.contains("%teamspeak%")) {
                s = s.replace("%teamspeak%", Main.getInstance().getConfig().getString("server.teamspeak"));
            }
            if (s.contains("%discord%")) {
                s = s.replace("%discord%", Main.getInstance().getConfig().getString("server.discord"));
            }
            if (s.contains("%website%")) {
                s = s.replace("%website%", Main.getInstance().getConfig().getString("server.website"));
            }
            if (s.contains("%store%")) {
                s = s.replace("%store%", Main.getInstance().getConfig().getString("server.store"));
            }
            sender.sendMessage(C.chat(s));
        }
        return false;
    }
}
