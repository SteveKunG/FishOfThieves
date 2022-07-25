package com.stevekung.fishofthieves.fabric.datagen;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.PondieVariant;
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
        this.tag(FishVariantTags.DEFAULT_PONDIE_SPAWNS).add(PondieVariant.CHARCOAL, PondieVariant.ORCHID, PondieVariant.BRONZE, PondieVariant.BRIGHT, PondieVariant.MOONSKY);
    }
}