package com.stevekung.fishofthieves.fabric.datagen.variant;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.common.entity.variant.StormfishVariant;
import com.stevekung.fishofthieves.common.registry.FOTRegistries;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import com.stevekung.fishofthieves.common.registry.variant.StormfishVariants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class StormfishVariantTagsProvider extends FabricTagProvider<StormfishVariant>
{
    public StormfishVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistries.STORMFISH_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FOTTags.FishVariant.DEFAULT_STORMFISH_SPAWNS).add(StormfishVariants.ANCIENT, StormfishVariants.SHORES, StormfishVariants.WILD, StormfishVariants.SHADOW, StormfishVariants.TWILIGHT);
    }
}