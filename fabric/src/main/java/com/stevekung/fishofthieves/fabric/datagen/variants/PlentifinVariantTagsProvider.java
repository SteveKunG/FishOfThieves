package com.stevekung.fishofthieves.fabric.datagen.variants;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.PlentifinVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class PlentifinVariantTagsProvider extends FabricTagProvider<PlentifinVariant>
{
    public PlentifinVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistry.PLENTIFIN_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FishVariantTags.DEFAULT_PLENTIFIN_SPAWNS).add(PlentifinVariant.OLIVE, PlentifinVariant.AMBER, PlentifinVariant.CLOUDY, PlentifinVariant.BONEDUST, PlentifinVariant.WATERY);
    }
}