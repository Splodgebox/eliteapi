package net.splodgebox.eliteapi.engine.filter;

import net.splodgebox.eliteapi.engine.EffectContext;
import net.splodgebox.eliteapi.engine.params.Params;
import org.bukkit.block.Block;

public interface BlockFilter {
    boolean test(Block block, EffectContext context, Params params);
}