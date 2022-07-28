package com.stevekung.fishofthieves.loot;

import java.util.List;

import com.stevekung.fishofthieves.predicates.FOTLocationCheck;
import com.stevekung.fishofthieves.predicates.FOTLocationPredicate;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FOTLootManager
{
    public static void dropWorms(List<ItemStack> droppedList, BlockState blockState, LootContext lootContext)
    {
        var waterPredicate = LocationPredicate.Builder.location().setFluid(FluidPredicate.Builder.fluid().of(FluidTags.WATER).build());
        var noSilkTouch = BlockLoot.HAS_NO_SILK_TOUCH.build().test(lootContext);
        var shouldDrop = BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f).build().test(lootContext);
        var waterSurrounded = LocationCheck.checkLocation(waterPredicate, new BlockPos(1, 0, 0))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(-1, 0, 0)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, 1)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, -1)))
                .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 1, 0))).build().test(lootContext);
        var coastBeach = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(BiomeTags.IS_BEACH).setContinentalness(Continentalness.COAST)).build().test(lootContext);
        var shouldDropLeeches = waterSurrounded && coastBeach;

        if (!noSilkTouch)
        {
            return;
        }

        if (shouldDrop && blockState.is(FOTTags.EARTHWORMS_DROPS))
        {
            droppedList.add(new ItemStack(FOTItems.EARTHWORMS));
        }
        if (shouldDrop && blockState.is(FOTTags.GRUBS_DROPS) && !waterSurrounded)
        {
            droppedList.add(new ItemStack(FOTItems.GRUBS));
        }
        if (shouldDrop && blockState.is(FOTTags.LEECHES_DROPS) && shouldDropLeeches)
        {
            droppedList.add(new ItemStack(FOTItems.LEECHES));
        }
    }

    public static LootPool.Builder getFishermanGiftLoot(LootPool.Builder builder)
    {
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
    }

    public static LootPool.Builder getPolarBearLoot(LootPool.Builder builder)
    {
        return builder.add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.PONDIE)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(6)
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
                        .setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(FOTItems.STORMFISH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                        .setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))));
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