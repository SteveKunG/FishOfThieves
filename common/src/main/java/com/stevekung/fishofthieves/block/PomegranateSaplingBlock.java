package com.stevekung.fishofthieves.block;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PomegranateSaplingBlock extends BushBlock implements BonemealableBlock
{
    public static final MapCodec<PomegranateSaplingBlock> CODEC = simpleCodec(PomegranateSaplingBlock::new);
    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 12, 13);

    public PomegranateSaplingBlock(Properties properties)
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

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        this.growToPomegranatePlant(level, pos, random);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return (level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos)) && super.canSurvive(state, level, pos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return random.nextInt(3) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        this.growToPomegranatePlant(level, pos, random);
    }

    private void growToPomegranatePlant(ServerLevel level, BlockPos pos, RandomSource randomSource)
    {
        if (level.getRawBrightness(pos.above(), 0) >= 9)
        {
            var doublePlantBlock = (DoublePlantBlock) FOTBlocks.TALL_POMEGRANATE_PLANT;

            if (randomSource.nextInt(5) == 0 && doublePlantBlock.defaultBlockState().canSurvive(level, pos) && level.isEmptyBlock(pos.above()))
            {
                DoublePlantBlock.placeAt(level, doublePlantBlock.defaultBlockState(), pos, Block.UPDATE_CLIENTS);
            }
            else
            {
                var blockState = FOTBlocks.POMEGRANATE_PLANT.defaultBlockState();
                level.setBlock(pos, blockState, Block.UPDATE_CLIENTS);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
            }
        }
    }
}