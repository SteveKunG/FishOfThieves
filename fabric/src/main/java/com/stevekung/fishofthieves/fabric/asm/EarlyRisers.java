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
        ClassTinkerers.enumBuilder(grassColorModifier, String.class).addEnumSubclass("FISHOFTHIEVES_TROPICAL_ISLAND", "com.stevekung.fishofthieves.fabric.asm.TropicalIslandGrassColorModifier", "fishofthieves:tropical_island").build();

        var boatType = remapper.mapClassName("intermediary", "net.minecraft.class_1690$class_1692");
        var block = 'L' + remapper.mapClassName("intermediary", "net.minecraft.class_2248") + ';';
        ClassTinkerers.enumBuilder(boatType, block, String.class)
                .addEnum("FISHOFTHIEVES_COCONUT", () -> new Object[] { null, "fishofthieves_coconut"})
                .build();
    }
}