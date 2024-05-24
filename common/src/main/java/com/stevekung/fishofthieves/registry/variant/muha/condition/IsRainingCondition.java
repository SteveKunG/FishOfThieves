package com.stevekung.fishofthieves.registry.variant.muha.condition;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.LivingEntity;

public record IsRainingCondition(Optional<Boolean> isThundering) implements SpawnCondition
{
    public static final MapCodec<IsRainingCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("thundering").forGetter(IsRainingCondition::isThundering)).apply(instance, IsRainingCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return SpawnConditions.IS_RAINING;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        var level = livingEntity.level();
        return level.isRaining() || this.isThundering.isPresent() && this.isThundering.get() == level.isThundering();
    }

    public static IsRainingCondition.Builder isRaining()
    {
        return new IsRainingCondition.Builder();
    }

    public static class Builder implements SpawnCondition.Builder
    {
        private Optional<Boolean> isThundering = Optional.empty();

        public IsRainingCondition.Builder setThundering(boolean isThundering)
        {
            this.isThundering = Optional.of(isThundering);
            return this;
        }

        @Override
        public IsRainingCondition build()
        {
            return new IsRainingCondition(this.isThundering);
        }
    }
}