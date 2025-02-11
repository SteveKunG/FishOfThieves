package com.stevekung.fishofthieves.block;

import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class PineappleCropBlock extends DoublePlantBlock implements BonemealableBlock
{
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 5);

    private static final VoxelShape BASE_SHAPE = Block.box(2, 0, 2, 14, 9, 14);
    private static final VoxelShape STAGE_3_SHAPE = Shapes.join(Block.box(6.5, 9, 6.5, 9.5, 12, 9.5), BASE_SHAPE, BooleanOp.OR);
    private static final VoxelShape STAGE_4_COLLISION_SHAPE = Block.box(5, -7, 5, 11, 1, 11);
    private static final VoxelShape STAGE_4_LOWER_SHAPE = Shapes.join(Block.box(5, 9, 5, 11, 17, 11), BASE_SHAPE, BooleanOp.OR);
    private static final VoxelShape STAGE_4_SHAPE = Shapes.join(STAGE_4_COLLISION_SHAPE, Block.box(5, 1, 5, 11, 5, 11), BooleanOp.OR);
    private static final VoxelShape STAGE_5_COLLISION_SHAPE = Shapes.join(Block.box(6, -7, 6, 10, -6, 10), Block.box(4, -6, 4, 12, 4, 12), BooleanOp.OR);
    private static final VoxelShape STAGE_5_LOWER_SHAPE = Stream.of(Block.box(6, 9, 6, 10, 10, 10), Block.box(4, 10, 4, 12, 20, 12), BASE_SHAPE).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape STAGE_5_SHAPE = Stream.of(STAGE_5_COLLISION_SHAPE, Block.box(4, 4, 4, 12, 11, 12)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public PineappleCropBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    private int getMaxAge()
    {
        return 5;
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
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int age = state.getValue(AGE);

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            if (age == 4)
            {
                return STAGE_4_COLLISION_SHAPE;
            }
            else if (age == 5)
            {
                return STAGE_5_COLLISION_SHAPE;
            }
        }
        return super.getCollisionShape(state, level, pos, context);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos)
    {
        if (!isLower(state) && state.getValue(AGE) >= 4)
        {
            var destroySpeed = 0.6f;
            var i = player.hasCorrectToolForDrops(state) ? 30 : 100;
            return player.getDestroySpeed(state) / destroySpeed / (float) i;
        }
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    public SoundType getSoundType(BlockState state)
    {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            var age = state.getValue(AGE);

            if (age >= 4)
            {
                return SoundType.WOOD;
            }
        }
        return super.getSoundType(state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        if (!sufficientLight(level, pos))
        {
            return false;
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.is(Blocks.FARMLAND) || state.is(BlockTags.DIRT);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(AGE));
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        var age = state.getValue(AGE);

        if (!level.isClientSide() && age >= 4 && EnchantmentHelper.hasSilkTouch(player.getMainHandItem()))
        {
            preventCreativeDropFromBottomPart(level, pos, state, player);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int age = state.getValue(AGE);

        if (age == 3)
        {
            return STAGE_3_SHAPE;
        }
        else if (age == 4)
        {
            return state.getValue(HALF) == DoubleBlockHalf.UPPER ? STAGE_4_SHAPE : STAGE_4_LOWER_SHAPE;
        }
        else if (age == 5)
        {
            return state.getValue(HALF) == DoubleBlockHalf.UPPER ? STAGE_5_SHAPE : STAGE_5_LOWER_SHAPE;
        }
        return BASE_SHAPE;
    }

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

            if (age >= 4)
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

    private boolean canGrow(LevelReader reader, BlockPos pos, BlockState state, int age)
    {
        return this.isLowerAge(state) && sufficientLight(reader, pos) && (age < 4 || canGrowInto(reader, pos.above()));
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
            this.grow(level, posAndState.state, posAndState.pos, Mth.nextInt(random, 1, 2));
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            return state.getValue(AGE) == 5 ? new ItemStack(FOTBlocks.RIPE_PINEAPPLE_BLOCK) : state.getValue(AGE) == 4 ? new ItemStack(FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK) : new ItemStack(FOTItems.PINEAPPLE_CROWN);
        }
        else
        {
            return state.getValue(AGE) == 0 ? new ItemStack(FOTItems.PINEAPPLE_SEEDS) : new ItemStack(FOTItems.PINEAPPLE_CROWN);
        }
    }

    record PosAndState(BlockPos pos, BlockState state)
    {}
}