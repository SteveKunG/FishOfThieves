package com.stevekung.fishofthieves.mixin.client.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.client.entity.FishPlaqueDisplay;

import net.minecraft.world.entity.Entity;

@Mixin(Entity.class)
public class MixinEntity implements FishPlaqueDisplay
{
    @Unique
    private boolean fishofthieves$inFishPlaque;

    @Override
    public void fishofthieves$setIsInFishPlaque(boolean inFishPlaque)
    {
        this.fishofthieves$inFishPlaque = inFishPlaque;
    }

    @Inject(method = "isInWater", cancellable = true, at = @At("HEAD"))
    private void fishofthieves$isInWaterForFishPlaque(CallbackInfoReturnable<Boolean> info)
    {
        if (this.fishofthieves$inFishPlaque)
        {
            info.setReturnValue(true);
        }
    }
}