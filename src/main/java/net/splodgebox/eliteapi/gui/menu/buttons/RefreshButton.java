package net.splodgebox.eliteapi.gui.menu.buttons;

import net.splodgebox.eliteapi.gui.menu.Button;
import net.splodgebox.eliteapi.gui.menu.types.PagedMenu;
import net.splodgebox.eliteapi.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RefreshButton extends Button {

    private static final ItemStack REFRESH_BUTTON = new ItemBuilder("&d&lRefresh", Material.PAPER)
            .addLore("&7Click to refresh the current page")
            .build();

    public RefreshButton(ItemStack item, PagedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            player.closeInventory();
            menu.getCurrent().refresh(player);
        });
    }

    public RefreshButton(PagedMenu menu) {
        this(REFRESH_BUTTON, menu);
    }

}
