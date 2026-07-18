package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;

public class BuddingPrismarizedLogBlock extends FOTRotatedPillarBlock implements BonemealableBlock
{
    public static final BooleanProperty BUD = BooleanProperty.create("bud");

    public BuddingPrismarizedLogBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(BUD, false));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (random.nextInt(80) == 0)
        {
            var blockState = level.getBlockState(pos.below());

            if (canFruitGrowAtState(blockState))
            {
                this.growGuardianFruit(level, pos);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(BUD));
    }

    public static boolean canFruitGrowAtState(BlockState state)
    {
        return state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source)
    {
        return canFruitGrowAtState(level.getBlockState(pos.below()));
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source)
    {
        return random.nextInt(10) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source)
    {
        this.growGuardianFruit(level, pos);
    }

    private void growGuardianFruit(Level level, BlockPos pos)
    {
        level.setBlockAndUpdate(pos.below(), FOTBlocks.GUARDIAN_FRUIT.defaultBlockState());
        level.playSound(null, pos, FOTSoundEvents.GUARDIAN_FRUIT_GROW, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
    }
}