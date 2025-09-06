package net.splodgebox.eliteapi.gui.buttons;

import net.splodgebox.eliteapi.gui.Button;
import net.splodgebox.eliteapi.gui.types.PagedMenu;
import net.splodgebox.eliteapi.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PreviousButton extends Button {

    private static final ItemStack PREVIOUS_BUTTON = new ItemBuilder("&d&lPrevious Page", Material.ARROW)
            .addLore("&7Click to view the previous page")
            .build();

    public PreviousButton(ItemStack item, PagedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            if (menu.getPrevious() != null) {
                menu.getPrevious().open(player);
                menu.setCurrentPageNumber(menu.getCurrentPageNumber() - 1);
            }
        });
    }

    public PreviousButton(PagedMenu menu) {
        this(PREVIOUS_BUTTON, menu);
    }

}
