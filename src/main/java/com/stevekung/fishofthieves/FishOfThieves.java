package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.mixin.DispenserBlockAccessor;

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
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
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

    public static final CreativeModeTab FOT_TAB = FabricItemGroupBuilder.build(new ResourceLocation(MOD_ID, "main"), () -> new ItemStack(FOTItems.SPLASHTAIL));

    @Override
    public void onInitialize()
    {
        AutoConfig.register(FishOfThievesConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(FishOfThievesConfig.class).getConfig();

        FOTItems.init();
        FOTEntities.init();
        FOTSoundEvents.init();

        var bucket = DispenserBlockAccessor.getDispenserRegistry().get(Items.WATER_BUCKET);
        DispenserBlock.registerBehavior(FOTItems.SPLASHTAIL_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.PONDIE_BUCKET, bucket);

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 1, list -> list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.SPLASHTAIL, 6, FOTItems.COOKED_SPLASHTAIL, 6, 16, 5)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 2, list -> list.add(new VillagerTrades.EmeraldForItems(FOTItems.SPLASHTAIL, 13, 16, 20)));

        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) ->
        {
            var pools = ((LootSupplierBuilderHooks)supplier).getPools();

            // Gameplay
            if (id.equals(BuiltInLootTables.FISHERMAN_GIFT))
            {
                var pool = FabricLootPoolBuilder.of(pools.get(0))
                        .with(LootItem.lootTableItem(FOTItems.SPLASHTAIL))
                        .with(LootItem.lootTableItem(FOTItems.PONDIE));
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
                                .when(FOTLootItemConditions.IN_RIVER.or(FOTLootItemConditions.IN_FOREST)));
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
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))));
                supplier.withPool(villagerFisher);
            }
            else if (id.equals(BuiltInLootTables.BURIED_TREASURE))
            {
                var buriedTreasure = FabricLootPoolBuilder.builder()
                        .rolls(ConstantValue.exactly(2.0f))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_SPLASHTAIL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 4.0f))))
                        .with(LootItem.lootTableItem(FOTItems.COOKED_PONDIE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 4.0f))));
                supplier.withPool(buriedTreasure);
            }
        });

        FabricDefaultAttributeRegistry.register(FOTEntities.SPLASHTAIL, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.PONDIE, AbstractFish.createAttributes());

        SpawnRestrictionAccessor.callRegister(FOTEntities.SPLASHTAIL, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnRestrictionAccessor.callRegister(FOTEntities.PONDIE, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.BiomeCategory.OCEAN), FOTEntities.SPLASHTAIL.getCategory(), FOTEntities.SPLASHTAIL, 15, 8, 8);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.BiomeCategory.RIVER, Biome.BiomeCategory.FOREST), FOTEntities.PONDIE.getCategory(), FOTEntities.PONDIE, 15, 2, 4);
    }
}