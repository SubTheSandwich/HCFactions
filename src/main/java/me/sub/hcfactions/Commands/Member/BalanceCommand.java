package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Utils.Economy.Economy;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                Economy economy = new Economy(p.getUniqueId().toString());
                p.sendMessage(C.chat(Locale.get().getString("command.balance.self").replace("%balance%", String.valueOf(economy.getBalance()))));
            } else if (args.length == 1) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                if (player != null) {
                    Economy economy = new Economy(player.getUniqueId().toString());
                    p.sendMessage(C.chat(Locale.get().getString("command.balance.other").replace("%balance%", String.valueOf(economy.getBalance())).replace("%player%", player.getName())));
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.balance.invalid-player")));
                }
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
