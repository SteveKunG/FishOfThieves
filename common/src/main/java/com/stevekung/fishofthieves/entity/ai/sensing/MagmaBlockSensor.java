package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;

public class MagmaBlockSensor extends Sensor<LivingEntity>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(MemoryModuleType.NEAREST_REPELLENT);
    }

    @Override
    protected void doTick(ServerLevel level, LivingEntity entity)
    {
        var brain = entity.getBrain();
        brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, findNearestRepellent(level, entity));
    }

    private static Optional<BlockPos> findNearestRepellent(ServerLevel level, LivingEntity livingEntity)
    {
        return BlockPos.findClosestMatch(livingEntity.blockPosition(), 10, 10, blockPos -> isValidRepellent(level, blockPos));
    }

    private static boolean isValidRepellent(ServerLevel level, BlockPos blockPos)
    {
        var blockState = level.getBlockState(blockPos);
        var isRepellent = blockState.is(FOTTags.Blocks.FISH_REPELLENTS);
        return isRepellent && blockState.is(Blocks.BUBBLE_COLUMN) ? blockState.getValue(BubbleColumnBlock.DRAG_DOWN) : isRepellent;
    }
}