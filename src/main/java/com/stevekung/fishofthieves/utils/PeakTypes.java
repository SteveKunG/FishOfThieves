package com.stevekung.fishofthieves.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.util.StringRepresentable;

public enum PeakTypes implements StringRepresentable
{
    LOW("Low"),
    MID("Mid"),
    HIGH("High"),
    PEAK("Peak"),
    VALLEY("Valley");

    private static final Map<String, PeakTypes> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(PeakTypes::getName, peakTypes -> peakTypes));
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