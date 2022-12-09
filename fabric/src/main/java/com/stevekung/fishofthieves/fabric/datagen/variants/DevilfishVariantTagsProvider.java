package com.stevekung.fishofthieves.fabric.datagen.variants;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.variants.DevilfishVariant;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
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
        this.getOrCreateTagBuilder(FishVariantTags.DEFAULT_DEVILFISH_SPAWNS).add(DevilfishVariant.ASHEN, DevilfishVariant.SEASHELL, DevilfishVariant.LAVA, DevilfishVariant.FORSAKEN, DevilfishVariant.FIRELIGHT);
    }
}