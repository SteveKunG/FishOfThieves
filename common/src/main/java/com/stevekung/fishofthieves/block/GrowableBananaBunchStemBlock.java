package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class GrowableBananaBunchStemBlock extends BananaStemBlock implements BonemealableBlock
{
    public GrowableBananaBunchStemBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.isRaining() && random.nextInt(10) == 0)
        {
            this.growBananaBunch(level, random, pos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> this.canGrowBananaBunch(level, pos, direction));
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return random.nextInt(5) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        this.growBananaBunch(level, random, pos);
    }

    private boolean canGrowBananaBunch(BlockGetter level, BlockPos pos, Direction direction)
    {
        var leavesState = level.getBlockState(pos.relative(direction));

        if (leavesState.is(FOTBlocks.BANANA_LEAVES))
        {
            return leavesState.getValue(BananaLeavesBlock.TYPE) == BananaLeavesBlock.Type.UPPER && level.getBlockState(pos.below().relative(direction)).isAir();
        }
        return false;
    }

    private void growBananaBunch(ServerLevel level, RandomSource random, BlockPos pos)
    {
        Direction.Plane.HORIZONTAL.shuffledCopy(random).stream().filter(direction -> this.canGrowBananaBunch(level, pos, direction)).findFirst().ifPresent(direction -> level.setBlock(pos.below().relative(direction), FOTBlocks.BANANA_BLOSSOM.defaultBlockState().setValue(BananaBlossomBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS));
    }
}