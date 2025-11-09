package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record RandomChanceCheck(int chance) implements SpawnCondition
{
    public static final MapCodec<RandomChanceCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("chance").forGetter(RandomChanceCheck::chance)
    ).apply(instance, RandomChanceCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return context.level().getRandom().nextInt(this.chance) == 0;
    }

    public static SpawnCondition chance(int chance)
    {
        return new RandomChanceCheck(chance);
    }
}