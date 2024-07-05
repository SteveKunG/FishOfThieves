package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.util.ExtraCodecs;

public record RandomChanceCondition(int chance) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<RandomChanceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("chance").forGetter(RandomChanceCondition::chance)
    ).apply(instance, RandomChanceCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.RANDOM_CHANCE;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.random().nextInt(this.chance) == 0;
    }

    public static Builder chance(int chance)
    {
        return () -> new RandomChanceCondition(chance);
    }
}