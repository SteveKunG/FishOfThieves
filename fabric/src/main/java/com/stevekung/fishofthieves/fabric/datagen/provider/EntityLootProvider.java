package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

import com.stevekung.fishofthieves.item.ResourceKeyHolder;
import com.stevekung.fishofthieves.loot.condition.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.predicate.TreasuredFishPredicate;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;
import com.stevekung.fishofthieves.registry.*;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class EntityLootProvider extends SimpleFabricLootTableProvider
{
    private final HolderLookup.Provider provider;

    public EntityLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, LootContextParamSets.ENTITY);
        this.provider = provider.join();
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        consumer.accept(FOTLootTables.Entities.FISH_BONE_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));

        this.simpleFishLoot(consumer, FOTEntities.SPLASHTAIL, FOTItems.SPLASHTAIL, i -> FOTEntitySubPredicate.variant(FOTRegistry.SPLASHTAIL_VARIANT.holders().toList().get(i).value()));
        this.simpleFishLoot(consumer, FOTEntities.PONDIE, FOTItems.PONDIE, i -> FOTEntitySubPredicate.variant(FOTRegistry.PONDIE_VARIANT.holders().toList().get(i).value()));
        this.simpleFishLoot(consumer, FOTEntities.ISLEHOPPER, FOTItems.ISLEHOPPER, i -> FOTEntitySubPredicate.variant(FOTRegistry.ISLEHOPPER_VARIANT.holders().toList().get(i).value()));
        this.simpleFishLoot(consumer, FOTEntities.ANCIENTSCALE, FOTItems.ANCIENTSCALE, i -> FOTEntitySubPredicate.variant(FOTRegistry.ANCIENTSCALE_VARIANT.holders().toList().get(i).value()));
        this.simpleFishLoot(consumer, FOTEntities.PLENTIFIN, FOTItems.PLENTIFIN, i -> FOTEntitySubPredicate.variant(FOTRegistry.PLENTIFIN_VARIANT.holders().toList().get(i).value()));
        this.simpleFishLoot(consumer, FOTEntities.WILDSPLASH, FOTItems.WILDSPLASH, i -> FOTEntitySubPredicate.variant(FOTRegistry.WILDSPLASH_VARIANT.holders().toList().get(i).value()));
        this.simpleFishLoot(consumer, FOTEntities.DEVILFISH, FOTItems.DEVILFISH, i -> FOTEntitySubPredicate.variant(FOTRegistry.DEVILFISH_VARIANT.holders().toList().get(i).value()));
        this.simpleFishLoot(consumer, FOTEntities.BATTLEGILL, FOTItems.BATTLEGILL, i -> FOTEntitySubPredicate.variant(FOTRegistry.BATTLEGILL_VARIANT.holders().toList().get(i).value()));
        this.simpleFishLoot(consumer, FOTEntities.WRECKER, FOTItems.WRECKER, i -> FOTEntitySubPredicate.variant(FOTRegistry.WRECKER_VARIANT.holders().toList().get(i).value()));
        this.simpleFishLoot(consumer, FOTEntities.STORMFISH, FOTItems.STORMFISH, i -> FOTEntitySubPredicate.variant(FOTRegistry.STORMFISH_VARIANT.holders().toList().get(i).value()));
    }

    private void simpleFishLoot(BiConsumer<ResourceLocation, LootTable.Builder> consumer, EntityType<?> entityType, Item item, IntFunction<EntitySubPredicate> function)
    {
        consumer.accept(entityType.getDefaultLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(this.applyVariant((ResourceKeyHolder) item, LootItem.lootTableItem(item)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(TrophyFishPredicate.trophy(true))))
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(TreasuredFishPredicate.treasured(false))))
                                ), entityType, function)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(Items.BONE_MEAL))
                        .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
    }

    @SuppressWarnings("deprecation")
    private LootPoolEntryContainer.Builder<?> applyVariant(ResourceKeyHolder resourceKeyHolder, LootPoolSingletonContainer.Builder<?> builder, EntityType<?> entityType, IntFunction<EntitySubPredicate> function)
    {
        var variantList = this.provider.lookupOrThrow(resourceKeyHolder.getResourceKey()).listElementIds().toList();

        // Variant items
        for (var i = 0; i < variantList.size(); i++)
        {
            var resourceKey = variantList.get(i);
            var compound = new CompoundTag();
            compound.putString(resourceKey.registry().getPath(), resourceKey.location().toString());

            builder.apply(SetNbtFunction.setTag(compound)
                    .when(FishVariantLootConfigCondition.configEnabled())
                    .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(entityType).subPredicate(function.apply(i))))
                    .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE).invert()));
        }

        // Default variant item
        var resourceKey = variantList.get(0);
        var compound = new CompoundTag();
        compound.putString(resourceKey.registry().getPath(), resourceKey.location().toString());
        builder.apply(SetNbtFunction.setTag(compound)
                .when(FishVariantLootConfigCondition.configEnabled().invert())
                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE).invert()));

        return builder;
    }
}