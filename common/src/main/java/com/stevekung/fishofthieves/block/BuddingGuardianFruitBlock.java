package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BuddingGuardianFruitBlock extends FOTRotatedPillarBlock
{
    public static final BooleanProperty BUD = BooleanProperty.create("bud");

    public BuddingGuardianFruitBlock(BlockBehaviour.Properties properties)
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
                var blockState2 = FOTBlocks.GUARDIAN_FRUIT.defaultBlockState();
                level.setBlockAndUpdate(pos.below(), blockState2);
                level.playSound(null, pos, FOTSoundEvents.GUARDIAN_FRUIT_GROW, SoundSource.BLOCKS, 1.0F, 1.0F);
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
}