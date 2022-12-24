package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.WreckerVariants;
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
        this.tag(FOTTags.FishVariant.DEFAULT_WRECKER_SPAWNS).add(WreckerVariants.ROSE, WreckerVariants.SUN, WreckerVariants.BLACKCLOUD, WreckerVariants.SNOW, WreckerVariants.MOON);
    }
}