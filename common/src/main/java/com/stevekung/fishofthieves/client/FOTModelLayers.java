package com.stevekung.fishofthieves.client;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.mixin.client.accessor.ModelLayersAccessor;

import net.minecraft.client.model.geom.ModelLayerLocation;

public class FOTModelLayers
{
    public static final ModelLayerLocation COCONUT_BOAT = register("boat/coconut");
    public static final ModelLayerLocation COCONUT_CHEST_BOAT = register("chest_boat/coconut");

    private static ModelLayerLocation register(String path)
    {
        return register(path, "main");
    }

    private static ModelLayerLocation createLocation(String path, String model)
    {
        return new ModelLayerLocation(FishOfThieves.id(path), model);
    }

    private static ModelLayerLocation register(String path, String model)
    {
        var modelLayerLocation = createLocation(path, model);

        if (!ModelLayersAccessor.getAllModels().add(modelLayerLocation))
        {
            throw new IllegalStateException("Duplicate registration for " + modelLayerLocation);
        }
        else
        {
            return modelLayerLocation;
        }
    }
}