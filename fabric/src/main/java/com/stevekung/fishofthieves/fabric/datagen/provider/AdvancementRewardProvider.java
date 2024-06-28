package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class AdvancementRewardProvider extends SimpleFabricLootTableProvider
{
    public AdvancementRewardProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider, LootContextParamSets.ADVANCEMENT_REWARD);
    }

    //@formatter:off
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
    {
        consumer.accept(FOTLootTables.Advancements.FISH_COLLECTORS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(TagEntry.expandTag(FOTTags.Items.WOODEN_FISH_PLAQUE))));

        consumer.accept(FOTLootTables.Advancements.MASTER_FISH_COLLECTORS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(TagEntry.expandTag(FOTTags.Items.IRON_FRAME_FISH_PLAQUE)))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(TagEntry.expandTag(FOTTags.Items.GOLDEN_FRAME_FISH_PLAQUE))));

        consumer.accept(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(TagEntry.expandTag(FOTTags.Items.GILDED_FRAME_FISH_PLAQUE))));
    }
    //@formatter:on
}