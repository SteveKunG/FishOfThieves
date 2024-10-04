package com.stevekung.fishofthieves.block;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.utils.CauldronUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class BananaLeavesBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock
{
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    public static final IntegerProperty COUNT = IntegerProperty.create("count", 1, 2);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape BOTTOM_AABB = Block.box(0, 2, 0, 16, 5, 16);
    private static final VoxelShape TOP_AABB = Block.box(0, 10, 0, 16, 13, 16);

    public BananaLeavesBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, Part.STEM).setValue(TYPE, Type.LOWER).setValue(COUNT, 1).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state)
    {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (state.getValue(PART) == Part.TAIL && random.nextFloat() < 0.2F)
        {
            CauldronUtils.fillCauldronFromLeavesTail(level, pos);
        }
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
    {
        builder.add(FACING, PART, TYPE, WATERLOGGED, COUNT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(TYPE) == Type.UPPER ? TOP_AABB : BOTTOM_AABB;
    }

    private static Direction getNeighbourDirection(Part part, Direction direction)
    {
        return part == Part.TAIL ? direction.getOpposite() : direction;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if (level.isRainingAt(pos.above()))
        {
            if (random.nextInt(8) == 0)
            {
                if (state.getValue(PART) == Part.STEM)
                {
                    var ySpawn = state.getValue(TYPE) == Type.UPPER ? 0.6d : 0.1d;
                    var blockPos = pos.below();
                    var blockState = level.getBlockState(blockPos);

                    if (!blockState.canOcclude() || !blockState.isFaceSturdy(level, blockPos, Direction.UP))
                    {
                        var x = pos.getX() + random.nextDouble();
                        var y = pos.getY() + ySpawn;
                        var z = pos.getZ() + random.nextDouble();
                        level.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0, 0.0, 0.0);
                    }
                }
                else
                {
                    var ySpawn = state.getValue(TYPE) == Type.UPPER ? Mth.nextDouble(level.random, -0.6, -0.2) : Mth.nextDouble(level.random, -1.2, -0.8);
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
                        var f = vec3.y + ySpawn * 0.5;
                        var g = vec3.z + Mth.nextDouble(level.random, -1.0, 1.0) * (bl3 ? 0.5 : d);
                        level.addParticle(ParticleTypes.DRIPPING_WATER, e, f, g, 0, 0, 0);
                    }
                }
            }
        }
    }

    private BlockState placeVerticalLeaves(Direction direction, boolean isWater)
    {
        var blockState = FOTBlocks.VERTICAL_BANANA_LEAVES.defaultBlockState().setValue(VerticalLeavesBlock.WATERLOGGED, isWater);
        return direction == Direction.DOWN ? blockState.setValue(VerticalLeavesBlock.CEILING, true) : blockState;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext)
    {
        return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && state.getValue(PART) != Part.TAIL && state.getValue(COUNT) < 2 || super.canBeReplaced(state, useContext);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var level = context.getLevel();
        var blockPos = context.getClickedPos();
        var fluidState = level.getFluidState(blockPos);
        var isWater = fluidState.getType() == Fluids.WATER;
        var blockState2 = this.defaultBlockState().setValue(TYPE, Type.LOWER).setValue(WATERLOGGED, isWater);
        var direction = context.getClickedFace();
        var blockState = level.getBlockState(context.getClickedPos());

        if (direction.getAxis() == Direction.Axis.Y)
        {
            if (!blockState.is(this))
            {
                return this.placeVerticalLeaves(direction, isWater);
            }
            else
            {
                if (blockState.getValue(PART) == Part.STEM)
                {
                    return blockState.setValue(COUNT, Math.min(2, blockState.getValue(COUNT) + 1));
                }
            }
        }
        else
        {
            if (blockState.is(this) && blockState.getValue(PART) == Part.STEM)
            {
                return blockState.setValue(COUNT, Math.min(2, blockState.getValue(COUNT) + 1));
            }
            else
            {
                blockState2 = blockState2.setValue(FACING, direction);

                if (blockState2.canSurvive(level, blockPos))
                {
                    var blockPos1 = blockPos.relative(blockState2.getValue(FACING));

                    if (level.getBlockState(blockPos1).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockPos1))
                    {
                        return direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double) blockPos.getY() > 0.5)) ? blockState2 : blockState2.setValue(TYPE, Type.UPPER);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if (!level.isClientSide() && player.isCreative())
        {
            var part = state.getValue(PART);

            if (part == Part.TAIL)
            {
                var blockPos = pos.relative(getNeighbourDirection(part, state.getValue(FACING)));
                var blockState = level.getBlockState(blockPos);

                if (blockState.is(this) && blockState.getValue(PART) == Part.STEM)
                {
                    level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(blockState));
                    level.destroyBlock(blockPos, false);
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var otherState = level.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));

        if (otherState.is(this) && otherState.getValue(PART) == Part.STEM)
        {
            return state.getValue(FACING) == otherState.getValue(FACING);
        }
        return otherState.is(BlockTags.LEAVES) && otherState.isCollisionShapeFullBlock(level, pos) || otherState.is(FOTBlocks.BANANA_STEM) || otherState.isFaceSturdy(level, pos, state.getValue(FACING));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (!level.isClientSide())
        {
            var blockPos = pos.relative(state.getValue(FACING));
            var fluidState = level.getFluidState(blockPos);
            level.setBlock(blockPos, state.setValue(PART, Part.TAIL).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER), Block.UPDATE_ALL);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        if (state.getValue(WATERLOGGED))
        {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (!state.canSurvive(level, pos))
        {
            return Blocks.AIR.defaultBlockState();
        }
        if (state.getValue(PART) == Part.STEM)
        {
            var blockPos = pos.relative(state.getValue(FACING));
            var blockState = level.getBlockState(blockPos);
            return blockState.is(this) && blockState.getValue(PART) == Part.TAIL ? state : Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    public enum Part implements StringRepresentable
    {
        STEM("stem"),
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

    public enum Type implements StringRepresentable
    {
        UPPER("upper"),
        LOWER("lower");

        private final String name;

        Type(String name)
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