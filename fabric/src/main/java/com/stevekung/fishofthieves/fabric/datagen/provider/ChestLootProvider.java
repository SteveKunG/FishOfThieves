package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import com.stevekung.fishofthieves.loot.function.TreasuredFishMapFunction;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import it.unimi.dsi.fastutil.ints.IntList;

public class ChestLootProvider extends SimpleFabricLootTableSubProvider
{
    private static final FireworkExplosion.Shape[] VALUES = FireworkExplosion.Shape.values();
    private static final IntList FIREWORK_COLORS = IntList.of(
            DyeColor.RED.getFireworkColor(),
            DyeColor.ORANGE.getFireworkColor(),
            DyeColor.YELLOW.getFireworkColor(),
            DyeColor.LIME.getFireworkColor(),
            DyeColor.BLUE.getFireworkColor(),
            DyeColor.CYAN.getFireworkColor(),
            DyeColor.LIGHT_BLUE.getFireworkColor(),
            DyeColor.PURPLE.getFireworkColor(),
            DyeColor.MAGENTA.getFireworkColor(),
            DyeColor.WHITE.getFireworkColor(),
            6942120 // athena
    );

    public ChestLootProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider, LootContextParamSets.CHEST);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
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
                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                        .setZoom((byte) 1)
                                        .setSkipKnownStructures(false))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.MAP)
                                .apply(TreasuredFishMapFunction.makeTreasuredFishMap()
                                        .setZoom((byte) 1)
                                        .setMinimumSearchRadius(50)
                                        .setMaximumSearchRadius(100)
                                        .setMaxAttempt(10)
                                        .setHighTierChance(0.4f))
                                .apply(SetNameFunction.setName(Component.translatable(Shoal.FILLED_MAP_TREASURED_FISH), SetNameFunction.Target.ITEM_NAME)))));

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
                .withPool(buildFirework(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 4.0F)))));
    }

    private static LootPool.Builder buildFirework(LootPool.Builder builder)
    {
        var random = RandomSource.create(69420);

        for (var color : FIREWORK_COLORS)
        {
            builder.add(LootItem.lootTableItem(Items.FIREWORK_ROCKET).setWeight(1)
                    .apply(setFirework(new ListOperation.StandAlone<>(List.of(new FireworkExplosion(Util.getRandom(VALUES, random), IntList.of(color), IntList.of(), random.nextBoolean(), random.nextBoolean())), ListOperation.Append.INSTANCE), Optional.of(1)))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))));
        }
        return builder;
    }

    private static LootItemConditionalFunction.Builder<?> setFirework(ListOperation.StandAlone<FireworkExplosion> explosions, Optional<Integer> flightDuration)
    {
        return LootItemConditionalFunction.simpleBuilder(lootItemConditions -> new SetFireworksFunction(lootItemConditions, Optional.of(explosions), flightDuration));
    }
}