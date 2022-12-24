package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.IslehopperVariants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class IslehopperVariantTagsProvider extends FabricTagProvider<IslehopperVariant>
{
    public IslehopperVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.ISLEHOPPER_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FOTTags.FishVariant.DEFAULT_ISLEHOPPER_SPAWNS).add(IslehopperVariants.STONE, IslehopperVariants.MOSS, IslehopperVariants.HONEY, IslehopperVariants.RAVEN, IslehopperVariants.AMETHYST);
    }
}