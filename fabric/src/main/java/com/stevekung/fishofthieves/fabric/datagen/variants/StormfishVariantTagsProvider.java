package com.stevekung.fishofthieves.fabric.datagen.variants;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.StormfishVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class StormfishVariantTagsProvider extends FabricTagProvider<StormfishVariant>
{
    public StormfishVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.STORMFISH_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FishVariantTags.DEFAULT_STORMFISH_SPAWNS).add(StormfishVariant.ANCIENT, StormfishVariant.SHORES, StormfishVariant.WILD, StormfishVariant.SHADOW, StormfishVariant.TWILIGHT);
    }
}