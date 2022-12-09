package com.stevekung.fishofthieves.fabric.datagen.variants;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.variants.BattlegillVariant;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class BattlegillVariantTagsProvider extends FabricTagProvider<BattlegillVariant>
{
    public BattlegillVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistries.BATTLEGILL_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FishVariantTags.DEFAULT_BATTLEGILL_SPAWNS).add(BattlegillVariant.JADE, BattlegillVariant.SKY, BattlegillVariant.RUM, BattlegillVariant.SAND, BattlegillVariant.BITTERSWEET);
    }
}