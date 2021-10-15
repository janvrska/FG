package cz.janvrska.fg;

import com.earth2me.essentials.IEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collection;

public class WarpGui implements Listener {
    private Inventory inv;
    private IEssentials essentials;

    public WarpGui(IEssentials essentials) {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        this.essentials = essentials;
        inv = Bukkit.createInventory(null, 54, "Warpy");
        this.initializeItems();
        // Put the items into the inventory
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.clear();
        Collection<String> warps = essentials.getWarps().getList();
        warps.forEach((n) -> inv.addItem(createGuiItem(n)));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(String name, String... lore) {
        ItemStack item = new ItemStack(Material.ENDER_EYE, 1);
        ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory() != inv) return;

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Player p = (Player) e.getWhoClicked();
        String warp = e.getCurrentItem().getItemMeta().getDisplayName();
        // Using slots click is a best option for your inventory click's
        p.performCommand("warp " + warp);

    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OnCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        String[] array = message.split(" ");
        if (array.length == 1) {
            if (array[0].equalsIgnoreCase("/warp")) {
                this.initializeItems();
                this.openInventory(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }
}
