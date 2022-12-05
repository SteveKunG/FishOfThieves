package com.stevekung.fishofthieves.fabric.datagen.variants;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.WildsplashVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class WildsplashVariantTagsProvider extends FabricTagProvider<WildsplashVariant>
{
    public WildsplashVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistry.WILDSPLASH_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FishVariantTags.DEFAULT_WILDSPLASH_SPAWNS).add(WildsplashVariant.RUSSET, WildsplashVariant.SANDY, WildsplashVariant.OCEAN, WildsplashVariant.MUDDY, WildsplashVariant.CORAL);
    }
}