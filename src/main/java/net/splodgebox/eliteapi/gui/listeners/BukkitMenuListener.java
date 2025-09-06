package net.splodgebox.eliteapi.gui.listeners;

import net.splodgebox.eliteapi.gui.Button;
import net.splodgebox.eliteapi.gui.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class BukkitMenuListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;

        Menu menu = Menu.getMenus().get(player.getUniqueId());
        if (menu == null) return;

        Inventory topInventory = player.getOpenInventory().getTopInventory();
        Inventory bottomInventory = player.getOpenInventory().getBottomInventory();

        if (topInventory.equals(clickedInventory)) {
            handleTopInventoryClick(event, player, menu);
        } else if (bottomInventory.equals(clickedInventory)) {
            handleBottomInventoryClick(event, player, menu);
        }
    }

    private void handleTopInventoryClick(InventoryClickEvent event, Player player, Menu menu) {
        Button button = menu.getButton(event.getRawSlot());
        if (button == null || button.getClickAction() == null) {
            event.setCancelled(true);
            return;
        }
        button.getClickAction().click(player, event);
    }

    private void handleBottomInventoryClick(InventoryClickEvent event, Player player, Menu menu) {
        if (menu.getBottomClickAction() != null) {
            menu.getBottomClickAction().click(player, event);
        } else if (menu.isBottomCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        Menu menu = Menu.getMenus().remove(player.getUniqueId());
        if (menu == null) return;

        if (menu.getCloseAction() != null) {
            menu.getCloseAction().close(player, event);
        }
    }
}
