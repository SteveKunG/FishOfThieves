package com.stevekung.fishofthieves.fabric.datagen.variants;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.WreckerVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class WreckerVariantTagsProvider extends FabricTagProvider<WreckerVariant>
{
    public WreckerVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.WRECKER_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FishVariantTags.DEFAULT_WRECKER_SPAWNS).add(WreckerVariant.ROSE, WreckerVariant.SUN, WreckerVariant.BLACKCLOUD, WreckerVariant.SNOW, WreckerVariant.MOON);
    }
}