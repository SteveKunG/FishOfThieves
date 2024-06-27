package com.stevekung.fishofthieves.fabric.datagen.provider;

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
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

public class CustomBlockLootProvider extends SimpleFabricLootTableProvider
{
    public CustomBlockLootProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, LootContextParamSets.BLOCK);
    }

    //@formatter:off
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        var waterPredicate = LocationPredicate.Builder.location().setFluid(FluidPredicate.Builder.fluid().of(FluidTags.WATER).build());
        var waterSurrounded = LocationCheck.checkLocation(waterPredicate, new BlockPos(1, 0, 0))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(-1, 0, 0)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, 1)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, -1)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 1, 0)));

        consumer.accept(FOTLootTables.Blocks.EARTHWORMS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(FOTItems.EARTHWORMS)
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                .when(waterSurrounded.invert())
        ));

        consumer.accept(FOTLootTables.Blocks.GRUBS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(FOTItems.GRUBS)
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                .when(waterSurrounded.invert())
        ));

        consumer.accept(FOTLootTables.Blocks.LEECHES_DROPS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                        .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(BiomeTags.IS_BEACH).setContinentalness(Continentalness.COAST)).or(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(BiomeTags.IS_RIVER))))
                        .when(waterSurrounded))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                        .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(FOTTags.Biomes.ALWAYS_DROP_LEECHES)))));
    }
    //@formatter:on
}