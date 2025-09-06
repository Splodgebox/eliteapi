package net.splodgebox.eliteapi.gui;

import lombok.Getter;
import net.splodgebox.eliteapi.gui.types.PagedMenu;

public abstract class Template {

    @Getter private final Button[] buttons = new Button[54];

    public void setButton(int index, Button button) {
        buttons[index] = button;
    }

    public abstract void set(PagedMenu menu);

}
