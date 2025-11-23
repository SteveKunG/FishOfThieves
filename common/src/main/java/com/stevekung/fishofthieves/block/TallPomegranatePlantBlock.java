package com.stevekung.fishofthieves.block;

import org.jspecify.annotations.Nullable;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TallPomegranatePlantBlock extends DoublePlantBlock implements BonemealableBlock
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    private static final VoxelShape LOWER_SHAPE = Block.box(3, 0, 3, 13, 16, 13);
    private static final VoxelShape UPPER_SHAPE = Block.box(1, 0, 1, 15, 16, 15);

    public TallPomegranatePlantBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, 0).setValue(PERSISTENT, false));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return !state.getValue(PERSISTENT) && state.getValue(HALF) == DoubleBlockHalf.LOWER && state.getValue(AGE) <= 2;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE : Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(AGE, PERSISTENT));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE : LOWER_SHAPE;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var level = context.getLevel();
        var blockPos = context.getClickedPos();
        var blockState = super.getStateForPlacement(context);
        var isUnobstructed = UPPER_SHAPE.isEmpty() || this.isUnobstructed(level, blockPos);
        return blockState != null && isUnobstructed ? blockState : null;
    }

    private boolean isUnobstructed(Level level, BlockPos pos)
    {
        return level.isUnobstructed(null, UPPER_SHAPE.move(pos.getX(), pos.getY(), pos.getZ())) && level.isUnobstructed(null, UPPER_SHAPE.move(pos.getX(), pos.getY() + 1, pos.getZ()));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, boolean intersectInsideBlock)
    {
        if (level instanceof ServerLevel serverLevel && entity instanceof Ravager && serverLevel.getGameRules().get(GameRules.MOB_GRIEFING))
        {
            serverLevel.destroyBlock(pos, true, entity);
        }
        super.entityInside(state, level, pos, entity, insideBlockEffectApplier, intersectInsideBlock);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext)
    {
        return false;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        var growthSpeed = FOTPlatform.getGrowthSpeedFromCropBlock(this.defaultBlockState(), level, pos);

        if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0)
        {
            this.grow(level, state, pos);
        }
    }

    @Override
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        var itemStack = player.getItemInHand(hand);
        int age = state.getValue(AGE);
        var canHarvest = age == 3;

        if (!state.getValue(PERSISTENT) && itemStack.is(Items.SHEARS))
        {
            if (!level.isClientSide())
            {
                level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, state.setValue(PERSISTENT, true), Block.UPDATE_ALL_IMMEDIATE);

                if (state.getValue(HALF) == DoubleBlockHalf.LOWER)
                {
                    level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(PERSISTENT, true), Block.UPDATE_ALL_IMMEDIATE);
                }
                else
                {
                    level.setBlock(pos.below(), state.setValue(HALF, DoubleBlockHalf.LOWER).setValue(PERSISTENT, true), Block.UPDATE_ALL_IMMEDIATE);
                }

                itemStack.hurtAndBreak(1, player, hand.asEquipmentSlot());
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }
            return InteractionResult.SUCCESS;
        }
        else if (!canHarvest && player.getItemInHand(hand).is(Items.BONE_MEAL))
        {
            return InteractionResult.PASS;
        }
        else if (canHarvest)
        {
            var count = 1 + level.random.nextInt(2);
            popResource(level, pos, new ItemStack(FOTItems.POMEGRANATE, count + 1));
            level.playSound(null, pos, FOTSoundEvents.POMEGRANATE_PLANT_PICK, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            var blockState = state.setValue(AGE, 0);
            level.setBlock(pos, blockState, Block.UPDATE_CLIENTS);

            if (state.getValue(HALF) == DoubleBlockHalf.LOWER)
            {
                level.setBlock(pos.above(), blockState.setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
            }
            else
            {
                level.setBlock(pos.below(), blockState.setValue(HALF, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
            }

            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
            return InteractionResult.SUCCESS;
        }
        else
        {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
    }

    private void grow(ServerLevel level, BlockState state, BlockPos pos)
    {
        var i = Math.min(state.getValue(AGE) + 1, 3);
        var persistent = state.getValue(PERSISTENT);

        if (this.canGrow(level, pos, state))
        {
            level.setBlock(pos, state.setValue(AGE, i).setValue(PERSISTENT, persistent), Block.UPDATE_CLIENTS);
            level.setBlock(pos.above(), copyWaterloggedFrom(level, pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, i).setValue(PERSISTENT, persistent)), Block.UPDATE_ALL);
        }
    }

    private static boolean sufficientLight(LevelReader level, BlockPos pos)
    {
        return level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos);
    }

    private static boolean isLower(BlockState state)
    {
        return state.is(FOTBlocks.TALL_POMEGRANATE_PLANT) && state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    private boolean canGrow(LevelReader reader, BlockPos pos, BlockState state)
    {
        return state.getValue(AGE) <= 2 && sufficientLight(reader, pos);
    }

    private TallPomegranatePlantBlock.@Nullable PosAndState getLowerHalf(LevelReader level, BlockPos pos, BlockState state)
    {
        if (isLower(state))
        {
            return new TallPomegranatePlantBlock.PosAndState(pos, state);
        }
        else
        {
            var blockPos = pos.below();
            var blockState = level.getBlockState(blockPos);
            return isLower(blockState) ? new TallPomegranatePlantBlock.PosAndState(blockPos, blockState) : null;
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state)
    {
        var posAndState = this.getLowerHalf(level, pos, state);
        return posAndState != null && this.canGrow(level, posAndState.pos, posAndState.state);
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
            this.grow(level, posAndState.state, posAndState.pos);
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type)
    {
        return false;
    }

    record PosAndState(BlockPos pos, BlockState state)
    {}
}