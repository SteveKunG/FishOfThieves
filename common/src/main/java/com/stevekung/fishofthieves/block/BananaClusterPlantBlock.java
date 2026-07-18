package com.stevekung.fishofthieves.block;

import java.util.Locale;
import java.util.Map;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BananaClusterPlantBlock extends AbstractBananaClusterBlock implements BonemealableBlock
{
    private static final Map<Direction, VoxelShape> SHAPES = Map.of(
            Direction.NORTH, Block.box(2, 0, 0, 14, 16, 12),
            Direction.WEST, Block.box(0, 0, 2, 12, 16, 14),
            Direction.SOUTH, Block.box(2, 0, 4, 14, 16, 16),
            Direction.EAST, Block.box(4, 0, 2, 16, 16, 14)
    );

    public static final EnumProperty<HangingType> HANGING = EnumProperty.create("hanging", HangingType.class);
    private final BananaClusterBlock.Type type;

    public BananaClusterPlantBlock(BananaClusterBlock.Type type, Properties properties)
    {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, HangingType.NONE).setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return this.type != BananaClusterBlock.Type.RIPE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (this.type == BananaClusterBlock.Type.BARELY_RIPE && BananaClusterBlock.canClusterPlantGrow(level, pos) && random.nextInt(10) == 0)
        {
            level.setBlock(pos, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.withPropertiesOf(state), Block.UPDATE_ALL);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source)
    {
        return state.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source)
    {
        return random.nextInt(6) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source)
    {
        if (this.type == BananaClusterBlock.Type.BARELY_RIPE)
        {
            level.setBlock(pos, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.withPropertiesOf(state), Block.UPDATE_ALL);
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData)
    {
        return new ItemStack(this.type.block().get());
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource)
    {
        if (direction == Direction.UP && neighborState.is(FOTTags.Blocks.BANANA_CLUSTER_PLANTS))
        {
            if (!neighborState.is(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT) && state.getValue(HANGING) == HangingType.SMALL_CLUSTER)
            {
                return state.setValue(HANGING, HangingType.NONE);
            }
            return state.getValue(HANGING) == HangingType.STEM || state.getValue(HANGING) == HangingType.SMALL_CLUSTER ? state : state.setValue(HANGING, HangingType.NONE);
        }
        return super.updateShape(state, level, scheduledTickAccess, currentPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction)
    {
        return adjacentState.is(FOTTags.Blocks.BANANA_CLUSTER_PLANTS) && !adjacentState.is(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT) && direction.getAxis().isVertical();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, HANGING, WATERLOGGED);
    }

    public enum HangingType implements StringRepresentable
    {
        NONE,
        SMALL_CLUSTER,
        STEM;

        public static final StringRepresentable.EnumCodec<HangingType> CODEC = StringRepresentable.fromEnum(HangingType::values);

        @Override
        public String getSerializedName()
        {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}