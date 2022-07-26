package com.stevekung.fishofthieves.fabric.datagen.variants;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.PlentifinVariant;
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
        this.tag(FishVariantTags.DEFAULT_PLENTIFIN_SPAWNS).add(PlentifinVariant.OLIVE, PlentifinVariant.AMBER, PlentifinVariant.CLOUDY, PlentifinVariant.BONEDUST, PlentifinVariant.WATERY);
    }
}