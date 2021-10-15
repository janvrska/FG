package cz.janvrska.fg.events;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import cz.janvrska.fg.Plugin;
import net.ess3.api.events.UserTeleportHomeEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnHomeTeleport implements Listener {
    Plugin plugin;

    public OnHomeTeleport(Plugin plugin) {
        this.plugin = plugin;
        FlagPermissions.addFlag("home");
    }

    @EventHandler
    public void onHome(UserTeleportHomeEvent event) {
        Location loc = event.getHomeLocation();
        ClaimedResidence res = this.plugin.residence.getResidenceManager().getByLoc(loc);

        if (res != null) {
            ResidencePermissions perms = res.getPermissions();
            String playerName = event.getUser().getName();
            boolean hasPermission = perms.playerHas(playerName, "home", true);
            if (!hasPermission) {
                event.getUser().sendMessage("Pravděpodobně sis nastavil home do zakázané oblasti! Teleportace zrušena!");
                event.setCancelled(true);
            }
        }
    }
}
