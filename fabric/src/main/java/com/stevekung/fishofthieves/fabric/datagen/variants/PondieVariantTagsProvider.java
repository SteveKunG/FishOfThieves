package com.stevekung.fishofthieves.fabric.datagen.variants;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.PondieVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class PondieVariantTagsProvider extends FabricTagProvider<PondieVariant>
{
    public PondieVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistry.PONDIE_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FishVariantTags.DEFAULT_PONDIE_SPAWNS).add(PondieVariant.CHARCOAL, PondieVariant.ORCHID, PondieVariant.BRONZE, PondieVariant.BRIGHT, PondieVariant.MOONSKY);
    }
}