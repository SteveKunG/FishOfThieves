package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.SplashtailVariants;
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
        this.tag(FOTTags.FishVariant.DEFAULT_SPLASHTAIL_SPAWNS).add(SplashtailVariants.RUBY, SplashtailVariants.SUNNY, SplashtailVariants.INDIGO, SplashtailVariants.UMBER, SplashtailVariants.SEAFOAM);
    }
}