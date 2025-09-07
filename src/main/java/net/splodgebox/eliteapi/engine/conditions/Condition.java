package net.splodgebox.eliteapi.engine.conditions;

import net.splodgebox.eliteapi.engine.EffectContext;
import net.splodgebox.eliteapi.engine.params.Params;

import javax.annotation.Nullable;

public interface Condition {
    boolean test(Object target, EffectContext context, Params params, @Nullable String expression);
}
