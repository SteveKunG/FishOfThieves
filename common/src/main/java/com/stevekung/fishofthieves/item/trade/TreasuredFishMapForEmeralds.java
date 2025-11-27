package com.stevekung.fishofthieves.item.trade;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import com.stevekung.fishofthieves.registry.FOTPoiTypes;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.VillagerTrades;
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
            var optional = serverLevel.getPoiManager().findClosest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), trader.blockPosition(), 100, PoiManager.Occupancy.ANY);

            if (optional.isPresent())
            {
                var blockPos = optional.get();
                var itemStack = MapItem.create(serverLevel, blockPos.getX(), blockPos.getZ(), (byte) 2, true, true);
                MapItem.renderBiomePreviewMap(serverLevel, itemStack);
                MapItemSavedData.addTargetDecoration(itemStack, blockPos, "+", FOTMapDecorationTypes.TREASURED_FISH);
                itemStack.set(DataComponents.ITEM_NAME, Component.translatable(this.displayName));
                Shoal.setTreasuredShoal(serverLevel, blockPos, this.tier);
                return new MerchantOffer(new ItemCost(Items.EMERALD, this.emeraldCost), Optional.of(new ItemCost(Items.COMPASS)), itemStack, this.maxUses, this.villagerXp, 0.2F);
            }
            else
            {
                return null;
            }
        }
    }
}