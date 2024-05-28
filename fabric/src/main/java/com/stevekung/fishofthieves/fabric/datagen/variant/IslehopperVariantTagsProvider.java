package com.stevekung.fishofthieves.fabric.datagen.variant;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.common.entity.variant.IslehopperVariant;
import com.stevekung.fishofthieves.common.registry.FOTRegistries;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import com.stevekung.fishofthieves.common.registry.variant.IslehopperVariants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

public class IslehopperVariantTagsProvider extends FabricTagProvider<IslehopperVariant>
{
    public IslehopperVariantTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, FOTRegistries.ISLEHOPPER_VARIANT_REGISTRY, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FOTTags.FishVariant.DEFAULT_ISLEHOPPER_SPAWNS).add(IslehopperVariants.STONE, IslehopperVariants.MOSS, IslehopperVariants.HONEY, IslehopperVariants.RAVEN, IslehopperVariants.AMETHYST);
    }
}