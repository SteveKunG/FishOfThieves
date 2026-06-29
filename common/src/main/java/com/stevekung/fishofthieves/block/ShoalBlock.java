package com.stevekung.fishofthieves.block;

import java.util.Optional;

import org.jspecify.annotations.Nullable;

import com.stevekung.fishofthieves.entity.shoal.Shoal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShoalBlock extends Block implements BucketPickup
{
    public static final BooleanProperty TREASURED = BooleanProperty.create("treasured");

    public ShoalBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TREASURED, false));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE, x + 0.5, y, z + 0.5, 0.0, 0.04, 0.0);
        level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE, x + random.nextFloat(), y + random.nextFloat(), z + random.nextFloat(), 0.0, 0.04, 0.0);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        super.tick(state, level, pos, random);
        this.destroyShoal(state, level, pos, level.getEntitiesOfClass(Shoal.class, new AABB(pos).inflate(1)).isEmpty());
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource)
    {
        scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        if (!state.canSurvive(level, currentPos))
        {
            scheduledTickAccess.scheduleTick(currentPos, this, 5);
        }
        return super.updateShape(state, level, scheduledTickAccess, currentPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston)
    {
        super.neighborChanged(state, level, pos, neighborBlock, orientation, movedByPiston);

        if (!state.canSurvive(level, pos) || movedByPiston)
        {
            this.destroyShoal(state, level, pos, false);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return canSurvive(level, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(TREASURED);
    }

    @Override
    public ItemStack pickupBlock(@Nullable LivingEntity livingEntity, LevelAccessor level, BlockPos pos, BlockState state)
    {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    public Optional<SoundEvent> getPickupSound()
    {
        return Fluids.WATER.getPickupSound();
    }

    public static boolean canSurvive(LevelReader level, BlockPos pos)
    {
        for (var blockPos : BlockPos.betweenClosed(pos.offset(1, -2, 1), pos.offset(-1, -1, -1)))
        {
            var blockState = level.getBlockState(blockPos);
            var fluidState = blockState.getFluidState();

            if (!fluidState.is(FluidTags.WATER) || !fluidState.isSource() || !blockState.getCollisionShape(level, blockPos).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    private void destroyShoal(BlockState state, Level level, BlockPos pos, boolean forceDestroy)
    {
        if (!state.canSurvive(level, pos) || forceDestroy)
        {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}