package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.PondieVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.PondieVariants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class PondieVariantTagsProvider extends FabricTagProvider<PondieVariant>
{
    public PondieVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.PONDIE_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FOTTags.FishVariant.DEFAULT_PONDIE_SPAWNS).add(PondieVariants.CHARCOAL, PondieVariants.ORCHID, PondieVariants.BRONZE, PondieVariants.BRIGHT, PondieVariants.MOONSKY);
    }
}