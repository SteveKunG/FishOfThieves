package com.stevekung.fishofthieves.forge.mixin;

import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(AxeItem.class)
public class MixinAxeItem
{
    @Shadow
    @Mutable
    @Final
    static Map<Block, Block> STRIPPABLES;

    @Unique
    private static final Map<Block, Block> CUSTOM_STRIPPABLES = new ImmutableMap.Builder<Block, Block>()
            .put(FOTBlocks.MEDIUM_COCONUT_LOG, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG)
            .put(FOTBlocks.MEDIUM_COCONUT_WOOD, FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD)
            .put(FOTBlocks.SMALL_COCONUT_WOOD, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD)
            .put(FOTBlocks.SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG)
            .put(FOTBlocks.GROWABLE_SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG)
            .put(FOTBlocks.TOP_SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD)
            .build();

    @ModifyVariable(method = "useOn", at = @At(value = "INVOKE", target = "java/util/Optional.isPresent()Z", ordinal = 0),
            slice = @Slice(from = @At(value = "INVOKE", target = "net/minecraft/world/item/context/UseOnContext.getItemInHand()Lnet/minecraft/world/item/ItemStack;")),
            index = 10, ordinal = 3)
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
        return optional;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void fishofthieves$putStrippableLogs(CallbackInfo info)
    {
        var newMap = Maps.newHashMap(STRIPPABLES);

        newMap.put(FOTBlocks.COCONUT_LOG, FOTBlocks.STRIPPED_COCONUT_LOG);
        newMap.put(FOTBlocks.COCONUT_WOOD, FOTBlocks.STRIPPED_COCONUT_WOOD);

        STRIPPABLES = newMap;
    }
}