package com.stevekung.fishofthieves.blockentity;

import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FOTHangingSignBlockEntity extends FOTSignBlockEntity
{
    public FOTHangingSignBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(FOTBlockEntityTypes.HANGING_SIGN, blockPos, blockState);
    }

    @Override
    public int getTextLineHeight()
    {
        return 9;
    }

    @Override
    public int getMaxTextLineWidth()
    {
        return 60;
    }
}