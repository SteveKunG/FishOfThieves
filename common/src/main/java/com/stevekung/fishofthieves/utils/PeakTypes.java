package com.stevekung.fishofthieves.utils;

import java.util.Locale;

import net.minecraft.util.StringRepresentable;

public enum PeakTypes implements StringRepresentable
{
    LOW("Low"),
    MID("Mid"),
    HIGH("High"),
    PEAK("Peak"),
    VALLEY("Valley");

    @SuppressWarnings("deprecation")
    public static final StringRepresentable.EnumCodec<PeakTypes> CODEC = StringRepresentable.fromEnum(PeakTypes::values);
    private final String name;

    PeakTypes(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public String getSerializedName()
    {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static PeakTypes byName(String name)
    {
        return CODEC.byName(name, LOW);
    }
}