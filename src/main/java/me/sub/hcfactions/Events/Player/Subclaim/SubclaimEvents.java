package me.sub.hcfactions.Events.Player.Subclaim;

import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Sign;

public class SubclaimEvents implements Listener {

    @EventHandler
    public void onEdit(SignChangeEvent e) {
        Player p = e.getPlayer();
        if (e.getLine(0).equals("[Subclaim]")) {
            if (e.getLines().length > 1) {
                OfflinePlayer player = Bukkit.getPlayer(e.getLine(1));
                Players addedPlayers = new Players(player.getUniqueId().toString());
                if (addedPlayers.exists()) {
                    if (addedPlayers.hasFaction()) {
                        if (new Players(p.getUniqueId().toString()).hasFaction()) {
                            Sign sign = (Sign) e.getBlock().getState().getData();
                            Block attached = e.getBlock().getRelative(sign.getAttachedFace());
                            if(attached.getType() == Material.CHEST || attached.getType() == Material.TRAPPED_CHEST) {
                                if (addedPlayers.getFaction().get().getString("uuid").equals(new Players(p.getUniqueId().toString()).getFaction().get().getString("uuid"))) {
                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.created").replace("%player%", player.getName())));
                                    e.setLine(0, C.chat("&c&l[Subclaim]"));
                                } else {
                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.not-faction")));
                                    e.setCancelled(true);
                                }
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.invalid")));
                            }
                        } else {
                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.no-faction")));
                            e.setCancelled(true);
                        }
                    } else {
                        p.sendMessage(C.chat(Locale.get().getString("events.subclaim.not-faction")));
                        e.setCancelled(true);
                    }
                } else {
                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.not-exist")));
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.invalid")));
            }

        }
    }

    public Block getAttachedSignBlock(Block block) {
        Sign sign = (Sign) block.getState().getData();
        return block.getRelative(sign.getAttachedFace());
    }

    @EventHandler
    public void onSignBreak(BlockBreakEvent e) {
        if (!e.isCancelled()) {
            Player p = e.getPlayer();
            Players players = new Players(p.getUniqueId().toString());
            Faction faction = players.getFaction();
            if (e.getBlock().getType().equals(Material.WALL_SIGN)) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) e.getBlock().getState();
                if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                    if (!player.getUniqueId().equals(p.getUniqueId())) {
                        if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                            if (!faction.isRaidable()) {
                                if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.isCancelled()) {
            Player p = e.getPlayer();
            Players players = new Players(p.getUniqueId().toString());
            Faction faction = players.getFaction();
            Block clickedBlock = e.getBlock();
            if (clickedBlock.getType().equals(Material.CHEST) || clickedBlock.getType().equals(Material.TRAPPED_CHEST)) {
                Chest chest = (Chest) clickedBlock.getState();
                BlockFace[] aside = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
                boolean dbl = false;
                boolean tdbl = false;
                for (BlockFace bf : aside) {
                    if (clickedBlock.getRelative(bf, 1).getType() == Material.CHEST) {
                        dbl = true;
                        break;
                    }
                }
                for (BlockFace bf : aside) {
                    if (clickedBlock.getRelative(bf, 1).getType() == Material.TRAPPED_CHEST) {
                        tdbl = true;
                        break;
                    }
                }
                if (dbl) {
                    DoubleChest doubleChest = (DoubleChest) chest.getInventory().getHolder();
                    Chest sideOne = (Chest) doubleChest.getLeftSide();
                    Chest sideTwo = (Chest) doubleChest.getRightSide();

                    Block leftSideOne = sideOne.getBlock().getLocation().add(1, 0, 0).getBlock();
                    Block rightSideOne = sideOne.getBlock().getLocation().subtract(1, 0, 0).getBlock();
                    Block forwardSideOne = sideOne.getBlock().getLocation().add(0, 0, 1).getBlock();
                    Block downSideOne = sideOne.getBlock().getLocation().subtract(0, 0, 1).getBlock();

                    Block leftSideTwo = sideTwo.getBlock().getLocation().add(1, 0, 0).getBlock();
                    Block rightSideTwo = sideTwo.getBlock().getLocation().subtract(1, 0, 0).getBlock();
                    Block forwardSideTwo = sideTwo.getBlock().getLocation().add(0, 0, 1).getBlock();
                    Block downSideTwo = sideTwo.getBlock().getLocation().subtract(0, 0, 1).getBlock();

                    if (leftSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftSideOne).getType().equals(Material.CHEST) && getAttachedSignBlock(leftSideOne).getLocation().equals(sideOne.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftSideOne.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (leftSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftSideTwo).getType().equals(Material.CHEST) && getAttachedSignBlock(leftSideTwo).getLocation().equals(sideTwo.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftSideTwo.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (rightSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightSideOne).getType().equals(Material.CHEST) && getAttachedSignBlock(rightSideOne).getLocation().equals(sideOne.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightSideOne.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (rightSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightSideTwo).getType().equals(Material.CHEST) && getAttachedSignBlock(rightSideTwo).getLocation().equals(sideTwo.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightSideTwo.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (forwardSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardSideOne).getType().equals(Material.CHEST) && getAttachedSignBlock(forwardSideOne).getLocation().equals(sideOne.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardSideOne.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (forwardSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardSideTwo).getType().equals(Material.CHEST) && getAttachedSignBlock(forwardSideTwo).getLocation().equals(sideTwo.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardSideTwo.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (downSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downSideOne).getType().equals(Material.CHEST) && getAttachedSignBlock(downSideOne).getLocation().equals(sideOne.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downSideOne.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (downSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downSideTwo).getType().equals(Material.CHEST) && getAttachedSignBlock(downSideTwo).getLocation().equals(sideTwo.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downSideTwo.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (tdbl) {
                    DoubleChest doubleChest = (DoubleChest) chest.getInventory().getHolder();
                    Chest sideOne = (Chest) doubleChest.getLeftSide();
                    Chest sideTwo = (Chest) doubleChest.getRightSide();

                    Block leftSideOne = sideOne.getBlock().getLocation().add(1, 0, 0).getBlock();
                    Block rightSideOne = sideOne.getBlock().getLocation().subtract(1, 0, 0).getBlock();
                    Block forwardSideOne = sideOne.getBlock().getLocation().add(0, 0, 1).getBlock();
                    Block downSideOne = sideOne.getBlock().getLocation().subtract(0, 0, 1).getBlock();

                    Block leftSideTwo = sideTwo.getBlock().getLocation().add(1, 0, 0).getBlock();
                    Block rightSideTwo = sideTwo.getBlock().getLocation().subtract(1, 0, 0).getBlock();
                    Block forwardSideTwo = sideTwo.getBlock().getLocation().add(0, 0, 1).getBlock();
                    Block downSideTwo = sideTwo.getBlock().getLocation().subtract(0, 0, 1).getBlock();

                    if (leftSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftSideOne).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(leftSideOne).getLocation().equals(sideOne.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftSideOne.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (leftSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftSideTwo).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(leftSideTwo).getLocation().equals(sideTwo.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftSideTwo.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (rightSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightSideOne).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(rightSideOne).getLocation().equals(sideOne.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightSideOne.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (rightSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightSideTwo).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(rightSideTwo).getLocation().equals(sideTwo.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightSideTwo.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (forwardSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardSideOne).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(forwardSideOne).getLocation().equals(sideOne.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardSideOne.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (forwardSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardSideTwo).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(forwardSideTwo).getLocation().equals(sideTwo.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardSideTwo.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (downSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downSideOne).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(downSideOne).getLocation().equals(sideOne.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downSideOne.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (downSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downSideTwo).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(downSideTwo).getLocation().equals(sideTwo.getLocation())) {
                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downSideTwo.getState();
                        if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                            if (!player.getUniqueId().equals(p.getUniqueId())) {
                                if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                    if (!faction.isRaidable()) {
                                        if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                            p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Block leftBlock = clickedBlock.getLocation().add(1, 0, 0).getBlock();
                    Block rightBlock = clickedBlock.getLocation().add(0, 0, 1).getBlock();
                    Block forwardBlock = clickedBlock.getLocation().subtract(0, 0, 1).getBlock();
                    Block downBlock = clickedBlock.getLocation().subtract(0, 0, 1).getBlock();
                    if (clickedBlock.getType().equals(Material.CHEST)) {
                        if (leftBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftBlock).getType().equals(Material.CHEST) && getAttachedSignBlock(leftBlock).getLocation().equals(clickedBlock.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftBlock.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (rightBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightBlock).getType().equals(Material.CHEST) && getAttachedSignBlock(rightBlock).getLocation().equals(clickedBlock.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightBlock.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (forwardBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardBlock).getType().equals(Material.CHEST) && getAttachedSignBlock(forwardBlock).getLocation().equals(clickedBlock.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardBlock.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (downBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downBlock).getType().equals(Material.CHEST) && getAttachedSignBlock(downBlock).getLocation().equals(clickedBlock.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downBlock.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (clickedBlock.getType().equals(Material.TRAPPED_CHEST)) {
                        if (leftBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftBlock).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(leftBlock).getLocation().equals(clickedBlock.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftBlock.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (rightBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightBlock).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(rightBlock).getLocation().equals(clickedBlock.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardBlock.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (forwardBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardBlock).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(forwardBlock).getLocation().equals(clickedBlock.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardBlock.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (downBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downBlock).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(downBlock).getLocation().equals(clickedBlock.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downBlock.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        if (!e.isCancelled()) {
            Player p = e.getPlayer();
            Players players = new Players(p.getUniqueId().toString());
            Faction faction = players.getFaction();
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Block clickedBlock = e.getClickedBlock();
                if (clickedBlock.getType().equals(Material.CHEST) || clickedBlock.getType().equals(Material.TRAPPED_CHEST)) {
                    Chest chest = (Chest) clickedBlock.getState();
                    BlockFace[] aside = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
                    boolean dbl = false;
                    boolean tdbl = false;
                    for(BlockFace bf : aside)
                    {
                        if(clickedBlock.getRelative(bf, 1).getType() == Material.CHEST)
                        {
                            dbl = true;
                            break;
                        }
                    }
                    for(BlockFace bf : aside)
                    {
                        if(clickedBlock.getRelative(bf, 1).getType() == Material.TRAPPED_CHEST)
                        {
                            tdbl = true;
                            break;
                        }
                    }
                    if(dbl) {
                        DoubleChest doubleChest = (DoubleChest) chest.getInventory().getHolder();
                        Chest sideOne = (Chest) doubleChest.getLeftSide();
                        Chest sideTwo = (Chest) doubleChest.getRightSide();

                        Block leftSideOne = sideOne.getBlock().getLocation().add(1, 0, 0).getBlock();
                        Block rightSideOne = sideOne.getBlock().getLocation().subtract(1, 0, 0).getBlock();
                        Block forwardSideOne = sideOne.getBlock().getLocation().add(0, 0, 1).getBlock();
                        Block downSideOne = sideOne.getBlock().getLocation().subtract(0, 0, 1).getBlock();

                        Block leftSideTwo = sideTwo.getBlock().getLocation().add(1, 0, 0).getBlock();
                        Block rightSideTwo = sideTwo.getBlock().getLocation().subtract(1, 0, 0).getBlock();
                        Block forwardSideTwo = sideTwo.getBlock().getLocation().add(0, 0, 1).getBlock();
                        Block downSideTwo = sideTwo.getBlock().getLocation().subtract(0, 0, 1).getBlock();

                        if (leftSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftSideOne).getType().equals(Material.CHEST) && getAttachedSignBlock(leftSideOne).getLocation().equals(sideOne.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftSideOne.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (leftSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftSideTwo).getType().equals(Material.CHEST) && getAttachedSignBlock(leftSideTwo).getLocation().equals(sideTwo.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftSideTwo.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (rightSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightSideOne).getType().equals(Material.CHEST) && getAttachedSignBlock(rightSideOne).getLocation().equals(sideOne.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightSideOne.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (rightSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightSideTwo).getType().equals(Material.CHEST) && getAttachedSignBlock(rightSideTwo).getLocation().equals(sideTwo.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightSideTwo.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (forwardSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardSideOne).getType().equals(Material.CHEST) && getAttachedSignBlock(forwardSideOne).getLocation().equals(sideOne.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardSideOne.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (forwardSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardSideTwo).getType().equals(Material.CHEST) && getAttachedSignBlock(forwardSideTwo).getLocation().equals(sideTwo.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardSideTwo.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (downSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downSideOne).getType().equals(Material.CHEST) && getAttachedSignBlock(downSideOne).getLocation().equals(sideOne.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downSideOne.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (downSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downSideTwo).getType().equals(Material.CHEST) && getAttachedSignBlock(downSideTwo).getLocation().equals(sideTwo.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downSideTwo.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (tdbl) {
                        DoubleChest doubleChest = (DoubleChest) chest.getInventory().getHolder();
                        Chest sideOne = (Chest) doubleChest.getLeftSide();
                        Chest sideTwo = (Chest) doubleChest.getRightSide();

                        Block leftSideOne = sideOne.getBlock().getLocation().add(1, 0, 0).getBlock();
                        Block rightSideOne = sideOne.getBlock().getLocation().subtract(1, 0, 0).getBlock();
                        Block forwardSideOne = sideOne.getBlock().getLocation().add(0, 0, 1).getBlock();
                        Block downSideOne = sideOne.getBlock().getLocation().subtract(0, 0, 1).getBlock();

                        Block leftSideTwo = sideTwo.getBlock().getLocation().add(1, 0, 0).getBlock();
                        Block rightSideTwo = sideTwo.getBlock().getLocation().subtract(1, 0, 0).getBlock();
                        Block forwardSideTwo = sideTwo.getBlock().getLocation().add(0, 0, 1).getBlock();
                        Block downSideTwo = sideTwo.getBlock().getLocation().subtract(0, 0, 1).getBlock();

                        if (leftSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftSideOne).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(leftSideOne).getLocation().equals(sideOne.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftSideOne.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (leftSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftSideTwo).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(leftSideTwo).getLocation().equals(sideTwo.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftSideTwo.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (rightSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightSideOne).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(rightSideOne).getLocation().equals(sideOne.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightSideOne.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (rightSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightSideTwo).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(rightSideTwo).getLocation().equals(sideTwo.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightSideTwo.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (forwardSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardSideOne).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(forwardSideOne).getLocation().equals(sideOne.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardSideOne.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (forwardSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardSideTwo).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(forwardSideTwo).getLocation().equals(sideTwo.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardSideTwo.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (downSideOne.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downSideOne).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(downSideOne).getLocation().equals(sideOne.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downSideOne.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (downSideTwo.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downSideTwo).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(downSideTwo).getLocation().equals(sideTwo.getLocation())) {
                            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downSideTwo.getState();
                            if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                if (!player.getUniqueId().equals(p.getUniqueId())) {
                                    if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                        if (!faction.isRaidable()) {
                                            if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Block leftBlock = clickedBlock.getLocation().add(1, 0, 0).getBlock();
                        Block rightBlock = clickedBlock.getLocation().add(0, 0, 1).getBlock();
                        Block forwardBlock = clickedBlock.getLocation().subtract(0, 0, 1).getBlock();
                        Block downBlock = clickedBlock.getLocation().subtract(0, 0, 1).getBlock();
                        if (clickedBlock.getType().equals(Material.CHEST)) {
                            if (leftBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftBlock).getType().equals(Material.CHEST) && getAttachedSignBlock(leftBlock).getLocation().equals(clickedBlock.getLocation())) {
                                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftBlock.getState();
                                if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                    OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                    if (!player.getUniqueId().equals(p.getUniqueId())) {
                                        if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                            if (!faction.isRaidable()) {
                                                if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                    e.setCancelled(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (rightBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightBlock).getType().equals(Material.CHEST) && getAttachedSignBlock(rightBlock).getLocation().equals(clickedBlock.getLocation())) {
                                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) rightBlock.getState();
                                if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                    OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                    if (!player.getUniqueId().equals(p.getUniqueId())) {
                                        if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                            if (!faction.isRaidable()) {
                                                if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                    e.setCancelled(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (forwardBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardBlock).getType().equals(Material.CHEST) && getAttachedSignBlock(forwardBlock).getLocation().equals(clickedBlock.getLocation())) {
                                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardBlock.getState();
                                if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                    OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                    if (!player.getUniqueId().equals(p.getUniqueId())) {
                                        if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                            if (!faction.isRaidable()) {
                                                if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                    e.setCancelled(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (downBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downBlock).getType().equals(Material.CHEST) && getAttachedSignBlock(downBlock).getLocation().equals(clickedBlock.getLocation())) {
                                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downBlock.getState();
                                if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                    OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                    if (!player.getUniqueId().equals(p.getUniqueId())) {
                                        if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                            if (!faction.isRaidable()) {
                                                if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                    e.setCancelled(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (clickedBlock.getType().equals(Material.TRAPPED_CHEST)) {
                            if (leftBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(leftBlock).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(leftBlock).getLocation().equals(clickedBlock.getLocation())) {
                                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) leftBlock.getState();
                                if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                    OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                    if (!player.getUniqueId().equals(p.getUniqueId())) {
                                        if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                            if (!faction.isRaidable()) {
                                                if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                    e.setCancelled(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (rightBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(rightBlock).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(rightBlock).getLocation().equals(clickedBlock.getLocation())) {
                                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardBlock.getState();
                                if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                    OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                    if (!player.getUniqueId().equals(p.getUniqueId())) {
                                        if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                            if (!faction.isRaidable()) {
                                                if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                    e.setCancelled(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (forwardBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(forwardBlock).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(forwardBlock).getLocation().equals(clickedBlock.getLocation())) {
                                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) forwardBlock.getState();
                                if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                    OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                    if (!player.getUniqueId().equals(p.getUniqueId())) {
                                        if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                            if (!faction.isRaidable()) {
                                                if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                    e.setCancelled(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (downBlock.getType().equals(Material.WALL_SIGN) && getAttachedSignBlock(downBlock).getType().equals(Material.TRAPPED_CHEST) && getAttachedSignBlock(downBlock).getLocation().equals(clickedBlock.getLocation())) {
                                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) downBlock.getState();
                                if (sign.getLine(0).equals(C.chat("&c&l[Subclaim]"))) {
                                    OfflinePlayer player = Bukkit.getOfflinePlayer(sign.getLine(1));
                                    if (!player.getUniqueId().equals(p.getUniqueId())) {
                                        if (!faction.get().getStringList("coleaders").contains(p.getUniqueId().toString()) || !faction.get().getString("leader").equals(p.getUniqueId().toString())) {
                                            if (!faction.isRaidable()) {
                                                if (!Main.getInstance().bypass.contains(p.getUniqueId())) {
                                                    p.sendMessage(C.chat(Locale.get().getString("events.subclaim.cant-open").replace("%player%", player.getName())));
                                                    e.setCancelled(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
