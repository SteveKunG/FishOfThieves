package com.stevekung.fishofthieves.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.entity.PartyFish;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer
{
    @Shadow
    ClientLevel level;

    @Inject(method = "playJukeboxSong", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/LevelRenderer.notifyNearbyEntities(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Z)V"))
    private void fishofthieves$notifyNearbyFish(Holder<JukeboxSong> holder, BlockPos blockPos, CallbackInfo info)
    {
        for (var livingEntity : this.level.getEntitiesOfClass(LivingEntity.class, new AABB(blockPos).inflate(GameEvent.JUKEBOX_PLAY.value().notificationRadius()), PartyFish.class::isInstance))
        {
            livingEntity.setRecordPlayingNearby(blockPos, true);
        }
    }
}