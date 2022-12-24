package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.PlentifinVariants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class PlentifinVariantTagsProvider extends FabricTagProvider<PlentifinVariant>
{
    public PlentifinVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.PLENTIFIN_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FOTTags.FishVariant.DEFAULT_PLENTIFIN_SPAWNS).add(PlentifinVariants.OLIVE, PlentifinVariants.AMBER, PlentifinVariants.CLOUDY, PlentifinVariants.BONEDUST, PlentifinVariants.WATERY);
    }
}