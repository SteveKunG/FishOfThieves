package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class BananaShootsBlock extends SaplingBlock
{
    public BananaShootsBlock(AbstractTreeGrower treeGrower, Properties properties)
    {
        super(treeGrower, properties);
    }

    @Override
    public float getMaxHorizontalOffset()
    {
        return 0F;
    }

    @Override
    public float getMaxVerticalOffset()
    {
        return 0.3F;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.is(FOTTags.Blocks.BANANA_SHOOTS_PLACEABLE_ON) || super.mayPlaceOn(state, level, pos);
    }
}