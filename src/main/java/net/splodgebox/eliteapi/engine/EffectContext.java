package net.splodgebox.eliteapi.engine;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;

public record EffectContext(
        Plugin plugin,
        Event event,
        @Nullable Player player,
        @Nullable LivingEntity other,
        @Nullable Block clickedBlock
) {}

