package com.stevekung.fishofthieves.loot;

import java.util.List;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTLootItemConditions;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FOTLootManager
{
    public static final ResourceLocation EARTHWORMS_DROPS = new ResourceLocation(FishOfThieves.MOD_ID, "blocks/earthworms_drops");
    public static final ResourceLocation GRUBS_DROPS = new ResourceLocation(FishOfThieves.MOD_ID, "blocks/grubs_drops");
    public static final ResourceLocation LEECHES_DROPS = new ResourceLocation(FishOfThieves.MOD_ID, "blocks/leeches_drops");
    public static final ResourceLocation FISH_BONE_DROP = new ResourceLocation(FishOfThieves.MOD_ID, "entities/fish_bone_drop");

    public static void dropWorms(List<ItemStack> droppedList, BlockState blockState, LootTables lootTables, LootContext lootContext)
    {
        if (blockState.is(FOTTags.EARTHWORMS_DROPS) && !blockState.is(FOTTags.EARTHWORMS_DROP_BLACKLIST))
        {
            droppedList.addAll(getAlternateLootStack(lootContext, lootTables.get(FOTLootManager.EARTHWORMS_DROPS)));
        }
        if (blockState.is(FOTTags.GRUBS_DROPS))
        {
            droppedList.addAll(getAlternateLootStack(lootContext, lootTables.get(FOTLootManager.GRUBS_DROPS)));
        }
        if (blockState.is(FOTTags.LEECHES_DROPS))
        {
            droppedList.addAll(getAlternateLootStack(lootContext, lootTables.get(FOTLootManager.LEECHES_DROPS)));
        }
    }

    private static List<ItemStack> getAlternateLootStack(LootContext lootContext, LootTable lootTable)
    {
        return lootTable.getRandomItems(lootContext);
    }

    public static LootPool.Builder getFishermanGiftLoot(LootPool.Builder builder)
    {
        //@formatter:off
        return builder.add(LootItem.lootTableItem(FOTItems.SPLASHTAIL))
                .add(LootItem.lootTableItem(FOTItems.PONDIE))
                .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER))
                .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE))
                .add(LootItem.lootTableItem(FOTItems.PLENTIFIN))
                .add(LootItem.lootTableItem(FOTItems.WILDSPLASH))
                .add(LootItem.lootTableItem(FOTItems.DEVILFISH))
                .add(LootItem.lootTableItem(FOTItems.BATTLEGILL))
                .add(LootItem.lootTableItem(FOTItems.WRECKER))
                .add(LootItem.lootTableItem(FOTItems.STORMFISH));
        //@formatter:on
    }

    public static LootPool.Builder getFishingLoot(LootPool.Builder builder)
    {
        //@formatter:off
        return builder.add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .setWeight(25)
                        .when(FOTLootItemConditions.IN_OCEAN))
                .add(LootItem.lootTableItem(FOTItems.PONDIE)
                        .setWeight(25)
                        .when(FOTLootItemConditions.IN_RIVER.or(FOTLootItemConditions.IN_FOREST)))
                .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                        .setWeight(30)
                        .when(FOTLootItemConditions.COAST))
                .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .setWeight(30)
                        .when(FOTLootItemConditions.IN_LUKEWARM_OCEAN.or(FOTLootItemConditions.IN_DEEP_LUKEWARM_OCEAN)))
                .add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .setWeight(35)
                        .when(FOTLootItemConditions.IN_LUKEWARM_OCEAN.or(FOTLootItemConditions.IN_DEEP_LUKEWARM_OCEAN).or(FOTLootItemConditions.IN_WARM_OCEAN)))
                .add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .setWeight(35)
                        .when(FOTLootItemConditions.IN_LUSH_CAVES.or(FOTLootItemConditions.IN_JUNGLE)))
                .add(LootItem.lootTableItem(FOTItems.DEVILFISH)
                        .setWeight(40)
                        .when(FOTLootItemConditions.IN_DRIPSTONE_CAVES))
                .add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                        .setWeight(40)
                        .when(FOTLootItemConditions.IN_OCEAN_MONUMENTS.or(FOTLootItemConditions.IN_PILLAGER_OUTPOSTS).or(FOTLootItemConditions.HAS_RAIDS)))
                .add(LootItem.lootTableItem(FOTItems.WRECKER)
                        .setWeight(50)
                        .when(FOTLootItemConditions.IN_SHIPWRECKS.or(FOTLootItemConditions.IN_RUINED_PORTAL_OCEAN)))
                .add(LootItem.lootTableItem(FOTItems.STORMFISH)
                        .setWeight(50)
                        .when(FOTLootItemConditions.THUNDERING));
        //@formatter:on
    }

    public static LootPool.Builder getPolarBearLoot(LootPool.Builder builder)
    {
        //@formatter:off
        return builder.add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.PONDIE)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.DEVILFISH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.WRECKER)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.STORMFISH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))));
        //@formatter:on
    }

    public static LootPool.Builder getVillageFisherLoot(LootPool.Builder builder)
    {
        return builder.add(TagEntry.expandTag(FOTTags.THIEVES_FISH).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 5.0f))));
    }

    public static LootPool.Builder getBuriedTreasureLoot(LootPool.Builder builder)
    {
        return builder.setRolls(ConstantValue.exactly(2.0f)).add(TagEntry.expandTag(FOTTags.COOKED_THIEVES_FISH).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 5.0f))));
    }
}