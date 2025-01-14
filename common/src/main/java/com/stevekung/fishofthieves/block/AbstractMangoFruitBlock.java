package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;

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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        int age = state.getValue(AGE);

        if (random.nextInt(5) == 0)
        {
            if (age < 2)
            {
                level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
            }
        }
        if (state.getValue(FALLING) && isFree(level.getBlockState(pos.below())) && (age == 1 && random.nextFloat() > 0.9f || age == 2 && random.nextFloat() > 0.75f))
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
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight())
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
        int age = blockState.getValue(AGE);
        level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, BlockPos.containing(vec3), Block.getId(fallingBlock.getBlockState()));
        level.gameEvent(fallingBlock, GameEvent.BLOCK_DESTROY, vec3);

        if (age == 1)
        {
            fallingBlock.spawnAtLocation(FOTItems.RAW_MANGO);
        }
        else if (age == 2)
        {
            fallingBlock.spawnAtLocation(FOTItems.MANGO);
        }
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
}