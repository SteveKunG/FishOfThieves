package com.stevekung.fishofthieves.fabric.asm;

import com.stevekung.fishofthieves.registry.FOTGrassColorModifier;

public class TropicalIslandGrassColorModifier extends FOTGrassModifier
{
    @Override
    public int modifyColor(double x, double z, int grassColor)
    {
        return FOTGrassColorModifier.getGrassColor(x, z);
    }
}