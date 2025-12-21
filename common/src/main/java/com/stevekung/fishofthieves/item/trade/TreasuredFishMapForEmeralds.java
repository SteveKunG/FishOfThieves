package com.stevekung.fishofthieves.item.trade;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import com.stevekung.fishofthieves.registry.FOTPoiTypes;
import com.stevekung.fishofthieves.shoal.ShoalSpawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public record TreasuredFishMapForEmeralds(int emeraldCost, String displayName, int maxUses, int villagerXp, int tier) implements VillagerTrades.ItemListing
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
            var farthest = ShoalSpawner.findFarthest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), trader.blockPosition(), 50, 100, serverLevel.getPoiManager());

            if (farthest.isPresent())
            {
                var blockPos = farthest.get();
                var itemStack = createTreasuredFishMap(serverLevel, blockPos, this.displayName, this.tier);
                FishOfThieves.LOGGER.debug("Found farthest shoal at: {}", blockPos);
                return new MerchantOffer(new ItemCost(Items.EMERALD, this.emeraldCost), Optional.of(new ItemCost(Items.COMPASS)), itemStack, this.maxUses, this.villagerXp, 0.2F);
            }
            else
            {
                var attemptPos = ShoalSpawner.attemptSpawnShoal(serverLevel, trader.blockPosition(), 10);

                if (attemptPos != null)
                {
                    FishOfThieves.LOGGER.debug("Shoal spawn from map by fisherman at: {}", attemptPos);
                    var itemStack = createTreasuredFishMap(serverLevel, attemptPos, this.displayName, this.tier);
                    return new MerchantOffer(new ItemCost(Items.EMERALD, this.emeraldCost), Optional.of(new ItemCost(Items.COMPASS)), itemStack, this.maxUses, this.villagerXp, 0.2F);
                }
                else
                {
                    var nearest = serverLevel.getPoiManager().findClosest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), trader.blockPosition(), 100, PoiManager.Occupancy.ANY);
                    var blockPos = nearest.get();
                    var itemStack = createTreasuredFishMap(serverLevel, blockPos, this.displayName, this.tier);
                    FishOfThieves.LOGGER.debug("Found nearest shoal at: {}", blockPos);
                    return new MerchantOffer(new ItemCost(Items.EMERALD, this.emeraldCost), Optional.of(new ItemCost(Items.COMPASS)), itemStack, this.maxUses, this.villagerXp, 0.2F);
                }
            }
        }
    }

    private static ItemStack createTreasuredFishMap(ServerLevel serverLevel, BlockPos blockPos, String displayName, int tier)
    {
        var itemStack = MapItem.create(serverLevel, blockPos.getX(), blockPos.getZ(), (byte) 1, true, true);
        MapItem.renderBiomePreviewMap(serverLevel, itemStack);
        MapItemSavedData.addTargetDecoration(itemStack, blockPos, "+", FOTMapDecorationTypes.TREASURED_FISH);
        itemStack.set(DataComponents.ITEM_NAME, Component.translatable(displayName));
        Shoal.setTreasuredShoal(serverLevel, blockPos, tier);
        return itemStack;
    }
}