package net.splodgebox.eliteapi.engine.conditions.impl;

import net.splodgebox.eliteapi.engine.EffectContext;
import net.splodgebox.eliteapi.engine.conditions.Condition;
import net.splodgebox.eliteapi.engine.params.Params;
import org.bukkit.block.Block;

import javax.annotation.Nullable;
import java.util.Locale;

public final class ContainsCondition implements Condition {
    @Override
    public boolean test(Object target, EffectContext context, Params params, @Nullable String expression) {
        if (expression == null) return true;
        if (target instanceof Block block) {
            String typeName = block.getType().name();
            String exprLower = expression.toLowerCase(Locale.ROOT);
            if (exprLower.contains(" contains ")) {
                String needle = expression.substring(exprLower.indexOf(" contains ") + 10).trim();
                return typeName.toUpperCase(Locale.ROOT).contains(needle.toUpperCase(Locale.ROOT));
            }
        }
        return true;
    }
}
