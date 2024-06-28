package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.loot.function.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetCustomModelDataFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class EntityLootProvider extends SimpleFabricLootTableProvider
{
    public EntityLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider, LootContextParamSets.ENTITY);
    }

    //@formatter:off
    @Override
    public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>,LootTable.Builder> consumer)
    {
        consumer.accept(FOTLootTables.Entities.FISH_BONE_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));

        simpleFishLoot(FOTEntities.SPLASHTAIL, FOTItems.SPLASHTAIL, consumer,
                FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.RUBY)),
                FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.SUNNY)),
                FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.INDIGO)),
                FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.UMBER)),
                FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.SEAFOAM)));

        simpleFishLoot(FOTEntities.PONDIE, FOTItems.PONDIE, consumer,
                FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.CHARCOAL)),
                FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.ORCHID)),
                FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.BRONZE)),
                FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.BRIGHT)),
                FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.MOONSKY)));

        simpleFishLoot(FOTEntities.ISLEHOPPER, FOTItems.ISLEHOPPER, consumer,
                FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.STONE)),
                FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.MOSS)),
                FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.HONEY)),
                FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.RAVEN)),
                FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.AMETHYST)));

        simpleFishLoot(FOTEntities.ANCIENTSCALE, FOTItems.ANCIENTSCALE, consumer,
                FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.ALMOND)),
                FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.SAPPHIRE)),
                FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.SMOKE)),
                FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.BONE)),
                FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.STARSHINE)));

        simpleFishLoot(FOTEntities.PLENTIFIN, FOTItems.PLENTIFIN, consumer,
                FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.OLIVE)),
                FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.AMBER)),
                FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.CLOUDY)),
                FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.BONEDUST)),
                FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.WATERY)));

        simpleFishLoot(FOTEntities.WILDSPLASH, FOTItems.WILDSPLASH, consumer,
                FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.RUSSET)),
                FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.SANDY)),
                FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.OCEAN)),
                FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.MUDDY)),
                FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.CORAL)));

        simpleFishLoot(FOTEntities.DEVILFISH, FOTItems.DEVILFISH, consumer,
                FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.ASHEN)),
                FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.SEASHELL)),
                FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.LAVA)),
                FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.FORSAKEN)),
                FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.FIRELIGHT)));

        simpleFishLoot(FOTEntities.BATTLEGILL, FOTItems.BATTLEGILL, consumer,
                FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.JADE)),
                FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.SKY)),
                FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.RUM)),
                FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.SAND)),
                FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.BITTERSWEET)));

        simpleFishLoot(FOTEntities.WRECKER, FOTItems.WRECKER, consumer,
                FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.ROSE)),
                FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.SUN)),
                FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.BLACKCLOUD)),
                FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.SNOW)),
                FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.MOON)));

        simpleFishLoot(FOTEntities.STORMFISH, FOTItems.STORMFISH, consumer,
                FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.ANCIENT)),
                FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.SHORES)),
                FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.WILD)),
                FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.SHADOW)),
                FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.TWILIGHT)));
    }
    //@formatter:on

    private static <T> HolderSet<T> getValue(HolderLookup.Provider provider, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> resourceKey)
    {
        return HolderSet.direct(provider.lookupOrThrow(registryKey).getOrThrow(resourceKey));
    }

    private static void simpleFishLoot(EntityType<?> entityType, Item item, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer, EntitySubPredicate... subPredicate)
    {
        consumer.accept(entityType.getDefaultLootTable(), simpleFishLoot(item, entityType, subPredicate));
    }

    //@formatter:off
    private static LootTable.Builder simpleFishLoot(Item item, EntityType<?> entityType, EntitySubPredicate... subPredicate)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(dropWithVariant(item, entityType, 0, subPredicate[0]))
                        .add(dropWithVariant(item, entityType, 1, subPredicate[1]))
                        .add(dropWithVariant(item, entityType, 2, subPredicate[2]))
                        .add(dropWithVariant(item, entityType, 3, subPredicate[3]))
                        .add(dropWithVariant(item, entityType, 4, subPredicate[4]))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(Items.BONE_MEAL))
                        .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F)));
    }

    private static LootPoolSingletonContainer.Builder<?> dropWithVariant(Item item, EntityType<?> entityType, int variant, EntitySubPredicate subPredicate)
    {
        return LootItem.lootTableItem(item)
                .apply(SmeltItemFunction.smelted()
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(new TrophyFishPredicate(true)))))
                .apply(hasCustomModelData(variant)
                        .when(FishVariantLootConfigCondition.configEnabled()))
                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(entityType).subPredicate(subPredicate)));
    }
    //@formatter:on

    private static LootItemConditionalFunction.Builder<?> hasCustomModelData(int data)
    {
        return LootItemConditionalFunction.simpleBuilder(lootItemConditions -> new SetCustomModelDataFunction(lootItemConditions, ConstantValue.exactly(data)));
    }
}