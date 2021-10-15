package cz.janvrska.fg.events;

import com.bekvon.bukkit.residence.event.ResidenceTPEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnResTeleport implements Listener {
    @EventHandler
    public void onTeleport(ResidenceTPEvent event) {
        Player p = event.getPlayer();
        if(event.getTeleportLocation().getWorld().getName().equals("flat_world")) {
            if(!p.hasPermission("fg.restp")){
                p.sendMessage("Do tohoto světa je zakázáno se teleportovat přes residence!");
                event.setCancelled(true);
            }
        }
    }
}
