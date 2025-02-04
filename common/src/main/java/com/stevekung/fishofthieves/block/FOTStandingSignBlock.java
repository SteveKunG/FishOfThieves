package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.blockentity.FOTSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class FOTStandingSignBlock extends StandingSignBlock
{
    public FOTStandingSignBlock(Properties properties, WoodType type)
    {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FOTSignBlockEntity(pos, state);
    }
}