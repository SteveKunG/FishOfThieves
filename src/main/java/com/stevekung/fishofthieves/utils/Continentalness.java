package com.stevekung.fishofthieves.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static final Map<String, Continentalness> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Continentalness::getName, continentalness -> continentalness));
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
        return this.name;
    }

    public static Continentalness byName(String name)
    {
        return BY_NAME.get(name);
    }
}