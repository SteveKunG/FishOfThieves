package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.List;
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
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public class CustomBlockLootProvider extends SimpleFabricLootTableProvider
{
    private final HolderLookup.Provider registries;

    public CustomBlockLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider, LootContextParamSets.BLOCK);
        this.registries = provider.join();
    }

    //@formatter:off
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
    {
        var waterPredicate = LocationPredicate.Builder.location().setFluid(FluidPredicate.Builder.fluid().of(Fluids.WATER));
        var waterSurrounded = LocationCheck.checkLocation(waterPredicate, new BlockPos(1, 0, 0))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(-1, 0, 0)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, 1)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, -1)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 1, 0)));

        consumer.accept(FOTLootTables.Blocks.EARTHWORMS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(FOTItems.EARTHWORMS)
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.05f, 0.1f, 0.15f, 0.2f)))
                .when(this.hasSilkTouch().invert())
                .when(waterSurrounded.invert())
        ));

        consumer.accept(FOTLootTables.Blocks.GRUBS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(FOTItems.GRUBS)
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.05f, 0.1f, 0.15f, 0.2f)))
                .when(this.hasSilkTouch().invert())
                .when(waterSurrounded.invert())
        ));

        consumer.accept(FOTLootTables.Blocks.LEECHES_DROPS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.05f, 0.1f, 0.15f, 0.2f)))
                        .when(this.hasSilkTouch().invert())
                        .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(this.registries.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_BEACH))).and(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setContinentalness(Continentalness.COAST))).or(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(this.registries.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_RIVER)))))
                        .when(waterSurrounded))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.05f, 0.1f, 0.15f, 0.2f)))
                        .when(this.hasSilkTouch().invert())
                        .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(this.registries.lookupOrThrow(Registries.BIOME).getOrThrow(FOTTags.Biomes.ALWAYS_DROP_LEECHES))))));
    }
    //@formatter:on

    private LootItemCondition.Builder hasSilkTouch()
    {
        var registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(registryLookup.getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))))));
    }
}