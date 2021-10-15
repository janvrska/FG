package cz.janvrska.fg.events;

import cz.janvrska.fg.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class OnInventoryInteract implements Listener {
    Plugin plugin;

    public OnInventoryInteract(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        String playerName = event.getView().getPlayer().getName();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (inventory != null) {
                for (ItemStack item : inventory) {
                    if (item != null) {
                        Map<Enchantment, Integer> enchants = item.getEnchantments();
                        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                            if (entry.getValue() > 40) {
                                String msg = "{\n" +
                                        "  \"embed\": {\n" +
                                        "    \"title\": \"Enchanty\",\n" +
                                        "    \"description\":\"" + playerName + " má enchanty lvl: " + entry.getValue() + "\"\n" +
                                        "  }\n" +
                                        "}";
                                plugin.discordMessager.sendMessageToChannelId("xxxxxx", msg);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }
}
