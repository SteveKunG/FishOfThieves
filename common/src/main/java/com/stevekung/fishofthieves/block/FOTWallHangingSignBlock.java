package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.blockentity.FOTHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class FOTWallHangingSignBlock extends WallHangingSignBlock
{
    public FOTWallHangingSignBlock(Properties properties, WoodType type)
    {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FOTHangingSignBlockEntity(pos, state);
    }
}