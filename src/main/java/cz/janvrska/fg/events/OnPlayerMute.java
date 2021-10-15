package cz.janvrska.fg.events;

import cz.janvrska.fg.Plugin;
import net.ess3.api.events.MuteStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.Timestamp;

public class OnPlayerMute implements Listener {
    Plugin plugin;

    public OnPlayerMute(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerMuteEvent(MuteStatusChangeEvent event) {
        String playerName = event.getAffected().getName();
        String executorName = null;
        if(event.getController() != null) {
            executorName = event.getController().getName();
        }
        String reason = event.getReason();
        Long timestamp = null;
        Timestamp sqlTimestamp = null;
        if(event.getTimestamp().isPresent()){
            timestamp = event.getTimestamp().get();
            sqlTimestamp = new Timestamp(timestamp);
        }

        String finalExecutorName = executorName;
        int type = 2;
        if(event.getValue()) {
            type = 3;
        }
        Timestamp finalTimestamp = sqlTimestamp;
        int finalType = type;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.model.createPlayerEvent(playerName,finalExecutorName,reason, finalTimestamp, finalType);
        });

    }

}
