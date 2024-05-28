package com.stevekung.fishofthieves.fabric.datagen.variant;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.common.entity.variant.SplashtailVariant;
import com.stevekung.fishofthieves.common.registry.FOTRegistries;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import com.stevekung.fishofthieves.common.registry.variant.SplashtailVariants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class SplashtailVariantTagsProvider extends FabricTagProvider<SplashtailVariant>
{
    public SplashtailVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistries.SPLASHTAIL_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FOTTags.FishVariant.DEFAULT_SPLASHTAIL_SPAWNS).add(SplashtailVariants.RUBY, SplashtailVariants.SUNNY, SplashtailVariants.INDIGO, SplashtailVariants.UMBER, SplashtailVariants.SEAFOAM);
    }
}