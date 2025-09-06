package net.splodgebox.eliteapi.gui.buttons;

import net.splodgebox.eliteapi.gui.Button;
import net.splodgebox.eliteapi.gui.types.PagedMenu;
import net.splodgebox.eliteapi.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NextButton extends Button {

    private static final ItemStack NEXT_BUTTON = new ItemBuilder("&d&lNext Page", Material.ARROW)
            .addLore("&7Click to view the next page")
            .build();

    public NextButton(ItemStack item, PagedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            if (menu.getNext() != null) {
                menu.getNext().open(player);
                menu.setCurrentPageNumber(menu.getCurrentPageNumber() + 1);
            }
        });
    }

    public NextButton(PagedMenu menu) {
        this(NEXT_BUTTON, menu);
    }

}
