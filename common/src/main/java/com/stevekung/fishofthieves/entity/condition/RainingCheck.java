package com.stevekung.fishofthieves.entity.condition;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record RainingCheck(Optional<Boolean> thundering) implements SpawnCondition
{
    public static final MapCodec<RainingCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("thundering").forGetter(RainingCheck::thundering)).apply(instance, RainingCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        var level = context.level().getLevel();
        return level.isRaining() || this.thundering.isPresent() && this.thundering.get() == level.isThundering();
    }

    public static RainingCheck.Builder raining()
    {
        return new RainingCheck.Builder();
    }

    public static class Builder
    {
        private Optional<Boolean> thundering = Optional.empty();

        public RainingCheck.Builder thundering(boolean thundering)
        {
            this.thundering = Optional.of(thundering);
            return this;
        }

        public RainingCheck build()
        {
            return new RainingCheck(this.thundering);
        }
    }
}