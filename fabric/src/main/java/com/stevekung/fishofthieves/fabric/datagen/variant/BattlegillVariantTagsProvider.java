package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.BattlegillVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.BattlegillVariants;
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
        this.tag(FOTTags.FishVariant.DEFAULT_BATTLEGILL_SPAWNS).add(BattlegillVariants.JADE, BattlegillVariants.SKY, BattlegillVariants.RUM, BattlegillVariants.SAND, BattlegillVariants.BITTERSWEET);
    }
}