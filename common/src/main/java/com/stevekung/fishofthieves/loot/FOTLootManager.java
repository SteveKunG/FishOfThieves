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
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

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
        var coastBeach = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.BEACH).setContinentalness(Continentalness.COAST)).build().test(lootContext);
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
}