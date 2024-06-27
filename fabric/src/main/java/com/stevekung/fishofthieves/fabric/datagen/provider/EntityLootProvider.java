package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.loot.function.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
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
    public EntityLootProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, LootContextParamSets.ENTITY);
    }

    //@formatter:off
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        consumer.accept(FOTLootTables.Entities.FISH_BONE_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));

        simpleFishLoot(FOTEntities.SPLASHTAIL, FOTItems.SPLASHTAIL, consumer,
                FOTEntitySubPredicate.variant(SplashtailVariants.RUBY),
                FOTEntitySubPredicate.variant(SplashtailVariants.SUNNY),
                FOTEntitySubPredicate.variant(SplashtailVariants.INDIGO),
                FOTEntitySubPredicate.variant(SplashtailVariants.UMBER),
                FOTEntitySubPredicate.variant(SplashtailVariants.SEAFOAM));

        simpleFishLoot(FOTEntities.PONDIE, FOTItems.PONDIE, consumer,
                FOTEntitySubPredicate.variant(PondieVariants.CHARCOAL),
                FOTEntitySubPredicate.variant(PondieVariants.ORCHID),
                FOTEntitySubPredicate.variant(PondieVariants.BRONZE),
                FOTEntitySubPredicate.variant(PondieVariants.BRIGHT),
                FOTEntitySubPredicate.variant(PondieVariants.MOONSKY));

        simpleFishLoot(FOTEntities.ISLEHOPPER, FOTItems.ISLEHOPPER, consumer,
                FOTEntitySubPredicate.variant(IslehopperVariants.STONE),
                FOTEntitySubPredicate.variant(IslehopperVariants.MOSS),
                FOTEntitySubPredicate.variant(IslehopperVariants.HONEY),
                FOTEntitySubPredicate.variant(IslehopperVariants.RAVEN),
                FOTEntitySubPredicate.variant(IslehopperVariants.AMETHYST));

        simpleFishLoot(FOTEntities.ANCIENTSCALE, FOTItems.ANCIENTSCALE, consumer,
                FOTEntitySubPredicate.variant(AncientscaleVariants.ALMOND),
                FOTEntitySubPredicate.variant(AncientscaleVariants.SAPPHIRE),
                FOTEntitySubPredicate.variant(AncientscaleVariants.SMOKE),
                FOTEntitySubPredicate.variant(AncientscaleVariants.BONE),
                FOTEntitySubPredicate.variant(AncientscaleVariants.STARSHINE));

        simpleFishLoot(FOTEntities.PLENTIFIN, FOTItems.PLENTIFIN, consumer,
                FOTEntitySubPredicate.variant(PlentifinVariants.OLIVE),
                FOTEntitySubPredicate.variant(PlentifinVariants.AMBER),
                FOTEntitySubPredicate.variant(PlentifinVariants.CLOUDY),
                FOTEntitySubPredicate.variant(PlentifinVariants.BONEDUST),
                FOTEntitySubPredicate.variant(PlentifinVariants.WATERY));

        simpleFishLoot(FOTEntities.WILDSPLASH, FOTItems.WILDSPLASH, consumer,
                FOTEntitySubPredicate.variant(WildsplashVariants.RUSSET),
                FOTEntitySubPredicate.variant(WildsplashVariants.SANDY),
                FOTEntitySubPredicate.variant(WildsplashVariants.OCEAN),
                FOTEntitySubPredicate.variant(WildsplashVariants.MUDDY),
                FOTEntitySubPredicate.variant(WildsplashVariants.CORAL));

        simpleFishLoot(FOTEntities.DEVILFISH, FOTItems.DEVILFISH, consumer,
                FOTEntitySubPredicate.variant(DevilfishVariants.ASHEN),
                FOTEntitySubPredicate.variant(DevilfishVariants.SEASHELL),
                FOTEntitySubPredicate.variant(DevilfishVariants.LAVA),
                FOTEntitySubPredicate.variant(DevilfishVariants.FORSAKEN),
                FOTEntitySubPredicate.variant(DevilfishVariants.FIRELIGHT));

        simpleFishLoot(FOTEntities.BATTLEGILL, FOTItems.BATTLEGILL, consumer,
                FOTEntitySubPredicate.variant(BattlegillVariants.JADE),
                FOTEntitySubPredicate.variant(BattlegillVariants.SKY),
                FOTEntitySubPredicate.variant(BattlegillVariants.RUM),
                FOTEntitySubPredicate.variant(BattlegillVariants.SAND),
                FOTEntitySubPredicate.variant(BattlegillVariants.BITTERSWEET));

        simpleFishLoot(FOTEntities.WRECKER, FOTItems.WRECKER, consumer,
                FOTEntitySubPredicate.variant(WreckerVariants.ROSE),
                FOTEntitySubPredicate.variant(WreckerVariants.SUN),
                FOTEntitySubPredicate.variant(WreckerVariants.BLACKCLOUD),
                FOTEntitySubPredicate.variant(WreckerVariants.SNOW),
                FOTEntitySubPredicate.variant(WreckerVariants.MOON));

        simpleFishLoot(FOTEntities.STORMFISH, FOTItems.STORMFISH, consumer,
                FOTEntitySubPredicate.variant(StormfishVariants.ANCIENT),
                FOTEntitySubPredicate.variant(StormfishVariants.SHORES),
                FOTEntitySubPredicate.variant(StormfishVariants.WILD),
                FOTEntitySubPredicate.variant(StormfishVariants.SHADOW),
                FOTEntitySubPredicate.variant(StormfishVariants.TWILIGHT));
    }

    private static LootTable.Builder simpleFishLoot(Item item, EntityType<?> entityType, EntitySubPredicate... subPredicate)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(item)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(TrophyFishPredicate.trophy(true)))))
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(entityType).subPredicate(subPredicate[0])))
                        )
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

    @SuppressWarnings("deprecation")
    private static LootPoolSingletonContainer.Builder<?> dropWithVariant(Item item, EntityType<?> entityType, int variant, EntitySubPredicate subPredicate)
    {
        return LootItem.lootTableItem(item)
                .apply(SmeltItemFunction.smelted()
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(TrophyFishPredicate.trophy(true)))))
                .apply(SetNbtFunction.setTag(Util.make(new CompoundTag(), tag -> tag.putInt("CustomModelData", variant)))
                        .when(FishVariantLootConfigCondition.configEnabled()))
                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(entityType).subPredicate(subPredicate)));
    }
    //@formatter:on

    private static void simpleFishLoot(EntityType<?> entityType, Item item, BiConsumer<ResourceLocation, LootTable.Builder> consumer, EntitySubPredicate... subPredicate)
    {
        consumer.accept(entityType.getDefaultLootTable(), simpleFishLoot(item, entityType, subPredicate));
    }
}