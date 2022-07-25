package com.stevekung.fishofthieves.fabric.datagen;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.IslehopperVariant;
import com.stevekung.fishofthieves.registry.variants.PondieVariant;
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
        this.tag(FishVariantTags.DEFAULT_ISLEHOPPER_SPAWNS).add(IslehopperVariant.STONE, IslehopperVariant.MOSS, IslehopperVariant.HONEY, IslehopperVariant.RAVEN, IslehopperVariant.AMETHYST);
    }
}