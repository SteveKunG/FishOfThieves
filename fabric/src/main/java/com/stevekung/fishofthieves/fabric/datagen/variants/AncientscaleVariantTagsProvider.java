package com.stevekung.fishofthieves.fabric.datagen.variants;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class AncientscaleVariantTagsProvider extends FabricTagProvider<AncientscaleVariant>
{
    public AncientscaleVariantTagsProvider(FabricDataGenerator dataGenerator)
    {
        super(dataGenerator, FOTRegistry.ANCIENTSCALE_VARIANT);
    }

    @Override
    protected void generateTags()
    {
        this.tag(FishVariantTags.DEFAULT_ANCIENTSCALE_SPAWNS).add(AncientscaleVariant.ALMOND, AncientscaleVariant.SAPPHIRE, AncientscaleVariant.SMOKE, AncientscaleVariant.BONE, AncientscaleVariant.STARSHINE);
    }
}