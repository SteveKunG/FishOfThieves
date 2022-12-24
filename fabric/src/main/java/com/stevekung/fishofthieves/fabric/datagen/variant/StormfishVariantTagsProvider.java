package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.StormfishVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.StormfishVariants;
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
        this.tag(FOTTags.FishVariant.DEFAULT_STORMFISH_SPAWNS).add(StormfishVariants.ANCIENT, StormfishVariants.SHORES, StormfishVariants.WILD, StormfishVariants.SHADOW, StormfishVariants.TWILIGHT);
    }
}