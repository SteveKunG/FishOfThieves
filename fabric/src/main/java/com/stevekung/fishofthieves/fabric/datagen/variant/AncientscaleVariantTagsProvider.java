package com.stevekung.fishofthieves.fabric.datagen.variant;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.variant.AncientscaleVariants;
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
        this.getOrCreateTagBuilder(FOTTags.FishVariant.DEFAULT_ANCIENTSCALE_SPAWNS).add(AncientscaleVariants.ALMOND, AncientscaleVariants.SAPPHIRE, AncientscaleVariants.SMOKE, AncientscaleVariants.BONE, AncientscaleVariants.STARSHINE);
    }
}