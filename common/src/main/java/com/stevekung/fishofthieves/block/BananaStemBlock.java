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
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BananaStemBlock extends SmallRotatedPillarBlock implements BonemealableBlock
{
    private static final VoxelShape SHAPE_VERTICAL = Block.box(3, 0, 3, 13, 16, 13);
    private static final VoxelShape SHAPE_HORIZONTAL_NS = Block.box(3, 3, 0, 13, 13, 16);
    private static final VoxelShape SHAPE_HORIZONTAL_WE = Block.box(0, 3, 3, 16, 13, 13);

    public BananaStemBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction)
    {
        return (adjacentState.is(this) || adjacentState.is(FOTTags.Blocks.NON_FULL_LOGS)) && state.getValue(AXIS) == adjacentState.getValue(AXIS);
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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.isRaining() && random.nextInt(10) == 0)
        {
            this.growBananaShoots(level, random, pos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source)
    {
        return Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> FOTBlocks.BANANA_SHOOTS_PLANT.defaultBlockState().canSurvive(level, pos.relative(direction)) && level.getBlockState(pos.relative(direction)).isAir());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source)
    {
        return random.nextInt(3) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source)
    {
        this.growBananaShoots(level, random, pos);
    }

    private void growBananaShoots(ServerLevel level, RandomSource random, BlockPos pos)
    {
        Direction.Plane.HORIZONTAL.shuffledCopy(random).stream()
                .filter(direction -> level.getBlockState(pos.relative(direction)).isAir() && FOTBlocks.BANANA_SHOOTS_PLANT.defaultBlockState().canSurvive(level, pos.relative(direction))).findFirst()
                .ifPresent(direction -> level.setBlock(pos.relative(direction), FOTBlocks.BANANA_SHOOTS_PLANT.defaultBlockState().setValue(BananaShootsPlantBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS));
    }
}