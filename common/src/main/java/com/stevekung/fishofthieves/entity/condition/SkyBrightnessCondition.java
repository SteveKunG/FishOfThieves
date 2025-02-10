package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.LightLayer;

public record SkyBrightnessCondition(MinMaxBounds.Ints brightness) implements SpawnCondition
{
    public static final MapCodec<SkyBrightnessCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Ints.CODEC.fieldOf("brightness").forGetter(SkyBrightnessCondition::brightness)).apply(instance, SkyBrightnessCondition::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return this.brightness.matches(context.level().getBrightness(LightLayer.SKY, context.pos()));
    }

    public static SpawnCondition skyBrightness(MinMaxBounds.Ints brightness)
    {
        return new SkyBrightnessCondition(brightness);
    }
}