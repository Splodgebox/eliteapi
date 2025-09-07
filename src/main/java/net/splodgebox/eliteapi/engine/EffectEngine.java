package net.splodgebox.eliteapi.engine;

import net.splodgebox.eliteapi.engine.conditions.Condition;
import net.splodgebox.eliteapi.engine.filter.BlockFilter;
import net.splodgebox.eliteapi.engine.params.Params;
import net.splodgebox.eliteapi.engine.parsing.EffectSpec;
import net.splodgebox.eliteapi.engine.parsing.EffectSpecParser;
import net.splodgebox.eliteapi.engine.targets.Targets;
import org.bukkit.block.Block;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class EffectEngine {
    private final EffectRegistry registry;
    private final Targets targets;
    private final List<BlockFilter> blockFilters;
    private final @Nullable Condition condition;

    public EffectEngine(EffectRegistry registry, Targets targets, List<BlockFilter> blockFilters, @Nullable Condition condition) {
        this.registry = registry;
        this.targets = targets;
        this.blockFilters = blockFilters;
        this.condition = condition;
    }

    public void execute(String dslLine, EffectContext context) {
        EffectSpec spec = EffectSpecParser.parse(dslLine);
        Effect effect = registry.get(spec.name());
        if (effect == null) return;

        Params params = new Params(spec.parameters());
        Iterable<?> rawTargets = targets.resolve(spec.targetToken(), context, params);

        List<Object> filtered = new ArrayList<>();
        for (Object candidate : rawTargets) {
            if (candidate instanceof Block block) {
                boolean pass = true;
                for (BlockFilter filter : blockFilters) {
                    if (!filter.test(block, context, params)) {
                        pass = false;
                        break;
                    }
                }
                if (pass && (condition == null || condition.test(block, context, params, spec.condition()))) {
                    filtered.add(block);
                }
            } else {
                if (condition == null || condition.test(candidate, context, params, spec.condition())) {
                    filtered.add(candidate);
                }
            }
        }
        effect.apply(context, params, filtered);
    }
}