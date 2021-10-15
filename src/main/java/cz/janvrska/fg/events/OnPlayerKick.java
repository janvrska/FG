package cz.janvrska.fg.events;

import cz.janvrska.fg.Plugin;
import net.essentialsx.api.v2.events.UserKickEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerKick implements Listener {
    Plugin plugin;

    public OnPlayerKick(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerKickEvent(UserKickEvent event) {
        String playerName = event.getKicker().getName();
        String executorName = null;
        if(event.getKicked() != null) {
            executorName = event.getKicked().getName();
        }
        String reason = event.getReason();

        String finalExecutorName = executorName;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.model.createPlayerEvent(playerName,finalExecutorName,reason,null,1);
        });

    }

}
