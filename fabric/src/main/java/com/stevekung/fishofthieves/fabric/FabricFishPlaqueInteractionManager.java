package com.stevekung.fishofthieves.fabric;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.data.FishPlaqueInteractionManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class FabricFishPlaqueInteractionManager extends FishPlaqueInteractionManager implements IdentifiableResourceReloadListener
{
    @Override
    public ResourceLocation getFabricId()
    {
        return FishOfThieves.res("fish_plaque_interactions");
    }
}