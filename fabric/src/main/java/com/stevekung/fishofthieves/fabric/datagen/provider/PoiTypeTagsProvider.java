package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTPoiTypes;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PoiTypeTagsProvider extends FabricTagsProvider<PoiType>
{
    public PoiTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(output, Registries.POINT_OF_INTEREST_TYPE, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.builder(FOTTags.PoiTypes.SHOAL).add(FOTPoiTypes.NATURAL_SHOAL).add(FOTPoiTypes.TREASURED_SHOAL);
    }
}