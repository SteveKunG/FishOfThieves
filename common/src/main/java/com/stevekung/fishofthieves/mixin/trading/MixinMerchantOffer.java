package com.stevekung.fishofthieves.mixin.trading;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.item.trade.TreasuredFishMapRestock;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

@Mixin(MerchantOffer.class)
public class MixinMerchantOffer implements TreasuredFishMapRestock
{
    @Shadow
    @Final
    @Mutable
    ItemStack result;

    @Shadow
    int uses;

    @Unique
    private boolean isTreasuredMap;

    @Unique
    private int tier;

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    private void fishofthieves$init(CompoundTag compoundTag, CallbackInfo info)
    {
        if (compoundTag.contains(TreasuredFishMapRestock.IS_TREASURED_MAP_TAG, CompoundTag.TAG_BYTE))
        {
            this.isTreasuredMap = compoundTag.getBoolean(TreasuredFishMapRestock.IS_TREASURED_MAP_TAG);
        }
        if (compoundTag.contains(TreasuredFishMapRestock.TIER_TAG, CompoundTag.TAG_INT))
        {
            this.tier = compoundTag.getInt(TreasuredFishMapRestock.TIER_TAG);
        }
    }

    @Inject(method = "createTag", at = @At("TAIL"))
    private void fishofthieves$createTag(CallbackInfoReturnable<CompoundTag> info, @Local CompoundTag compoundTag)
    {
        if (this.isTreasuredMap)
        {
            compoundTag.putBoolean(TreasuredFishMapRestock.IS_TREASURED_MAP_TAG, true);
            compoundTag.putInt(TreasuredFishMapRestock.TIER_TAG, this.tier);
        }
    }

    @Override
    public boolean fishofthieves$isTreasuredFishMap()
    {
        return this.isTreasuredMap;
    }

    @Override
    public void fishofthieves$setIsTreasuredFishMap(boolean isTreasuredMap)
    {
        this.isTreasuredMap = isTreasuredMap;
    }

    @Override
    public void fishofthieves$setResult(ItemStack result)
    {
        this.result = result;
    }

    @Override
    public int fishofthieves$getTier()
    {
        return this.tier;
    }

    @Override
    public void fishofthieves$setTier(int tier)
    {
        this.tier = tier;
    }

    @Override
    public void fishofthieves$setUses(int uses)
    {
        this.uses = uses;
    }
}