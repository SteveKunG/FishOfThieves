package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class GrowableBananaStemBlock extends BananaStemBlock implements BonemealableBlock
{
    public GrowableBananaStemBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.isRaining() && random.nextInt(10) == 0)
        {
            this.growBananaShoots(level, random, pos);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(FOTBlocks.BANANA_STEM);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> FOTBlocks.BANANA_SHOOTS_PLANT.defaultBlockState().canSurvive(level, pos) && level.getBlockState(pos.relative(direction)).isAir());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return random.nextInt(3) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        this.growBananaShoots(level, random, pos);
    }

    private void growBananaShoots(ServerLevel level, RandomSource random, BlockPos pos)
    {
        Direction.Plane.HORIZONTAL.shuffledCopy(random).stream().filter(direction -> level.getBlockState(pos.relative(direction)).isAir()).findFirst().ifPresent(direction -> level.setBlock(pos.relative(direction), FOTBlocks.BANANA_SHOOTS_PLANT.defaultBlockState().setValue(BananaShootsPlantBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS));
    }
}