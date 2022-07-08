package me.sub.hcfactions.Utils.Faction;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Claim {

    public static String getItemName() {
        return C.chat(Locale.get().getString("command.faction.claim.wand.name"));
    }

    public static Material getItemMaterial() {
        return Material.getMaterial(Locale.get().getString("command.faction.claim.wand.item"));
    }

    public static void giveItem(Player p) {
        ItemStack i = new ItemStack(Material.getMaterial(Locale.get().getString("command.faction.claim.wand.item")));
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(C.chat(Locale.get().getString("command.faction.claim.wand.name")));
        ArrayList<String> lore = new ArrayList<>();
        for (String s : Locale.get().getStringList("command.faction.claim.wand.lore")) {
            lore.add(C.chat(s));
        }
        meta.setLore(lore);
        i.setItemMeta(meta);
        p.getInventory().setItemInHand(i);
    }

    public static Boolean isValidSelectedBlock(Location location) {

        if (location.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
            boolean valid = true;
            if (factions != null) {
                for (File f : factions) {
                    YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                    if (file.isConfigurationSection("claims")) {
                        if (file.isConfigurationSection("claims.0") && file.isConfigurationSection("claims.1")) {

                        } else if (file.isConfigurationSection("claims.0")) {
                            Location location1 = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getInt("claims.0.sideOne.x"), file.getInt("claims.0.sideOne.y"), file.getInt("claims.0.sideOne.z"));
                            Location location2 = new Location(Bukkit.getWorld(file.getString("claims.0.world")), file.getInt("claims.0.sideTwo.x"), file.getInt("claims.0.sideTwo.y"), file.getInt("claims.0.sideTwo.z"));

                            location1.setY(0);
                            location2.setY(0);

                            location.setY(0);
                            Cuboid cuboid = new Cuboid(location1, location2);
                            for (Block b : cuboid) {
                                if (b.getX() == (int) location.getX() && b.getZ() == (int) location.getZ()) {
                                    valid = false;
                                }
                            }
                        }
                    }
                }
            }

            if (location.getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || location.getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                return true;
            } else if (location.getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || location.getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                return true;
            }
        }

        return false;
    }

    public static void executeClaimfor(Player p) {
        if (Main.getInstance().posClaimOne.containsKey(p.getUniqueId()) && Main.getInstance().posClaimTwo.containsKey(p.getUniqueId())) {
            Faction faction = new Faction(Main.getInstance().claimFor.get(p.getUniqueId()));
            if (!faction.get().isConfigurationSection("claims.0")) {
                Location locationOne = Main.getInstance().posClaimOne.get(p.getUniqueId());
                Location locationTwo = Main.getInstance().posClaimTwo.get(p.getUniqueId());
                locationOne.setY(0);
                locationTwo.setY(0);
                Location newLocationOne = Main.getInstance().posClaimOne.get(p.getUniqueId());
                Location newLocationTwo = Main.getInstance().posClaimTwo.get(p.getUniqueId());
                faction.get().set("claims.0.world", p.getWorld().getName());
                faction.get().set("claims.0.sideOne.x", newLocationOne.getX());
                faction.get().set("claims.0.sideOne.y", newLocationOne.getY());
                faction.get().set("claims.0.sideOne.z", newLocationOne.getZ());
                faction.get().set("claims.0.sideTwo.x", newLocationTwo.getX());
                faction.get().set("claims.0.sideTwo.y", newLocationTwo.getY());
                faction.get().set("claims.0.sideTwo.z", newLocationTwo.getZ());
                faction.save();
                Main.getInstance().posClaimOne.remove(p.getUniqueId());
                Main.getInstance().posClaimTwo.remove(p.getUniqueId());
                Main.getInstance().claimFor.remove(p.getUniqueId());
                p.sendMessage(C.chat(Locale.get().getString("command.faction.claimfor.purchased").replace("%faction%", faction.get().getString("name"))));
                p.getInventory().remove(p.getItemInHand());
            } else {
                int number = 0;
                for (String ignored : faction.get().getConfigurationSection("claims").getKeys(false)) {
                    number = number + 1;
                }
                String ofNumber = String.valueOf(number);
                Location locationOne = Main.getInstance().posClaimOne.get(p.getUniqueId());
                Location locationTwo = Main.getInstance().posClaimTwo.get(p.getUniqueId());
                locationOne.setY(0);
                locationTwo.setY(0);
                Location newLocationOne = Main.getInstance().posClaimOne.get(p.getUniqueId());
                Location newLocationTwo = Main.getInstance().posClaimTwo.get(p.getUniqueId());
                faction.get().set("claims." + ofNumber + ".world", p.getWorld().getName());
                faction.get().set("claims." + ofNumber + ".sideOne.x", newLocationOne.getX());
                faction.get().set("claims." + ofNumber + ".sideOne.y", newLocationOne.getY());
                faction.get().set("claims." + ofNumber + ".sideOne.z", newLocationOne.getZ());
                faction.get().set("claims." + ofNumber + ".sideTwo.x", newLocationTwo.getX());
                faction.get().set("claims." + ofNumber + ".sideTwo.y", newLocationTwo.getY());
                faction.get().set("claims." + ofNumber + ".sideTwo.z", newLocationTwo.getZ());
                faction.save();
                Main.getInstance().posClaimOne.remove(p.getUniqueId());
                Main.getInstance().posClaimTwo.remove(p.getUniqueId());
                Main.getInstance().claimFor.remove(p.getUniqueId());
                p.sendMessage(C.chat(Locale.get().getString("command.faction.claimfor.purchased").replace("%faction%", faction.get().getString("name"))));
                p.getInventory().remove(p.getItemInHand());
            }
        } else {
            p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.cannot-claim")));
        }
    }

    public static void claimSelectOne(Player p, Location clickedLocation) {
        ArrayList<Material> blockTypes = new ArrayList<>();
        blockTypes.add(Material.DIAMOND_BLOCK);
        blockTypes.add(Material.EMERALD_BLOCK);
        blockTypes.add(Material.IRON_BLOCK);
        blockTypes.add(Material.GOLD_BLOCK);
        blockTypes.add(Material.COAL_BLOCK);
        blockTypes.add(Material.LOG);

        Random random = new Random();
        int pos = random.nextInt(blockTypes.size());
        Material material = blockTypes.get(pos);

        int startingInt = clickedLocation.getBlockY() + 1;

        for (int i = 0; i < startingInt; i++) {
            Location loc = clickedLocation;
            loc.setY(i);
            if (loc.getWorld().getBlockAt(loc).getType().equals(Material.AIR)) {
                if (i / 3 == 0) {
                    p.sendBlockChange(loc, material, (byte) 0);
                } else {
                    p.sendBlockChange(loc, Material.GLASS, (byte) 0);
                }
            }
        }

        Main.getInstance().posClaimOne.put(p.getUniqueId(), clickedLocation);
        p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.selected-position-one").replace("%x%", String.valueOf(Cooldown.round(p.getLocation().getX(), 1))).replace("%z%", String.valueOf(Cooldown.round(p.getLocation().getZ(), 1)))));
        if (Main.getInstance().posClaimTwo.containsKey(p.getUniqueId())) {
            ArrayList<String> msgs = new ArrayList<>();
            Location locationOne = Main.getInstance().posClaimOne.get(p.getUniqueId());
            Location locationTwo = Main.getInstance().posClaimTwo.get(p.getUniqueId());
            locationOne.setY(0);
            locationTwo.setY(0);
            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
            int size = 0;
            for (Block b : cuboid.getBlocks()) {
                size++;
            }
            double cost;
            String blockSize = cuboid.getSizeX() + "x" + cuboid.getSizeZ();
            if (blockSize.equals("5x5")) {
                cost = Main.getInstance().getConfig().getInt("claim.price.multiplier");
            } else {
                int cos = Main.getInstance().getConfig().getInt("claim.price.multiplier");
                double multiplier = Main.getInstance().getConfig().getDouble("claim.price.per-block");
                double not = 0;
                for (Block b : cuboid.getBlocks()) {
                    not = not + multiplier;
                }
                cost = multiplier + not;
            }
            for (String s : Locale.get().getStringList("command.faction.claim.claim-create-before")) {
                if (s.contains("%cost%")) {
                    s = s.replace("%cost%", String.valueOf(cost));
                }

                if (s.contains("%size%")) {
                    s = s.replace("%size%", String.valueOf(size));
                }

                if (s.contains("%x-size%")) {
                    s = s.replace("%x-size%", String.valueOf(cuboid.getSizeX()));
                }

                if (s.contains("%z-size%")) {
                    s = s.replace("%z-size%", String.valueOf(cuboid.getSizeZ()));
                }

                msgs.add(C.chat(s));
            }

            for (String s : msgs) {
                p.sendMessage(C.chat(s));
            }
        }
    }

    public static void executeClaim(Player p) {
        if (Main.getInstance().posClaimOne.containsKey(p.getUniqueId()) && Main.getInstance().posClaimTwo.containsKey(p.getUniqueId())) {
            Players players = new Players(p.getUniqueId().toString());
            Faction faction = players.getFaction();
            double balance = faction.get().getDouble("balance");
            if (!faction.get().isConfigurationSection("claims.0")) {
                Location locationOne = Main.getInstance().posClaimOne.get(p.getUniqueId());
                Location locationTwo = Main.getInstance().posClaimTwo.get(p.getUniqueId());
                locationOne.setY(0);
                locationTwo.setY(0);
                Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                int size = 0;
                for (Block b : cuboid.getBlocks()) {
                    size++;
                }
                double cost;
                String blockSize = cuboid.getSizeX() + "x" + cuboid.getSizeZ();
                if (blockSize.equals("5x5")) {
                    cost = Main.getInstance().getConfig().getInt("claim.price.multiplier");
                } else {
                    double multiplier = Main.getInstance().getConfig().getDouble("claim.price.per-block");
                    double not = 0;
                    for (Block ignored : cuboid.getBlocks()) {
                        not = not + multiplier;
                    }
                    cost = multiplier + not;
                }
                if (balance >= cost) {
                    if (cuboid.getSizeX() >= Main.getInstance().getConfig().getInt("claim.least-x") && cuboid.getSizeZ() >= Main.getInstance().getConfig().getInt("claim.least-z")) {
                        if (cuboid.getSizeX() < Main.getInstance().getConfig().getInt("claim.max-x") && cuboid.getSizeZ() < Main.getInstance().getConfig().getInt("claim.max-z")) {
                            Location newLocationOne = Main.getInstance().posClaimOne.get(p.getUniqueId());
                            Location newLocationTwo = Main.getInstance().posClaimTwo.get(p.getUniqueId());
                            faction.get().set("balance", balance - cost);
                            faction.get().set("claims.0.world", p.getWorld().getName());
                            faction.get().set("claims.0.sideOne.x", newLocationOne.getX());
                            faction.get().set("claims.0.sideOne.y", newLocationOne.getY());
                            faction.get().set("claims.0.sideOne.z", newLocationOne.getZ());
                            faction.get().set("claims.0.sideTwo.x", newLocationTwo.getX());
                            faction.get().set("claims.0.sideTwo.y", newLocationTwo.getY());
                            faction.get().set("claims.0.sideTwo.z", newLocationTwo.getZ());
                            faction.save();
                            Main.getInstance().posClaimOne.remove(p.getUniqueId());
                            Main.getInstance().posClaimTwo.remove(p.getUniqueId());
                            Main.getInstance().claiming.remove(p.getUniqueId());
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.purchased").replace("%cost%", String.valueOf(cost))));
                            p.getInventory().remove(p.getItemInHand());
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.too-large")));
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.too-small")));
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.not-enough")));
                }
            } else {

            }
        } else {
            p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.cannot-claim")));
        }
    }

    public static void cancelClaim(Player p) {
        Main.getInstance().posClaimOne.remove(p.getUniqueId());
        Main.getInstance().posClaimTwo.remove(p.getUniqueId());
        if (Main.getInstance().claiming.contains(p.getUniqueId())) {
            Main.getInstance().claiming.remove(p.getUniqueId());
        } else if (Main.getInstance().claimingAgainst.contains(p.getUniqueId())) {
            Main.getInstance().claimingAgainst.remove(p.getUniqueId());
        } else {
            Main.getInstance().claimFor.remove(p.getUniqueId());
        }
        p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.cancelled")));
        p.getInventory().remove(p.getItemInHand());
    }

    public static void claimSelectTwo(Player p, Location clickedLocation) {
        ArrayList<Material> blockTypes = new ArrayList<>();
        blockTypes.add(Material.DIAMOND_BLOCK);
        blockTypes.add(Material.EMERALD_BLOCK);
        blockTypes.add(Material.IRON_BLOCK);
        blockTypes.add(Material.GOLD_BLOCK);
        blockTypes.add(Material.COAL_BLOCK);
        blockTypes.add(Material.LOG);

        Random random = new Random();
        int pos = random.nextInt(blockTypes.size());
        Material material = blockTypes.get(pos);

        int startingInt = clickedLocation.getBlockY() + 1;

        for (int i = 0; i < startingInt; i++) {
            Location loc = clickedLocation;
            loc.setY(i);
            if (loc.getWorld().getBlockAt(loc).getType().equals(Material.AIR)) {
                if (i / 3 == 0) {
                    p.sendBlockChange(loc, material, (byte) 0);
                } else {
                    p.sendBlockChange(loc, Material.GLASS, (byte) 0);
                }
            }
        }
        Main.getInstance().posClaimTwo.put(p.getUniqueId(), clickedLocation);
        p.sendMessage(C.chat(Locale.get().getString("command.faction.claim.selected-position-two").replace("%x%", String.valueOf(Cooldown.round(p.getLocation().getX(), 1))).replace("%z%", String.valueOf(Cooldown.round(p.getLocation().getZ(), 1)))));
        if (Main.getInstance().posClaimOne.containsKey(p.getUniqueId())) {
            ArrayList<String> msgs = new ArrayList<>();
            Location locationOne = Main.getInstance().posClaimOne.get(p.getUniqueId());
            Location locationTwo = Main.getInstance().posClaimTwo.get(p.getUniqueId());
            locationOne.setY(0);
            locationTwo.setY(0);
            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
            int size = 0;
            for (Block b : cuboid.getBlocks()) {
                size++;
            }
            double cost;
            String blockSize = cuboid.getSizeX() + "x" + cuboid.getSizeZ();
            if (blockSize.equals("5x5")) {
                cost = Main.getInstance().getConfig().getInt("claim.price.multiplier");
            } else {
                int cos = Main.getInstance().getConfig().getInt("claim.price.multiplier");
                double multiplier = Main.getInstance().getConfig().getDouble("claim.price.per-block");
                double not = 0;
                for (Block b : cuboid.getBlocks()) {
                    not = not + multiplier;
                }
                cost = not + cos;
            }
            for (String s : Locale.get().getStringList("command.faction.claim.claim-create-before")) {
                if (s.contains("%cost%")) {
                    s = s.replace("%cost%", String.valueOf(cost));
                }

                if (s.contains("%size%")) {
                    s = s.replace("%size%", String.valueOf(size));
                }

                if (s.contains("%x-size%")) {
                    s = s.replace("%x-size%", String.valueOf(cuboid.getSizeX()));
                }

                if (s.contains("%z-size%")) {
                    s = s.replace("%z-size%", String.valueOf(cuboid.getSizeZ()));
                }

                msgs.add(C.chat(s));
            }

            for (String s : msgs) {
                p.sendMessage(C.chat(s));
            }
        }
    }
}
