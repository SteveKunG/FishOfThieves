package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

@SuppressWarnings("deprecation")
public class CoconutGrowableLogBlock extends SmallRotatedPillarBlock implements BonemealableBlock
{
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty GROW = BooleanProperty.create("grow");

    public CoconutGrowableLogBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(GROW, false).setValue(TOP, false));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (state.getValue(GROW) && random.nextInt(20) == 0)
        {
            this.growCoconuts(level, random, pos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return state.getValue(GROW) && Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> level.getBlockState(pos.relative(direction)).isAir());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        this.growCoconuts(level, random, pos);
    }

    private void growCoconuts(ServerLevel level, RandomSource random, BlockPos pos)
    {
        var direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        var blockState = level.getBlockState(pos.relative(direction));

        if (blockState.isAir())
        {
            level.setBlock(pos.relative(direction), FOTBlocks.COCONUT_FRUIT.defaultBlockState().setValue(CoconutFruitBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(TOP, GROW));
    }
}