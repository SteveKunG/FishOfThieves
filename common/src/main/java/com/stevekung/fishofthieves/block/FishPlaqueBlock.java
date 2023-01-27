package com.stevekung.fishofthieves.block;

import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.api.block.FishPlaqueRegistry;
import com.stevekung.fishofthieves.api.block.FishPlaqueTagConverter;
import com.stevekung.fishofthieves.blockentity.FishPlaqueBlockEntity;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class FishPlaqueBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
{
    private final Map<Direction, VoxelShape> aabb;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 1, 8);

    public FishPlaqueBlock(BlockBehaviour.Properties properties, Type type)
    {
        super(properties);
        this.aabb = type.aabb;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(ROTATION, 1));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FishPlaqueBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return this.aabb.get(state.getValue(FACING));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        var itemStack = player.getItemInHand(hand);
        var item = itemStack.getItem();
        var blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof FishPlaqueBlockEntity fishPlaque) || itemStack.is(FOTTags.Items.FISH_PLAQUE_BUCKET_BLACKLIST))
        {
            return InteractionResult.PASS;
        }
        else
        {
            if (fishPlaque.hasPlaqueData())
            {
                if (item == Items.WATER_BUCKET)
                {
                    var entity = EntityType.loadEntityRecursive(fishPlaque.getPlaqueData(), level, Function.identity());

                    if (entity instanceof Bucketable bucketable)
                    {
                        var itemStack2 = bucketable.getBucketItemStack();
                        bucketable.saveToBucketTag(itemStack2);
                        level.playSound(player, pos, bucketable.getPickupSound(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        var itemStack3 = ItemUtils.createFilledResult(itemStack, player, itemStack2, false);
                        player.setItemInHand(hand, itemStack3);
                        fishPlaque.clearDisplayEntity();
                        blockEntity.setChanged();
                        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    }
                }
                else
                {
                    level.playSound(player, pos, FOTSoundEvents.FISH_PLAQUE_ROTATE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    level.setBlock(pos, state.cycle(ROTATION), Block.UPDATE_CLIENTS);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }

            if (item instanceof MobBucketItem bucket)
            {
                var tag = itemStack.copy().getOrCreateTag();
                var entityType = FOTPlatform.getMobInBucketItem(bucket);
                var converter = FishPlaqueRegistry.getTagConverter(entityType);
                tag.putString("id", Registry.ENTITY_TYPE.getKey(entityType).toString());

                if (converter != FishPlaqueTagConverter.NOOP)
                {
                    converter.convert(tag);
                }

                level.playSound(player, pos, FOTPlatform.getEmptySoundInBucketItem(bucket), SoundSource.NEUTRAL, 1.0F, 1.0F);
                fishPlaque.setPlaqueData(tag);
                blockEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

                if (!player.getAbilities().instabuild)
                {
                    player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
                }
                if (!level.isClientSide)
                {
                    player.awardStat(Stats.ITEM_USED.get(item));
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var blockState = this.defaultBlockState();
        var fluidState = context.getLevel().getFluidState(context.getClickedPos());
        var levelReader = context.getLevel();
        var blockPos = context.getClickedPos();
        var directions = context.getNearestLookingDirections();

        for (var direction : directions)
        {
            if (direction.getAxis().isHorizontal())
            {
                var direction2 = direction.getOpposite();
                blockState = blockState.setValue(FACING, direction2);

                if (blockState.canSurvive(levelReader, blockPos))
                {
                    return blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
                }
            }
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var direction = state.getValue(FACING);
        var blockPos = pos.relative(direction.getOpposite());
        var blockState = level.getBlockState(blockPos);
        return blockState.isFaceSturdy(level, blockPos, direction);
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience)
    {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        this.spawnFish(state, level, pos, level.getBlockEntity(pos));
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool)
    {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);

        if (!level.isClientSide)
        {
            this.spawnFish(state, level, pos, blockEntity);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos)
    {
        if (state.getValue(WATERLOGGED))
        {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPossibleToRespawnInThis()
    {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, WATERLOGGED, ROTATION);
    }

    private void spawnFish(BlockState state, Level level, BlockPos pos, BlockEntity blockEntity)
    {
        if (blockEntity instanceof FishPlaqueBlockEntity fishPlaque && fishPlaque.hasPlaqueData())
        {
            var entity = EntityType.loadEntityRecursive(fishPlaque.getPlaqueData(), level, Function.identity());

            if (!state.getValue(WATERLOGGED))
            {
                entity.setAirSupply(100);
            }

            entity.moveTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state.getValue(FACING).toYRot(), 0.0f);
            entity.setDeltaMovement(level.random.nextDouble() * 0.2, 0.4, level.random.nextDouble() * 0.2);
            level.addFreshEntity(entity);
        }
    }

    public enum Type
    {
        WOODEN(Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0, 4, 14.0, 16.0, 12, 16.0), Direction.SOUTH, Block.box(0.0, 4, 0.0, 16.0, 12, 2.0), Direction.EAST, Block.box(0.0, 4, 0.0, 2.0, 12, 16.0), Direction.WEST, Block.box(14.0, 4, 0.0, 16.0, 12, 16.0)))),
        IRON(Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0, 3, 13, 16, 13, 16), Direction.SOUTH, Block.box(0, 3, 0, 16, 13, 3), Direction.EAST, Block.box(0, 3, 0, 3, 13, 16), Direction.WEST, Block.box(13, 3, 0, 16, 13, 16)))),
        GOLDEN(Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0, 3, 13, 16, 13, 16), Direction.SOUTH, Block.box(0, 3, 0, 16, 13, 3), Direction.EAST, Block.box(0, 3, 0, 3, 13, 16), Direction.WEST, Block.box(13, 3, 0, 16, 13, 16)))),
        GILDED(Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Shapes.join(Block.box(0, 3, 13, 16, 13, 16), Block.box(1, 13, 14, 15, 15, 16), BooleanOp.OR), Direction.SOUTH, Shapes.join(Block.box(0, 3, 0, 16, 13, 3), Block.box(1, 13, 0, 15, 15, 2), BooleanOp.OR), Direction.EAST, Shapes.join(Block.box(0, 3, 0, 3, 13, 16), Block.box(0, 13, 1, 2, 15, 15), BooleanOp.OR), Direction.WEST, Shapes.join(Block.box(13, 3, 0, 16, 13, 16), Block.box(14, 13, 1, 16, 15, 15), BooleanOp.OR))));

        private final Map<Direction, VoxelShape> aabb;

        Type(Map<Direction, VoxelShape> aabb)
        {
            this.aabb = aabb;
        }
    }
}