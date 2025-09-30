package com.stevekung.fishofthieves.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class FishPlaqueBlockEntityRenderState extends BlockEntityRenderState
{
    public EntityRenderState displayEntity;
    public boolean isHorizontal;
    public float scale;
}