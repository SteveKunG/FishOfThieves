package com.stevekung.fishofthieves.feature.surfacerules;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.mixin.accessor.SurfaceRules_ContextAccessor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class WaterSurroundedConditionSource extends SurfaceRules implements SurfaceRules.ConditionSource
{
    public static final MapCodec<WaterSurroundedConditionSource> CODEC = MapCodec.unit(new WaterSurroundedConditionSource());
    private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

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
            var accessor = ((SurfaceRules_ContextAccessor) (Object) context);
            var blockPos = this.mutablePos.set(accessor.getBlockX(), accessor.getBlockY(), accessor.getBlockZ());
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

                var fluidState = accessor.getChunk().getFluidState(blockPos.relative(direction));

                if (fluidState.is(FluidTags.WATER))
                {
                    return true;
                }
            }
            return false;
        };
    }
}