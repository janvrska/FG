package cz.janvrska.fg.events;

import cz.janvrska.fg.Plugin;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnEnchant implements Listener {

    private Plugin plugin;

    public OnEnchant(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnEnchant(Event event) {

        plugin.getServer().getConsoleSender().sendMessage(event.getEventName());
    }
}
