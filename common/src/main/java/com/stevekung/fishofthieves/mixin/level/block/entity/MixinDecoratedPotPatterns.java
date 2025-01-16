package com.stevekung.fishofthieves.mixin.level.block.entity;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Maps;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

@Mixin(DecoratedPotPatterns.class)
public class MixinDecoratedPotPatterns
{
    @Shadow
    @Final
    @Mutable
    static Map<Item, ResourceKey<String>> ITEM_TO_POT_TEXTURE;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void fishofthieves$reinitNewSherds(CallbackInfo info)
    {
        ITEM_TO_POT_TEXTURE = Maps.newHashMap(ITEM_TO_POT_TEXTURE);
    }
}