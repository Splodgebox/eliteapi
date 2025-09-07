package net.splodgebox.eliteapi.engine.targets.impl;

import net.splodgebox.eliteapi.engine.EffectContext;
import net.splodgebox.eliteapi.engine.params.Params;
import net.splodgebox.eliteapi.engine.targets.TargetProvider;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AroundEntitiesTarget implements TargetProvider {
    @Override
    public boolean supports(String token) {
        return token.equalsIgnoreCase("@around");
    }

    @Override
    public Iterable<?> resolve(EffectContext context, Params params) {
        int radius = Math.max(1, params.getInt("radius", 3));
        boolean includeSelf = params.getBool("include_self", false);
        boolean playersOnly = params.getBool("players_only", false);

        Location center = (context.clickedBlock() != null)
                ? context.clickedBlock().getLocation()
                : (context.player() != null ? context.player().getLocation() : null);
        if (center == null) return List.of();

        Collection<Entity> nearby = center.getWorld().getNearbyEntities(center, radius, radius, radius);
        List<LivingEntity> targets = new ArrayList<>();
        for (Entity e : nearby) {
            if (!(e instanceof LivingEntity living)) continue;
            if (!includeSelf && context.player() != null && living.equals(context.player())) continue;
            if (playersOnly && !(living instanceof Player)) continue;
            targets.add(living);
        }
        return targets;
    }
}
