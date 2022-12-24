package com.stevekung.fishofthieves.fabric.datagen.variant;

import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.AncientscaleVariants;
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
        this.tag(FOTTags.FishVariant.DEFAULT_ANCIENTSCALE_SPAWNS).add(AncientscaleVariants.ALMOND, AncientscaleVariants.SAPPHIRE, AncientscaleVariants.SMOKE, AncientscaleVariants.BONE, AncientscaleVariants.STARSHINE);
    }
}