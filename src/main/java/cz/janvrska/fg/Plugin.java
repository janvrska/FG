package cz.janvrska.fg;

import com.bekvon.bukkit.residence.Residence;
import com.earth2me.essentials.IEssentials;
import cz.janvrska.fg.commands.*;
import cz.janvrska.fg.events.*;
import fr.xephi.authme.api.v3.AuthMeApi;
import io.ipinfo.api.IPInfo;
import io.ipinfo.api.cache.SimpleCache;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.util.ArrayList;

public class Plugin extends JavaPlugin {

    public AdminChat achat;
    public OnChat chat;
    public IEssentials essentials;
    public Residence residence;
    public ArrayList<AuthCode> authCodes = new ArrayList<>();
    public DiscordMessager discordMessager;
    public UDPClient udpClient;
    public AuthMeApi authmeApi;
    public IPInfo ipInfo;
    public Maze maze = new Maze();
    public boolean countryLock = true;
    public DatabaseModel model;

    @Override
    public void onEnable() {

        System.out.println("Plugin FG Started");
        CreateFile();

        ipInfo = IPInfo.builder().setToken("xxxxx").setCache(new SimpleCache(Duration.ofDays(5))).build();
        authmeApi = AuthMeApi.getInstance();
        essentials = (IEssentials) getServer().getPluginManager().getPlugin("Essentials");

        if (getServer().getPluginManager().getPlugin("Residence") != null) {
            residence = Residence.getInstance();
        }

        achat = new AdminChat(this);
        chat = new OnChat(this);
        discordMessager = new DiscordMessager();
        model = new DatabaseModel(this);

        getServer().getPluginManager().registerEvents(new OnButtonPress(), this);
        getServer().getPluginManager().registerEvents(chat, this);
        getServer().getPluginManager().registerEvents(new WarpGui(essentials), this);
        getServer().getPluginManager().registerEvents(new OnHomeTeleport(this), this);
        getServer().getPluginManager().registerEvents(new OnInventoryDropItem(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerTakeLecternBook(this), this);
        getServer().getPluginManager().registerEvents(new OnLogin(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerInteract(this), this);
        getServer().getPluginManager().registerEvents(new OnLeaveMaze(this), this);
        getServer().getPluginManager().registerEvents(new OnMazeFinished(this), this);
        getServer().getPluginManager().registerEvents(new OnInventoryInteract(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerKick(this),this);
        getServer().getPluginManager().registerEvents(new OnPlayerMute(this),this);
        getServer().getPluginManager().registerEvents(new OnResTeleport(),this);
        getServer().getPluginManager().registerEvents(new OnPlayerBan(this),this);

        getCommand("fg").setExecutor(new FgCommands(this));
        getCommand("a").setExecutor(achat);
        getCommand("lobby").setExecutor(new Lobby(this));
        getCommand("atomovka").setExecutor(new Bomb(this));
        getCommand("wrecker").setExecutor(new Wrecker(this));
        getCommand("countrylock").setExecutor(new Countrylock(this));

        try {
            udpClient = new UDPClient(InetAddress.getLocalHost(), 2000);
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.runTaskTimerAsynchronously(this, () -> {
                try {
                    udpClient.sendMessage("Live");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 0, 15 * 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CreateFile() {
        try {
            new File("plugins/fg").mkdirs();
            File myObj = new File("plugins/fg/buttons.json");
            if (!myObj.createNewFile()) {
                System.out.println("FG: File already exists.");
            }
        } catch (IOException e) {
            System.out.println("FG: An error occurred.");
            e.printStackTrace();
        }
    }
}
