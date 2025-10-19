package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import vazkii.patchouli.common.item.PatchouliItems;

public class AdvancementRewardProvider extends SimpleFabricLootTableProvider
{
    public AdvancementRewardProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, LootContextParamSets.ADVANCEMENT_REWARD);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        consumer.accept(FOTLootTables.Advancements.FISH_COLLECTORS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(TagEntry.expandTag(FOTTags.Items.WOODEN_FISH_PLAQUE))));

        consumer.accept(FOTLootTables.Advancements.MASTER_FISH_COLLECTORS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(TagEntry.expandTag(FOTTags.Items.IRON_FRAME_FISH_PLAQUE)))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(TagEntry.expandTag(FOTTags.Items.COPPER_FRAME_FISH_PLAQUE)))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(TagEntry.expandTag(FOTTags.Items.GOLDEN_FRAME_FISH_PLAQUE))));

        consumer.accept(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(TagEntry.expandTag(FOTTags.Items.GILDED_FRAME_FISH_PLAQUE))));

        consumer.accept(FOTLootTables.Advancements.PATCHOULI_BOOK, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(BuiltInRegistries.ITEM.get(PatchouliItems.BOOK_ID))
                                .apply(SetNbtFunction.setTag(Util.make(
                                        new CompoundTag(), compoundTag -> compoundTag.putString("patchouli:book", "fishofthieves:guide_book")))))
                ));
    }
}
