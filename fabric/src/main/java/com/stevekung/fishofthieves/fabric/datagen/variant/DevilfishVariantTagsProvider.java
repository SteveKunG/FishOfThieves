package com.stevekung.fishofthieves.fabric.datagen.variant;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.entity.variant.DevilfishVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.DevilfishVariants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class DevilfishVariantTagsProvider extends FabricTagProvider<DevilfishVariant>
{
    public DevilfishVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistries.DEVILFISH_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FOTTags.FishVariant.DEFAULT_DEVILFISH_SPAWNS).add(DevilfishVariants.ASHEN, DevilfishVariants.SEASHELL, DevilfishVariants.LAVA, DevilfishVariants.FORSAKEN, DevilfishVariants.FIRELIGHT);
    }
}