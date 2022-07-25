package com.stevekung.fishofthieves.fabric.datagen.variants;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.SplashtailVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class SplashtailVariantTagsProvider extends FabricTagProvider<SplashtailVariant>
{
    public SplashtailVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.SPLASHTAIL_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FishVariantTags.DEFAULT_SPLASHTAIL_SPAWNS).add(SplashtailVariant.RUBY, SplashtailVariant.SUNNY, SplashtailVariant.INDIGO, SplashtailVariant.UMBER, SplashtailVariant.SEAFOAM);
    }
}