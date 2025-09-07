package net.splodgebox.eliteapi.engine;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class EffectRegistry {
    private final Map<String, Effect> effects = new HashMap<>();

    public void register(Effect effect) {
        effects.put(effect.name().toUpperCase(), effect);
    }

    public @Nullable Effect get(String name) {
        return effects.get(name.toUpperCase());
    }
}
