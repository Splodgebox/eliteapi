package net.splodgebox.eliteapi.gui;

import com.google.common.collect.Sets;

import java.util.Set;

public class MenuPartition {

    private final Set<Integer> slots = Sets.newHashSet();

    public MenuPartition(int minIndex, int maxIndex) {
        for (int i = minIndex; i <= maxIndex; ++i)
            slots.add(i);
    }

    public boolean contains(int slot) {
        return slots.contains(slot);
    }

}
