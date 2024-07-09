package com.stevekung.fishofthieves.forge.datagen;

<<<<<<< HEAD
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
=======
>>>>>>> bad19cf26ef1c304766713b499168895f9c0e82d
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
<<<<<<< HEAD
import com.stevekung.fishofthieves.forge.loot.FOTForgeLootTables;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.registry.FOTEntities;
=======
>>>>>>> bad19cf26ef1c304766713b499168895f9c0e82d
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
<<<<<<< HEAD
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
=======
>>>>>>> bad19cf26ef1c304766713b499168895f9c0e82d
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
<<<<<<< HEAD
        dataGenerator.addProvider(event.includeServer(), new LootTableProvider(packOutput, FOTForgeLootTables.all(), List.of(
                //@formatter:off
                new LootTableProvider.SubProviderEntry(EntityLootTableProvider::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(ChestLootTableProvider::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(ArchaeologyLootTableProvider::new, LootContextParamSets.ARCHAEOLOGY),
                new LootTableProvider.SubProviderEntry(GiftLootTableProvider::new, LootContextParamSets.GIFT),
                new LootTableProvider.SubProviderEntry(FishingLootTableProvider::new, LootContextParamSets.FISHING)
                //@formatter:on
        ), provider));
        dataGenerator.addProvider(event.includeServer(), new ForgeItemTags(packOutput, provider, helper));
        dataGenerator.addProvider(event.includeServer(), new FishingReal(packOutput, provider));
=======
        dataGenerator.addProvider(event.includeServer(), new ForgeItemTags(packOutput, provider, FishOfThieves.MOD_ID, helper));
>>>>>>> bad19cf26ef1c304766713b499168895f9c0e82d
    }

    private static class ArchaeologyLootTableProvider implements LootTableSubProvider
    {
        @Override
        public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            consumer.accept(FOTForgeLootTables.Archaeology.OCEAN_RUINS, LootTable.lootTable().withPool(FOTLootManager.getOceanRuinsArchaeologyLoot(LootPool.lootPool())));
        }
    }

    private static class ChestLootTableProvider implements LootTableSubProvider
    {
        @Override
        public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            consumer.accept(FOTForgeLootTables.Chest.VILLAGE_FISHER, LootTable.lootTable().withPool(FOTLootManager.getVillageFisherLoot(LootPool.lootPool())));
            consumer.accept(FOTForgeLootTables.Chest.BURIED_TREASURE, LootTable.lootTable().withPool(FOTLootManager.getBuriedTreasureLoot(LootPool.lootPool())));
        }
    }

    private static class EntityLootTableProvider implements LootTableSubProvider
    {
        @Override
        public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            consumer.accept(FOTForgeLootTables.Entity.POLAR_BEAR, LootTable.lootTable().withPool(FOTLootManager.getPolarBearLoot(LootPool.lootPool())));
        }
    }

    private static class GiftLootTableProvider implements LootTableSubProvider
    {
        @Override
        public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            consumer.accept(FOTForgeLootTables.Gift.FISHERMAN_GIFT, LootTable.lootTable().withPool(FOTLootManager.getFishermanGiftLoot(LootPool.lootPool())));
        }
    }

    private static class FishingLootTableProvider implements LootTableSubProvider
    {
        @Override
        public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            consumer.accept(FOTForgeLootTables.Fishing.FISHING_FISH, LootTable.lootTable().withPool(FOTLootManager.getFishingLoot(LootPool.lootPool())));
        }
    }

    private static class ForgeItemTags extends ItemTagsProvider
    {
        public ForgeItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper)
        {
            super(output, provider, new VanillaBlockTagsProvider(output, provider).contentsGetter(), FishOfThieves.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider)
        {
            var rawFishes = new Item[] { FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH };
            var cookedFishes = new Item[] { FOTItems.COOKED_SPLASHTAIL, FOTItems.COOKED_PONDIE, FOTItems.COOKED_ISLEHOPPER, FOTItems.COOKED_ANCIENTSCALE, FOTItems.COOKED_PLENTIFIN, FOTItems.COOKED_WILDSPLASH, FOTItems.COOKED_DEVILFISH, FOTItems.COOKED_BATTLEGILL, FOTItems.COOKED_WRECKER, FOTItems.COOKED_STORMFISH };

            this.tag(RAW_FISHES).add(rawFishes);
            this.tag(COOKED_FISHES).add(cookedFishes);
        }
    }
}