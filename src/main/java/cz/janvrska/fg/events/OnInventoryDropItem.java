package cz.janvrska.fg.events;

import cz.janvrska.fg.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OnInventoryDropItem implements Listener {
    private Plugin plugin;
    List<String> loresList = new ArrayList<>();

    public OnInventoryDropItem(Plugin plugin) {
        this.plugin = plugin;
        loresList.add("Neprodejné");
    }

    @EventHandler
    public void OnDropItem(InventoryCreativeEvent event) {
        ItemStack item = event.getCursor();
        if (!event.getWhoClicked().hasPermission("fg.give")) {
            setLores("Neprodejné", event.getWhoClicked().getName());
            ItemMeta meta = item.getItemMeta();

            try {
                if (meta != null) {
                    meta.setLore(loresList);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            item.setItemMeta(meta);
        }
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + event.getWhoClicked().getName() + " " + item.toString());
    }

    public void setLores(String lore1, String lore2) {
        loresList.clear();
        loresList.add(lore1);
        loresList.add(lore2);
    }
}
