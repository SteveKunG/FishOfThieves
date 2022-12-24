package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.WildsplashVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.WildsplashVariants;
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
        this.tag(FOTTags.FishVariant.DEFAULT_WILDSPLASH_SPAWNS).add(WildsplashVariants.RUSSET, WildsplashVariants.SANDY, WildsplashVariants.OCEAN, WildsplashVariants.MUDDY, WildsplashVariants.CORAL);
    }
}