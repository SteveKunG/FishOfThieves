package com.stevekung.fishofthieves.forge.proxy;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import com.google.common.collect.Lists;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import com.stevekung.fishofthieves.entity.animal.Devilfish;
import com.stevekung.fishofthieves.entity.animal.Wrecker;
import com.stevekung.fishofthieves.forge.registry.FOTLootItemConditionsForge;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.Util;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxyForge
{
    public void init()
    {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerAttributes);
    }

    public void commonSetup(FMLCommonSetupEvent event)
    {
    }

    public void clientSetup(FMLClientSetupEvent event)
    {
    }

    @SubscribeEvent
    public void registerLootTables(LootTableLoadEvent event)
    {
        var id = event.getName();
        var table = event.getTable();

        // Gameplay
        if (id.equals(BuiltInLootTables.FISHERMAN_GIFT))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(FOTItems.SPLASHTAIL))
                    .add(LootItem.lootTableItem(FOTItems.PONDIE))
                    .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER))
                    .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE))
                    .add(LootItem.lootTableItem(FOTItems.PLENTIFIN))
                    .add(LootItem.lootTableItem(FOTItems.WILDSPLASH))
                    .add(LootItem.lootTableItem(FOTItems.DEVILFISH))
                    .add(LootItem.lootTableItem(FOTItems.BATTLEGILL))
                    .add(LootItem.lootTableItem(FOTItems.WRECKER))
                    .add(LootItem.lootTableItem(FOTItems.STORMFISH));
            inject(table, "main", pool.entries);
        }
        else if (id.equals(BuiltInLootTables.FISHING_FISH))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                            .setWeight(25)
                            .when(FOTLootItemConditionsForge.IN_OCEAN))
                    .add(LootItem.lootTableItem(FOTItems.PONDIE)
                            .setWeight(45)
                            .when(FOTLootItemConditionsForge.IN_RIVER.or(FOTLootItemConditionsForge.IN_FOREST)))
                    .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                            .setWeight(60)
                            .when(FOTLootItemConditionsForge.COAST))
                    .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                            .setWeight(70)
                            .when(FOTLootItemConditionsForge.IN_LUKEWARM_OCEAN.or(FOTLootItemConditionsForge.IN_DEEP_LUKEWARM_OCEAN)))
                    .add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                            .setWeight(65)
                            .when(FOTLootItemConditionsForge.IN_LUKEWARM_OCEAN.or(FOTLootItemConditionsForge.IN_DEEP_LUKEWARM_OCEAN).or(FOTLootItemConditionsForge.IN_WARM_OCEAN)))
                    .add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                            .setWeight(75)
                            .when(FOTLootItemConditionsForge.IN_LUSH_CAVES.or(FOTLootItemConditionsForge.IN_JUNGLE)))
                    .add(LootItem.lootTableItem(FOTItems.DEVILFISH)
                            .setWeight(90)
                            .when(FOTLootItemConditionsForge.IN_DRIPSTONE_CAVES))
                    .add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                            .setWeight(95)
                            .when(FOTLootItemConditionsForge.IN_OCEAN_MONUMENTS.or(FOTLootItemConditionsForge.IN_PILLAGER_OUTPOSTS).or(FOTLootItemConditionsForge.RAID_ACTIVE)))
                    .add(LootItem.lootTableItem(FOTItems.WRECKER)
                            .setWeight(100)
                            .when(FOTLootItemConditionsForge.IN_SHIPWRECKS))
                    .add(LootItem.lootTableItem(FOTItems.STORMFISH)
                            .setWeight(120)
                            .when(FOTLootItemConditionsForge.THUNDERING));
            inject(table, "main", pool.entries);
        }
        // Entity Loot
        else if (id.equals(EntityType.POLAR_BEAR.getDefaultLootTable()))
        {
            var list = Lists.<LootPoolEntryContainer>newArrayList();

            list.add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(6)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
            list.add(LootItem.lootTableItem(FOTItems.PONDIE)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(3)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
            list.add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(2)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
            list.add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(2)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
            list.add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(2)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
            list.add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(1)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
            list.add(LootItem.lootTableItem(FOTItems.DEVILFISH)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(1)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
            list.add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(1)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
            list.add(LootItem.lootTableItem(FOTItems.WRECKER)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(1)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());
            list.add(LootItem.lootTableItem(FOTItems.STORMFISH)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .setWeight(1)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))).build());

            inject(table, "main", list);
        }
        // Chests
        else if (id.equals(BuiltInLootTables.VILLAGE_FISHER))
        {
            var villagerFisher = LootPool.lootPool()
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
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))));
            inject(table, "main", villagerFisher.entries);
        }
        else if (id.equals(BuiltInLootTables.BURIED_TREASURE))
        {
            var buriedTreasure = LootPool.lootPool()
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
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))));
            inject(table, "main", buriedTreasure.entries);
        }
    }

    @SubscribeEvent
    public void registerVillagerTrades(VillagerTradesEvent event)
    {
        if (event.getType() == VillagerProfession.FISHERMAN)
        {
            event.getTrades().put(1, Util.make(Lists.newArrayList(), FishOfThieves::getTierOneTrades));
            event.getTrades().put(2, Util.make(Lists.newArrayList(), FishOfThieves::getTierTwoTrades));
        }
    }

    public void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(FOTEntities.SPLASHTAIL, AbstractFish.createAttributes().build());
        event.put(FOTEntities.PONDIE, AbstractFish.createAttributes().build());
        event.put(FOTEntities.ISLEHOPPER, AbstractFish.createAttributes().build());
        event.put(FOTEntities.ANCIENTSCALE, AbstractFish.createAttributes().build());
        event.put(FOTEntities.PLENTIFIN, AbstractFish.createAttributes().build());
        event.put(FOTEntities.WILDSPLASH, AbstractFish.createAttributes().build());
        event.put(FOTEntities.DEVILFISH, Devilfish.createAttributes().build());
        event.put(FOTEntities.BATTLEGILL, Battlegill.createAttributes().build());
        event.put(FOTEntities.WRECKER, Wrecker.createAttributes().build());
        event.put(FOTEntities.STORMFISH, AbstractFish.createAttributes().build());
    }

    private static void inject(LootTable table, String poolName, List<LootPoolEntryContainer> entries)
    {
        var pool = table.getPool(poolName);
        pool.entries = ArrayUtils.addAll(pool.entries, entries.toArray(LootPoolEntryContainer[]::new));
    }
}