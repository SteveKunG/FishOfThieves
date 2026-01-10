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
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public record TreasuredFishMapForEmeralds(int farEmeraldCost, int nearEmeraldCost, String displayName, int maxUses, int villagerXp, int tier) implements VillagerTrades.ItemListing
{
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
            var pair = getTreasuredFishMap(serverLevel, trader, this.displayName, this.farEmeraldCost, this.nearEmeraldCost, this.tier);
            return pair == null ? null : new RestockableMerchantOffer(this.tier, new ItemStack(Items.EMERALD, pair.getSecond()), new ItemStack(Items.COMPASS), pair.getFirst(), this.maxUses, this.villagerXp, 0.2F);
        }
    }

    @Nullable
    public static Pair<ItemStack, Integer> getTreasuredFishMap(ServerLevel serverLevel, Entity trader, String displayName, int tier)
    {
        // Cost for restock
        return getTreasuredFishMap(serverLevel, trader, displayName, 14, 22, tier);
    }

    @Nullable
    public static Pair<ItemStack, Integer> getTreasuredFishMap(ServerLevel serverLevel, Entity trader, String displayName, int farEmeraldCost, int nearEmeraldCost, int tier)
    {
        Pair<ItemStack, Integer> pair = null;
        var farthest = ShoalSpawner.findFarthest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), trader.blockPosition(), 50, 100, serverLevel.getPoiManager());

        if (farthest.isPresent())
        {
            var blockPos = farthest.get();
            pair = Pair.of(createTreasuredFishMap(serverLevel, blockPos, displayName, tier), farEmeraldCost);
            FishOfThieves.LOGGER.debug("Found farthest shoal at: {}", blockPos);
        }
        else
        {
            var attemptPos = ShoalSpawner.attemptSpawnShoal(serverLevel, trader.blockPosition(), 10);

            if (attemptPos != null)
            {
                pair = Pair.of(createTreasuredFishMap(serverLevel, attemptPos, displayName, tier), farEmeraldCost);
                FishOfThieves.LOGGER.debug("Shoal spawn from map by fisherman at: {}", attemptPos);
            }
            else
            {
                var nearest = serverLevel.getPoiManager().findClosest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), trader.blockPosition(), 100, PoiManager.Occupancy.ANY);

                if (nearest.isPresent())
                {
                    var blockPos = nearest.get();
                    pair = Pair.of(createTreasuredFishMap(serverLevel, blockPos, displayName, tier), nearEmeraldCost);
                    FishOfThieves.LOGGER.debug("Found nearest shoal at: {}", blockPos);
                }
            }
        }
        return pair;
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