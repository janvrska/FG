package cz.janvrska.fg.events;

import cz.janvrska.fg.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeaveMaze implements Listener {
    private Plugin plugin;

    public OnLeaveMaze(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeaveMaze(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        String[] command = event.getMessage().split(" ");

        if (command[0] != null &&
                (
                    command[0].equals("/spawn") ||
                    command[0].equals("/home") ||
                    command[0].equals("/tpa") ||
                    command[0].equals("/tpyes") ||
                    command[0].equals("/tpask") ||
                    command[0].equals("/tpaccept") ||
                    command[0].equals("/warp")
                )
        ) {
            if (plugin.maze.mazePlayer.remove(p.getName()) != null) {
                p.sendMessage("Počítání času zastaveno. Bludiště si se snažil opustit!");
            }
        }
    }

    @EventHandler
    public void onLeaveMazeQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        plugin.maze.mazePlayer.remove(p.getName());
    }

}
