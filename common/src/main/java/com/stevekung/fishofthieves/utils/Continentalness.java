package com.stevekung.fishofthieves.utils;

import java.util.Locale;

import net.minecraft.util.StringRepresentable;

public enum Continentalness implements StringRepresentable
{
    COAST("Coast"),
    OCEAN("Ocean"),
    DEEP_OCEAN("Deep ocean"),
    NEAR_INLAND("Near inland"),
    MID_INLAND("Mid inland"),
    FAR_INLAND("Far inland"),
    MUSHROOM_FIELDS("Mushroom fields");

    @SuppressWarnings("deprecation")
    public static final StringRepresentable.EnumCodec<Continentalness> CODEC = StringRepresentable.fromEnum(Continentalness::values);
    private final String name;

    Continentalness(String name)
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

    public static Continentalness byName(String name)
    {
        return CODEC.byName(name, OCEAN);
    }
}