package net.splodgebox.eliteapi.gui.menu.types;

import lombok.Getter;
import net.splodgebox.eliteapi.gui.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Getter
public abstract class UpdatingMenu extends Menu {

    private boolean closing = false;

    public UpdatingMenu(String name, int rows) {
        super(name, rows);
    }

    public abstract void onUpdate(Player player, long tick);

    public void update(Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if (!closing)
            getButtonListeners().forEach((slot, button) ->
                    inventory.setItem(slot, button.getItemStack()));
    }

    public void closeMenuSupressError(Player player) {
        closing = true;
        player.closeInventory();
    }
}
