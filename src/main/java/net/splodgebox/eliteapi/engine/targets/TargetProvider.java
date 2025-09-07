package net.splodgebox.eliteapi.engine.targets;

import net.splodgebox.eliteapi.engine.EffectContext;
import net.splodgebox.eliteapi.engine.params.Params;

public interface TargetProvider {
    boolean supports(String token);

    Iterable<?> resolve(EffectContext context, Params params);
}