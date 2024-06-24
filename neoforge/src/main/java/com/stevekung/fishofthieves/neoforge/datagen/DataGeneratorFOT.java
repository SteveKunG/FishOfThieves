package com.stevekung.fishofthieves.neoforge.datagen;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = FishOfThieves.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGeneratorFOT
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var provider = event.getLookupProvider();
        dataGenerator.addProvider(event.includeServer(), new FishingReal(packOutput, provider));
    }

    private static class FishingReal extends FishingRealProvider
    {
        public FishingReal(PackOutput output, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(output, provider);
        }

        @Override
        public void addFishingReal()
        {
            this.add(FOTItems.SPLASHTAIL, FOTEntities.SPLASHTAIL);
            this.add(FOTItems.PONDIE, FOTEntities.PONDIE);
            this.add(FOTItems.ISLEHOPPER, FOTEntities.ISLEHOPPER);
            this.add(FOTItems.ANCIENTSCALE, FOTEntities.ANCIENTSCALE);
            this.add(FOTItems.PLENTIFIN, FOTEntities.PLENTIFIN);
            this.add(FOTItems.WILDSPLASH, FOTEntities.WILDSPLASH);
            this.add(FOTItems.DEVILFISH, FOTEntities.DEVILFISH);
            this.add(FOTItems.BATTLEGILL, FOTEntities.BATTLEGILL);
            this.add(FOTItems.WRECKER, FOTEntities.WRECKER);
            this.add(FOTItems.STORMFISH, FOTEntities.STORMFISH);
        }
    }

    private static abstract class FishingRealProvider implements DataProvider
    {
        private final Map<ResourceLocation, FishingRealBuilder> builders = Maps.newLinkedHashMap();
        private final PackOutput output;
        private final CompletableFuture<HolderLookup.Provider> provider;

        public FishingRealProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider)
        {
            this.output = output;
            this.provider = provider;
        }

        public abstract void addFishingReal();

        public void add(Item item, EntityType<?> entityType)
        {
            this.add(item, entityType, null);
        }

        public void add(Item item, EntityType<?> entityType, @Nullable CompoundTag compoundTag)
        {
            var builder = new FishingRealBuilder(item, entityType, compoundTag);
            this.builders.put(BuiltInRegistries.ENTITY_TYPE.getKey(entityType), builder);
        }

        @Override
        public CompletableFuture<?> run(CachedOutput cachedOutput)
        {
            return this.provider.thenCompose(provider ->
            {
                this.builders.clear();
                this.addFishingReal();

                return CompletableFuture.allOf(this.builders.entrySet().stream().map(entry ->
                {
                    var jsonObject = entry.getValue().serializeToJson();
                    var path = this.getPath(entry.getKey());
                    return DataProvider.saveStable(cachedOutput, jsonObject, path);
                }).toArray(CompletableFuture[]::new));
            });
        }

        @Override
        public String getName()
        {
            return "FishingReal";
        }

        private Path getPath(ResourceLocation id)
        {
            return this.output.getOutputFolder().resolve("data/fishingreal/fishing/" + id.getPath() + ".json");
        }

        record FishingRealBuilder(Item item, EntityType<?> entityType, @Nullable CompoundTag compoundTag)
        {
            public JsonObject serializeToJson()
            {
                var jsonObject = new JsonObject();
                var jsonItem = new JsonObject();
                var jsonResult = new JsonObject();
                jsonItem.addProperty("item", BuiltInRegistries.ITEM.getKey(this.item).toString());
                jsonResult.addProperty("id", BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType).toString());

                if (this.compoundTag != null)
                {
                    jsonResult.addProperty("nbt", this.compoundTag.toString());
                }

                jsonObject.add("input", jsonItem);
                jsonObject.add("result", jsonResult);
                return jsonObject;
            }
        }
    }
}