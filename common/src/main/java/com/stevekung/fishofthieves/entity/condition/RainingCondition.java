package com.stevekung.fishofthieves.entity.condition;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.world.entity.LivingEntity;

public record RainingCondition(Optional<Boolean> thundering) implements SpawnCondition
{
    public static final MapCodec<RainingCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("thundering").forGetter(RainingCondition::thundering)).apply(instance, RainingCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.IS_RAINING;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        var level = livingEntity.level();
        return level.isRaining() || this.thundering.isPresent() && this.thundering.get() == level.isThundering();
    }

    public static RainingCondition.Builder raining()
    {
        return new RainingCondition.Builder();
    }

    public static class Builder implements SpawnCondition.Builder
    {
        private Optional<Boolean> thundering = Optional.empty();

        public RainingCondition.Builder thundering(boolean thundering)
        {
            this.thundering = Optional.of(thundering);
            return this;
        }

        @Override
        public RainingCondition build()
        {
            return new RainingCondition(this.thundering);
        }
    }
}