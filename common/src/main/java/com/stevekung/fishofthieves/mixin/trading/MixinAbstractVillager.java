package com.stevekung.fishofthieves.mixin.trading;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.trade.RestockableData;
import com.stevekung.fishofthieves.item.trade.RestockableVillager;
import com.stevekung.fishofthieves.item.trade.TreasuredFishMapRestock;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

@Mixin(AbstractVillager.class)
public abstract class MixinAbstractVillager extends AgeableMob implements RestockableVillager
{
    @Shadow
    @Nullable
    MerchantOffers offers;

    @Unique
    private Set<RestockableData> restockableDataSet = new LinkedHashSet<>();

    MixinAbstractVillager()
    {
        super(null, null);
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "INVOKE", target = "net/minecraft/world/level/storage/ValueOutput.store(Ljava/lang/String;Lcom/mojang/serialization/Codec;Ljava/lang/Object;)V", shift = At.Shift.AFTER))
    private void fishofthieves$saveRestockableData(ValueOutput output, CallbackInfo info)
    {
        this.validateTreasuredFishMapIndex(this.offers);
        output.store(TreasuredFishMapRestock.RESTOCKABLE_DATA_TAG, RestockableData.CODEC_LINKED_SET, this.restockableDataSet);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void fishofthieves$readRestockableData(ValueInput input, CallbackInfo info)
    {
        this.restockableDataSet = input.read(TreasuredFishMapRestock.RESTOCKABLE_DATA_TAG, RestockableData.CODEC_LINKED_SET).orElse(new LinkedHashSet<>());

        if (this.offers != null)
        {
            this.validateTreasuredFishMapIndex(this.offers);
        }
    }

    @Inject(method = "addOffersFromTradeSet", at = @At("TAIL"))
    private void fishofthieves$addRestockableData(ServerLevel level, MerchantOffers merchantOffers, ResourceKey<TradeSet> resourceKey, CallbackInfo info)
    {
        for (var index = 0; index < merchantOffers.size(); index++)
        {
            var offer = merchantOffers.get(index);

            if (offer instanceof TreasuredFishMapRestock restock && restock.fishofthieves$isTreasuredFishMap())
            {
                this.restockableDataSet.add(new RestockableData(index, restock.fishofthieves$getResourceKey()));
                FishOfThieves.LOGGER.debug("Restockable data added with index: {}, resourceKey: {}", index, restock.fishofthieves$getResourceKey());
            }
        }
    }

    @WrapOperation(method = "addOffersFromItemListings", at = @At(value = "INVOKE", target = "net/minecraft/world/item/trading/MerchantOffers.add(Ljava/lang/Object;)Z"))
    private static boolean fishofthieves$setIsTreasuredFishMapFromOffer(MerchantOffers merchantOffers, Object object, Operation<Boolean> operation, @Local Optional<Holder<VillagerTrade>> villagerTrade)
    {
        return operation.call(merchantOffers, temporarySetTreasuredFishMapData(object, villagerTrade.get()));
    }

    @WrapOperation(method = "addOffersFromItemListingsWithoutDuplicates", at = @At(value = "INVOKE", target = "net/minecraft/world/item/trading/MerchantOffers.add(Ljava/lang/Object;)Z"))
    private static boolean fishofthieves$setIsTreasuredFishMapFromDuplicateOffer(MerchantOffers merchantOffers, Object object, Operation<Boolean> operation, @Local Holder<VillagerTrade> villagerTrade)
    {
        return operation.call(merchantOffers, temporarySetTreasuredFishMapData(object, villagerTrade));
    }

    @Override
    public Set<RestockableData> fishofthieves$getRestockableDataSet()
    {
        return this.restockableDataSet;
    }

    @Unique
    @SuppressWarnings("deprecation")
    private void validateTreasuredFishMapIndex(MerchantOffers offers)
    {
        for (var index = 0; index < offers.size(); index++)
        {
            var offer = offers.get(index);
            var result = offer.getResult();
            var finalIndex = index;
            Function<RestockableData, Boolean> removeFunction = restockableData -> restockableData.index() == finalIndex;

            // Check if current offer index is not a filled map or does not contain map decoration component
            if (!result.is(Items.FILLED_MAP) || !result.has(DataComponents.MAP_DECORATIONS))
            {
                this.restockableDataSet.removeIf(removeFunction::apply);
            }
            // Check for filled map but isn't treasured fish map
            else if (result.is(Items.FILLED_MAP) && result.has(DataComponents.MAP_DECORATIONS))
            {
                var mapDecorations = result.get(DataComponents.MAP_DECORATIONS);

                for (var entry : mapDecorations.decorations().values())
                {
                    if (!entry.type().is(FOTMapDecorationTypes.TREASURED_FISH))
                    {
                        this.restockableDataSet.removeIf(removeFunction::apply);
                    }
                }
            }
        }
    }

    @Unique
    private static Object temporarySetTreasuredFishMapData(Object object, Holder<VillagerTrade> villagerTrade)
    {
        if (object instanceof TreasuredFishMapRestock restock && villagerTrade.is(FOTTags.VillagerTrades.TREASURED_FISH_MAP))
        {
            var resourceKey = villagerTrade.unwrapKey().orElseThrow();
            restock.fishofthieves$setIsTreasuredFishMap();
            restock.fishofthieves$setResourceKey(resourceKey);
            FishOfThieves.LOGGER.debug("Getting treasured fish map offer from: {}", resourceKey);
            return restock;
        }
        return object;
    }
}