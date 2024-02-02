package com.stevekung.fishofthieves.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class FishPlaqueInteractionManager implements PreparableReloadListener
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter FISH_PLAQUE_INTERACTIONS_LISTER = FileToIdConverter.json("fish_plaque_interactions");
    private final Map<String, Item> fishPlaqueInteractionMap = Maps.newHashMap();
    public static final FishPlaqueInteractionManager INSTANCE = new FishPlaqueInteractionManager();

    public Map<String, Item> getFishPlaqueInteraction()
    {
        return this.fishPlaqueInteractionMap;
    }

    @Override
    public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor)
    {
        record FishPlaqueInteractionDefinition(ResourceLocation id, Optional<Item> item)
        {}

        var completableFuture = CompletableFuture.supplyAsync(() -> FISH_PLAQUE_INTERACTIONS_LISTER.listMatchingResources(resourceManager), backgroundExecutor).thenCompose(map ->
        {
            List<CompletableFuture<FishPlaqueInteractionDefinition>> list = new ArrayList<>(map.size());
            map.forEach((resourceLocation, resource) ->
            {
                var resourceLocation2 = FISH_PLAQUE_INTERACTIONS_LISTER.fileToId(resourceLocation);
                System.out.println(resourceLocation2);
                list.add(CompletableFuture.supplyAsync(() -> new FishPlaqueInteractionDefinition(resourceLocation2, this.loadFishPlaqueInteraction(resourceLocation2, resource)), backgroundExecutor));
            });
            return Util.sequence(list);
        });
        return CompletableFuture.allOf(completableFuture).thenCompose(preparationBarrier::wait).thenAcceptAsync(void_ ->
        {
            completableFuture.join().forEach(definition ->
            {
                var optionalItem = definition.item();
                optionalItem.ifPresent(item -> this.fishPlaqueInteractionMap.put(definition.id().toString(), optionalItem.get()));
            });
        }, gameExecutor);
    }

    private Optional<Item> loadFishPlaqueInteraction(ResourceLocation id, Resource resource)
    {
        try (var reader = resource.openAsReader())
        {
            Optional<Item> optionalItem;

            try
            {
                optionalItem = Optional.of(itemFromJson(GsonHelper.parse(reader), id));
            }
            catch (Throwable t)
            {
                try
                {
                    reader.close();
                }
                catch (Throwable t2)
                {
                    t.addSuppressed(t2);
                }
                throw t;
            }
            return optionalItem;
        }
        catch (IOException e)
        {
            throw new IllegalStateException("Failed to load fish plaque interaction " + id, e);
        }
    }

    private static Item itemFromJson(JsonObject itemObject, ResourceLocation id)
    {
        var string = GsonHelper.getAsString(itemObject, "item");
        var itemOptional = BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(string));

        if (itemOptional.isEmpty())
        {
            LOGGER.warn("Unknown item {} from {}, fallback to Water Bucket", string, id);
            return Items.WATER_BUCKET;
        }
        else
        {
            var item = itemOptional.get();

            if (item == Items.AIR)
            {
                throw new JsonSyntaxException("Empty interaction item not allowed here");
            }
            else
            {
                return item;
            }
        }
    }
}