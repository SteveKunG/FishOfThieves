package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.loot.function.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;
import com.stevekung.fishofthieves.registry.*;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetCustomModelDataFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class EntityLootProvider extends SimpleFabricLootTableProvider
{
    private final HolderLookup.Provider provider;

    public EntityLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider, LootContextParamSets.ENTITY);
        this.provider = provider.join();
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
    {
        //@formatter:off
        consumer.accept(FOTLootTables.Entities.FISH_BONE_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.provider, 0.025F, 0.01F))));
        //@formatter:on

        this.simpleFishLoot(consumer, FOTItems.SPLASHTAIL, FOTEntities.SPLASHTAIL, FOTRegistries.SPLASHTAIL_VARIANT, FOTEntitySubPredicate::splashtail);
        this.simpleFishLoot(consumer, FOTItems.PONDIE, FOTEntities.PONDIE, FOTRegistries.PONDIE_VARIANT, FOTEntitySubPredicate::pondie);
        this.simpleFishLoot(consumer, FOTItems.ISLEHOPPER, FOTEntities.ISLEHOPPER, FOTRegistries.ISLEHOPPER_VARIANT, FOTEntitySubPredicate::islehopper);
        this.simpleFishLoot(consumer, FOTItems.ANCIENTSCALE, FOTEntities.ANCIENTSCALE, FOTRegistries.ANCIENTSCALE_VARIANT, FOTEntitySubPredicate::ancientscale);
        this.simpleFishLoot(consumer, FOTItems.PLENTIFIN, FOTEntities.PLENTIFIN, FOTRegistries.PLENTIFIN_VARIANT, FOTEntitySubPredicate::plentifin);
        this.simpleFishLoot(consumer, FOTItems.WILDSPLASH, FOTEntities.WILDSPLASH, FOTRegistries.WILDSPLASH_VARIANT, FOTEntitySubPredicate::wildsplash);
        this.simpleFishLoot(consumer, FOTItems.DEVILFISH, FOTEntities.DEVILFISH, FOTRegistries.DEVILFISH_VARIANT, FOTEntitySubPredicate::devilfish);
        this.simpleFishLoot(consumer, FOTItems.BATTLEGILL, FOTEntities.BATTLEGILL, FOTRegistries.BATTLEGILL_VARIANT, FOTEntitySubPredicate::battlegill);
        this.simpleFishLoot(consumer, FOTItems.WRECKER, FOTEntities.WRECKER, FOTRegistries.WRECKER_VARIANT, FOTEntitySubPredicate::wrecker);
        this.simpleFishLoot(consumer, FOTItems.STORMFISH, FOTEntities.STORMFISH, FOTRegistries.STORMFISH_VARIANT, FOTEntitySubPredicate::stormfish);
    }

    private <T extends AbstractFishVariant> void simpleFishLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer, Item item, EntityType<?> entityType, ResourceKey<Registry<T>> registryKey, Function<HolderSet<T>, EntitySubPredicate> function)
    {
        //@formatter:off
        consumer.accept(entityType.getDefaultLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(this.applyCustomModelDataFromVariant(LootItem.lootTableItem(item)
                                .apply(SmeltItemFunction.smelted()
                                        .when(FOTLootManager.shouldSmeltLoot(this.provider)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(new TrophyFishPredicate(true))))), entityType, registryKey, function)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(Items.BONE_MEAL))
                        .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.provider, 0.025F, 0.01F))));
        //@formatter:on
    }

    private <T extends AbstractFishVariant> LootPoolEntryContainer.Builder<?> applyCustomModelDataFromVariant(LootPoolSingletonContainer.Builder<?> builder, EntityType<?> entityType, ResourceKey<Registry<T>> registryKey, Function<HolderSet<T>, EntitySubPredicate> function)
    {
        this.provider.lookupOrThrow(registryKey).listElements().sorted(Comparator.comparing(holder -> holder.value().customModelData())).forEach(holder ->
        {
            //@formatter:off
            builder.apply(this.setCustomModelData(holder.value().customModelData())
                    .when(FishVariantLootConfigCondition.configEnabled())
                    .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(this.provider.lookupOrThrow(Registries.ENTITY_TYPE), entityType).subPredicate(function.apply(HolderSet.direct(this.provider.lookupOrThrow(registryKey).getOrThrow(holder.key()))))))
                    .when(FOTLootManager.shouldSmeltLoot(this.provider).invert()));
            //@formatter:on
        });
        return builder;
    }

    private LootItemConditionalFunction.Builder<?> setCustomModelData(int data)
    {
        return LootItemConditionalFunction.simpleBuilder(lootItemConditions -> new SetCustomModelDataFunction(lootItemConditions, ConstantValue.exactly(data)));
    }
}