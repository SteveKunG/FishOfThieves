package com.stevekung.fishofthieves.loot.predicate;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.PeakTypes;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public record FOTLocationPredicate(Optional<TagKey<Biome>> biomes, Optional<TagKey<Structure>> structures, Optional<Continentalness> continentalness, Optional<PeakTypes> peakType, Optional<Boolean> hasRaids)
{
    public static final Codec<FOTLocationPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(TagKey.codec(Registries.BIOME).optionalFieldOf("biomes").forGetter(FOTLocationPredicate::biomes), TagKey.codec(Registries.STRUCTURE).optionalFieldOf("structures").forGetter(FOTLocationPredicate::structures), Continentalness.CODEC.optionalFieldOf("continentalness").forGetter(FOTLocationPredicate::continentalness), PeakTypes.CODEC.optionalFieldOf("peak_type").forGetter(FOTLocationPredicate::peakType), Codec.BOOL.optionalFieldOf("has_raids").forGetter(FOTLocationPredicate::hasRaids)).apply(instance, FOTLocationPredicate::new));

    public boolean matches(ServerLevel level, double x, double y, double z)
    {
        var blockPos = BlockPos.containing(x, y, z);
        var loaded = level.isLoaded(blockPos);

        if (!loaded)
        {
            return false;
        }

        if (this.biomes.isPresent())
        {
            return level.getBiome(blockPos).is(this.biomes.get());
        }
        else if (this.structures.isPresent())
        {
            return level.structureManager().getStructureWithPieceAt(blockPos, this.structures.get()).isValid();
        }
        else if (this.continentalness.isPresent())
        {
            return this.continentalness.get() == TerrainUtils.getContinentalness(level, blockPos);
        }
        else if (this.peakType.isPresent())
        {
            return this.peakType.get() == TerrainUtils.getPeakTypes(level, blockPos);
        }
        else if (this.hasRaids.isPresent())
        {
            return this.hasRaids.get() == level.isRaided(blockPos);
        }
        return false;
    }

    public static class Builder
    {
        private Optional<TagKey<Biome>> biomes = Optional.empty();
        private Optional<TagKey<Structure>> structures = Optional.empty();
        private Optional<Continentalness> continentalness = Optional.empty();
        private Optional<PeakTypes> peakType = Optional.empty();
        private Optional<Boolean> hasRaids = Optional.empty();

        public static Builder location()
        {
            return new Builder();
        }

        public Builder setBiomes(TagKey<Biome> biomes)
        {
            this.biomes = Optional.of(biomes);
            return this;
        }

        public Builder setStructures(TagKey<Structure> structures)
        {
            this.structures = Optional.of(structures);
            return this;
        }

        public Builder setContinentalness(Continentalness continentalness)
        {
            this.continentalness = Optional.of(continentalness);
            return this;
        }

        public Builder setPeakType(PeakTypes peakType)
        {
            this.peakType = Optional.of(peakType);
            return this;
        }

        public Builder hasRaids()
        {
            this.hasRaids = Optional.of(true);
            return this;
        }

        public FOTLocationPredicate build()
        {
            return new FOTLocationPredicate(this.biomes, this.structures, this.continentalness, this.peakType, this.hasRaids);
        }
    }
}