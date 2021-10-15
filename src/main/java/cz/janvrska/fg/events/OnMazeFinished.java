package cz.janvrska.fg.events;

import cz.janvrska.fg.Plugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnMazeFinished implements Listener {
    private Plugin plugin;

    public OnMazeFinished(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMazeFinished(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (plugin.maze.mazePlayer.get(p.getName()) == null)
            return;

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clicked = event.getClickedBlock();

            if(clicked == null)
                return;

            Material blockMaterial = clicked.getType();
            if (!blockMaterial.equals(Material.CRIMSON_BUTTON))
                return;

            Location btnLoc = new Location(plugin.essentials.getWorld("world"),-4837,169,5031);

            if(clicked.getLocation().equals(btnLoc)){
                p.sendMessage("Dokončil si bludiště");
                plugin.maze.mazePlayer.remove(p.getName());
            }

        }
    }
}
