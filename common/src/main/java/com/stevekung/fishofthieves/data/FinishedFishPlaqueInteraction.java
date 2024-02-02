package com.stevekung.fishofthieves.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public interface FinishedFishPlaqueInteraction
{
    void serializeInteractionData(JsonObject json);

    default JsonObject serializeInteraction()
    {
        var jsonObject = new JsonObject();
        this.serializeInteractionData(jsonObject);
        return jsonObject;
    }

    ResourceLocation id();
}