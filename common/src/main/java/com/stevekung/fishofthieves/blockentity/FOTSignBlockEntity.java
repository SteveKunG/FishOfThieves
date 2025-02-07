package com.stevekung.fishofthieves.blockentity;

import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FOTSignBlockEntity extends SignBlockEntity
{
    public FOTSignBlockEntity(BlockPos pos, BlockState blockState)
    {
        super(FOTBlockEntityTypes.SIGN, pos, blockState);
    }

    public FOTSignBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState)
    {
        super(type, pos, blockState);
    }
}