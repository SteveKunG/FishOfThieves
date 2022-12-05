package com.stevekung.fishofthieves.fabric.datagen.variants;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.SplashtailVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class SplashtailVariantTagsProvider extends FabricTagProvider<SplashtailVariant>
{
    public SplashtailVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistry.SPLASHTAIL_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FishVariantTags.DEFAULT_SPLASHTAIL_SPAWNS).add(SplashtailVariant.RUBY, SplashtailVariant.SUNNY, SplashtailVariant.INDIGO, SplashtailVariant.UMBER, SplashtailVariant.SEAFOAM);
    }
}