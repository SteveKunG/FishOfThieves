package com.stevekung.fishofthieves.block;

import java.util.Locale;

import net.minecraft.util.StringRepresentable;

public enum BananaHangingType implements StringRepresentable
{
    SMALL_CLUSTER, CLUSTER, STEM;

    @Override
    public String getSerializedName()
    {
        return this.name().toLowerCase(Locale.ROOT);
    }
}