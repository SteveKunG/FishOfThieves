package com.stevekung.fishofthieves.block;

import java.util.Locale;

import net.minecraft.util.StringRepresentable;

@SuppressWarnings("deprecation")
public enum BananaHangingType implements StringRepresentable
{
    SMALL_CLUSTER, CLUSTER, STEM;

    public static final StringRepresentable.EnumCodec<BananaHangingType> CODEC = StringRepresentable.fromEnum(BananaHangingType::values);

    @Override
    public String getSerializedName()
    {
        return this.name().toLowerCase(Locale.ROOT);
    }
}