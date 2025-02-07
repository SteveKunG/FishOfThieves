package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingMangoFruitBlock extends AbstractMangoFruitBlock
{
    private static final VoxelShape STAGE_0 = Block.box(6.5, 7, 6.5, 9.5, 16, 9.5);
    private static final VoxelShape STAGE_FULL = Block.box(5.5, 5, 5.5, 10.5, 16, 10.5);

    public HangingMangoFruitBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(FALLING, false));
    }

    @Override
    public float getMaxVerticalOffset()
    {
        return 0.05F;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos.above());
        return blockState.is(FOTBlocks.MANGO_LEAVES);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        var age = Math.min(state.getValue(AGE), 1);
        var offset = state.getOffset(level, pos);
        return (age == 0 ? STAGE_0 : STAGE_FULL).move(offset.x, offset.y, offset.z);
    }
}