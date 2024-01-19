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
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = FishOfThieves.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneratorFOT
{
    private static final TagKey<Item> RAW_FISHES = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_fishes"));
    private static final TagKey<Item> COOKED_FISHES = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "cooked_fishes"));

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var provider = event.getLookupProvider();
        var helper = event.getExistingFileHelper();
        dataGenerator.addProvider(event.includeServer(), new ForgeItemTags(packOutput, provider, FishOfThieves.MOD_ID, helper));
        dataGenerator.addProvider(event.includeServer(), new FishingReal(packOutput, provider));
    }

    private static class ForgeItemTags extends ItemTagsProvider
    {
        public ForgeItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modId, @Nullable ExistingFileHelper existingFileHelper)
        {
            super(output, provider, new VanillaBlockTagsProvider(output, provider).contentsGetter(), modId, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider)
        {
            var rawFishes = new Item[] {FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH};
            var cookedFishes = new Item[] {FOTItems.COOKED_SPLASHTAIL, FOTItems.COOKED_PONDIE, FOTItems.COOKED_ISLEHOPPER, FOTItems.COOKED_ANCIENTSCALE, FOTItems.COOKED_PLENTIFIN, FOTItems.COOKED_WILDSPLASH, FOTItems.COOKED_DEVILFISH, FOTItems.COOKED_BATTLEGILL, FOTItems.COOKED_WRECKER, FOTItems.COOKED_STORMFISH};

            this.tag(RAW_FISHES).add(rawFishes);
            this.tag(COOKED_FISHES).add(cookedFishes);
        }
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