package com.stevekung.fishofthieves.mixin.accessor;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

@Mixin(DecoratedPotPatterns.class)
public interface DecoratedPotPatternsAccessor
{
    @Accessor("ITEM_TO_POT_TEXTURE")
    static Map<Item, ResourceKey<String>> getItemToPotTexture()
    {
        throw new AssertionError("Implemented via mixin");
    }
}