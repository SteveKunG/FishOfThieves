package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.mixin.DispenserBlockAccessor;
import com.stevekung.fishofthieves.registry.*;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.mixin.loot.table.LootPoolBuilderHooks;
import net.fabricmc.fabric.mixin.loot.table.LootSupplierBuilderHooks;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FishOfThieves implements ModInitializer
{
    public static final String MOD_ID = "fishofthieves";
    public static FishOfThievesConfig CONFIG;

    public static final CreativeModeTab FOT_TAB = FabricItemGroupBuilder.build(new ResourceLocation(MOD_ID, "main"), () -> new ItemStack(FOTItems.SPLASHTAIL_BUCKET));

    @Override
    public void onInitialize()
    {
        AutoConfig.register(FishOfThievesConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(FishOfThievesConfig.class).getConfig();

        FOTItems.init();
        FOTEntities.init();
        FOTSoundEvents.init();
        FOTCriteriaTriggers.init();

        var bucket = DispenserBlockAccessor.getDispenserRegistry().get(Items.WATER_BUCKET);
        DispenserBlock.registerBehavior(FOTItems.SPLASHTAIL_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.PONDIE_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.ISLEHOPPER_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.ANCIENTSCALE_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.PLENTIFIN_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.WILDSPLASH_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.DEVILFISH_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.BATTLEGILL_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.WRECKER_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.STORMFISH_BUCKET, bucket);

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 1, list ->
        {
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.SPLASHTAIL, 6, FOTItems.COOKED_SPLASHTAIL, 6, 16, 5));
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.PONDIE, 4, FOTItems.COOKED_PONDIE, 4, 13, 3));
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.ISLEHOPPER, 8, FOTItems.COOKED_ISLEHOPPER, 2, 6, 4));
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.ANCIENTSCALE, 10, FOTItems.COOKED_ANCIENTSCALE, 2, 4, 6));
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.PLENTIFIN, 9, FOTItems.COOKED_PLENTIFIN, 3, 5, 8));
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.WILDSPLASH, 5, FOTItems.COOKED_WILDSPLASH, 2, 6, 10));
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.DEVILFISH, 2, FOTItems.COOKED_DEVILFISH, 8, 4, 15));
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.BATTLEGILL, 2, FOTItems.COOKED_BATTLEGILL, 12, 4, 18));
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.WRECKER, 1, FOTItems.COOKED_WRECKER, 14, 4, 20));
            list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.STORMFISH, 1, FOTItems.COOKED_STORMFISH, 20, 5, 30));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 2, list ->
        {
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.SPLASHTAIL, 13, 16, 20));
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.PONDIE, 10, 12, 16));
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.ISLEHOPPER, 16, 8, 25));
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.ANCIENTSCALE, 16, 4, 28));
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.PLENTIFIN, 12, 6, 25));
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.WILDSPLASH, 10, 4, 26));
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.DEVILFISH, 12, 2, 30));
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.BATTLEGILL, 14, 2, 30));
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.WRECKER, 15, 1, 30));
            list.add(new VillagerTrades.EmeraldForItems(FOTItems.STORMFISH, 20, 1, 30));
        });

        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) ->
        {
            var pools = ((LootSupplierBuilderHooks)supplier).getPools();

            // Gameplay
            if (id.equals(BuiltInLootTables.FISHERMAN_GIFT))
            {
                var pool = FabricLootPoolBuilder.of(pools.get(0))
                        .with(LootItem.lootTableItem(FOTItems.SPLASHTAIL))
                        .with(LootItem.lootTableItem(FOTItems.PONDIE))
                        .with(LootItem.lootTableItem(FOTItems.ISLEHOPPER))
                        .with(LootItem.lootTableItem(FOTItems.ANCIENTSCALE))
                        .with(LootItem.lootTableItem(FOTItems.PLENTIFIN))
                        .with(LootItem.lootTableItem(FOTItems.WILDSPLASH))
                        .with(LootItem.lootTableItem(FOTItems.DEVILFISH))
                        .with(LootItem.lootTableItem(FOTItems.BATTLEGILL))
                        .with(LootItem.lootTableItem(FOTItems.WRECKER))
                        .with(LootItem.lootTableItem(FOTItems.STORMFISH));
                pools.set(0, pool.build());
            }
            else if (id.equals(BuiltInLootTables.FISHING_FISH))
            {
                var pool = FabricLootPoolBuilder.of(pools.get(0))
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
                                .when(FOTLootItemConditions.THUNDERING));
                pools.set(0, pool.build());
            }
            // Entity Loot
            else if (id.equals(EntityType.POLAR_BEAR.getDefaultLootTable()))
            {
                var pool = FabricLootPoolBuilder.of(pools.get(0));
                var builder = ((LootPoolBuilderHooks)pool).getEntries();

                builder.add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
                builder.add(LootItem.lootTableItem(FOTItems.PONDIE)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(3)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
                builder.add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(2)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
                builder.add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(2)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
                builder.add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(2)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
                builder.add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
                builder.add(LootItem.lootTableItem(FOTItems.DEVILFISH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
                builder.add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
                builder.add(LootItem.lootTableItem(FOTItems.WRECKER)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
                builder.add(LootItem.lootTableItem(FOTItems.STORMFISH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());

                pools.set(0, pool.build());
            }
            // Chests
            else if (id.equals(BuiltInLootTables.VILLAGE_FISHER))
            {
                var villagerFisher = FabricLootPoolBuilder.builder()
                        .with(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .with(LootItem.lootTableItem(FOTItems.PONDIE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .with(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .with(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .with(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f))))
                        .with(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.DEVILFISH)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.WRECKER)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.STORMFISH)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))));
                supplier.withPool(villagerFisher);
            }
            else if (id.equals(BuiltInLootTables.BURIED_TREASURE))
            {
                var buriedTreasure = FabricLootPoolBuilder.builder()
                        .rolls(ConstantValue.exactly(2.0f))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_SPLASHTAIL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 4.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_PONDIE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_ISLEHOPPER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_ANCIENTSCALE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_PLENTIFIN)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_WILDSPLASH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_DEVILFISH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_BATTLEGILL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_WRECKER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_STORMFISH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))));
                supplier.withPool(buriedTreasure);
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

        SpawnRestrictionAccessor.callRegister(FOTEntities.SPLASHTAIL, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.PONDIE, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.ISLEHOPPER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Islehopper::checkSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.ANCIENTSCALE, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.PLENTIFIN, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Plentifin::checkSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.WILDSPLASH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wildsplash::checkSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.DEVILFISH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Devilfish::checkSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.BATTLEGILL, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Battlegill::checkSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.WRECKER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wrecker::checkSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.STORMFISH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stormfish::checkSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.BiomeCategory.OCEAN), FOTEntities.SPLASHTAIL.getCategory(), FOTEntities.SPLASHTAIL, 15, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.BiomeCategory.RIVER, Biome.BiomeCategory.FOREST), FOTEntities.PONDIE.getCategory(), FOTEntities.PONDIE, 15, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.BiomeCategory.OCEAN, Biome.BiomeCategory.BEACH, Biome.BiomeCategory.JUNGLE, Biome.BiomeCategory.SWAMP, Biome.BiomeCategory.UNDERGROUND), FOTEntities.ISLEHOPPER.getCategory(), FOTEntities.ISLEHOPPER, 8, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), FOTEntities.ANCIENTSCALE.getCategory(), FOTEntities.ANCIENTSCALE, 8, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN).or(BiomeSelectors.all().and(BiomeSelectors.categories(Biome.BiomeCategory.UNDERGROUND))), FOTEntities.PLENTIFIN.getCategory(), FOTEntities.PLENTIFIN, 12, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUSH_CAVES, Biomes.WARM_OCEAN).or(BiomeSelectors.categories(Biome.BiomeCategory.OCEAN, Biome.BiomeCategory.BEACH, Biome.BiomeCategory.JUNGLE, Biome.BiomeCategory.SWAMP)), FOTEntities.WILDSPLASH.getCategory(), FOTEntities.WILDSPLASH, 10, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(BiomeSelectors.excludeByKey(Biomes.LUSH_CAVES)), FOTEntities.DEVILFISH.getCategory(), FOTEntities.DEVILFISH, 4, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.all(), FOTEntities.BATTLEGILL.getCategory(), FOTEntities.BATTLEGILL, 5, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.BiomeCategory.OCEAN), FOTEntities.WRECKER.getCategory(), FOTEntities.WRECKER, 50, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.BiomeCategory.OCEAN).or(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE)), FOTEntities.STORMFISH.getCategory(), FOTEntities.STORMFISH, 12, 4, 8);
    }
}