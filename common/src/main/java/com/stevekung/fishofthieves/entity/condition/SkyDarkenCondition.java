package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.advancements.critereon.MinMaxBounds;

public record SkyDarkenCondition(MinMaxBounds.Ints darken) implements SpawnCondition
{
    public static final MapCodec<SkyDarkenCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Ints.CODEC.fieldOf("darken").forGetter(SkyDarkenCondition::darken)).apply(instance, SkyDarkenCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.SKY_DARKEN;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return this.darken.matches(context.level().getSkyDarken());
    }

    public static Builder skyDarken(MinMaxBounds.Ints darken)
    {
        return () -> new SkyDarkenCondition(darken);
    }
}