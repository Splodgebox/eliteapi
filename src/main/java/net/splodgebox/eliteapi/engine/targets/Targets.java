package net.splodgebox.eliteapi.engine.targets;

import net.splodgebox.eliteapi.engine.EffectContext;
import net.splodgebox.eliteapi.engine.params.Params;

import java.util.ArrayList;
import java.util.List;

public final class Targets {
    private final List<TargetProvider> providers = new ArrayList<>();

    public void register(TargetProvider provider) {
        providers.add(provider);
    }

    public Iterable<?> resolve(String token, EffectContext context, Params params) {
        for (TargetProvider p : providers) if (p.supports(token)) return p.resolve(context, params);
        return List.of(); // default: no targets
    }
}
