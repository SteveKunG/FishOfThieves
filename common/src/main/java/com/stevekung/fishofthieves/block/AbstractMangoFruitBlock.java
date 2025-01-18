package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;

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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
public class AbstractMangoFruitBlock extends FallingBlock implements BonemealableBlock
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public static final BooleanProperty FALLING = BlockStateProperties.FALLING;

    public AbstractMangoFruitBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(AGE) < 2 || state.getValue(FALLING);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        int age = state.getValue(AGE);

        if (age < 2 && random.nextInt(5) == 0)
        {
            level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
        }
        if (canMangoFall(level.getBlockState(pos.below())) && (age == 1 && random.nextFloat() > 0.9f || age == 2 && random.nextFloat() > 0.75f))
        {
            level.scheduleTick(pos, this, 2);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
    }

    @Override
    protected void falling(FallingBlockEntity entity)
    {
        entity.setHurtsEntities(1.0f, 1);
        entity.disableDrop();
    }

    @Override
    public DamageSource getFallDamageSource(Entity entity)
    {
        return entity.damageSources().fallingMango(entity);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston)
    {
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        if (!state.canSurvive(level, pos))
        {
            if (!canMangoFall(level.getBlockState(pos.below())))
            {
                return Blocks.AIR.defaultBlockState();
            }
            level.scheduleTick(pos, this, 2);
        }
        return state;
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile)
    {
        var blockPos = hit.getBlockPos();

        if (!level.isClientSide() && projectile.mayInteract(level, blockPos) && projectile.getType().is(EntityTypeTags.IMPACT_PROJECTILES))
        {
            if (!canMangoFall(level.getBlockState(blockPos.below())))
            {
                level.destroyBlock(blockPos, true, projectile);
            }
            else
            {
                level.scheduleTick(blockPos, this, 2);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (canMangoFall(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight())
        {
            var fallingBlockEntity = FallingBlockEntity.fall(level, pos, state);
            this.falling(fallingBlockEntity);
        }
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
        level.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), Block.UPDATE_ALL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(AGE, FALLING);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return state.getValue(AGE) == 1 ? new ItemStack(FOTItems.RAW_MANGO) : new ItemStack(FOTItems.MANGO);
    }

    public static boolean canMangoFall(BlockState state)
    {
        return state.is(FOTBlocks.MANGO_FRUIT) || FallingBlock.isFree(state);
    }
}