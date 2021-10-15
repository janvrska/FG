package cz.janvrska.fg;

public class Scheduler {
    org.bukkit.plugin.Plugin plugin;

    public Scheduler(Plugin plugin) {
        this.plugin = plugin;
        Start();
    }

    public void Start() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "holograms reload"), 12000, 12000);
    }
}
