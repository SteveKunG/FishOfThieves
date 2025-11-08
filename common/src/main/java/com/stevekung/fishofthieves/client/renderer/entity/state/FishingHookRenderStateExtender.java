package com.stevekung.fishofthieves.client.renderer.entity.state;

import net.minecraft.client.renderer.item.ItemStackRenderState;

public interface FishingHookRenderStateExtender
{
    default ItemStackRenderState fishofthieves$getBaitStack()
    {
        throw new AssertionError("Implemented via mixin");
    }
}