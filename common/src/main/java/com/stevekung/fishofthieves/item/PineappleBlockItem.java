package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.block.PineappleCropBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class PineappleBlockItem extends BlockItem
{
    private final boolean isCrown;

    public PineappleBlockItem(boolean isCrown, Properties properties)
    {
        super(FOTBlocks.PINEAPPLE_CROP, properties);
        this.isCrown = isCrown;
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext context)
    {
        var blockState = super.getPlacementState(context);

        if (blockState != null)
        {
            return this.isCrown ? blockState.setValue(PineappleCropBlock.AGE, 1) : blockState;
        }
        return super.getPlacementState(context);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state)
    {
        if (context.getLevel().getBlockState(context.getClickedPos().below()).is(BlockTags.DIRT))
        {
            return false;
        }
        return super.canPlace(context, state);
    }
}