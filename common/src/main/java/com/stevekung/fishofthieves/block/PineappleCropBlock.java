package com.stevekung.fishofthieves.block;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * From Crown
 * State 0: crop
 * State 1: crown
 * State 2: bush
 * State 3: flowering red bud
 * State 4: green pineapple
 * State 5: light green pineapple
 * State 6: pineapple
 */
@SuppressWarnings("deprecation")
public class PineappleCropBlock extends DoublePlantBlock implements BonemealableBlock
{
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);

    private static final VoxelShape FULL_UPPER_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 15.0, 13.0);
    private static final VoxelShape FULL_LOWER_SHAPE = Block.box(3.0, -1.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape COLLISION_SHAPE_BULB = Block.box(5.0, -1.0, 5.0, 11.0, 3.0, 11.0);
    private static final VoxelShape COLLISION_SHAPE_CROP = Block.box(3.0, -1.0, 3.0, 13.0, 5.0, 13.0);
    private static final VoxelShape[] UPPER_SHAPE_BY_AGE = new VoxelShape[] { Block.box(3.0, 0.0, 3.0, 13.0, 11.0, 13.0), FULL_UPPER_SHAPE };
    private static final VoxelShape[] LOWER_SHAPE_BY_AGE = new VoxelShape[] { COLLISION_SHAPE_BULB, Block.box(3.0, -1.0, 3.0, 13.0, 14.0, 13.0), FULL_LOWER_SHAPE, FULL_LOWER_SHAPE, FULL_LOWER_SHAPE };

    public PineappleCropBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    private int getMaxAge()
    {
        return 6;
    }

    private boolean isLowerAge(BlockState state)
    {
        return state.getValue(AGE) < this.getMaxAge();
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && this.isLowerAge(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        if (state.getValue(AGE) == 0)
        {
            return COLLISION_SHAPE_BULB;
        }
        else
        {
            return state.getValue(HALF) == DoubleBlockHalf.LOWER ? COLLISION_SHAPE_CROP : super.getCollisionShape(state, level, pos, context);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return !isLower(state) ? super.canSurvive(state, level, pos) : this.mayPlaceOn(level.getBlockState(pos.below()), level, pos.below()) && sufficientLight(level, pos) && (state.getValue(AGE) < 3 || isUpper(level.getBlockState(pos.above())));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(AGE));
    }

    //    @Override
    //    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    //    {
    //        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE_BY_AGE[Math.min(Math.abs(4 - (state.getValue(AGE) + 1)), UPPER_SHAPE_BY_AGE.length - 1)] : LOWER_SHAPE_BY_AGE[state.getValue(AGE)];
    //    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (entity instanceof Ravager && level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING))
        {
            level.destroyBlock(pos, true, entity);
        }

        super.entityInside(state, level, pos, entity);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext)
    {
        return false;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        var growthSpeed = CropBlock.getGrowthSpeed(this, level, pos);

        if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0)
        {
            this.grow(level, state, pos, 1);
        }
    }

    private void grow(ServerLevel level, BlockState state, BlockPos pos, int growthAge)
    {
        var age = Math.min(state.getValue(AGE) + growthAge, this.getMaxAge());

        if (this.canGrow(level, pos, state, age))
        {
            level.setBlock(pos, state.setValue(AGE, age), Block.UPDATE_CLIENTS);

            if (age >= 3)
            {
                var blockPos = pos.above();
                level.setBlock(blockPos, copyWaterloggedFrom(level, pos, this.defaultBlockState().setValue(AGE, age).setValue(HALF, DoubleBlockHalf.UPPER)), Block.UPDATE_ALL);
            }
        }
    }

    private static boolean canGrowInto(LevelReader level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        return blockState.isAir() || blockState.is(FOTBlocks.PINEAPPLE_CROP);
    }

    private static boolean sufficientLight(LevelReader level, BlockPos pos)
    {
        return level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos);
    }

    private static boolean isLower(BlockState state)
    {
        return state.is(FOTBlocks.PINEAPPLE_CROP) && state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    private static boolean isUpper(BlockState state)
    {
        return state.is(FOTBlocks.PINEAPPLE_CROP) && state.getValue(HALF) == DoubleBlockHalf.UPPER;
    }

    private boolean canGrow(LevelReader reader, BlockPos pos, BlockState state, int age)
    {
        return this.isLowerAge(state) && sufficientLight(reader, pos) && (age < 3 || canGrowInto(reader, pos.above()));
    }

    @Nullable
    private PineappleCropBlock.PosAndState getLowerHalf(LevelReader level, BlockPos pos, BlockState state)
    {
        if (isLower(state))
        {
            return new PineappleCropBlock.PosAndState(pos, state);
        }
        else
        {
            var blockPos = pos.below();
            var blockState = level.getBlockState(blockPos);
            return isLower(blockState) ? new PineappleCropBlock.PosAndState(blockPos, blockState) : null;
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        var posAndState = this.getLowerHalf(level, pos, state);
        return posAndState != null && this.canGrow(level, posAndState.pos, posAndState.state, posAndState.state.getValue(AGE) + 1);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        var posAndState = this.getLowerHalf(level, pos, state);

        if (posAndState != null)
        {
            this.grow(level, posAndState.state, posAndState.pos, Mth.nextInt(level.random, 1, 2));
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return state.getValue(AGE) == 0 ? new ItemStack(FOTItems.PINEAPPLE_SEEDS) : new ItemStack(FOTItems.PINEAPPLE_CROWN);
    }

    record PosAndState(BlockPos pos, BlockState state)
    {}
}