package com.stevekung.fishofthieves.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer
{
    @Inject(method = "notifyNearbyEntities", at = @At("HEAD"))
    private void notifyNearbyFishes(Level level, BlockPos blockPos, boolean playing, CallbackInfo info)
    {
        var list = level.getEntitiesOfClass(AbstractFish.class, new AABB(blockPos).inflate(8.0D));

        for (var fishEntity : list)
        {
            fishEntity.setRecordPlayingNearby(blockPos, playing);
        }
    }
}