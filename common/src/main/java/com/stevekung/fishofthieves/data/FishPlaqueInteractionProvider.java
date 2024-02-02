package com.stevekung.fishofthieves.data;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.google.common.collect.Sets;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public abstract class FishPlaqueInteractionProvider implements DataProvider
{
    private final PackOutput.PathProvider fishPlaqueInteractionPathProvider;

    public FishPlaqueInteractionProvider(PackOutput output)
    {
        this.fishPlaqueInteractionPathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "fish_plaque_interactions");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output)
    {
        var set = Sets.<ResourceLocation>newHashSet();
        var list = new ArrayList<CompletableFuture<?>>();

        this.generate(finishedFishPlaqueInteraction ->
        {
            if (!set.add(finishedFishPlaqueInteraction.id()))
            {
                throw new IllegalStateException("Duplicate interaction: " + finishedFishPlaqueInteraction.id());
            }
            else
            {
                list.add(DataProvider.saveStable(output, finishedFishPlaqueInteraction.serializeInteraction(), this.fishPlaqueInteractionPathProvider.json(finishedFishPlaqueInteraction.id())));
            }
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName()
    {
        return "FishPlaqueInteraction";
    }

    public abstract void generate(Consumer<FinishedFishPlaqueInteraction> output);
}