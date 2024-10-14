package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.block.FOTWallHangingSignBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FOTHangingSignItem extends FOTSignItem
{
    public FOTHangingSignItem(Block block, Block wallBlock, Item.Properties properties)
    {
        super(properties, block, wallBlock, Direction.UP);
    }

    @Override
    protected boolean canPlace(LevelReader level, BlockState state, BlockPos pos)
    {
        if (state.getBlock() instanceof FOTWallHangingSignBlock wallHangingSignBlock && !wallHangingSignBlock.canPlace(state, level, pos))
        {
            return false;
        }
        return super.canPlace(level, state, pos);
    }
}