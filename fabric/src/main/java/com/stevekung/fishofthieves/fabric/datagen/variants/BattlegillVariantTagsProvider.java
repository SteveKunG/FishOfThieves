package com.stevekung.fishofthieves.fabric.datagen.variants;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.BattlegillVariant;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class BattlegillVariantTagsProvider extends FabricTagProvider<BattlegillVariant>
{
    public BattlegillVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.BATTLEGILL_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FishVariantTags.DEFAULT_BATTLEGILL_SPAWNS).add(BattlegillVariant.JADE, BattlegillVariant.SKY, BattlegillVariant.RUM, BattlegillVariant.SAND, BattlegillVariant.BITTERSWEET);
    }
}