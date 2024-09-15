package com.stevekung.fishofthieves.block;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class CoconutFrondsBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock
{
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape BASE = Block.box(0, 12, 0, 16, 15, 16);
    private static final VoxelShape NOT_MIDDLE = Block.box(0, 9, 0, 16, 15, 16);

    public CoconutFrondsBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, Part.SINGLE).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        var otherState = level.getBlockState(pos.relative(state.getValue(FACING)));
        return otherState.canBeReplaced();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        var fluidState = level.getFluidState(pos.relative(state.getValue(FACING)));
        level.setBlock(pos.relative(state.getValue(FACING)), state.setValue(PART, Part.TAIL).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER), Block.UPDATE_ALL);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        var otherState = level.getBlockState(pos.relative(state.getValue(FACING)));

        if (state.getValue(WATERLOGGED))
        {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        switch (state.getValue(PART))
        {
            case SINGLE ->
            {
                // Update leaf to stem state
                if (otherState.is(this) && otherState.getValue(FACING) == state.getValue(FACING))
                {
                    return state.setValue(PART, Part.STEM);
                }
            }
            case STEM ->
            {
                // Update leaf to single state if destroyed
                if (!otherState.is(this))
                {
                    return state.setValue(PART, Part.SINGLE);
                }
            }
            case MIDDLE ->
            {
                // Update leaf to tail state if destroyed
                if (!otherState.is(this))
                {
                    return state.setValue(PART, Part.TAIL);
                }
            }
            case TAIL ->
            {
                // Update leaf to middle state if placing more leaf
                if (otherState.is(this) && otherState.getValue(FACING) == state.getValue(FACING) && otherState.getValue(PART) == Part.TAIL)
                {
                    return state.setValue(PART, Part.MIDDLE);
                }
            }
        }
        return state.canSurvive(level, pos) ? super.updateShape(state, direction, neighborState, level, pos, neighborPos) : Blocks.AIR.defaultBlockState();
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos)
    {
        return Shapes.empty();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var otherState = level.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));

        if (otherState.is(this))
        {
            return state.getValue(FACING) == otherState.getValue(FACING);
        }
        return otherState.is(FOTTags.Blocks.COCONUT_LOGS) || otherState.is(BlockTags.LEAVES) && otherState.isCollisionShapeFullBlock(level, pos) || otherState.isFaceSturdy(level, pos, state.getValue(FACING));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if (level.isRainingAt(pos.above()))
        {
            if (random.nextInt(8) == 0)
            {
                if (state.getValue(PART) == Part.MIDDLE)
                {
                    var blockPos = pos.below();
                    var blockState = level.getBlockState(blockPos);

                    if (!blockState.canOcclude() || !blockState.isFaceSturdy(level, blockPos, Direction.UP))
                    {
                        var x = pos.getX() + random.nextDouble();
                        var y = pos.getY() + 0.75d;
                        var z = pos.getZ() + random.nextDouble();
                        level.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0, 0.0, 0.0);
                    }
                }
                else
                {
                    var count = UniformInt.of(1, 2);
                    var axis = state.getValue(FACING).getAxis();
                    var d = 0.2;
                    var vec3 = Vec3.atCenterOf(pos);
                    var bl = axis == Direction.Axis.X;
                    var bl3 = axis == Direction.Axis.Z;
                    var i = count.sample(level.random);

                    for (var j = 0; j < i; j++)
                    {
                        var e = vec3.x + Mth.nextDouble(level.random, -1.0, 1.0) * (bl ? 0.5 : d);
                        var f = vec3.y + Mth.nextDouble(level.random, -0.1, 0.2) * 0.5;
                        var g = vec3.z + Mth.nextDouble(level.random, -1.0, 1.0) * (bl3 ? 0.5 : d);
                        level.addParticle(ParticleTypes.DRIPPING_WATER, e, f, g, 0, 0, 0);
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var fluidState = context.getLevel().getFluidState(context.getClickedPos());

        for (var direction : context.getNearestLookingDirections())
        {
            BlockState blockState;

            if (direction.getAxis() == Direction.Axis.Y)
            {
                blockState = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
            }
            else
            {
                blockState = this.defaultBlockState().setValue(FACING, direction.getOpposite());
            }

            var otherState = context.getLevel().getBlockState(context.getClickedPos().relative(blockState.getValue(FACING).getOpposite()));

            if (otherState.is(this))
            {
                if (otherState.getValue(PART) == Part.SINGLE || otherState.getValue(PART) == Part.TAIL)
                {
                    blockState = blockState.setValue(PART, Part.TAIL);
                }
            }

            if (blockState.canSurvive(context.getLevel(), context.getClickedPos()))
            {
                return blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
            }
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        if (state.getValue(PART) != Part.MIDDLE)
        {
            return NOT_MIDDLE;
        }
        return BASE;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
    {
        builder.add(FACING, PART, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public enum Part implements StringRepresentable
    {
        SINGLE("single"),
        STEM("stem"),
        MIDDLE("middle"),
        TAIL("tail");

        private final String name;

        Part(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        @Override
        public String getSerializedName()
        {
            return this.name;
        }
    }
}