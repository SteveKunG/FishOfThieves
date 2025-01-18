package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class CoconutFruitBlock extends HorizontalDirectionalBlock implements BonemealableBlock, Fallable
{
    private static final VoxelShape[] EAST_AABB = new VoxelShape[] {
            Block.box(12, 8, 5, 18, 14, 11),
            Block.box(10, 6, 4, 18, 14, 12),
            Block.box(7, 3, 2.5, 18, 14, 13.5) };
    private static final VoxelShape[] WEST_AABB = new VoxelShape[] {
            Block.box(-2, 8, 5, 4, 14, 11),
            Block.box(-2, 6, 4, 6, 14, 12),
            Block.box(-2, 3, 2.5, 9, 14, 13.5) };
    private static final VoxelShape[] NORTH_AABB = new VoxelShape[] {
            Block.box(5, 8, -2, 11, 14, 4),
            Block.box(4, 6, -2, 12, 14, 6),
            Block.box(2.5, 3, -2, 13.5, 14, 9) };
    private static final VoxelShape[] SOUTH_AABB = new VoxelShape[] {
            Block.box(5, 8, 12, 11, 14, 18),
            Block.box(4, 6, 10, 12, 14, 18),
            Block.box(2.5, 3, 7, 13.5, 14, 18) };

    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

    public CoconutFruitBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(AGE) < 2;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        int age = state.getValue(AGE);

        if (age < 2)
        {
            if (random.nextInt(5) == 0)
            {
                level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight())
        {
            var fallingBlockEntity = FallingBlockEntity.fall(level, pos, state);
            fallingBlockEntity.setHurtsEntities(0.5f * Math.max(1, state.getValue(AGE)), 20);
            fallingBlockEntity.disableDrop();
        }
    }

    @Override
    public DamageSource getFallDamageSource(Entity entity)
    {
        return entity.damageSources().fallingCoconut(entity);
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock)
    {
        var vec3 = fallingBlock.getBoundingBox().getCenter();
        var blockState = fallingBlock.getBlockState();
        level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, BlockPos.containing(vec3), Block.getId(fallingBlock.getBlockState()));
        level.gameEvent(fallingBlock, GameEvent.BLOCK_DESTROY, vec3);
        Block.dropResources(blockState, level, pos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos.relative(state.getValue(FACING)));
        return blockState.is(FOTTags.Blocks.SMALL_COCONUT_LOGS) && blockState.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int i = state.getValue(AGE);
        return switch (state.getValue(FACING))
        {
            case SOUTH -> SOUTH_AABB[i];
            case WEST -> WEST_AABB[i];
            case EAST -> EAST_AABB[i];
            default -> NORTH_AABB[i];
        };
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        if (!state.canSurvive(level, pos))
        {
            if (!FallingBlock.isFree(level.getBlockState(pos.below())))
            {
                return Blocks.AIR.defaultBlockState();
            }
            level.scheduleTick(pos, this, 2);
        }
        return state;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return state.getValue(AGE) < 2;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        level.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), Block.UPDATE_CLIENTS);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, AGE);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(FOTItems.COCONUT);
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile)
    {
        var blockPos = hit.getBlockPos();

        if (!level.isClientSide() && projectile.mayInteract(level, blockPos) && projectile.getType().is(EntityTypeTags.IMPACT_PROJECTILES))
        {
            if (!FallingBlock.isFree(level.getBlockState(blockPos.below())))
            {
                level.destroyBlock(blockPos, true, projectile);
            }
            else
            {
                level.scheduleTick(blockPos, this, 2);
            }
        }
    }
}