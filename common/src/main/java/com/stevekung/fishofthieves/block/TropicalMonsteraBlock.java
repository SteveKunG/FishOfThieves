package com.stevekung.fishofthieves.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TropicalMonsteraBlock extends BushBlock
{
    public static final MapCodec<TropicalMonsteraBlock> CODEC = simpleCodec(TropicalMonsteraBlock::new);
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 8, 14);

    public TropicalMonsteraBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec()
    {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }
}