package com.stevekung.fishofthieves.fabric.datagen.variant;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.common.entity.variant.PlentifinVariant;
import com.stevekung.fishofthieves.common.registry.FOTRegistries;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import com.stevekung.fishofthieves.common.registry.variant.PlentifinVariants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class PlentifinVariantTagsProvider extends FabricTagProvider<PlentifinVariant>
{
    public PlentifinVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistries.PLENTIFIN_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FOTTags.FishVariant.DEFAULT_PLENTIFIN_SPAWNS).add(PlentifinVariants.OLIVE, PlentifinVariants.AMBER, PlentifinVariants.CLOUDY, PlentifinVariants.BONEDUST, PlentifinVariants.WATERY);
    }
}