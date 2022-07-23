package me.sub.hcfactions.Commands.Member;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FilterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length != 2) {
                p.sendMessage(C.chat(Locale.get().getString("command.filter.usage")));
            } else {
                if (args[0].equalsIgnoreCase("add")) {
                    Material material = Material.matchMaterial(args[1]);
                    if (material != null) {
                        if (Main.getInstance().blockedItems == null || Main.getInstance().blockedItems.get(p.getUniqueId()) == null || !Main.getInstance().blockedItems.get(p.getUniqueId()).contains(material)) {
                            ArrayList<Material> materials = new ArrayList<>();
                            if (Main.getInstance().blockedItems != null && Main.getInstance().blockedItems.get(p.getUniqueId()) != null) {
                                materials = Main.getInstance().blockedItems.get(p.getUniqueId());
                            }
                            materials.add(material);
                            Main.getInstance().blockedItems.put(p.getUniqueId(), materials);
                            p.sendMessage(C.chat(Locale.get().getString("command.filter.added").replace("%material%", material.name())));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.filter.usage")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.filter.usage")));
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    Material material = Material.matchMaterial(args[1]);
                    if (material != null) {
                        if (Main.getInstance().blockedItems != null && Main.getInstance().blockedItems.get(p.getUniqueId()) != null && Main.getInstance().blockedItems.get(p.getUniqueId()).contains(material)) {
                            ArrayList<Material> materials = new ArrayList<>(Main.getInstance().blockedItems.get(p.getUniqueId()));
                            materials.remove(material);
                            Main.getInstance().blockedItems.put(p.getUniqueId(), materials);
                            p.sendMessage(C.chat(Locale.get().getString("command.filter.removed").replace("%material%", material.name())));
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.filter.usage")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.filter.usage")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.filter.usage")));
                }
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));

        }
        return false;
    }
}
