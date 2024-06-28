package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ChestLootProvider extends SimpleFabricLootTableProvider
{
    //@formatter:off
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
    //@formatter:on

    public ChestLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider, LootContextParamSets.CHEST);
    }

    //@formatter:off
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
    {
        consumer.accept(FOTLootTables.Chests.SEAPOST_BARREL_SUPPLY, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 12.0F))
                        .add(LootItem.lootTableItem(Items.SUSPICIOUS_STEW).setWeight(10)
                                .apply(SetStewEffectFunction.stewEffect()
                                        .withEffect(MobEffects.NIGHT_VISION, UniformGenerator.between(7.0F, 10.0F))
                                        .withEffect(MobEffects.JUMP, UniformGenerator.between(7.0F, 10.0F))
                                        .withEffect(MobEffects.WEAKNESS, UniformGenerator.between(6.0F, 8.0F))
                                        .withEffect(MobEffects.BLINDNESS, UniformGenerator.between(5.0F, 7.0F))
                                        .withEffect(MobEffects.POISON, UniformGenerator.between(10.0F, 20.0F))
                                        .withEffect(MobEffects.SATURATION, UniformGenerator.between(7.0F, 10.0F))))
                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(9)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(9)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(9)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                        .add(LootItem.lootTableItem(Items.OAK_LOG).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.BAMBOO).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 24.0F))))
                        .add(TagEntry.expandTag(FOTTags.Items.WORMS).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 16.0F))))
                        .add(FOTTagEntry.expandTag(FOTTags.Items.THIEVES_FISH).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.MAP)
                                .apply(ExplorationMapFunction.makeExplorationMap()
                                        .setDestination(StructureTags.ON_TREASURE_MAPS)
                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                        .setZoom((byte)1)
                                        .setSkipKnownStructures(false)))));

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
    //@formatter:on

    //@formatter:off
    private static LootPool.Builder buildFirework(LootPool.Builder builder)
    {
        var random = RandomSource.create(69420);

        for (var color : FIREWORK_COLORS)
        {
            builder.add(LootItem.lootTableItem(Items.FIREWORK_ROCKET).setWeight(1)
                    .apply(setFirework(new ListOperation.StandAlone<>(List.of(new FireworkExplosion(Util.getRandom(FireworkExplosion.Shape.values(), random), IntList.of(color), IntList.of(), random.nextBoolean(), random.nextBoolean())), ListOperation.Append.INSTANCE), Optional.of(1)))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))));
        }
        return builder;
    }
    //@formatter:on

    private static LootItemConditionalFunction.Builder<?> setFirework(ListOperation.StandAlone<FireworkExplosion> explosions, Optional<Integer> flightDuration)
    {
        return LootItemConditionalFunction.simpleBuilder(lootItemConditions -> new SetFireworksFunction(lootItemConditions, Optional.of(explosions), flightDuration));
    }
}