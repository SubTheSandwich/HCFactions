package me.sub.hcfactions.Main;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.object.LCWaypoint;
import io.github.thatkawaiisam.ziggurat.Ziggurat;
import io.github.thatkawaiisam.ziggurat.ZigguratAdapter;
import io.github.thatkawaiisam.ziggurat.ZigguratCommons;
import io.github.thatkawaiisam.ziggurat.utils.BufferedTabObject;
import io.github.thatkawaiisam.ziggurat.utils.TabColumn;
import me.sub.hcfactions.Commands.Admin.*;
import me.sub.hcfactions.Commands.Member.*;
import me.sub.hcfactions.Commands.Staff.*;
import me.sub.hcfactions.Events.Admin.ItemSignCreate;
import me.sub.hcfactions.Events.Brew.BrewItemEvent;
import me.sub.hcfactions.Events.Brew.BrewSpeedEvent;
import me.sub.hcfactions.Events.Deathban.DeathbanJoinEvent;
import me.sub.hcfactions.Events.Deaths.DeathMessageSendEvent;
import me.sub.hcfactions.Events.Enchant.AnvilEnchantEvent;
import me.sub.hcfactions.Events.Enchant.EnchantmentLimiter;
import me.sub.hcfactions.Events.End.EnterEndEvent;
import me.sub.hcfactions.Events.End.LeaveEndEvent;
import me.sub.hcfactions.Events.Faction.FactionInteractEvent;
import me.sub.hcfactions.Events.Furnace.FurnaceSmelt;
import me.sub.hcfactions.Events.Logger.CombatLoggerCreate;
import me.sub.hcfactions.Events.Logger.LoggerRemove;
import me.sub.hcfactions.Events.Logout.LogoutEvents;
import me.sub.hcfactions.Events.Mapkit.MapkitClickEvent;
import me.sub.hcfactions.Events.Player.*;
import me.sub.hcfactions.Events.Player.Border.CrossWorldBorderEvent;
import me.sub.hcfactions.Events.Player.Chat.PlayerGlobalChatEvent;
import me.sub.hcfactions.Events.Player.Classes.Archer.ArcherJumpEffect;
import me.sub.hcfactions.Events.Player.Classes.Archer.ArcherSpeedEffect;
import me.sub.hcfactions.Events.Player.Classes.Bard.EffectUseEvent;
import me.sub.hcfactions.Events.Player.Classes.LoadClass;
import me.sub.hcfactions.Events.Player.Classes.Rogue.BackstabEvent;
import me.sub.hcfactions.Events.Player.Classes.Rogue.RogueJumpEffect;
import me.sub.hcfactions.Events.Player.Classes.Rogue.RogueSpeedEffect;
import me.sub.hcfactions.Events.Player.Combat.ArcherTagEvent;
import me.sub.hcfactions.Events.Player.Combat.PlayerEnterCombat;
import me.sub.hcfactions.Events.Player.Conquest.ConquestMovementEvent;
import me.sub.hcfactions.Events.Player.Consume.AppleConsumeEvent;
import me.sub.hcfactions.Events.Player.Crowbar.CrowbarUseEvent;
import me.sub.hcfactions.Events.Player.Crowbar.SpawnerPlaceEvent;
import me.sub.hcfactions.Events.Player.Faction.ClaimSelectEvent;
import me.sub.hcfactions.Events.Player.Faction.ElevatorCreateEvent;
import me.sub.hcfactions.Events.Player.Faction.ElevatorInteractEvent;
import me.sub.hcfactions.Events.Player.Faction.HomeEvents;
import me.sub.hcfactions.Events.Player.Frozen.FrozenEvents;
import me.sub.hcfactions.Events.Player.Items.EnderpearlEvent;
import me.sub.hcfactions.Events.Player.Koths.KothMovementEvent;
import me.sub.hcfactions.Events.Player.Lunar.LoadPlayerName;
import me.sub.hcfactions.Events.Player.Lunar.SetupSpawnWaypoint;
import me.sub.hcfactions.Events.Player.Mountain.MountainSelectEvent;
import me.sub.hcfactions.Events.Player.Ore.OreMineRegisterEvent;
import me.sub.hcfactions.Events.Player.Profile.ProfileClickEvent;
import me.sub.hcfactions.Events.Player.Settings.SettingsClickEvent;
import me.sub.hcfactions.Events.Player.Subclaim.SubclaimEvents;
import me.sub.hcfactions.Events.Scoreboard.LoadScoreboard;
import me.sub.hcfactions.Events.Scoreboard.RemoveScoreboard;
import me.sub.hcfactions.Events.SignInteractEvent;
import me.sub.hcfactions.Events.Staff.StaffEvents;
import me.sub.hcfactions.Files.Faction.Faction;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Mountain.Mountain;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Files.Tab.Tab;
import me.sub.hcfactions.Utils.Color.C;
import me.sub.hcfactions.Utils.Cooldowns.Cooldown;
import me.sub.hcfactions.Utils.Cuboid.Cuboid;
import me.sub.hcfactions.Utils.Timer.Timer;
import me.sub.hcfactions.Utils.Timer.Timers;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

// add in default client nametags
// Add in other types of chat
// Add in /f stuck
// Add in /f top
// Add in /f forcejoin, /f unclaimfor, /f forcedisband, /f forceleader and stuff like that


public class Main extends JavaPlugin {

    public HashMap<UUID, String> claimFor = new HashMap<>();

    public HashMap<UUID, Material> randomlyGeneratedMaterial = new HashMap<>();

    public HashMap<UUID, ArrayList<Cuboid>> mappedLocations = new HashMap<>();

    public ArrayList<UUID> bypass = new ArrayList<>();

    public HashMap<UUID, String> selectingMountain = new HashMap<>();
    public HashMap<UUID, Location> mountainPositionOne = new HashMap<>();
    public HashMap<UUID, Location> mountainPositionTwo = new HashMap<>();
    public HashMap<String, Integer> resetTimerMountain = new HashMap<>();

