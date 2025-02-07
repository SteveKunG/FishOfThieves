package com.stevekung.fishofthieves.mixin.client.accessor;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;

@Mixin(ModelLayers.class)
public interface ModelLayersAccessor
{
    @Accessor("ALL_MODELS")
    static Set<ModelLayerLocation> getAllModels()
    {
        throw new AssertionError("Implemented via mixin");
    }
}