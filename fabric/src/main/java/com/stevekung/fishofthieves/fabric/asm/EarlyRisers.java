package com.stevekung.fishofthieves.fabric.asm;

import net.fabricmc.loader.api.FabricLoader;

import xyz.bluspring.fork.mm.api.ClassTinkerers;

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