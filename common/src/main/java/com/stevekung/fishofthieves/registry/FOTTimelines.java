package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.attribute.modifier.BooleanModifier;
import net.minecraft.world.timeline.Timeline;

public interface FOTTimelines
{
    ResourceKey<Timeline> DAY = key("day");

    static void bootstrap(BootstrapContext<Timeline> context)
    {
        //TODO Query exact value for amber plentifin
        var builder = Timeline.builder()
                .setPeriodTicks(24000)
                .addModifierTrack(FOTEnvironmentAttributes.AMBER_PLENTIFIN_SPAWNS, BooleanModifier.OR, builderx -> builderx
                        .addKeyframe(1000, true)
                        .addKeyframe(3000, false)
                        .addKeyframe(9000, true)
                        .addKeyframe(11000, false));
        context.register(DAY, builder.build());
    }

    private static ResourceKey<Timeline> key(String string)
    {
        return ResourceKey.create(Registries.TIMELINE, FishOfThieves.id(string));
    }
}