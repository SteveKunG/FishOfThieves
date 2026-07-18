package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class AdvancementRewardProvider extends SimpleFabricLootTableSubProvider
{
    private final HolderGetter<Item> items;

    public AdvancementRewardProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider, LootContextParamSets.ADVANCEMENT_REWARD);
        this.items = provider.join().lookupOrThrow(Registries.ITEM);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
    {
        consumer.accept(FOTLootTables.Advancements.FISH_COLLECTORS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(TagEntry.expandTag(this.items.getOrThrow(FOTTags.Items.WOODEN_FISH_PLAQUE)))));

        consumer.accept(FOTLootTables.Advancements.MASTER_FISH_COLLECTORS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(TagEntry.expandTag(this.items.getOrThrow(FOTTags.Items.IRON_FRAME_FISH_PLAQUE))))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(TagEntry.expandTag(this.items.getOrThrow(FOTTags.Items.COPPER_FRAME_FISH_PLAQUE))))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(TagEntry.expandTag(this.items.getOrThrow(FOTTags.Items.GOLDEN_FRAME_FISH_PLAQUE)))));

        consumer.accept(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(TagEntry.expandTag(this.items.getOrThrow(FOTTags.Items.GILDED_FRAME_FISH_PLAQUE)))));
    }

    @Override
    public void run()
    {
        //TODO
    }
}