    public HashMap<String, HashMap<String, Integer>> conquestTimer = new HashMap<>();
    public HashMap<String, HashMap<String, UUID>> capturingColorFaction = new HashMap<>();
    public HashMap<String, Integer> conquestPoints = new HashMap<>();

    public HashMap<String, Integer> kothTimer = new HashMap<>();
    public HashMap<String, UUID> capturingKothFaction = new HashMap<>();

    public LunarClientAPI lunarClientAPI;

    public ArrayList<UUID> claimingAgainst = new ArrayList<>();

    public HashMap<String, Integer> customTimers = new HashMap<>();
    public HashMap<String, Boolean> customTimersPaused = new HashMap<>();

    public HashMap<UUID, BigDecimal> archerTag = new HashMap<>();

    public HashMap<UUID, Location> posClaimOne = new HashMap<>();
    public HashMap<UUID, Location> posClaimTwo = new HashMap<>();

    public HashMap<Player, String> hcfClass = new HashMap<>();
    public HashMap<Player, Double> bardEnergy = new HashMap<>();
    public HashMap<Player, ArrayList<String>> invites = new HashMap<>();
    public HashMap<String, Player> focusedPlayer = new HashMap<>();
    public HashMap<String, String> focusedFaction = new HashMap<>();
    public ArrayList<Player> staff = new ArrayList<>();
    public ArrayList<Player> vanished = new ArrayList<>();

    public HashMap<Player, BigDecimal> homeTimer = new HashMap<>();

    public HashMap<UUID, BigDecimal> chatSlowPlayer = new HashMap<>();

    public HashMap<UUID, ArrayList<Material>> blockedItems = new HashMap<>();

    public HashMap<Player, ItemStack[]> savedInventoryArmorStaff = new HashMap<>();
    public HashMap<Player, ItemStack[]> savedInventoryContentsStaff = new HashMap<>();
    public HashMap<Player, GameMode> savedGameModeStaff = new HashMap<>();
    public HashMap<UUID, ItemStack[]> savedInventoryArmorDeath = new HashMap<>();
    public HashMap<UUID, ItemStack[]> savedInventoryContentsDeath = new HashMap<>();
    public HashMap<Player, Double> savedHealthStaff = new HashMap<>();
    public HashMap<Player, Integer> savedHungerStaff = new HashMap<>();
    public HashMap<Player, BigDecimal> logoutTimer = new HashMap<>();

    public HashMap<UUID, BigDecimal> appleTimer = new HashMap<>();
    public HashMap<UUID, Integer> archerSpeedCooldown = new HashMap<>();
    public HashMap<UUID, Integer> archerJumpCooldown = new HashMap<>();

    public HashMap<UUID, Integer> rogueBackstabCooldown = new HashMap<>();
    public HashMap<UUID, Integer> rogueSpeedCooldown = new HashMap<>();
    public HashMap<UUID, Integer> rogueJumpCooldown = new HashMap<>();

    public HashMap<UUID, Integer> pvpTimer = new HashMap<>();

    public HashMap<UUID, BigDecimal> enderpearlCooldown = new HashMap<>();
    public HashMap<UUID, BigDecimal> combatTimer = new HashMap<>();
    public ArrayList<UUID> ticking = new ArrayList<>();
    public HashMap<String, LCWaypoint> currentRally = new HashMap<>();

    public HashMap<Timers, Integer> timers = new HashMap<>();

    public ArrayList<UUID> requestCooldown = new ArrayList<>();
    public ArrayList<UUID> reportCooldown = new ArrayList<>();

    public HashMap<UUID, ItemStack[]> logoutArmorContents = new HashMap<>();
    public HashMap<UUID, ItemStack[]> logoutContents = new HashMap<>();

    public ArrayList<UUID> revived = new ArrayList<>();

    public HashMap<UUID, String> currentFactionLocation = new HashMap<>();

    public HashMap<OfflinePlayer, Integer> lffCooldown = new HashMap<>();
    public HashMap<OfflinePlayer, BigDecimal> effectCooldown = new HashMap<>();
    public boolean disabledChat = false;
    public int chatSlow = 0;
    public ArrayList<Player> factionChat = new ArrayList<>();
    public ArrayList<Player> staffChat = new ArrayList<>();

    public HashMap<String, Integer> onlineFactions = new HashMap<>();
    public ArrayList<UUID> renameCooldown = new ArrayList<>();

    public ArrayList<UUID> loggedDeath = new ArrayList<>();

    public ArrayList<Player> frozen = new ArrayList<>();

    public ArrayList<UUID> claiming = new ArrayList<>();

    public ArrayList<Player> sotwEnabled = new ArrayList<>();
    public boolean sotwStarted = false;
    public boolean sotwPaused = false;

    public HashMap<UUID, Integer> goppleTimer = new HashMap<>();

    public HashMap<String, Integer> regen = new HashMap<>();

    public ArrayList<UUID> cooldownCreate = new ArrayList<>();

    public ArrayList<UUID> messageSent = new ArrayList<>();

    private Chat chat;

    public UUID randomGeneratedUUIDConquest = null;

    public boolean eotwStarted = false;

