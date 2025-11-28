package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTPoiTypes;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PoiTypeTagsProvider extends TagsProvider<PoiType>
{
    public PoiTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(output, Registries.POINT_OF_INTEREST_TYPE, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.tag(FOTTags.PoiTypes.SHOAL).add(FOTPoiTypes.NATURAL_SHOAL, FOTPoiTypes.TREASURED_SHOAL);
    }
}