package com.stevekung.fishofthieves.block;

import java.util.Locale;
import java.util.Map;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.blockentity.FishPlaqueBlockEntity;
import com.stevekung.fishofthieves.entity.BucketableEntityType;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
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
    public static final MapCodec<FishPlaqueBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(propertiesCodec(), Type.CODEC.fieldOf("type").forGetter(FishPlaqueBlock::getType)).apply(instance, FishPlaqueBlock::new));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 1, 8);
    private final Type type;

    public FishPlaqueBlock(BlockBehaviour.Properties properties, Type type)
    {
        super(properties);
        this.type = type;
        this.aabb = type.aabb;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(ROTATION, 1));
    }

    public Type getType()
    {
        return this.type;
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
    public ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        var item = itemStack.getItem();
        var blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof FishPlaqueBlockEntity fishPlaque) || itemStack.is(FOTTags.Items.FISH_PLAQUE_BUCKET_BLACKLIST))
        {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        else
        {
            if (fishPlaque.hasPlaqueData())
            {
                var entity = FishPlaqueBlockEntity.createEntity(fishPlaque, level);
                var interactionOptional = level.registryAccess().registryOrThrow(FOTRegistries.FISH_PLAQUE_INTERACTION).holders().filter(holder -> fishPlaque.getEntityKeyFromPlaqueData().equals(holder.value().entityType().toString())).findFirst();

                if (itemStack.is(interactionOptional.map(holder -> BuiltInRegistries.ITEM.get(holder.value().item())).orElse(Items.WATER_BUCKET)))
                {
                    if (entity instanceof Bucketable bucketable)
                    {
                        var itemStack2 = bucketable.getBucketItemStack();
                        bucketable.saveToBucketTag(itemStack2);
                        level.playSound(player, pos, bucketable.getPickupSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                        var itemStack3 = ItemUtils.createFilledResult(itemStack, player, itemStack2, false);
                        player.setItemInHand(hand, itemStack3);
                        fishPlaque.clearDisplayEntity();
                        blockEntity.setChanged();
                        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                        return ItemInteractionResult.sidedSuccess(level.isClientSide());
                    }
                }
                else
                {
                    if (fishPlaque.isWaxed())
                    {
                        if (item instanceof AxeItem)
                        {
                            fishPlaque.setWaxed(false);
                            blockEntity.setChanged();

                            if (player instanceof ServerPlayer serverPlayer)
                            {
                                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, itemStack);
                            }

                            level.playSound(player, pos, FOTSoundEvents.FISH_PLAQUE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                            level.levelEvent(player, 3004, pos, 0);
                            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                            itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                            return ItemInteractionResult.sidedSuccess(level.isClientSide());
                        }
                    }
                    else
                    {
                        if (item == Items.HONEYCOMB)
                        {
                            fishPlaque.setWaxed(true);
                            blockEntity.setChanged();

                            if (player instanceof ServerPlayer serverPlayer)
                            {
                                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, itemStack);
                            }

                            if (!player.getAbilities().instabuild)
                            {
                                itemStack.shrink(1);
                            }
                            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                            level.levelEvent(player, 3003, pos, 0);
                        }
                        level.playSound(player, pos, FOTSoundEvents.FISH_PLAQUE_ROTATE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                        level.setBlock(pos, cycleRotation(state, player.isSecondaryUseActive()), Block.UPDATE_ALL);
                        return ItemInteractionResult.sidedSuccess(level.isClientSide());
                    }
                }
            }
            else
            {
                if (item instanceof MobBucketItem bucket)
                {
                    var tag = new CompoundTag();
                    var entityType = FOTPlatform.getMobInBucketItem(bucket);
                    var entityKey = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
                    var interactionOptional = level.registryAccess().registryOrThrow(FOTRegistries.FISH_PLAQUE_INTERACTION).holders().filter(holder -> BuiltInRegistries.ENTITY_TYPE.getKey(entityType).equals(holder.value().entityType())).findFirst();
                    tag.putString("id", entityKey);

                    if (level instanceof ServerLevel serverLevel)
                    {
                        var entityToSave = ((BucketableEntityType<?>) entityType).spawnByBucket(serverLevel, itemStack, player, MobSpawnType.BUCKET);
                        entityToSave.saveWithoutId(tag);
                        tag.remove(Entity.UUID_TAG); // remove UUID from an entity to allow them spawns in the world
                    }

                    var bucketData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
                    tag.merge(bucketData.copyTag());

                    level.playSound(player, pos, FOTPlatform.getEmptySoundInBucketItem(bucket), SoundSource.BLOCKS, 1.0F, 1.0F);
                    fishPlaque.setPlaqueData(tag);
                    blockEntity.setChanged();
                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

                    if (!player.getAbilities().instabuild)
                    {
                        player.setItemInHand(hand, new ItemStack(interactionOptional.map(holder -> BuiltInRegistries.ITEM.get(holder.value().item())).orElse(Items.WATER_BUCKET)));
                    }
                    if (!level.isClientSide())
                    {
                        player.awardStat(Stats.ITEM_USED.get(item));
                    }
                    return ItemInteractionResult.sidedSuccess(level.isClientSide());
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
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
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!state.is(newState.getBlock()))
        {
            var blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof FishPlaqueBlockEntity)
            {
                level.updateNeighbourForOutputSignal(pos, state.getBlock());
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
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

        if (!level.isClientSide())
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
    public boolean isPossibleToRespawnInThis(BlockState blockState)
    {
        return true;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
    {
        var blockEntity = level.getBlockEntity(pos);

        if (blockEntity instanceof FishPlaqueBlockEntity fishPlaque && fishPlaque.hasPlaqueData())
        {
            return state.getValue(FishPlaqueBlock.ROTATION);
        }
        return 0;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
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
            var entity = FishPlaqueBlockEntity.createEntity(fishPlaque, level);
            var direction = state.getValue(FACING);
            var random = level.random.nextDouble() * 0.1 + 0.2;

            if (!state.getValue(WATERLOGGED))
            {
                entity.setAirSupply(100);
            }

            entity.moveTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, direction.toYRot(), 0.0f);
            entity.setDeltaMovement(level.random.triangle(direction.getStepX() * random, 0.0172275), 0.4, level.random.triangle(direction.getStepZ() * random, 0.0172275));
            level.addFreshEntity(entity);
        }
    }

    private static BlockState cycleRotation(BlockState state, boolean backwards)
    {
        return state.setValue(FishPlaqueBlock.ROTATION, getRelative(FishPlaqueBlock.ROTATION.getPossibleValues(), state.getValue(FishPlaqueBlock.ROTATION), backwards));
    }

    private static <T> T getRelative(Iterable<T> allowedValues, @Nullable T currentValue, boolean backwards)
    {
        return backwards ? Util.findPreviousInIterable(allowedValues, currentValue) : Util.findNextInIterable(allowedValues, currentValue);
    }

    public enum Type implements StringRepresentable
    {
        WOODEN(Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0, 4, 14.0, 16.0, 12, 16.0), Direction.SOUTH, Block.box(0.0, 4, 0.0, 16.0, 12, 2.0), Direction.EAST, Block.box(0.0, 4, 0.0, 2.0, 12, 16.0), Direction.WEST, Block.box(14.0, 4, 0.0, 16.0, 12, 16.0)))),
        IRON(Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0, 3, 13, 16, 13, 16), Direction.SOUTH, Block.box(0, 3, 0, 16, 13, 3), Direction.EAST, Block.box(0, 3, 0, 3, 13, 16), Direction.WEST, Block.box(13, 3, 0, 16, 13, 16)))),
        GOLDEN(Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0, 3, 13, 16, 13, 16), Direction.SOUTH, Block.box(0, 3, 0, 16, 13, 3), Direction.EAST, Block.box(0, 3, 0, 3, 13, 16), Direction.WEST, Block.box(13, 3, 0, 16, 13, 16)))),
        GILDED(Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Shapes.join(Block.box(0, 3, 13, 16, 13, 16), Block.box(1, 13, 14, 15, 15, 16), BooleanOp.OR), Direction.SOUTH, Shapes.join(Block.box(0, 3, 0, 16, 13, 3), Block.box(1, 13, 0, 15, 15, 2), BooleanOp.OR), Direction.EAST, Shapes.join(Block.box(0, 3, 0, 3, 13, 16), Block.box(0, 13, 1, 2, 15, 15), BooleanOp.OR), Direction.WEST, Shapes.join(Block.box(13, 3, 0, 16, 13, 16), Block.box(14, 13, 1, 16, 15, 15), BooleanOp.OR))));

        private final Map<Direction, VoxelShape> aabb;
        public static final StringRepresentable.EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        Type(Map<Direction, VoxelShape> aabb)
        {
            this.aabb = aabb;
        }

        @Override
        public String getSerializedName()
        {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}