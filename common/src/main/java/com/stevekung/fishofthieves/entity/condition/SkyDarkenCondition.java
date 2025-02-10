package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record SkyDarkenCondition(MinMaxBounds.Ints darken) implements SpawnCondition
{
    public static final MapCodec<SkyDarkenCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Ints.CODEC.fieldOf("darken").forGetter(SkyDarkenCondition::darken)).apply(instance, SkyDarkenCondition::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return this.darken.matches(context.level().getSkyDarken());
    }

    public static SpawnCondition skyDarken(MinMaxBounds.Ints darken)
    {
        return new SkyDarkenCondition(darken);
    }
}