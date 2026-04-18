package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.LightLayer;

public record SkyBrightnessCheck(MinMaxBounds.Ints brightness) implements SpawnCondition
{
    public static final MapCodec<SkyBrightnessCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Ints.CODEC.fieldOf("brightness").forGetter(SkyBrightnessCheck::brightness)).apply(instance, SkyBrightnessCheck::new));

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
        return new SkyBrightnessCheck(brightness);
    }
}