package cz.janvrska.fg.events;

import cz.janvrska.fg.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener {
    private Plugin plugin;

    public OnChat(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("fg.a") && plugin.achat.isPlayerToggled(p)) {
            String message = event.getMessage();
            message = event.getPlayer().getDisplayName() + ChatColor.AQUA + " [AdminChat] >> " + ChatColor.WHITE + message;
            plugin.getServer().getConsoleSender().sendMessage(message);

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("fg.a")) {
                    player.sendMessage(message);
                }
            }
            event.setCancelled(true);
        }
    }
}
