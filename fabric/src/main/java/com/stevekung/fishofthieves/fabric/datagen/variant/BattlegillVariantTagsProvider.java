package com.stevekung.fishofthieves.fabric.datagen.variant;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.entity.variant.BattlegillVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.BattlegillVariants;
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
        this.getOrCreateTagBuilder(FOTTags.FishVariant.DEFAULT_BATTLEGILL_SPAWNS).add(BattlegillVariants.JADE, BattlegillVariants.SKY, BattlegillVariants.RUM, BattlegillVariants.SAND, BattlegillVariants.BITTERSWEET);
    }
}