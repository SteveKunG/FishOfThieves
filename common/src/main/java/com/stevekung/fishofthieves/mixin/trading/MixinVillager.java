package com.stevekung.fishofthieves.mixin.trading;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.item.trade.TreasuredFishMapForEmeralds;
import com.stevekung.fishofthieves.item.trade.TreasuredFishMapRestock;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.MerchantOffer;

@Mixin(Villager.class)
public abstract class MixinVillager extends AbstractVillager
{
    MixinVillager()
    {
        super(null, null);
    }

    @Inject(method = { "restock", "catchUpDemand" }, cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/world/item/trading/MerchantOffer.resetUses()V"))
    private void fishofthieves$treasuredFishMapRestock(CallbackInfo info, @Local MerchantOffer merchantOffer)
    {
        var treasuredFishOffer = (TreasuredFishMapRestock) merchantOffer;
        var isTreasuredFishMap = treasuredFishOffer.fishofthieves$isTreasuredFishMap();

        if (isTreasuredFishMap)
        {
            var pair = TreasuredFishMapForEmeralds.getTreasuredFishMap((ServerLevel) this.level(), this, Shoal.FILLED_MAP_TREASURED_FISH, treasuredFishOffer.fishofthieves$getTier());

            if (pair != null)
            {
                treasuredFishOffer.fishofthieves$setResult(pair.getFirst());
                merchantOffer.getBaseCostA().setCount(pair.getSecond());
                FishOfThieves.LOGGER.info("Villager {} has restocked at {}", this, this.blockPosition().toShortString());
            }
            else
            {
                // Do not reset uses until new treasured fish location is available
                FishOfThieves.LOGGER.info("Villager {} at {} cannot restock due to no suitable locations for shoal", this, this.blockPosition().toShortString());
                treasuredFishOffer.fishofthieves$setUses(merchantOffer.getUses());
                info.cancel();
            }
        }
    }
}