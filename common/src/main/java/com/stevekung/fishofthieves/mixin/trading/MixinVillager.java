package com.stevekung.fishofthieves.mixin.trading;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.item.trade.RestockableVillager;
import com.stevekung.fishofthieves.item.trade.TreasuredFishMapForEmeralds;
import com.stevekung.fishofthieves.item.trade.TreasuredFishMapRestock;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;

@Mixin(Villager.class)
public abstract class MixinVillager extends AbstractVillager implements RestockableVillager
{
    MixinVillager()
    {
        super(null, null);
    }

    @Inject(method = { "restock", "catchUpDemand" }, at = @At(value = "INVOKE", target = "net/minecraft/world/entity/npc/Villager.getOffers()Lnet/minecraft/world/item/trading/MerchantOffers;"))
    private void fishofthieves$treasuredFishMapRestock(CallbackInfo info, @Share("treasuredMapOfferIndex") LocalIntRef treasuredMapOfferIndexRef)
    {
        var data = this.fishofthieves$getRestockableDataSet();
        // Initial value
        treasuredMapOfferIndexRef.set(-1);

        for (var index = 0; index < this.getOffers().size(); index++)
        {
            var offer = this.getOffers().get(index);
            var finalIndex = index;
            var optional = data.stream().filter(restockableData -> restockableData.index() == finalIndex).findFirst();

            if (optional.isPresent())
            {
                FishOfThieves.LOGGER.debug("Restock merchant offer at index {} with data: {}", index, optional.get());

                var treasuredFishOffer = (TreasuredFishMapRestock) offer;

                if (offer.isOutOfStock())
                {
                    var pair = TreasuredFishMapForEmeralds.getTreasuredFishMap((ServerLevel) this.level(), this, Shoal.FILLED_MAP_TREASURED_FISH, optional.get().tier());

                    if (pair != null)
                    {
                        treasuredFishOffer.fishofthieves$setResult(pair.getFirst());
                        offer.getBaseCostA().setCount(pair.getSecond());
                        FishOfThieves.LOGGER.debug("Villager {} has restocked at {}", this, this.blockPosition().toShortString());
                    }
                    else
                    {
                        // Do not reset uses until new treasured fish location is available
                        FishOfThieves.LOGGER.debug("Villager {} at {} cannot restock due to no suitable locations for shoal", this, this.blockPosition().toShortString());
                        treasuredMapOfferIndexRef.set(index);
                    }
                }
            }
        }
    }

    @Inject(method = { "restock", "catchUpDemand" }, cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/world/item/trading/MerchantOffer.resetUses()V"))
    private void fishofthieves$storeOfferIndexAndCancelResetUses(CallbackInfo info, @Share("offerIndex") LocalIntRef offerIndexRef, @Share("treasuredMapOfferIndex") LocalIntRef treasuredMapOfferIndexRef)
    {
        var offerIndex = offerIndexRef.get();
        offerIndexRef.set(offerIndex + 1);
        FishOfThieves.LOGGER.debug("offerIndex: {}, treasuredMapOfferIndex: {}", offerIndex, treasuredMapOfferIndexRef.get());

        if (offerIndex == treasuredMapOfferIndexRef.get())
        {
            FishOfThieves.LOGGER.debug("Prevent reset uses at index: {}", offerIndex);
            info.cancel();
        }
    }
}