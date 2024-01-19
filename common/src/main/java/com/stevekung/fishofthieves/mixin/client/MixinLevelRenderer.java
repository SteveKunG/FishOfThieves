package com.stevekung.fishofthieves.mixin.client;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.entity.PartyFish;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer
{
    @Shadow
    ClientLevel level;

    @Inject(method = "playStreamingMusic", at = @At("TAIL"))
    private void fishofthieves$notifyNearbyFish(@Nullable SoundEvent soundEvent, BlockPos blockPos, CallbackInfo info)
    {
        for (var livingEntity : this.level.getEntitiesOfClass(LivingEntity.class, new AABB(blockPos).inflate(GameEvent.JUKEBOX_PLAY.getNotificationRadius()), PartyFish.class::isInstance))
        {
            livingEntity.setRecordPlayingNearby(blockPos, soundEvent != null);
        }
    }
}