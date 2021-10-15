package cz.janvrska.fg.commands;

import cz.janvrska.fg.Plugin;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Lobby implements CommandExecutor {
    private Plugin plugin;

    public Lobby(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.teleport(new Location(plugin.getServer().getWorld("ivy"), -0.5, 57, -57.5, (float) 1.70, (float) 0.1));
        }
        return true;
    }
}
