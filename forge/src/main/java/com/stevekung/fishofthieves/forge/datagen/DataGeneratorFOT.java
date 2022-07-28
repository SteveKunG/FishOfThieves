package com.stevekung.fishofthieves.forge.datagen;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FishOfThieves.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneratorFOT
{
    private static final TagKey<Item> RAW_FISHES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "raw_fishes"));
    private static final TagKey<Item> COOKED_FISHES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "cooked_fishes"));

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        var dataGenerator = event.getGenerator();
        var helper = event.getExistingFileHelper();

        if (event.includeServer())
        {
            dataGenerator.addProvider(new ForgeItemTags(dataGenerator, FishOfThieves.MOD_ID, helper));
            dataGenerator.addProvider(new FishingReal(dataGenerator));
        }
    }

    private static class ForgeItemTags extends ItemTagsProvider
    {
        public ForgeItemTags(DataGenerator dataGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper)
        {
            super(dataGenerator, new BlockTagsProvider(dataGenerator, modId, existingFileHelper), modId, existingFileHelper);
        }

        @Override
        protected void addTags()
        {
            var rawFishes = new Item[] {FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH};
            var cookedFishes = new Item[] {FOTItems.COOKED_SPLASHTAIL, FOTItems.COOKED_PONDIE, FOTItems.COOKED_ISLEHOPPER, FOTItems.COOKED_ANCIENTSCALE, FOTItems.COOKED_PLENTIFIN, FOTItems.COOKED_WILDSPLASH, FOTItems.COOKED_DEVILFISH, FOTItems.COOKED_BATTLEGILL, FOTItems.COOKED_WRECKER, FOTItems.COOKED_STORMFISH};

            this.tag(RAW_FISHES).add(rawFishes);
            this.tag(COOKED_FISHES).add(cookedFishes);
        }
    }

    private static class FishingReal extends FishingRealProvider
    {
        public FishingReal(DataGenerator dataGenerator)
        {
            super(dataGenerator);
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
        private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        private final Map<ResourceLocation, FishingRealBuilder> builders = Maps.newLinkedHashMap();

        protected final DataGenerator dataGenerator;

        public FishingRealProvider(DataGenerator dataGenerator)
        {
            this.dataGenerator = dataGenerator;
        }

        public abstract void addFishingReal();

        public void add(Item item, EntityType<?> entityType)
        {
            this.add(item, entityType, null);
        }

        public void add(Item item, EntityType<?> entityType, @Nullable CompoundTag compoundTag)
        {
            var builder = new FishingRealBuilder(item, entityType, compoundTag);
            this.builders.put(entityType.getRegistryName(), builder);
        }

        @Override
        public void run(HashCache cache)
        {
            this.builders.clear();
            this.addFishingReal();
            this.builders.forEach((resourceLocation, builder) ->
            {
                var jsonObject = builder.serializeToJson();
                var path = this.getPath(resourceLocation);

                try
                {
                    var string = GSON.toJson(jsonObject);
                    var string2 = SHA1.hashUnencodedChars(string).toString();

                    if (!Objects.equals(cache.getHash(path), string2) || !Files.exists(path))
                    {
                        Files.createDirectories(path.getParent());

                        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path))
                        {
                            bufferedWriter.write(string);
                        }
                    }
                    cache.putNew(path, string2);
                }
                catch (IOException e)
                {
                    FishOfThieves.LOGGER.error("Couldn't save FishingReal to {}", path, e);
                }
            });
        }

        @Override
        public String getName()
        {
            return "FishingReal";
        }

        private Path getPath(ResourceLocation id)
        {
            return this.dataGenerator.getOutputFolder().resolve("data/fishingreal/fishing/" + id.getPath() + ".json");
        }

        record FishingRealBuilder(Item item, EntityType<?> entityType, @Nullable CompoundTag compoundTag)
        {
            public JsonObject serializeToJson()
            {
                var jsonObject = new JsonObject();
                var jsonItem = new JsonObject();
                var jsonResult = new JsonObject();
                jsonItem.addProperty("item", ForgeRegistries.ITEMS.getKey(this.item).toString());
                jsonResult.addProperty("id", ForgeRegistries.ENTITIES.getKey(this.entityType).toString());

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