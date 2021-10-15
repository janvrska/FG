package cz.janvrska.fg.commands;

import cz.janvrska.fg.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Countrylock implements CommandExecutor {

    Plugin plugin;

    public Countrylock(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("fg.countrylock")) {
                if(plugin.countryLock) {
                    plugin.countryLock = false;
                    p.sendMessage("Country lock vypnut");
                } else {
                    plugin.countryLock = true;
                    p.sendMessage("Country lock zapnut");
                }

            }
        }
        return false;
    }
}
