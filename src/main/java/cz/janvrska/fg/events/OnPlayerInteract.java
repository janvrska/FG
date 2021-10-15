package cz.janvrska.fg.events;

import cz.janvrska.fg.Plugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.Timestamp;

public class OnPlayerInteract implements Listener {
    private Plugin plugin;

    public OnPlayerInteract(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Action action = event.getAction();
        if (!action.equals(Action.PHYSICAL)) {
            return;
        }

        Location loc = event.getClickedBlock().getLocation();

        if (!loc.getWorld().getName().equals("world")) {
            return;
        }

        Player p = event.getPlayer();

        Location blockLocation = new Location(loc.getWorld(),-4788,71,5032);
        Location blockLocation2 = new Location(loc.getWorld(),-4788,71,5031);

        /*Location blockLocation = new Location(plugin.essentials.getWorld("world"),-144,63,-202);
        Location blockLocation2 = new Location(plugin.essentials.getWorld("world"),-145,63,-202);*/

        if(loc.equals(blockLocation) || loc.equals(blockLocation2)){
            plugin.maze.mazePlayer.put(p.getName(), new Timestamp(System.currentTimeMillis()));
            p.sendMessage("Vítej v bludišti, počítá se ti čas, snaž se být co nejrychlejší");
        }
    }

}
