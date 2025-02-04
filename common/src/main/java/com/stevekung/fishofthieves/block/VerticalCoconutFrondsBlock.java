package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class VerticalCoconutFrondsBlock extends VerticalLeavesBlock
{
    private static final VoxelShape DOWN_AABB = Block.box(0, 0, 0, 16, 8, 16);
    private static final VoxelShape UP_AABB = Block.box(0, 8, 0, 16, 16, 16);

    public VerticalCoconutFrondsBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CEILING, false).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(CEILING) ? UP_AABB : DOWN_AABB;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(FOTBlocks.COCONUT_FRONDS);
    }
}