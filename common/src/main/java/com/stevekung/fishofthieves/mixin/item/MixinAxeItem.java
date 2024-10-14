package com.stevekung.fishofthieves.mixin.item;

import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.block.CoconutGrowableLogBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@Mixin(AxeItem.class)
public class MixinAxeItem
{
    @Unique
    private static final Map<Block, Block> CUSTOM_STRIPPABLES = new ImmutableMap.Builder<Block, Block>()
            .put(FOTBlocks.MEDIUM_COCONUT_LOG, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG)
            .put(FOTBlocks.MEDIUM_COCONUT_WOOD, FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD)
            .put(FOTBlocks.SMALL_COCONUT_WOOD, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD)
            .build();

    @ModifyVariable(method = "useOn", at = @At(value = "INVOKE", target = "java/util/Optional.isPresent()Z", ordinal = 0), index = 10, ordinal = 3)
    private Optional<BlockState> fishofthieves$stripNonFullCoconutLog(Optional<BlockState> optional, UseOnContext context)
    {
        var level = context.getLevel();
        var blockPos = context.getClickedPos();
        var blockState = level.getBlockState(blockPos);
        var block = blockState.getBlock();

        if (CUSTOM_STRIPPABLES.containsKey(block))
        {
            level.playSound(null, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
            return Optional.of(CUSTOM_STRIPPABLES.get(block).withPropertiesOf(blockState));
        }
        else if (blockState.is(FOTBlocks.SMALL_COCONUT_LOG))
        {
            var blockState1 = FOTBlocks.STRIPPED_SMALL_COCONUT_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS)).setValue(BlockStateProperties.WATERLOGGED, blockState.getValue(BlockStateProperties.WATERLOGGED));
            level.playSound(null, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);

            if (blockState.getValue(CoconutGrowableLogBlock.TOP))
            {
                blockState1 = FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD.withPropertiesOf(blockState1);
            }
            return Optional.of(blockState1);
        }
        return optional;
    }
}