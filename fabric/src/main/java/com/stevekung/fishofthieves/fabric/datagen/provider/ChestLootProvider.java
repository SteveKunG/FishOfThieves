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
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

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
    private final HolderGetter<Item> items;
    private final HolderGetter<Structure> structures;

    public ChestLootProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider, LootContextParamSets.CHEST);
        this.items = provider.join().lookupOrThrow(Registries.ITEM);
        this.structures = provider.join().lookupOrThrow(Registries.STRUCTURE);
    }

    @Override
    public void run()
    {
        this.generate((_, _) -> {});
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
    {
        consumer.accept(FOTLootTables.Chests.SEAPOST_BARREL_SUPPLY, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.between(4, 12))
                        .add(LootItem.lootTableItem(FOTItems.BANANA).setWeight(12)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                        .add(LootItem.lootTableItem(FOTItems.COCONUT).setWeight(10)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                        .add(LootItem.lootTableItem(FOTItems.POMEGRANATE).setWeight(8)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        .add(LootItem.lootTableItem(FOTItems.MANGO).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 3))))
                        .add(LootItem.lootTableItem(FOTItems.PINEAPPLE).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                        .add(LootItem.lootTableItem(Items.OAK_PLANKS).setWeight(8)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                        .add(TagEntry.expandTag(this.items.getOrThrow(FOTTags.Items.WORMS)).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(8, 16))))
                        .add(LootItem.lootTableItem(Items.OAK_LOG).setWeight(2)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                        .add(FOTTagEntry.expandTag(this.items.getOrThrow(FOTTags.Items.THIEVES_FISH)).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                        .add(LootItem.lootTableItem(Items.MAP)
                                .apply(ExplorationMapFunction.makeExplorationMap(this.structures.getOrThrow(StructureTags.ON_TREASURE_MAPS))
                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                        .setZoom((byte) 1)
                                        .setSkipKnownStructures(false))))
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
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
                        .setRolls(ContextIntProviders.between(2, 6))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        .add(LootItem.lootTableItem(Items.FIRE_CHARGE).setWeight(4)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.TNT).setWeight(2)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))));

        consumer.accept(FOTLootTables.Chests.SEAPOST_BARREL_FIREWORK, LootTable.lootTable()
                .withPool(buildFirework(LootPool.lootPool().setRolls(ContextIntProviders.between(2, 4)))));
    }

    private static LootPool.Builder buildFirework(LootPool.Builder builder)
    {
        var random = RandomSource.create(69420);

        for (var color : FIREWORK_COLORS)
        {
            builder.add(LootItem.lootTableItem(Items.FIREWORK_ROCKET).setWeight(1)
                    .apply(setFirework(new ListOperation.StandAlone<>(List.of(new FireworkExplosion(Util.getRandom(VALUES, random), IntList.of(color), IntList.of(), random.nextBoolean(), random.nextBoolean())), ListOperation.Append.INSTANCE), Optional.of(1)))
                    .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))));
        }
        return builder;
    }

    private static LootItemConditionalFunction.Builder<?> setFirework(ListOperation.StandAlone<FireworkExplosion> explosions, Optional<Integer> flightDuration)
    {
        return LootItemConditionalFunction.simpleBuilder(lootItemConditions -> new SetFireworksFunction(lootItemConditions, Optional.of(explosions), flightDuration));
    }
}