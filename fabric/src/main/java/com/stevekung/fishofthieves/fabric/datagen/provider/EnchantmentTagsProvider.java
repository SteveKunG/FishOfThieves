package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class EnchantmentTagsProvider extends FabricTagsProvider<Enchantment>
{
    public EnchantmentTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, Registries.ENCHANTMENT, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.builder(FOTTags.Enchantments.DROP_PINEAPPLE_BLOCK_WHEN_MINING).add(Enchantments.SILK_TOUCH);
    }
}