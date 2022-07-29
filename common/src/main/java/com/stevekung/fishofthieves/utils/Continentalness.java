package com.stevekung.fishofthieves.utils;

import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final Map<String, Continentalness> BY_NAME = Stream.of(values()).collect(Collectors.toMap(Continentalness::getName, Function.identity()));
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
        return BY_NAME.get(name);
    }
}