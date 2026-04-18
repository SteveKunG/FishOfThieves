package com.stevekung.fishofthieves.mixin.level.block.entity;

import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.registry.FOTDecoratedPotPatterns;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

@Mixin(DecoratedPotPatterns.class)
public class MixinDecoratedPotPatterns
{
    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void fishofthieves$bootstrapEarly(Registry<DecoratedPotPattern> registry, CallbackInfoReturnable<DecoratedPotPattern> info)
    {
        FOTDecoratedPotPatterns.init();
    }

    @Inject(method = "itemToPatternMappings", at = @At("TAIL"))
    private static void fishofthieves$itemToPatternMappings(BiConsumer<ResourceKey<Item>, ResourceKey<DecoratedPotPattern>> itemToPattern, CallbackInfo info)
    {
        FOTDecoratedPotPatterns.putItemsToPotTexture(itemToPattern);
    }
}