package net.splodgebox.eliteapi.engine.parsing;

import javax.annotation.Nullable;
import java.util.Map;

public record EffectSpec(
        String name,
        Map<String, String> parameters,
        String targetToken,
        @Nullable String condition
) {}
