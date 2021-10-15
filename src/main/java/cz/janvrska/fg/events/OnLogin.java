package cz.janvrska.fg.events;

import cz.janvrska.fg.AuthCode;
import cz.janvrska.fg.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnLogin implements Listener {

    private Plugin plugin;

    public OnLogin(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLoginEvent(PlayerCommandPreprocessEvent event) {

        Player p = event.getPlayer();

        if (p.hasPermission("fg.auth")) {

            String[] command = event.getMessage().split(" ");


            if (command[0] != null && (command[0].equals("/login") || command[0].equals("/l"))) {
                if (command.length == 3) {

                    String code = "";
                    for (AuthCode authCode : plugin.authCodes) {
                        if (authCode.getPlayerName().equals(p.getName())) {
                            code = String.valueOf(authCode.getCode());
                            break;
                        }
                    }

                    if (command[2].equals(code)) {
                        event.setMessage("/login " + command[1]);
                    } else {
                        p.sendMessage("/login heslo kod");
                        event.setCancelled(true);
                    }

                } else {
                    p.sendMessage("/login heslo kod");
                    event.setCancelled(true);
                }
            }

        }
    }
}
