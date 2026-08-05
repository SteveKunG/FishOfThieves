package com.stevekung.fishofthieves.feature.surfacerules;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class WaterSurroundedConditionSource extends SurfaceRules implements SurfaceRules.ConditionSource
{
    public static final MapCodec<WaterSurroundedConditionSource> CODEC = MapCodec.unit(new WaterSurroundedConditionSource());

    @Override
    public MapCodec<? extends ConditionSource> codec()
    {
        return WaterSurroundedConditionSource.CODEC;
    }

    @Override
    public SurfaceRules.Condition apply(SurfaceRules.Context context)
    {
        return () ->
        {
            var blockPos = context.pos;
            var localX = blockPos.getX() & 15;
            var localZ = blockPos.getZ() & 15;

            for (var direction : Direction.Plane.HORIZONTAL)
            {
                // Skip directions that would cross into a neighboring chunk
                if (direction == Direction.WEST && localX == 0 ||
                        direction == Direction.EAST && localX == 15 ||
                        direction == Direction.NORTH && localZ == 0 ||
                        direction == Direction.SOUTH && localZ == 15)
                {
                    continue;
                }

                var fluidState = context.chunk.getFluidState(blockPos.relative(direction));

                if (fluidState.is(FluidTags.WATER))
                {
                    return true;
                }
            }
            return false;
        };
    }
}