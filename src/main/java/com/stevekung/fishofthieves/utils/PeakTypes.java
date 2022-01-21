package com.stevekung.fishofthieves.utils;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.util.StringRepresentable;

public enum PeakTypes implements StringRepresentable
{
    LOW("Low"),
    MID("Mid"),
    HIGH("High"),
    PEAK("Peak"),
    VALLEY("Valley");

    private static final Map<String, PeakTypes> BY_NAME = Stream.of(values()).collect(Collectors.toMap(PeakTypes::getName, Function.identity()));
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
        return this.name;
    }

    public static PeakTypes byName(String name)
    {
        return BY_NAME.get(name);
    }
}