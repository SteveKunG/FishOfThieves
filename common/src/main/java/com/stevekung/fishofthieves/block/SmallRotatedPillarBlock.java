package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallRotatedPillarBlock extends RotatedPillarBlock implements BonemealableBlock
{
    private static final VoxelShape SHAPE_VERTICAL = Block.box(2, 0, 2, 14, 16, 14);
    private static final VoxelShape SHAPE_HORIZONTAL_NS = Block.box(2, 2, 0, 14, 14, 16);
    private static final VoxelShape SHAPE_HORIZONTAL_WE = Block.box(0, 2, 2, 16, 14, 14);
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    public SmallRotatedPillarBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(TOP, false));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return state.getValue(TOP) && Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> level.getBlockState(pos.relative(direction)).isAir());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        var direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        var blockState = level.getBlockState(pos.relative(direction));

        if (blockState.isAir())
        {
            level.setBlock(pos.relative(direction), FOTBlocks.COCONUT_FRUIT.defaultBlockState().setValue(CoconutFruitBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction)
    {
        return adjacentState.is(FOTTags.Blocks.NON_FULL_LOGS) && state.getValue(AXIS) == adjacentState.getValue(AXIS);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        switch (state.getValue(AXIS))
        {
            case X ->
            {
                return SHAPE_HORIZONTAL_WE;
            }
            case Z ->
            {
                return SHAPE_HORIZONTAL_NS;
            }
            default ->
            {
                return SHAPE_VERTICAL;
            }
        }
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state)
    {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(TOP));
    }
}