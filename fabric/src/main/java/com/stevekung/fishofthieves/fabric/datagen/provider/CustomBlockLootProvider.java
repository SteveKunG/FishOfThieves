package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.loot.function.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

public class CustomBlockLootProvider extends SimpleFabricLootTableProvider
{
    public CustomBlockLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider, LootContextParamSets.BLOCK);
    }

    //@formatter:off
    @Override
    public void generate(HolderLookup.Provider registries, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
    {
        var waterPredicate = LocationPredicate.Builder.location().setFluid(FluidPredicate.Builder.fluid().of(Fluids.WATER));
        var waterSurrounded = LocationCheck.checkLocation(waterPredicate, new BlockPos(1, 0, 0))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(-1, 0, 0)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, 1)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, -1)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 1, 0)));

        consumer.accept(FOTLootTables.Blocks.EARTHWORMS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(FOTItems.EARTHWORMS)
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                .when(waterSurrounded.invert())
        ));

        consumer.accept(FOTLootTables.Blocks.GRUBS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(FOTItems.GRUBS)
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                .when(waterSurrounded.invert())
        ));

        consumer.accept(FOTLootTables.Blocks.LEECHES_DROPS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                        .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                        .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(registries.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_BEACH))).and(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setContinentalness(Continentalness.COAST))).or(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(registries.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_RIVER)))))
                        .when(waterSurrounded))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                        .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                        .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(registries.lookupOrThrow(Registries.BIOME).getOrThrow(FOTTags.Biomes.ALWAYS_DROP_LEECHES))))));
    }
    //@formatter:on
}