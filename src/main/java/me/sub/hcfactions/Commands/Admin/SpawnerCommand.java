package me.sub.hcfactions.Commands.Admin;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnerCommand implements CommandExecutor {

    public void setSpawner(Block block, EntityType ent) {
        BlockState blockState = block.getState();
        CreatureSpawner spawner = ((CreatureSpawner) blockState);
        spawner.setSpawnedType(ent);
        blockState.update();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("hcfactions.admin") || p.hasPermission("hcfactions.command.spawner")) {
                if (args.length != 1) {
                    p.sendMessage(C.chat(Locale.get().getString("command.spawner.usage")));
                } else {
                    try {
                        EntityType mobType = EntityType.valueOf(args[0].toUpperCase());
                        Block locatedBlock = p.getTargetBlock(null, 200);
                        if (locatedBlock != null && locatedBlock.getType().equals(Material.MOB_SPAWNER)) {
                            p.sendMessage(C.chat(Locale.get().getString("command.spawner.set").replace("%type%", args[0].toUpperCase())));
                            setSpawner(locatedBlock, mobType);
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.spawner.usage")));
                        }
                    } catch (EnumConstantNotPresentException enumConstantNotPresentException) {
                        p.sendMessage(C.chat(Locale.get().getString("command.spawner.usage")));
                    }
                }
            } else {
                p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            }
        } else {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
        }
        return false;
    }
}
