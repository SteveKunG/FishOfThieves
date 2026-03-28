package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTTimelines;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TimelineTags;
import net.minecraft.world.timeline.Timeline;

public class TimelineTagsProvider extends FabricTagsProvider<Timeline>
{
    public TimelineTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> completableFuture)
    {
        super(dataOutput, Registries.TIMELINE, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.builder(TimelineTags.IN_OVERWORLD).add(FOTTimelines.DAY);
    }
}