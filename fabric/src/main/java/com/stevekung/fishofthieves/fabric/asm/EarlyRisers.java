package com.stevekung.fishofthieves.fabric.asm;

import com.chocohead.mm.api.ClassTinkerers;

import net.fabricmc.loader.api.FabricLoader;

public class EarlyRisers implements Runnable
{
    @Override
    public void run()
    {
        var remapper = FabricLoader.getInstance().getMappingResolver();

        var grassColorModifier = remapper.mapClassName("intermediary", "net.minecraft.class_4763$class_5486");
        ClassTinkerers.enumBuilder(grassColorModifier, String.class).addEnumSubclass("FISHOFTHIEVES_TROPICAL_ISLAND", "com.stevekung.fishofthieves.fabric.asm.TropicalIslandGrassColorModifier", "fishofthieves_tropical_island").build();
    }
}