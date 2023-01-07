package com.stevekung.fishofthieves.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.MobBucketItem;

@Mixin(MobBucketItem.class)
public interface MobBucketItemAccessor
{
    @Invoker(remap = false)
    EntityType<?> invokeGetFishType();

    @Invoker(remap = false)
    SoundEvent invokeGetEmptySound();
}