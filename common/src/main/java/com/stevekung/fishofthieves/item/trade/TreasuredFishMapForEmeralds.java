package com.stevekung.fishofthieves.item.trade;

import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import com.stevekung.fishofthieves.registry.FOTPoiTypes;
import com.stevekung.fishofthieves.shoal.ShoalSpawner;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public record TreasuredFishMapForEmeralds(String displayName, int maxUses, int villagerXp, int tier) implements VillagerTrades.ItemListing
{
    private static final int TIER_1_MIN_EMERALD_COST = 12;
    private static final int TIER_1_MAX_EMERALD_COST = 32;
    private static final int TIER_2_MIN_EMERALD_COST = 16;
    private static final int TIER_2_MAX_EMERALD_COST = 48;
    private static final double MAX_SEARCH_DISTANCE = 100.0D;

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity trader, RandomSource random)
    {
        if (!(trader.level() instanceof ServerLevel serverLevel))
        {
            return null;
        }
        else
        {
            var pair = getTreasuredFishMap(serverLevel, trader, this.displayName, this.tier);
            return pair == null ? null : new RestockableMerchantOffer(this.tier, new ItemStack(Items.EMERALD, pair.getSecond()), new ItemStack(Items.COMPASS), pair.getFirst(), this.maxUses, this.villagerXp, 0.2F);
        }
    }

    @Nullable
    public static Pair<ItemStack, Integer> getTreasuredFishMap(ServerLevel serverLevel, Entity trader, String displayName, int tier)
    {
        Pair<ItemStack, Integer> pair = null;
        var traderPos = trader.blockPosition();
        var farthest = ShoalSpawner.findFarthest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), traderPos, 50, 100, serverLevel.getPoiManager());

        if (farthest.isPresent())
        {
            var blockPos = farthest.get();
            var cost = calculateEmeraldCost(tier, Math.sqrt(blockPos.distSqr(traderPos)));
            pair = Pair.of(createTreasuredFishMap(serverLevel, blockPos, displayName, tier), cost);
            FishOfThieves.LOGGER.debug("Found the farthest shoal at: {} (cost: {})", blockPos, cost);
        }
        else
        {
            var attemptPos = ShoalSpawner.attemptSpawnShoal(serverLevel, traderPos, 10);

            if (attemptPos != null)
            {
                var cost = calculateEmeraldCost(tier, Math.sqrt(attemptPos.distSqr(traderPos)));
                pair = Pair.of(createTreasuredFishMap(serverLevel, attemptPos, displayName, tier), cost);
                FishOfThieves.LOGGER.debug("Shoal spawn from map by fisherman at: {} (cost: {})", attemptPos, cost);
            }
            else
            {
                var nearest = serverLevel.getPoiManager().findClosest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), traderPos, 100, PoiManager.Occupancy.ANY);

                if (nearest.isPresent())
                {
                    var blockPos = nearest.get();
                    var cost = calculateEmeraldCost(tier, Math.sqrt(blockPos.distSqr(traderPos)));
                    pair = Pair.of(createTreasuredFishMap(serverLevel, blockPos, displayName, tier), cost);
                    FishOfThieves.LOGGER.debug("Found nearest shoal at: {} (cost: {})", blockPos, cost);
                }
            }
        }
        return pair;
    }

    /**
     * Calculates a dynamic emerald cost based on distance between trader and shoal.
     * Closer shoals are more expensive, farther shoals are cheaper.
     */
    private static int calculateEmeraldCost(int tier, double distance)
    {
        var minEmeraldCost = tier == 2 ? TIER_2_MIN_EMERALD_COST : TIER_1_MIN_EMERALD_COST;
        var maxEmeraldCost = tier == 2 ? TIER_2_MAX_EMERALD_COST : TIER_1_MAX_EMERALD_COST;
        var clampedDistance = Mth.clamp(distance, 0.0D, MAX_SEARCH_DISTANCE);
        var ratio = clampedDistance / MAX_SEARCH_DISTANCE;
        var cost = maxEmeraldCost - (int) Math.round(ratio * (maxEmeraldCost - minEmeraldCost));
        return Mth.clamp(cost, minEmeraldCost, maxEmeraldCost);
    }

    private static ItemStack createTreasuredFishMap(ServerLevel serverLevel, BlockPos blockPos, String displayName, int tier)
    {
        var itemStack = MapItem.create(serverLevel, blockPos.getX(), blockPos.getZ(), (byte) 1, true, true);
        MapItem.renderBiomePreviewMap(serverLevel, itemStack);
        MapItemSavedData.addTargetDecoration(itemStack, blockPos, "+", FOTMapDecorationTypes.TREASURED_FISH);
        itemStack.setHoverName(Component.translatable(displayName));
        Shoal.setTreasuredShoal(serverLevel, blockPos, tier);
        return itemStack;
    }
}