package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;

public interface ScaleableModel<T extends Entity>
{
    void scale(T entity, PoseStack poseStack);
}