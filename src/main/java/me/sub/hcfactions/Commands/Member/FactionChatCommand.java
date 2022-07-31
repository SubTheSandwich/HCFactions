package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Players players = new Players(p.getUniqueId().toString());
            if (players.hasFaction()) {
                p.sendMessage(C.chat(Locale.get().getString("command.factionchat.toggled")));
                Main.getInstance().factionChat.remove(p);
                Main.getInstance().factionChat.add(p);
            } else {
                p.sendMessage(C.chat(Locale.get().getString("command.factionchat.no-faction")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
