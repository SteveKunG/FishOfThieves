package com.stevekung.fishofthieves.mixin.client.debug;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTDebugSubscriptions;

import net.minecraft.client.multiplayer.ClientDebugSubscriber;
import net.minecraft.util.debug.DebugSubscription;

@Mixin(ClientDebugSubscriber.class)
public class MixinClientDebugSubscriber
{
    @Shadow
    static void addFlag(Set<DebugSubscription<?>> subscriptions, DebugSubscription<?> subscription, boolean enabled)
    {
        throw new AssertionError();
    }

    @Inject(method = "requestedSubscriptions", at = @At("TAIL"))
    private void fishofthieves$addSubscriptions(CallbackInfoReturnable<Set<DebugSubscription<?>>> info, @Local Set<DebugSubscription<?>> set)
    {
        addFlag(set, FOTDebugSubscriptions.STRUCTURE_CENTER_POS, FishOfThieves.CONFIG.debug.enableStructureCenterPosRender);
    }
}