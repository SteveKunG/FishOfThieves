package com.stevekung.fishofthieves.fabric.core;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import com.stevekung.fishofthieves.entity.animal.Devilfish;
import com.stevekung.fishofthieves.entity.animal.Wrecker;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTLootItemConditions;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FishOfThievesFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        FishOfThieves.init();
        FOTItems.init();
        FOTEntities.init();
        FishOfThieves.initCommon();
        FOTLootItemConditions.init();

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 1, FishOfThieves::getTierOneTrades);
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 2, FishOfThieves::getTierTwoTrades);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) ->
        {
            // Gameplay
            if (id.equals(BuiltInLootTables.FISHERMAN_GIFT))
            {
                tableBuilder.modifyPools(builder -> builder
                        .add(LootItem.lootTableItem(FOTItems.SPLASHTAIL))
                        .add(LootItem.lootTableItem(FOTItems.PONDIE))
                        .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER))
                        .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE))
                        .add(LootItem.lootTableItem(FOTItems.PLENTIFIN))
                        .add(LootItem.lootTableItem(FOTItems.WILDSPLASH))
                        .add(LootItem.lootTableItem(FOTItems.DEVILFISH))
                        .add(LootItem.lootTableItem(FOTItems.BATTLEGILL))
                        .add(LootItem.lootTableItem(FOTItems.WRECKER))
                        .add(LootItem.lootTableItem(FOTItems.STORMFISH))
                        .build());
            }
            else if (id.equals(BuiltInLootTables.FISHING_FISH))
            {
                tableBuilder.modifyPools(builder -> builder
                        .add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                                .setWeight(25)
                                .when(FOTLootItemConditions.IN_OCEAN))
                        .add(LootItem.lootTableItem(FOTItems.PONDIE)
                                .setWeight(45)
                                .when(FOTLootItemConditions.IN_RIVER.or(FOTLootItemConditions.IN_FOREST)))
                        .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                                .setWeight(60)
                                .when(FOTLootItemConditions.COAST))
                        .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                                .setWeight(70)
                                .when(FOTLootItemConditions.IN_LUKEWARM_OCEAN.or(FOTLootItemConditions.IN_DEEP_LUKEWARM_OCEAN)))
                        .add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                                .setWeight(65)
                                .when(FOTLootItemConditions.IN_LUKEWARM_OCEAN.or(FOTLootItemConditions.IN_DEEP_LUKEWARM_OCEAN).or(FOTLootItemConditions.IN_WARM_OCEAN)))
                        .add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                                .setWeight(75)
                                .when(FOTLootItemConditions.IN_LUSH_CAVES.or(FOTLootItemConditions.IN_JUNGLE)))
                        .add(LootItem.lootTableItem(FOTItems.DEVILFISH)
                                .setWeight(90)
                                .when(FOTLootItemConditions.IN_DRIPSTONE_CAVES))
                        .add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                                .setWeight(95)
                                .when(FOTLootItemConditions.IN_OCEAN_MONUMENTS.or(FOTLootItemConditions.IN_PILLAGER_OUTPOSTS).or(FOTLootItemConditions.RAID_ACTIVE)))
                        .add(LootItem.lootTableItem(FOTItems.WRECKER)
                                .setWeight(100)
                                .when(FOTLootItemConditions.IN_SHIPWRECKS))
                        .add(LootItem.lootTableItem(FOTItems.STORMFISH)
                                .setWeight(120)
                                .when(FOTLootItemConditions.THUNDERING))
                        .build());
            }
            // Entity Loot
            else if (id.equals(EntityType.POLAR_BEAR.getDefaultLootTable()))
            {
                tableBuilder.modifyPools(builder -> builder
                        .add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(6)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.PONDIE)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.DEVILFISH)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.WRECKER)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.STORMFISH)
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                        .build());
            }
            // Chests
            else if (id.equals(BuiltInLootTables.VILLAGE_FISHER))
            {
                tableBuilder.modifyPools(builder -> builder
                        .add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .add(LootItem.lootTableItem(FOTItems.PONDIE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f))))
                        .add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.DEVILFISH)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.WRECKER)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.STORMFISH)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .build());
            }
            else if (id.equals(BuiltInLootTables.BURIED_TREASURE))
            {
                tableBuilder.modifyPools(builder -> builder
                        .setRolls(ConstantValue.exactly(2.0f))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_SPLASHTAIL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 4.0f))))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_PONDIE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f))))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_ISLEHOPPER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f))))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_ANCIENTSCALE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_PLENTIFIN)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_WILDSPLASH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_DEVILFISH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_BATTLEGILL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_WRECKER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .add(LootItem.lootTableItem(FOTItems.COOKED_STORMFISH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .build());
            }
        });

        FabricDefaultAttributeRegistry.register(FOTEntities.SPLASHTAIL, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.PONDIE, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.ISLEHOPPER, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.ANCIENTSCALE, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.PLENTIFIN, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.WILDSPLASH, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.DEVILFISH, Devilfish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.BATTLEGILL, Battlegill.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.WRECKER, Wrecker.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.STORMFISH, AbstractFish.createAttributes());

        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_SPLASHTAILS), FOTEntities.SPLASHTAIL.getCategory(), FOTEntities.SPLASHTAIL, 15, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_PONDIES), FOTEntities.PONDIE.getCategory(), FOTEntities.PONDIE, 15, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_ISLEHOPPERS), FOTEntities.ISLEHOPPER.getCategory(), FOTEntities.ISLEHOPPER, 8, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_ANCIENTSCALES), FOTEntities.ANCIENTSCALE.getCategory(), FOTEntities.ANCIENTSCALE, 8, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_PLENTIFINS), FOTEntities.PLENTIFIN.getCategory(), FOTEntities.PLENTIFIN, 12, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_WILDSPLASH), FOTEntities.WILDSPLASH.getCategory(), FOTEntities.WILDSPLASH, 10, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_DEVILFISH), FOTEntities.DEVILFISH.getCategory(), FOTEntities.DEVILFISH, 4, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_BATTLEGILLS), FOTEntities.BATTLEGILL.getCategory(), FOTEntities.BATTLEGILL, 5, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_WRECKERS), FOTEntities.WRECKER.getCategory(), FOTEntities.WRECKER, 50, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.SPAWNS_STORMFISH).or(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE)), FOTEntities.STORMFISH.getCategory(), FOTEntities.STORMFISH, 12, 4, 8);
    }
}