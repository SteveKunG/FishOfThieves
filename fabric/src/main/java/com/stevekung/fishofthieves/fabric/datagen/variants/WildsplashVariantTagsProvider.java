package com.stevekung.fishofthieves.fabric.datagen.variants;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.WildsplashVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class WildsplashVariantTagsProvider extends FabricTagProvider<WildsplashVariant>
{
    public WildsplashVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.WILDSPLASH_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FishVariantTags.DEFAULT_WILDSPLASH_SPAWNS).add(WildsplashVariant.RUSSET, WildsplashVariant.SANDY, WildsplashVariant.OCEAN, WildsplashVariant.MUDDY, WildsplashVariant.CORAL);
    }
}