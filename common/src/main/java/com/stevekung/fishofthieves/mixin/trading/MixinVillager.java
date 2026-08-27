package com.stevekung.fishofthieves.mixin.trading;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.trade.RestockableVillager;
import com.stevekung.fishofthieves.item.trade.TreasuredFishMapRestock;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

@Mixin(Villager.class)
public abstract class MixinVillager extends AbstractVillager implements RestockableVillager
{
    MixinVillager()
    {
        super(null, null);
    }

    @Inject(method = { "restock", "catchUpDemand" }, at = @At(value = "INVOKE", target = "net/minecraft/world/entity/npc/villager/Villager.getOffers()Lnet/minecraft/world/item/trading/MerchantOffers;"))
    private void fishofthieves$treasuredFishMapRestock(CallbackInfo info, @Share("treasuredMapOfferIndex") LocalIntRef treasuredMapOfferIndexRef)
    {
        if (!(this.level() instanceof ServerLevel serverLevel))
        {
            return;
        }

        var data = this.fishofthieves$getRestockableDataSet();
        // Initial value
        treasuredMapOfferIndexRef.set(-1);

        for (var index = 0; index < this.getOffers().size(); index++)
        {
            var offer = this.getOffers().get(index);
            var finalIndex = index;
            var optional = data.stream().filter(restockableData -> restockableData.index() == finalIndex).findFirst();
            var posString = this.blockPosition().toShortString();

            if (optional.isPresent())
            {
                FishOfThieves.LOGGER.debug("Restock merchant offer at index {} with data: {}", index, optional.get());
                var treasuredFishOffer = (TreasuredFishMapRestock) offer;

                if (offer.isOutOfStock())
                {
                    var villagerData = Villager.class.cast(this).getVillagerData();
                    var profession = villagerData.profession().value();
                    var trades = profession.getTrades(villagerData.level());

                    if (trades != null)
                    {
                        var tradeSetOpt = this.registryAccess().lookupOrThrow(Registries.TRADE_SET).getOptional(trades);

                        if (tradeSetOpt.isEmpty())
                        {
                            FishOfThieves.LOGGER.debug("Missing expected trade set {}", trades);
                        }
                        else
                        {
                            var treasuredFishMapTrade = this.registryAccess().lookupOrThrow(Registries.VILLAGER_TRADE).getValue(optional.get().trade());
                            var lootContext = new LootContext.Builder(new LootParams.Builder(serverLevel)
                                    .withParameter(LootContextParams.ORIGIN, this.position())
                                    .withParameter(LootContextParams.THIS_ENTITY, this)
                                    .withParameter(LootContextParams.ADDITIONAL_COST_COMPONENT_ALLOWED, Unit.INSTANCE)
                                    .create(LootContextParamSets.VILLAGER_TRADE)).create(tradeSetOpt.get().randomSequence());

                            if (treasuredFishMapTrade != null)
                            {
                                var treasuredFishMapOffer = treasuredFishMapTrade.getOffer(lootContext);

                                if (treasuredFishMapOffer != null)
                                {
                                    treasuredFishOffer.fishofthieves$setResult(treasuredFishMapOffer.getResult());
                                    //TODO Set price based on distance
                                    //offer.getBaseCostA().setCount(pair.getSecond());
                                    FishOfThieves.LOGGER.debug("Villager {} has restocked at {}", this, posString);
                                }
                                else
                                {
                                    // Do not reset uses until new treasured fish location is available
                                    FishOfThieves.LOGGER.debug("Villager {} at {} cannot restock due to no suitable locations for shoal", this, posString);
                                    treasuredMapOfferIndexRef.set(index);
                                }
                            }
                        }
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