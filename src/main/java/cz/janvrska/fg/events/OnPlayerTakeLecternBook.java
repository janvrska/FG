package cz.janvrska.fg.events;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import cz.janvrska.fg.Plugin;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

public class OnPlayerTakeLecternBook implements Listener {
    private Plugin plugin;

    public OnPlayerTakeLecternBook(Plugin plugin) {
        this.plugin = plugin;
        FlagPermissions.addFlag("book");

    }

    @EventHandler
    public void OnPlayerTakeBook(PlayerTakeLecternBookEvent event) {
        Location loc = event.getLectern().getLocation();
        ClaimedResidence res = this.plugin.residence.getResidenceManager().getByLoc(loc);

        if (res != null) {
            ResidencePermissions perms = res.getPermissions();
            String playerName = event.getPlayer().getName();
            boolean hasPermission = perms.playerHas(playerName, "book", true);
            if (!hasPermission) {
                event.getPlayer().sendMessage("Knihy se nekradou!");
                event.setCancelled(true);
            }
        }
    }

}
