package com.stevekung.fishofthieves.mixin.client.renderer.entity.state;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.stevekung.fishofthieves.client.renderer.entity.state.FishingHookRenderStateExtender;

import net.minecraft.client.renderer.entity.state.FishingHookRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Mixin(FishingHookRenderState.class)
public class MixinFishingHookRenderState implements FishingHookRenderStateExtender
{
    @Unique
    private final ItemStackRenderState baitStack = new ItemStackRenderState();

    @Override
    public ItemStackRenderState fishofthieves$getBaitStack()
    {
        return this.baitStack;
    }
}