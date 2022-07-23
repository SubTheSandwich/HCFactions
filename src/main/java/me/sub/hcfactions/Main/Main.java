package me.sub.hcfactions.Main;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.object.LCWaypoint;
import me.sub.hcfactions.Commands.Admin.*;
import me.sub.hcfactions.Commands.Member.*;
import me.sub.hcfactions.Commands.Staff.*;
import me.sub.hcfactions.Events.Admin.ItemSignCreate;
import me.sub.hcfactions.Events.Brew.BrewItemEvent;
import me.sub.hcfactions.Events.Brew.BrewSpeedEvent;
import me.sub.hcfactions.Events.Deathban.DeathbanJoinEvent;
import me.sub.hcfactions.Events.Deaths.DeathMessageSendEvent;
import me.sub.hcfactions.Events.Economy.ParseEconomyDisconnect;
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
import me.sub.hcfactions.Events.Player.Chat.PlayerGlobalChatEvent;
import me.sub.hcfactions.Events.Player.Classes.Archer.ArcherJumpEffect;
import me.sub.hcfactions.Events.Player.Classes.Archer.ArcherSpeedEffect;
import me.sub.hcfactions.Events.Player.Classes.Bard.EffectUseEvent;
import me.sub.hcfactions.Events.Player.Classes.LoadClass;
import me.sub.hcfactions.Events.Player.Classes.Rogue.BackstabEvent;
import me.sub.hcfactions.Events.Player.Classes.Rogue.RogueJumpEffect;
import me.sub.hcfactions.Events.Player.Classes.Rogue.RogueSpeedEffect;
import me.sub.hcfactions.Events.Player.Combat.PlayerEnterCombat;
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
import me.sub.hcfactions.Events.Scoreboard.LoadScoreboard;
import me.sub.hcfactions.Events.Scoreboard.RemoveScoreboard;
import me.sub.hcfactions.Events.SignInteractEvent;
import me.sub.hcfactions.Events.Staff.StaffEvents;
import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Files.Mountain.Mountain;
import me.sub.hcfactions.Files.Players.Players;
import me.sub.hcfactions.Utils.Color.C;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    public HashMap<UUID, String> claimFor = new HashMap<>();

    public ArrayList<UUID> bypass = new ArrayList<>();

    public HashMap<UUID, String> selectingMountain = new HashMap<>();
    public HashMap<UUID, Location> mountainPositionOne = new HashMap<>();
    public HashMap<UUID, Location> mountainPositionTwo = new HashMap<>();
    public HashMap<String, Integer> resetTimerMountain = new HashMap<>();

    public HashMap<String, Integer> kothTimer = new HashMap<>();
    public HashMap<String, UUID> capturingKothFaction = new HashMap<>();

    public LunarClientAPI lunarClientAPI;

    public ArrayList<UUID> claimingAgainst = new ArrayList<>();

    public HashMap<String, Integer> customTimers = new HashMap<>();
    public HashMap<String, Boolean> customTimersPaused = new HashMap<>();

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

    public HashMap<UUID, ItemStack[]> logoutArmorContents = new HashMap<>();
    public HashMap<UUID, ItemStack[]> logoutContents = new HashMap<>();

    public ArrayList<UUID> revived = new ArrayList<>();

    public HashMap<UUID, String> currentFactionLocation = new HashMap<>();

    public HashMap<OfflinePlayer, Integer> lffCooldown = new HashMap<>();
    public HashMap<OfflinePlayer, BigDecimal> effectCooldown = new HashMap<>();
    public boolean disabledChat = false;
    public int chatSlow = 0;
    public ArrayList<Player> factionChat = new ArrayList<>();
    public ArrayList<Player> allyChat = new ArrayList<>();
    public ArrayList<Player> staffChat = new ArrayList<>();

    public ArrayList<UUID> loggedDeath = new ArrayList<>();

    public ArrayList<Player> frozen = new ArrayList<>();

    public ArrayList<UUID> claiming = new ArrayList<>();

    public ArrayList<Player> sotwEnabled = new ArrayList<>();
    public boolean sotwStarted = false;
    public boolean sotwPaused = false;

    private Chat chat;

    public boolean eotwStarted = false;

    // Add money for kills
    // Lunar Client Nametags are buggy fix them soon
    // Do some work on faction command

    //End command doesn't work


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
        Timer.trackTimers();
        if (Locale.get().getBoolean("firstRun")) {
            Locale.get().set("firstRun", false);
            Locale.save();
        }
    }
    @Override
    public void onDisable() {

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
        pm.registerEvents(new ParseEconomyDisconnect(), this);
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
}
