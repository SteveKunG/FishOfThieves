package com.stevekung.fishofthieves.loot.predicate;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public record FOTLocationPredicate(Optional<Continentalness> continentalness, Optional<Boolean> hasRaids)
{
    public static final Codec<FOTLocationPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(Continentalness.CODEC.optionalFieldOf("continentalness").forGetter(FOTLocationPredicate::continentalness), Codec.BOOL.optionalFieldOf("has_raids").forGetter(FOTLocationPredicate::hasRaids)).apply(instance, FOTLocationPredicate::new));

    public boolean matches(ServerLevel level, double x, double y, double z)
    {
        var blockPos = BlockPos.containing(x, y, z);
        var loaded = level.isLoaded(blockPos);

        if (loaded)
        {
            return false;
        }

        if (this.continentalness.isPresent())
        {
            return this.continentalness.get() == TerrainUtils.getContinentalness(level, blockPos);
        }
        else if (this.hasRaids.isPresent())
        {
            return this.hasRaids.get() == level.isRaided(blockPos);
        }
        return false;
    }

    public static class Builder
    {
        private Optional<Continentalness> continentalness = Optional.empty();
        private Optional<Boolean> hasRaids = Optional.empty();

        public static Builder location()
        {
            return new Builder();
        }

        public Builder setContinentalness(Continentalness continentalness)
        {
            this.continentalness = Optional.of(continentalness);
            return this;
        }

        public Builder hasRaids()
        {
            this.hasRaids = Optional.of(true);
            return this;
        }

        public FOTLocationPredicate build()
        {
            return new FOTLocationPredicate(this.continentalness, this.hasRaids);
        }
    }
}