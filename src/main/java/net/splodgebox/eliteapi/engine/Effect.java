package net.splodgebox.eliteapi.engine;

import net.splodgebox.eliteapi.engine.params.Params;

public interface Effect {
    String name();

    void apply(EffectContext context, Params params, Iterable<?> targets);
}