package com.stevekung.fishofthieves.fabric.datagen.variants;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.WreckerVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class WreckerVariantTagsProvider extends FabricTagProvider<WreckerVariant>
{
    public WreckerVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistry.WRECKER_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FishVariantTags.DEFAULT_WRECKER_SPAWNS).add(WreckerVariant.ROSE, WreckerVariant.SUN, WreckerVariant.BLACKCLOUD, WreckerVariant.SNOW, WreckerVariant.MOON);
    }
}