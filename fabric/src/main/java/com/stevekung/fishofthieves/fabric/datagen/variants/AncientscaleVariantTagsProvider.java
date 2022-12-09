package com.stevekung.fishofthieves.fabric.datagen.variants;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.variants.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class AncientscaleVariantTagsProvider extends FabricTagProvider<AncientscaleVariant>
{
    public AncientscaleVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistries.ANCIENTSCALE_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FishVariantTags.DEFAULT_ANCIENTSCALE_SPAWNS).add(AncientscaleVariant.ALMOND, AncientscaleVariant.SAPPHIRE, AncientscaleVariant.SMOKE, AncientscaleVariant.BONE, AncientscaleVariant.STARSHINE);
    }
}