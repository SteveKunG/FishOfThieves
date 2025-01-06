package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class PineappleBlock extends Block implements SimpleWaterloggedBlock
{
    private static final VoxelShape UNDERRIPE_SHAPE = Block.box(5, 0, 5, 11, 8, 11);
    private static final VoxelShape NORMAL_SHAPE = Block.box(4, 0, 4, 12, 10, 12);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final Type type;

    public PineappleBlock(Type type, Properties properties)
    {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        var itemStack = player.getItemInHand(hand);

        if (this.type == Type.RIPE && itemStack.is(Items.SHEARS))
        {
            if (!level.isClientSide())
            {
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                Block.popResource(level, pos, new ItemStack(FOTItems.PINEAPPLE_CROWN));
                itemStack.hurtAndBreak(1, player, playerx -> playerx.broadcastBreakEvent(hand));
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        else
        {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        if (state.getValue(WATERLOGGED))
        {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return this.type == Type.UNDERRIPE ? UNDERRIPE_SHAPE : NORMAL_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
    }

    public enum Type
    {
        UNDERRIPE,
        CROWNLESS,
        RIPE
    }
}