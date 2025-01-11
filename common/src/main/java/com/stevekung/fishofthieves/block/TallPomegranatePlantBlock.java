package com.stevekung.fishofthieves.block;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class TallPomegranatePlantBlock extends DoublePlantBlock implements BonemealableBlock
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape LOWER_SHAPE = Block.box(3, 0, 3, 13, 16, 13);
    private static final VoxelShape UPPER_COLLISION_SHAPE = Block.box(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape UPPER_SHAPE = Shapes.or(UPPER_COLLISION_SHAPE, LOWER_SHAPE);

    public TallPomegranatePlantBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && state.getValue(AGE) <= 2;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_COLLISION_SHAPE : Shapes.empty();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return !isLower(state) ? super.canSurvive(state, level, pos) : this.mayPlaceOn(level.getBlockState(pos.below()), level, pos.below());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(AGE));
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
        var isUnobstructed = UPPER_COLLISION_SHAPE.isEmpty() || this.isUnobstructed(level, blockPos);
        return blockState != null && isUnobstructed ? blockState : null;
    }

    private boolean isUnobstructed(Level level, BlockPos pos)
    {
        return level.isUnobstructed(null, UPPER_COLLISION_SHAPE.move(pos.getX(), pos.getY(), pos.getZ())) && level.isUnobstructed(null, UPPER_COLLISION_SHAPE.move(pos.getX(), pos.getY() + 1, pos.getZ()));
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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        var growthSpeed = CropBlock.getGrowthSpeed(this, level, pos);

        if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0)
        {
            this.grow(level, state, pos);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        int age = state.getValue(AGE);
        var canHarvest = age == 3;

        if (!canHarvest && player.getItemInHand(hand).is(Items.BONE_MEAL))
        {
            return InteractionResult.PASS;
        }
        else if (canHarvest)
        {
            var count = 1 + level.random.nextInt(2);
            popResource(level, pos, new ItemStack(FOTItems.POMEGRANATE, count + 1));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
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
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else
        {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    private void grow(ServerLevel level, BlockState state, BlockPos pos)
    {
        var i = Math.min(state.getValue(AGE) + 1, 3);

        if (this.canGrow(level, pos, state))
        {
            level.setBlock(pos, state.setValue(AGE, i), Block.UPDATE_CLIENTS);
            level.setBlock(pos.above(), copyWaterloggedFrom(level, pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, i)), Block.UPDATE_ALL);
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

    @Nullable
    private TallPomegranatePlantBlock.PosAndState getLowerHalf(LevelReader level, BlockPos pos, BlockState state)
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
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
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

    record PosAndState(BlockPos pos, BlockState state)
    {}
}