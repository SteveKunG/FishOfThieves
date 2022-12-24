package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.DevilfishVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.DevilfishVariants;
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
        this.tag(FOTTags.FishVariant.DEFAULT_DEVILFISH_SPAWNS).add(DevilfishVariants.ASHEN, DevilfishVariants.SEASHELL, DevilfishVariants.LAVA, DevilfishVariants.FORSAKEN, DevilfishVariants.FIRELIGHT);
    }
}