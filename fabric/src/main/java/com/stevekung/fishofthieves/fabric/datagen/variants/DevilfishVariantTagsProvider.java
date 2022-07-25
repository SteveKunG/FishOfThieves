package com.stevekung.fishofthieves.fabric.datagen.variants;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.DevilfishVariant;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class DevilfishVariantTagsProvider extends FabricTagProvider<DevilfishVariant>
{
    public DevilfishVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.DEVILFISH_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FishVariantTags.DEFAULT_DEVILFISH_SPAWNS).add(DevilfishVariant.ASHEN, DevilfishVariant.SEASHELL, DevilfishVariant.LAVA, DevilfishVariant.FORSAKEN, DevilfishVariant.FIRELIGHT);
    }
}