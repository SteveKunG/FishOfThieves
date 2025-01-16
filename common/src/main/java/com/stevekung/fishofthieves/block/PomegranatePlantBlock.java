package com.stevekung.fishofthieves.block;

import org.jetbrains.annotations.Nullable;

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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class PomegranatePlantBlock extends BushBlock implements BonemealableBlock
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    private static final VoxelShape STAGE_0_SHAPE = Block.box(3, 0, 3, 13, 12, 13);
    private static final VoxelShape SHAPE = Shapes.or(Block.box(1, 4, 1, 15, 16, 15), Block.box(6.0, 0.0, 6.0, 10.0, 8.0, 10.0));

    public PomegranatePlantBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(PERSISTENT, false));
    }

    @Override
    public SoundType getSoundType(BlockState state)
    {
        return state.getValue(AGE) > 0 ? SoundType.AZALEA : super.getSoundType(state);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return state.getValue(AGE) == 0 ? new ItemStack(FOTItems.POMEGRANATE_SEEDS) : new ItemStack(FOTBlocks.POMEGRANATE_PLANT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(AGE) == 0 ? STAGE_0_SHAPE : SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(AGE) == 0 ? Shapes.empty() : SHAPE;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return !state.getValue(PERSISTENT) && state.getValue(AGE) < 4;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return context.getItemInHand().is(FOTItems.POMEGRANATE_SEEDS) ? super.getStateForPlacement(context) : this.defaultBlockState().setValue(AGE, 1);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        int age = state.getValue(AGE);

        if (age < 4 && random.nextInt(5) == 0 && level.getRawBrightness(pos.above(), 0) >= 9)
        {
            var doublePlantBlock = (DoublePlantBlock) FOTBlocks.TALL_POMEGRANATE_PLANT;

            if (age == 0 && random.nextInt(5) == 0 && doublePlantBlock.defaultBlockState().canSurvive(level, pos) && level.isEmptyBlock(pos.above()))
            {
                DoublePlantBlock.placeAt(level, doublePlantBlock.defaultBlockState(), pos, Block.UPDATE_CLIENTS);
            }
            else
            {
                var blockState = state.setValue(AGE, age + 1);
                level.setBlock(pos, blockState, Block.UPDATE_CLIENTS);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        var itemStack = player.getItemInHand(hand);
        int age = state.getValue(AGE);
        var canHarvest = age == 4;

        if (!state.getValue(PERSISTENT) && itemStack.is(Items.SHEARS))
        {
            if (!level.isClientSide())
            {
                level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, state.setValue(PERSISTENT, true), Block.UPDATE_ALL_IMMEDIATE);
                itemStack.hurtAndBreak(1, player, playerx -> playerx.broadcastBreakEvent(hand));
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
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
            var blockState = state.setValue(AGE, 1);
            level.setBlock(pos, blockState, Block.UPDATE_CLIENTS);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else
        {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(AGE, PERSISTENT);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return state.getValue(AGE) < 4;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        var doublePlantBlock = (DoublePlantBlock) FOTBlocks.TALL_POMEGRANATE_PLANT;

        if (state.getValue(AGE) == 0 && random.nextInt(10) == 0)
        {
            if (doublePlantBlock.defaultBlockState().canSurvive(level, pos) && level.isEmptyBlock(pos.above()))
            {
                DoublePlantBlock.placeAt(level, doublePlantBlock.defaultBlockState(), pos, Block.UPDATE_CLIENTS);
            }
        }
        else
        {
            var age = Math.min(4, state.getValue(AGE) + 1);
            level.setBlock(pos, state.setValue(AGE, age), Block.UPDATE_CLIENTS);
        }
    }
}