package com.stevekung.fishofthieves.block;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        super.tick(state, level, pos, random);
        this.destroyShoal(state, level, pos);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        if (!state.canSurvive(level, pos))
        {
            level.scheduleTick(pos, this, 5);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston)
    {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        if (!state.canSurvive(level, pos) || movedByPiston)
        {
            this.destroyShoal(state, level, pos);
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
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state)
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
        return BlockPos.betweenClosedStream(pos.offset(-1, 0, -1), pos.offset(1, -2, 1)).allMatch(blockPos ->
        {
            var blockState = level.getBlockState(blockPos);
            var fluidState = blockState.getFluidState();
            return fluidState.is(FluidTags.WATER) && fluidState.isSource() && blockState.getCollisionShape(level, blockPos).isEmpty();
        });
    }

    private void destroyShoal(BlockState state, Level level, BlockPos pos)
    {
        if (!state.canSurvive(level, pos))
        {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}