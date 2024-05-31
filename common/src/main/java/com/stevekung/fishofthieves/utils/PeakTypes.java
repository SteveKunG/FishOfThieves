package com.stevekung.fishofthieves.utils;

import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum PeakTypes implements StringRepresentable
{
    LOW("Low"),
    MID("Mid"),
    HIGH("High"),
    PEAK("Peak"),
    VALLEY("Valley");

    public static final Codec<PeakTypes> CODEC = StringRepresentable.fromEnum(PeakTypes::values);
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
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static PeakTypes byName(String name)
    {
        return BY_NAME.get(name);
    }
}