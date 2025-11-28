package com.stevekung.fishofthieves.client.renderer.entity.state;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class ShoalRenderState extends EntityRenderState
{
    public List<? extends EntityRenderState> shoalFishClient = new ArrayList<>();
}