    private static Main instance;

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public Chat getChat() {
        return chat;
    }

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        lunarClientAPI = LunarClientAPI.getInstance();
        setupChat();
        files();
        events();
        commands();
        generateMountains();
        loadTab();
        regenerateDTR();
        Timer.trackTimers();
        if (Locale.get().getBoolean("firstRun")) {
            Locale.get().set("firstRun", false);
            Locale.save();
        }
    }
    @Override
    public void onDisable() {

    }

    private void loadTab() {
        new Ziggurat(Main.getInstance(), new ZigguratAdapter() {
            @Override
            public Set<BufferedTabObject> getSlots(Player p) {
                Players players = new Players(p.getUniqueId().toString());
                Set<BufferedTabObject> toReturn = new HashSet<>();
                //Top Left hand corner
                Tab tab = new Tab();
                for (String s : tab.get().getConfigurationSection("tab.left").getKeys(false)) {
                    int lineNumber = Integer.parseInt(s);
                    s = tab.get().getString("tab.left." + s);
                    if (s.contains("%faction-home-location%")) {
                        if (players.hasFaction() &&  players.getFaction().get().isConfigurationSection("home")) {
                            String format = "%x%, %z%";
                            format = format.replace("%x%", String.valueOf(players.getFaction().get().getInt("home.x")));
                            format = format.replace("%z%", String.valueOf(players.getFaction().get().getInt("home.z")));
                            s = s.replace("%faction-home-location%", format);
                        } else {
                            s = s.replace("%faction-home-location%", "None");
                        }
                    }
                    if (s.contains("%kills%")) {
                        s = s.replace("%kills%", String.valueOf(players.get().getInt("kills")));
                    }
                    if (s.contains("%deaths%")) {
                        s = s.replace("%deaths%", String.valueOf(players.get().getInt("deaths")));
                    }
                    if (s.contains("%faction-in%")) {
                        if (getFactionInClaim(p.getLocation()) != null) {
                            Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                            if (faction.exists()) {
                                if (faction.get().getString("color") != null) {
                                    if (faction.get().getString("type").equals("KOTH")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                    } else if (faction.get().getString("type").equals("ROAD")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                    } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain");
                                    } else {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                    }
                                } else {
                                    if (faction.get().getString("uuid").equals(players.get().getString("faction"))) {
                                        s = s.replace("%faction-in%", "&a" + faction.get().getString("name"));
                                    } else {
                                        s = s.replace("%faction-in%", "&c" + faction.get().getString("name"));
                                    }
                                }
                            } else {
                                s = s.replace("%faction-in%", getFactionInClaim(p.getLocation()));
                            }
                        } else {
                            s = s.replace("%faction-in%", "None");
                        }
                    }
                    if (s.contains("%x%")) {
                        s = s.replace("%x%", String.valueOf(p.getLocation().getBlockX()));
                    }
                    if (s.contains("%z%")) {
                        s = s.replace("%z%", String.valueOf(p.getLocation().getBlockZ()));
                    }
                    if (s.contains("%facing%")) {
                        s = s.replace("%facing%", getCardinalDirection(p));
                    }
                    if (s.contains("%online%")) {
                        s = s.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    }
                    if (s.contains("%max-players%")) {
                        s = s.replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                    }
                    if (s.contains("%protection-max%")) {
                        s = s.replace("%protection-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL")));
                    }
                    if (s.contains("%sharpness-max%")) {
                        s = s.replace("%sharpness-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.DAMAGE_ALL")));
                    }
                    if (s.contains("%active-koth%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            s = s.replace("%active-koth%", new ArrayList<>(Main.getInstance().kothTimer.keySet()).get(0));
                        } else {
                            s = s.replace("%active-koth%", "&7");
                        }
                    }
                    if (s.contains("%koth-time%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            int time = Main.getInstance().kothTimer.get(keys.get(0));
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%koth-time%", timee);
                        } else {
                            s = s.replace("%koth-time%", "&7");
                        }
                    }
                    if (s.contains("%koth-location%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            if (getFactionByName(keys.get(0)) != null) {
                                Faction faction = getFactionByName(keys.get(0));
                                if (faction.get().isConfigurationSection("location")) {
                                    String format = "%x%, %z%";
                                    format = format.replace("%x%", String.valueOf(faction.get().getString("location.x")));
                                    format = format.replace("%z%", String.valueOf(faction.get().getString("location.z")));
                                    s = s.replace("%koth-location%", format);
                                } else {
                                    s = s.replace("%koth-location%", "None");
                                }
                            } else {
                                s = s.replace("%koth-location%", "None");
                            }
                        } else {
                            s = s.replace("%koth-location%", "&7");
                        }
                    }
                    if (s.contains("%team-list%")) {
                        HashMap<String, Integer> track = new HashMap<>();
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players play = new Players(d.getUniqueId().toString());
                            if (play.hasFaction()) {
                                track.put(play.getFaction().get().getString("uuid"), play.getFaction().getAllOnlinePlayers().size());
                            }
                        }
                        Map<String, Integer> sortedMap = track.entrySet().stream()
                                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> { throw new AssertionError(); },
                                        LinkedHashMap::new
                                ));
                        if (sortedMap.size() != 0) {
                            if (sortedMap.size() <= lineNumber - 1) {
                                Faction foundFaction = null;
                                for (String faction : sortedMap.keySet()) {
                                    if (sortedMap.get(faction) == lineNumber - 1) {
                                        foundFaction = new Faction(faction);
                                    }
                                }
                                if (foundFaction != null) {
                                    String format = tab.get().getString("team-list-format");
                                    format = format.replace("%name%", foundFaction.get().getString("name"));
                                    format = format.replace("%online-members%", String.valueOf(foundFaction.getAllOnlinePlayers().size()));
                                    s = s.replace("%team-list%", format);
                                } else {
                                    s = s.replace("%team-list%", "&7");
                                }
                            } else {
                                s = s.replace("%team-list%", "&7");
                            }
                        } else {
                            s = s.replace("%team-list%", "&7");
                        }
                    }
                    toReturn.add(
                            new BufferedTabObject()
                                    //Text
                                    .text(s)
                                    //Column
                                    .column(TabColumn.LEFT)
                                    //Slot
                                    .slot(lineNumber)
                                    //Ping (little buggy with 1.7 clients but defaults to 0 if thats the case
                                    .ping(999)
                                    //Texture (need to get skin sig and key
                                    .skin(ZigguratCommons.defaultTexture)
                    );
                }

                for (String s : tab.get().getConfigurationSection("tab.middle").getKeys(false)) {
                    int lineNumber = Integer.parseInt(s);
                    s = tab.get().getString("tab.middle." + s);
                    if (s.contains("%faction-home-location%")) {
                        if (players.hasFaction() &&  players.getFaction().get().isConfigurationSection("home")) {
                            String format = "%x%, %z%";
                            format = format.replace("%x%", String.valueOf(players.getFaction().get().getInt("home.x")));
                            format = format.replace("%z%", String.valueOf(players.getFaction().get().getInt("home.z")));
                            s = s.replace("%faction-home-location%", format);
                        } else {
                            s = s.replace("%faction-home-location%", "None");
                        }
                    }
                    if (s.contains("%kills%")) {
                        s = s.replace("%kills%", String.valueOf(players.get().getInt("kills")));
                    }
                    if (s.contains("%deaths%")) {
                        s = s.replace("%deaths%", String.valueOf(players.get().getInt("deaths")));
                    }
                    if (s.contains("%faction-in%")) {
                        if (getFactionInClaim(p.getLocation()) != null) {
                            Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                            if (faction.exists()) {
                                if (faction.get().getString("color") != null) {
                                    if (faction.get().getString("type").equals("KOTH")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                    } else if (faction.get().getString("type").equals("ROAD")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                    } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain");
                                    } else {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                    }
                                } else {
                                    if (faction.get().getString("uuid").equals(players.get().getString("faction"))) {
                                        s = s.replace("%faction-in%", "&a" + faction.get().getString("name"));
                                    } else {
                                        s = s.replace("%faction-in%", "&c" + faction.get().getString("name"));
                                    }
                                }
                            } else {
                                s = s.replace("%faction-in%", getFactionInClaim(p.getLocation()));
                            }
                        } else {
                            s = s.replace("%faction-in%", "None");
                        }
                    }
                    if (s.contains("%x%")) {
                        s = s.replace("%x%", String.valueOf(p.getLocation().getBlockX()));
                    }
                    if (s.contains("%z%")) {
                        s = s.replace("%z%", String.valueOf(p.getLocation().getBlockZ()));
                    }
                    if (s.contains("%facing%")) {
                        s = s.replace("%facing%", getCardinalDirection(p));
                    }
                    if (s.contains("%online%")) {
                        s = s.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    }
                    if (s.contains("%max-players%")) {
                        s = s.replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                    }
                    if (s.contains("%protection-max%")) {
                        s = s.replace("%protection-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL")));
                    }
                    if (s.contains("%sharpness-max%")) {
                        s = s.replace("%sharpness-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.DAMAGE_ALL")));
                    }
                    if (s.contains("%active-koth%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            s = s.replace("%active-koth%", new ArrayList<>(Main.getInstance().kothTimer.keySet()).get(0));
                        } else {
                            s = s.replace("%active-koth%", "&7");
                        }
                    }
                    if (s.contains("%koth-time%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            int time = Main.getInstance().kothTimer.get(keys.get(0));
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%koth-time%", timee);
                        } else {
                            s = s.replace("%koth-time%", "&7");
                        }
                    }
                    if (s.contains("%koth-location%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            if (getFactionByName(keys.get(0)) != null) {
                                Faction faction = getFactionByName(keys.get(0));
                                if (faction.get().isConfigurationSection("location")) {
                                    String format = "%x%, %z%";
                                    format = format.replace("%x%", String.valueOf(faction.get().getString("location.x")));
                                    format = format.replace("%z%", String.valueOf(faction.get().getString("location.z")));
                                    s = s.replace("%koth-location%", format);
                                } else {
                                    s = s.replace("%koth-location%", "None");
                                }
                            } else {
                                s = s.replace("%koth-location%", "None");
                            }
                        } else {
                            s = s.replace("%koth-location%", "&7");
                        }
                    }
                    if (s.contains("%team-list%")) {
                        HashMap<String, Integer> track = new HashMap<>();
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players play = new Players(d.getUniqueId().toString());
                            if (play.hasFaction()) {
                                track.put(play.getFaction().get().getString("uuid"), play.getFaction().getAllOnlinePlayers().size());
                            }
                        }
                        Map<String, Integer> sortedMap = track.entrySet().stream()
                                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> { throw new AssertionError(); },
                                        LinkedHashMap::new
                                ));
                        if (sortedMap.size() != 0) {
                            if (sortedMap.size() <= lineNumber - 1) {
                                Faction foundFaction = null;
                                for (String faction : sortedMap.keySet()) {
                                    if (sortedMap.get(faction) == lineNumber - 1) {
                                        foundFaction = new Faction(faction);
                                    }
                                }
                                if (foundFaction != null) {
                                    String format = tab.get().getString("team-list-format");
                                    format = format.replace("%name%", foundFaction.get().getString("name"));
                                    format = format.replace("%online-members%", String.valueOf(foundFaction.getAllOnlinePlayers().size()));
                                    s = s.replace("%team-list%", format);
                                } else {
                                    s = s.replace("%team-list%", "&7");
                                }
                            } else {
                                s = s.replace("%team-list%", "&7");
                            }
                        } else {
                            s = s.replace("%team-list%", "&7");
                        }
                    }
                    toReturn.add(
                            new BufferedTabObject()
                                    //Text
                                    .text(s)
                                    //Column
                                    .column(TabColumn.MIDDLE)
                                    //Slot
                                    .slot(lineNumber)
                                    //Ping (little buggy with 1.7 clients but defaults to 0 if thats the case
                                    .ping(999)
                                    //Texture (need to get skin sig and key
                                    .skin(ZigguratCommons.defaultTexture)
                    );
                }

                for (String s : tab.get().getConfigurationSection("tab.right").getKeys(false)) {
                    int lineNumber = Integer.parseInt(s);
                    s = tab.get().getString("tab.right." + s);
                    if (s.contains("%faction-home-location%")) {
                        if (players.hasFaction() &&  players.getFaction().get().isConfigurationSection("home")) {
                            String format = "%x%, %z%";
                            format = format.replace("%x%", String.valueOf(players.getFaction().get().getInt("home.x")));
                            format = format.replace("%z%", String.valueOf(players.getFaction().get().getInt("home.z")));
                            s = s.replace("%faction-home-location%", format);
                        } else {
                            s = s.replace("%faction-home-location%", "None");
                        }
                    }
                    if (s.contains("%kills%")) {
                        s = s.replace("%kills%", String.valueOf(players.get().getInt("kills")));
                    }
                    if (s.contains("%deaths%")) {
                        s = s.replace("%deaths%", String.valueOf(players.get().getInt("deaths")));
                    }
                    if (s.contains("%faction-in%")) {
                        if (getFactionInClaim(p.getLocation()) != null) {
                            Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                            if (faction.exists()) {
                                if (faction.get().getString("color") != null) {
                                    if (faction.get().getString("type").equals("KOTH")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                    } else if (faction.get().getString("type").equals("ROAD")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                    } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain");
                                    } else {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                    }
                                } else {
                                    if (faction.get().getString("uuid").equals(players.get().getString("faction"))) {
                                        s = s.replace("%faction-in%", "&a" + faction.get().getString("name"));
                                    } else {
                                        s = s.replace("%faction-in%", "&c" + faction.get().getString("name"));
                                    }
                                }
                            } else {
                                s = s.replace("%faction-in%", getFactionInClaim(p.getLocation()));
                            }
                        } else {
                            s = s.replace("%faction-in%", "None");
                        }
                    }
                    if (s.contains("%x%")) {
                        s = s.replace("%x%", String.valueOf(p.getLocation().getBlockX()));
                    }
                    if (s.contains("%z%")) {
                        s = s.replace("%z%", String.valueOf(p.getLocation().getBlockZ()));
                    }
                    if (s.contains("%facing%")) {
                        s = s.replace("%facing%", getCardinalDirection(p));
                    }
                    if (s.contains("%online%")) {
                        s = s.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    }
                    if (s.contains("%max-players%")) {
                        s = s.replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                    }
                    if (s.contains("%protection-max%")) {
                        s = s.replace("%protection-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL")));
                    }
                    if (s.contains("%sharpness-max%")) {
                        s = s.replace("%sharpness-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.DAMAGE_ALL")));
                    }
                    if (s.contains("%active-koth%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            s = s.replace("%active-koth%", new ArrayList<>(Main.getInstance().kothTimer.keySet()).get(0));
                        } else {
                            s = s.replace("%active-koth%", "&7");
                        }
                    }
                    if (s.contains("%koth-time%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            int time = Main.getInstance().kothTimer.get(keys.get(0));
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%koth-time%", timee);
                        } else {
                            s = s.replace("%koth-time%", "&7");
                        }
                    }
                    if (s.contains("%koth-location%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            if (getFactionByName(keys.get(0)) != null) {
                                Faction faction = getFactionByName(keys.get(0));
                                if (faction.get().isConfigurationSection("location")) {
                                    String format = "%x%, %z%";
                                    format = format.replace("%x%", String.valueOf(faction.get().getString("location.x")));
                                    format = format.replace("%z%", String.valueOf(faction.get().getString("location.z")));
                                    s = s.replace("%koth-location%", format);
                                } else {
                                    s = s.replace("%koth-location%", "None");
                                }
                            } else {
                                s = s.replace("%koth-location%", "None");
                            }
                        } else {
                            s = s.replace("%koth-location%", "&7");
                        }
                    }
                    if (s.contains("%team-list%")) {
                        HashMap<String, Integer> track = new HashMap<>();
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players play = new Players(d.getUniqueId().toString());
                            if (play.hasFaction()) {
                                track.put(play.getFaction().get().getString("uuid"), play.getFaction().getAllOnlinePlayers().size());
                            }
                        }
                        Map<String, Integer> sortedMap = track.entrySet().stream()
                                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> { throw new AssertionError(); },
                                        LinkedHashMap::new
                                ));
                        if (sortedMap.size() != 0) {
                            if (sortedMap.size() <= lineNumber - 1) {
                                Faction foundFaction = null;
                                for (String faction : sortedMap.keySet()) {
                                    if (sortedMap.get(faction) == lineNumber - 1) {
                                        foundFaction = new Faction(faction);
                                    }
                                }
                                if (foundFaction != null) {
                                    String format = tab.get().getString("team-list-format");
                                    format = format.replace("%name%", foundFaction.get().getString("name"));
                                    format = format.replace("%online-members%", String.valueOf(foundFaction.getAllOnlinePlayers().size()));
                                    s = s.replace("%team-list%", format);
                                } else {
                                    s = s.replace("%team-list%", "&7");
                                }
                            } else {
                                s = s.replace("%team-list%", "&7");
                            }
                        } else {
                            s = s.replace("%team-list%", "&7");
                        }
                    }
                    toReturn.add(
                            new BufferedTabObject()
                                    //Text
                                    .text(s)
                                    //Column
                                    .column(TabColumn.RIGHT)
                                    //Slot
                                    .slot(lineNumber)
                                    //Ping (little buggy with 1.7 clients but defaults to 0 if thats the case
                                    .ping(999)
                                    //Texture (need to get skin sig and key
                                    .skin(ZigguratCommons.defaultTexture)
                    );
                }

                for (String s : tab.get().getConfigurationSection("tab.farright").getKeys(false)) {
                    int lineNumber = Integer.parseInt(s);
                    s = tab.get().getString("tab.farright." + s);
                    if (s.contains("%faction-home-location%")) {
                        if (players.hasFaction() &&  players.getFaction().get().isConfigurationSection("home")) {
                            String format = "%x%, %z%";
                            format = format.replace("%x%", String.valueOf(players.getFaction().get().getInt("home.x")));
                            format = format.replace("%z%", String.valueOf(players.getFaction().get().getInt("home.z")));
                            s = s.replace("%faction-home-location%", format);
                        } else {
                            s = s.replace("%faction-home-location%", "None");
                        }
                    }
                    if (s.contains("%kills%")) {
                        s = s.replace("%kills%", String.valueOf(players.get().getInt("kills")));
                    }
                    if (s.contains("%deaths%")) {
                        s = s.replace("%deaths%", String.valueOf(players.get().getInt("deaths")));
                    }
                    if (s.contains("%faction-in%")) {
                        if (getFactionInClaim(p.getLocation()) != null) {
                            Faction faction = new Faction(getFactionInClaim(p.getLocation()));
                            if (faction.exists()) {
                                if (faction.get().getString("color") != null) {
                                    if (faction.get().getString("type").equals("KOTH")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " KOTH");
                                    } else if (faction.get().getString("type").equals("ROAD")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Road");
                                    } else if (faction.get().getString("type").equals("MOUNTAIN")) {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name") + " Mountain");
                                    } else {
                                        s = s.replace("%faction-in%", C.convertColorCode(faction.get().getString("color")) + faction.get().getString("name"));
                                    }
                                } else {
                                    if (faction.get().getString("uuid").equals(players.get().getString("faction"))) {
                                        s = s.replace("%faction-in%", "&a" + faction.get().getString("name"));
                                    } else {
                                        s = s.replace("%faction-in%", "&c" + faction.get().getString("name"));
                                    }
                                }
                            } else {
                                s = s.replace("%faction-in%", getFactionInClaim(p.getLocation()));
                            }
                        } else {
                            s = s.replace("%faction-in%", "None");
                        }
                    }
                    if (s.contains("%x%")) {
                        s = s.replace("%x%", String.valueOf(p.getLocation().getBlockX()));
                    }
                    if (s.contains("%z%")) {
                        s = s.replace("%z%", String.valueOf(p.getLocation().getBlockZ()));
                    }
                    if (s.contains("%facing%")) {
                        s = s.replace("%facing%", getCardinalDirection(p));
                    }
                    if (s.contains("%online%")) {
                        s = s.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    }
                    if (s.contains("%max-players%")) {
                        s = s.replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                    }
                    if (s.contains("%protection-max%")) {
                        s = s.replace("%protection-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.PROTECTION_ENVIRONMENTAL")));
                    }
                    if (s.contains("%sharpness-max%")) {
                        s = s.replace("%sharpness-max%", String.valueOf(Main.getInstance().getConfig().getInt("settings.limits.enchantments.DAMAGE_ALL")));
                    }
                    if (s.contains("%active-koth%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            s = s.replace("%active-koth%", new ArrayList<>(Main.getInstance().kothTimer.keySet()).get(0));
                        } else {
                            s = s.replace("%active-koth%", "&7");
                        }
                    }
                    if (s.contains("%koth-time%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            int time = Main.getInstance().kothTimer.get(keys.get(0));
                            Calendar calender = Calendar.getInstance();
                            calender.clear();
                            calender.add(Calendar.SECOND, time);
                            String format = "mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                            String timee = simpleDateFormat.format(calender.getTimeInMillis());
                            s = s.replace("%koth-time%", timee);
                        } else {
                            s = s.replace("%koth-time%", "&7");
                        }
                    }
                    if (s.contains("%koth-location%")) {
                        if (Main.getInstance().kothTimer.size() != 0) {
                            ArrayList<String> keys = new ArrayList<>(Main.getInstance().kothTimer.keySet());
                            if (getFactionByName(keys.get(0)) != null) {
                                Faction faction = getFactionByName(keys.get(0));
                                if (faction.get().isConfigurationSection("location")) {
                                    String format = "%x%, %z%";
                                    format = format.replace("%x%", String.valueOf(faction.get().getString("location.x")));
                                    format = format.replace("%z%", String.valueOf(faction.get().getString("location.z")));
                                    s = s.replace("%koth-location%", format);
                                } else {
                                    s = s.replace("%koth-location%", "None");
                                }
                            } else {
                                s = s.replace("%koth-location%", "None");
                            }
                        } else {
                            s = s.replace("%koth-location%", "&7");
                        }
                    }
                    if (s.contains("%team-list%")) {
                        HashMap<String, Integer> track = new HashMap<>();
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            Players play = new Players(d.getUniqueId().toString());
                            if (play.hasFaction()) {
                                track.put(play.getFaction().get().getString("uuid"), play.getFaction().getAllOnlinePlayers().size());
                            }
                        }
                        Map<String, Integer> sortedMap = track.entrySet().stream()
                                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> { throw new AssertionError(); },
                                        LinkedHashMap::new
                                ));
                        if (sortedMap.size() != 0) {
                            if (sortedMap.size() <= lineNumber - 1) {
                                Faction foundFaction = null;
                                for (String faction : sortedMap.keySet()) {
                                    if (sortedMap.get(faction) == lineNumber - 1) {
                                        foundFaction = new Faction(faction);
                                    }
                                }
                                if (foundFaction != null) {
                                    String format = tab.get().getString("team-list-format");
                                    format = format.replace("%name%", foundFaction.get().getString("name"));
                                    format = format.replace("%online-members%", String.valueOf(foundFaction.getAllOnlinePlayers().size()));
                                    s = s.replace("%team-list%", format);
                                } else {
                                    s = s.replace("%team-list%", "&7");
                                }
                            } else {
                                s = s.replace("%team-list%", "&7");
                            }
                        } else {
                            s = s.replace("%team-list%", "&7");
                        }
                    }
                    toReturn.add(
                            new BufferedTabObject()
                                    //Text
                                    .text(s)
                                    //Column
                                    .column(TabColumn.FAR_RIGHT)
                                    //Slot
                                    .slot(lineNumber)
                                    //Ping (little buggy with 1.7 clients but defaults to 0 if thats the case
                                    .ping(999)
                                    //Texture (need to get skin sig and key
                                    .skin(ZigguratCommons.defaultTexture)
                    );
                }


                return toReturn;
            }

            @Override
            public String getFooter() {
                return null;
            }

            @Override
            public String getHeader() {
                return null;
            }
        });
    }


    private void regenerateDTR(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() > 0) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (new Players(p.getUniqueId().toString()).hasFaction()) {
                            Faction faction = new Players(p.getUniqueId().toString()).getFaction();
                            if (faction.get().getBoolean("regening")) {
                                if (!Main.getInstance().regen.containsKey(faction.get().getString("uuid"))) {
                                    Main.getInstance().regen.put(faction.get().getString("uuid"), 1);
                                } else {
                                    int time = Main.getInstance().regen.get(faction.get().getString("uuid")) + 1;
                                    int delay = Main.getInstance().getConfig().getInt("dtr.regen.delay") * 20;
                                    if (time >= delay) {
                                        Main.getInstance().regen.put(faction.get().getString("uuid"), 0);
                                        double dtr = faction.get().getDouble("dtr");
                                        if (dtr > faction.getMaximumDTR()) {
                                            dtr = faction.getMaximumDTR();
                                            dtr = Cooldown.round(dtr, 2);
                                            faction.get().set("dtr", dtr);
                                            faction.get().set("regening", false);
                                            faction.get().set("startregen", "");
                                            faction.save();
                                        } else if (dtr < faction.getMaximumDTR()) {
                                            dtr = dtr + Main.getInstance().getConfig().getDouble("dtr.regen.increment");
                                            dtr = Cooldown.round(dtr, 2);
                                            if (dtr > faction.getMaximumDTR()) {
                                                dtr = faction.getMaximumDTR();
                                                faction.get().set("regening", false);
                                                faction.get().set("startregen", "");
                                            }
                                            faction.get().set("dtr", dtr);
                                            faction.save();
                                        }
                                    } else {
                                        Main.getInstance().regen.put(faction.get().getString("uuid"), time);
                                    }
                                }
                            } else {
                                if (faction.get().getLong("startregen") != 0) {
                                    if (faction.get().getLong("startregen") - System.currentTimeMillis() <= 0) {
                                        faction.get().set("regening", true);
                                        faction.get().set("startregen", 0);
                                        faction.save();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    private void generateMountains() {
        new BukkitRunnable() {
            @Override
            public void run() {
                File[] mountains = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/mountains").listFiles();
                if (mountains != null) {
                    for (File f : mountains) {
                        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                        Mountain mountain = new Mountain(file.getString("uuid"));
                        if (mountain.isSetup() && mountain.getCuboid() != null) {
                            if (!resetTimerMountain.containsKey(file.getString("uuid"))) {
                                resetTimerMountain.put(file.getString("uuid"), file.getInt("reset-delay"));
                            } else {
                                int time = resetTimerMountain.get(file.getString("uuid"));
                                time = time - 1;
                                if (time <= 0) {
                                    resetTimerMountain.put(file.getString("uuid"), file.getInt("reset-delay"));
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        Players players = new Players(p.getUniqueId().toString());
                                        if (players.get().getBoolean("settings.publicChat")) {
                                            p.sendMessage(C.chat(Locale.get().getString("command.mountain.regenerated").replace("%name%", file.getString("name"))));
                                            for (Block b : mountain.getCuboid()) {
                                                b.setType(Material.matchMaterial(file.getString("block")));
                                            }
                                        }
                                    }
                                } else {
                                    resetTimerMountain.put(file.getString("uuid"), time);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);
    }

    private void commands() {
        getCommand("faction").setExecutor(new FactionCommand());
        getCommand("mapkit").setExecutor(new MapkitCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());
        getCommand("staff").setExecutor(new StaffCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("sotw").setExecutor(new SOTWCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("economy").setExecutor(new EconomyCommand());
        getCommand("logout").setExecutor(new LogoutCommand());
        getCommand("i").setExecutor(new ICommand());
        getCommand("revive").setExecutor(new ReviveCommand());
        getCommand("lives").setExecutor(new LivesCommand());
        getCommand("lff").setExecutor(new LFFCommand());
        getCommand("staffchat").setExecutor(new StaffchatCommand());
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("timers").setExecutor(new TimersCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("world").setExecutor(new WorldCommand());
        getCommand("pvp").setExecutor(new PvPCommand());
        getCommand("ci").setExecutor(new CICommand());
        getCommand("koth").setExecutor(new KOTHCommand());
        getCommand("end").setExecutor(new EndCommand());
        getCommand("customtimer").setExecutor(new CustomTimerCommand());
        getCommand("crowbar").setExecutor(new CrowbarCommand());
        getCommand("spawner").setExecutor(new SpawnerCommand());
        getCommand("profile").setExecutor(new ProfileCommand());
        getCommand("mountain").setExecutor(new MountainCommand());
        getCommand("reclaim").setExecutor(new ReclaimCommand());
        getCommand("filter").setExecutor(new FilterCommand());
        getCommand("request").setExecutor(new RequestCommand());
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("conquest").setExecutor(new ConquestCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("regen").setExecutor(new RegenCommand());
        getCommand("eotw").setExecutor(new EOTWCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("factionchat").setExecutor(new FactionChatCommand());
        getCommand("globalchat").setExecutor(new GlobalChatCommand());
    }

    private void events() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new LoadScoreboard(), this);
        pm.registerEvents(new RemoveScoreboard(), this);
        pm.registerEvents(new LoadClass(), this);
        pm.registerEvents(new ArcherSpeedEffect(), this);
        pm.registerEvents(new ArcherJumpEffect(), this);
        pm.registerEvents(new BackstabEvent(), this);
        pm.registerEvents(new RogueSpeedEffect(), this);
        pm.registerEvents(new RogueJumpEffect(), this);
        pm.registerEvents(new DeathMessageSendEvent(), this);
        pm.registerEvents(new EnderpearlEvent(), this);
        pm.registerEvents(new SetupSpawnWaypoint(), this);
        pm.registerEvents(new EnchantmentLimiter(), this);
        pm.registerEvents(new MapkitClickEvent(), this);
        pm.registerEvents(new LoadPlayerName(), this);
        pm.registerEvents(new PlayerEnterCombat(), this);
        pm.registerEvents(new FrozenEvents(), this);
        pm.registerEvents(new PlayerGlobalChatEvent(), this);
        pm.registerEvents(new StaffEvents(), this);
        pm.registerEvents(new LogoutEvents(), this);
        pm.registerEvents(new DeathbanJoinEvent(), this);
        pm.registerEvents(new AnvilEnchantEvent(), this);
        pm.registerEvents(new FurnaceSmelt(), this);
        pm.registerEvents(new BrewItemEvent(), this);
        pm.registerEvents(new BrewSpeedEvent(), this);
        pm.registerEvents(new MineDiamondsEvent(), this);
        pm.registerEvents(new AppleConsumeEvent(), this);
        pm.registerEvents(new EffectUseEvent(), this);
        pm.registerEvents(new CombatLoggerCreate(), this);
        pm.registerEvents(new LoggerRemove(), this);
        pm.registerEvents(new ClaimSelectEvent(), this);
        pm.registerEvents(new FactionInteractEvent(), this);
        pm.registerEvents(new HomeEvents(), this);
        pm.registerEvents(new CombatMoveEvent(), this);
        pm.registerEvents(new KothMovementEvent(), this);
        pm.registerEvents(new EnterEndEvent(), this);
        pm.registerEvents(new LeaveEndEvent(), this);
        pm.registerEvents(new ItemSignCreate(), this);
        pm.registerEvents(new SignInteractEvent(), this);
        pm.registerEvents(new ElevatorCreateEvent(), this);
        pm.registerEvents(new ElevatorInteractEvent(), this);
        pm.registerEvents(new FactionMultimoveEvent(), this);
        pm.registerEvents(new FactionNetherMultimoveEvent(), this);
        pm.registerEvents(new CrowbarUseEvent(), this);
        pm.registerEvents(new SpawnerPlaceEvent(), this);
        pm.registerEvents(new ProfileClickEvent(), this);
        pm.registerEvents(new OreMineRegisterEvent(), this);
        pm.registerEvents(new MountainSelectEvent(), this);
        pm.registerEvents(new EnderchestEvents(), this);
        pm.registerEvents(new FilterItemEvent(), this);
        pm.registerEvents(new ConquestMovementEvent(), this);
        pm.registerEvents(new ArcherTagEvent(), this);
        pm.registerEvents(new CrossWorldBorderEvent(), this);
        pm.registerEvents(new SubclaimEvents(), this);
        pm.registerEvents(new FactionEndMultimoveEvent(), this);
        pm.registerEvents(new SettingsClickEvent(), this);
    }

    private void files() {
        me.sub.hcfactions.Files.Timers.Timers.setup();
        me.sub.hcfactions.Files.Timers.Timers.save();
        saveResource("config.yml", false);
        saveResource("messages.yml", false);
        saveResource("locale.yml", false);
        saveResource("lunar.yml", false);
        saveResource("reclaims.yml", false);
        saveResource("staff.yml", false);
        saveResource("locations.yml", false);
        saveResource("tab.yml", false);
    }

    public static String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    private static String getDirection(double rot) {
        if (0 <= rot && rot < 22.5) {
            return "N";
        } else if (22.5 <= rot && rot < 67.5) {
            return "NE";
        } else if (67.5 <= rot && rot < 112.5) {
            return "E";
        } else if (112.5 <= rot && rot < 157.5) {
            return "SE";
        } else if (157.5 <= rot && rot < 202.5) {
            return "S";
        } else if (202.5 <= rot && rot < 247.5) {
            return "SW";
        } else if (247.5 <= rot && rot < 292.5) {
            return "W";
        } else if (292.5 <= rot && rot < 337.5) {
            return "NW";
        } else if (337.5 <= rot && rot < 360.0) {
            return "N";
        } else {
            return null;
        }
    }

    private static String getFactionInClaim(Location loc) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.isConfigurationSection("claims.0")) {
                    for (String s : file.getConfigurationSection("claims").getKeys(false)) {
                        Location locationOne = new Location(Bukkit.getWorld(file.getString("claims." + s + ".world")), file.getDouble("claims." + s + ".sideOne.x"), file.getDouble("claims." + s + ".sideOne.y"), file.getDouble("claims." + s + ".sideOne.z"));
                        Location locationTwo = new Location(Bukkit.getWorld(file.getString("claims." + s + ".world")), file.getDouble("claims." + s + ".sideTwo.x"), file.getDouble("claims." + s + ".sideTwo.y"), file.getDouble("claims." + s + ".sideTwo.z"));
                        Cuboid cuboid = new Cuboid(locationOne, locationTwo);
                        if (cuboid.contains(loc)) {
                            return file.getString("uuid");
                        }
                    }
                }
            }
        }

        if (loc.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (loc.getX() > Main.getInstance().getConfig().getInt("worlds.default.warzone") || loc.getZ() > Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                return Main.getInstance().getConfig().getString("claim.wilderness.name");
            } else if (loc.getX() < -Main.getInstance().getConfig().getInt("worlds.default.warzone") || loc.getZ() < -Main.getInstance().getConfig().getInt("worlds.default.warzone")) {
                return Main.getInstance().getConfig().getString("claim.wilderness.name");
            } else {
                return Main.getInstance().getConfig().getString("claim.warzone.name");
            }
        } else if (loc.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
            if (loc.getX() > Main.getInstance().getConfig().getInt("worlds.nether.warzone") || loc.getZ() > Main.getInstance().getConfig().getInt("worlds.nether.warzone")) {
                return Main.getInstance().getConfig().getString("claim.wilderness.name");
            } else if (loc.getX() < -Main.getInstance().getConfig().getInt("worlds.nether.warzone") || loc.getZ() < -Main.getInstance().getConfig().getInt("worlds.nether.warzone")) {
                return Main.getInstance().getConfig().getString("claim.wilderness.name");
            } else {
                return Main.getInstance().getConfig().getString("claim.warzone.name");
            }
        } else if (loc.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
            return Main.getInstance().getConfig().getString("claim.wilderness.name");
        }

        return null;
    }

    private Faction getFactionByName(String name) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("HCFactions").getDataFolder().getPath() + "/data/factions").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.getString("name").equals(name)) {
                    return new Faction(file.getString("uuid"));
                }
            }
        }
        return null;
    }
}
