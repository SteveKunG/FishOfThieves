package com.stevekung.fishofthieves.fabric.datagen.variant;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.common.entity.variant.PondieVariant;
import com.stevekung.fishofthieves.common.registry.FOTRegistries;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import com.stevekung.fishofthieves.common.registry.variant.PondieVariants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class PondieVariantTagsProvider extends FabricTagProvider<PondieVariant>
{
    public PondieVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistries.PONDIE_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FOTTags.FishVariant.DEFAULT_PONDIE_SPAWNS).add(PondieVariants.CHARCOAL, PondieVariants.ORCHID, PondieVariants.BRONZE, PondieVariants.BRIGHT, PondieVariants.MOONSKY);
    }
}