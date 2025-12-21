package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import com.stevekung.fishofthieves.loot.function.SetRandomFireworkFunction;
import com.stevekung.fishofthieves.loot.function.TreasuredFishMapFunction;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ChestLootProvider extends SimpleFabricLootTableProvider
{
    public ChestLootProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, LootContextParamSets.CHEST);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        consumer.accept(FOTLootTables.Chests.SEAPOST_BARREL_SUPPLY, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 12.0F))
                        .add(LootItem.lootTableItem(FOTItems.BANANA).setWeight(12)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        .add(LootItem.lootTableItem(FOTItems.COCONUT).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                        .add(LootItem.lootTableItem(FOTItems.POMEGRANATE).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(FOTItems.MANGO).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))))
                        .add(LootItem.lootTableItem(FOTItems.PINEAPPLE).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.OAK_PLANKS).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        .add(TagEntry.expandTag(FOTTags.Items.WORMS).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 16.0F))))
                        .add(LootItem.lootTableItem(Items.OAK_LOG).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                        .add(FOTTagEntry.expandTag(FOTTags.Items.THIEVES_FISH).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.MAP)
                                .apply(ExplorationMapFunction.makeExplorationMap()
                                        .setDestination(StructureTags.ON_TREASURE_MAPS)
                                        .setMapDecoration(MapDecoration.Type.RED_X)
                                        .setZoom((byte) 1)
                                        .setSkipKnownStructures(false))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.MAP)
                                .apply(TreasuredFishMapFunction.makeTreasuredFishMap()
                                        .setZoom((byte) 1)
                                        .setMinimumSearchRadius(50)
                                        .setMaximumSearchRadius(100)
                                        .setHighTierChance(0.4f))
                                .apply(SetNameFunction.setName(Component.translatable(Shoal.FILLED_MAP_TREASURED_FISH))))));

        consumer.accept(FOTLootTables.Chests.SEAPOST_BARREL_COMBAT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 6.0F))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.FIRE_CHARGE).setWeight(4)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.TNT).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));

        consumer.accept(FOTLootTables.Chests.SEAPOST_BARREL_FIREWORK, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET).setWeight(3)
                                .apply(SetRandomFireworkFunction.builder()
                                        .withColor(DyeColor.RED)
                                        .withColor(DyeColor.ORANGE)
                                        .withColor(DyeColor.YELLOW)
                                        .withColor(DyeColor.LIME)
                                        .withColor(DyeColor.BLUE)
                                        .withColor(DyeColor.CYAN)
                                        .withColor(DyeColor.LIGHT_BLUE)
                                        .withColor(DyeColor.PURPLE)
                                        .withColor(DyeColor.MAGENTA)
                                        .withColor(DyeColor.WHITE)
                                        .withColor(6942120) // athena
                                )
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))));
    }
}