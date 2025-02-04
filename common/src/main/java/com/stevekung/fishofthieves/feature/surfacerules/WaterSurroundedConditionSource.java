package com.stevekung.fishofthieves.feature.surfacerules;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class WaterSurroundedConditionSource extends SurfaceRules implements SurfaceRules.ConditionSource
{
    private static final KeyDispatchDataCodec<WaterSurroundedConditionSource> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(new WaterSurroundedConditionSource()));

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec()
    {
        return WaterSurroundedConditionSource.CODEC;
    }

    @Override
    public SurfaceRules.Condition apply(SurfaceRules.Context context)
    {
        return () ->
        {
            for (var direction : Direction.Plane.HORIZONTAL)
            {
                var fluidState = context.chunk.getFluidState(context.pos.relative(direction));

                if (fluidState.is(FluidTags.WATER))
                {
                    return true;
                }
            }
            return false;
        };
    }
}