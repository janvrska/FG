package cz.janvrska.fg.events;

import cz.janvrska.fg.AuthCode;
import cz.janvrska.fg.DiscordMessager;
import cz.janvrska.fg.Plugin;
import io.ipinfo.api.errors.RateLimitedException;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Random;


public class OnPlayerJoin implements Listener {
    Plugin plugin;
    DiscordMessager messager;

    public OnPlayerJoin(Plugin plugin) {
        this.plugin = plugin;
        this.messager = plugin.discordMessager;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String playerIp = p.getAddress().getHostString();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            List<String> playersAcc = plugin.authmeApi.getNamesByIp(playerIp);

            if(playersAcc.isEmpty()){
                String countryCode = null;

                try {
                    countryCode = plugin.ipInfo.lookupIP(playerIp).getCountryCode();
                } catch (RateLimitedException e) {
                    e.printStackTrace();
                }

                String[] countries = {"CZ", "SK"};
                if(countryCode == null || !ArrayUtils.contains(countries,countryCode)){
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                        String msg = "{\n" +
                                "  \"embed\": {\n" +
                                "    \"title\": \"CountryLock\",\n" +
                                "    \"description\":\""+playerIp+"\"\n" +
                                "  }\n" +
                                "}";
                        messager.sendMessageToChannelId("xxxxx", msg);
                    });
                    if(plugin.countryLock) {
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            p.kickPlayer("VPN není povolena");
                        });
                    }
                }
            }
        });

        if (p.hasPermission("fg.auth")) {
            String channelId;

            switch (p.getName()){
                case "xx":
                    channelId = "xx";
                    break;
                case "xxx":
                    channelId = "xx";
                    break;
                case "xxxx":
                    channelId = "xx";
                    break;
                case "xxxxx":
                    channelId = "xx";
                    break;
                default:
                    return;
            }
            final String channelIdAsync = channelId;
            final String playerName = p.getName();

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

                plugin.authCodes.removeIf(authCode -> authCode.getPlayerName().equals(playerName));
                int code = getRandomNumberUsingNextInt(1000,9999);
                plugin.authCodes.add(new AuthCode(code, playerName));

                final String message = "{\n" +
                        "  \"embed\": {\n" +
                        "    \"title\": \"Ověření přihlášení\",\n" +
                        "    \"description\": \"Tvůj kód je: " + code + "\\nPřihlásil ses z IP: " + playerIp + "\"\n" +
                        "  }\n" +
                        "}";

                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> messager.sendMessageToChannelId(channelIdAsync, message));
            });
        }

        if (plugin.achat.isPlayerToggled(p)) {
            plugin.achat.setScoreBoard(p);
        }
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
