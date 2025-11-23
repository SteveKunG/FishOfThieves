package com.stevekung.fishofthieves.block;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.client.AngledLeavesComponent;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.CauldronUtils;
import com.stevekung.fishofthieves.utils.ParticleUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BananaLeavesBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, BonemealableBlock
{
    public static final MapCodec<BananaLeavesBlock> CODEC = simpleCodec(BananaLeavesBlock::new);
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
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return CODEC;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state)
    {
        return state.getValue(COUNT) < 2;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        if (state.getValue(COUNT) < 2)
        {
            BlockPos blockPos;

            if (state.getValue(PART) == Part.STEM)
            {
                blockPos = pos.relative(state.getValue(FACING));
                level.setBlock(blockPos, level.getBlockState(blockPos).setValue(COUNT, 2), Block.UPDATE_ALL);
            }
            else
            {
                blockPos = pos.relative(state.getValue(FACING).getOpposite());
                level.setBlock(blockPos, level.getBlockState(blockPos).setValue(COUNT, 2), Block.UPDATE_ALL);
            }
            level.setBlock(pos, state.setValue(COUNT, 2), Block.UPDATE_ALL);
        }
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state)
    {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.isRainingAt(pos.above()) && state.getValue(PART) == Part.TAIL && random.nextFloat() < 0.5F)
        {
            CauldronUtils.fillCauldronFromLeavesTail(state, level, pos);
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
        if (level.isRainingAt(pos.above()) && random.nextInt(4) == 0)
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
                var direction = state.getValue(FACING);
                var yOffset = state.getValue(TYPE) == Type.UPPER ? 0.1d : -0.4d;
                var component = new AngledLeavesComponent(22.5d, 0.85d, 60d);
                ParticleUtils.spawnDrippingWaterParticlesForLeaves(level, direction, pos, random, UniformInt.of(2, 6), yOffset, 4, false, true, component);
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
        return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && state.getValue(COUNT) < 2 || super.canBeReplaced(state, useContext);
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
                if (blockState.getValue(PART) == Part.TAIL)
                {
                    var blockPos1 = blockPos.relative(blockState.getValue(FACING).getOpposite());
                    level.setBlock(blockPos1, level.getBlockState(blockPos1).setValue(COUNT, Math.min(2, blockState.getValue(COUNT) + 1)), Block.UPDATE_CLIENTS);
                }
                return blockState.setValue(COUNT, Math.min(2, blockState.getValue(COUNT) + 1));
            }
        }
        else
        {
            if (blockState.is(this))
            {
                if (blockState.getValue(PART) == Part.TAIL)
                {
                    var blockPos1 = blockPos.relative(blockState.getValue(FACING).getOpposite());
                    level.setBlock(blockPos1, level.getBlockState(blockPos1).setValue(COUNT, Math.min(2, blockState.getValue(COUNT) + 1)), Block.UPDATE_CLIENTS);
                }
                return blockState.setValue(COUNT, Math.min(2, blockState.getValue(COUNT) + 1));
            }
            else
            {
                blockState2 = blockState2.setValue(FACING, direction);

                if (blockState2.canSurvive(level, blockPos))
                {
                    var blockPos1 = blockPos.relative(blockState2.getValue(FACING));

                    if (level.isUnobstructed(blockState2, blockPos1, CollisionContext.empty()) && level.getBlockState(blockPos1).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockPos1))
                    {
                        return context.getClickLocation().y - blockPos.getY() > 0.5d ? blockState2.setValue(TYPE, Type.UPPER) : blockState2;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
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
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var otherState = level.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));

        if (otherState.is(this) && otherState.getValue(PART) == Part.STEM)
        {
            return state.getValue(FACING) == otherState.getValue(FACING);
        }
        return otherState.is(BlockTags.LEAVES) && otherState.isCollisionShapeFullBlock(level, pos) || otherState.is(FOTTags.Blocks.BANANA_STEMS) || otherState.isFaceSturdy(level, pos, state.getValue(FACING));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (!level.isClientSide())
        {
            var blockPos = pos.relative(state.getValue(FACING));
            var fluidState = level.getFluidState(blockPos);

            if (state.is(this) && state.getValue(PART) != Part.TAIL)
            {
                level.setBlock(blockPos, state.setValue(PART, Part.TAIL).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER), Block.UPDATE_ALL);
            }

            level.updateNeighborsAt(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource)
    {
        if (state.getValue(WATERLOGGED))
        {
            scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (!state.canSurvive(level, currentPos))
        {
            return Blocks.AIR.defaultBlockState();
        }
        if (state.getValue(PART) == Part.STEM)
        {
            var blockPos = currentPos.relative(state.getValue(FACING));
            var blockState = level.getBlockState(blockPos);
            return blockState.is(this) && blockState.getValue(PART) == Part.TAIL ? state : Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, scheduledTickAccess, currentPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType pathComputationType)
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