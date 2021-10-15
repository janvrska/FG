package cz.janvrska.fg.events;

import com.earth2me.essentials.craftbukkit.BanLookup;
import cz.janvrska.fg.Plugin;
import net.ess3.api.IEssentials;
import org.bukkit.BanEntry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerBan implements Listener {


    private Plugin plugin;

    public OnPlayerBan(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerBan(PlayerQuitEvent event){
        Player p = event.getPlayer();
        if(p.isBanned()){
            BanEntry banEntry = BanLookup.getBanEntry((IEssentials) plugin.essentials,p.getName());

        }
    }
}
