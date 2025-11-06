package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.FishingHookBait;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

@Mixin(FishingHook.class)
public class MixinFishingHook implements FishingHookBait
{
    @Unique
    private ItemStack baitStack;

    @Override
    public void fishofthieves$setBaitStack(ItemStack itemStack)
    {
        this.baitStack = itemStack;
    }

    @Override
    public ItemStack fishofthieves$getBaitStack()
    {
        return this.baitStack;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void fishofthieves$saveBait(CompoundTag compound, CallbackInfo info)
    {
        if (!this.fishofthieves$getBaitStack().isEmpty())
        {
            compound.put(FishingHookBait.FISHING_HOOK_BAIT_TAG, this.fishofthieves$getBaitStack().save(new CompoundTag()));
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void fishofthieves$readBait(CompoundTag compound, CallbackInfo info)
    {
        var compoundTag = compound.getCompound(FishingHookBait.FISHING_HOOK_BAIT_TAG);

        if (!compoundTag.isEmpty())
        {
            var itemStack = ItemStack.of(compoundTag);

            if (itemStack.isEmpty())
            {
                FishOfThieves.LOGGER.warn("Unable to load item from: {}", compoundTag);
            }
            this.fishofthieves$setBaitStack(itemStack);
        }
    }
}