package com.stevekung.fishofthieves.loot.predicate;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public record FOTLocationPredicate(Optional<TagKey<Biome>> biome, Optional<TagKey<Structure>> structure, Optional<Continentalness> continentalness, Optional<Boolean> hasRaids)
{
    public static final Codec<FOTLocationPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(ExtraCodecs.strictOptionalField(TagKey.codec(Registries.BIOME), "biome").forGetter(FOTLocationPredicate::biome), ExtraCodecs.strictOptionalField(TagKey.codec(Registries.STRUCTURE), "structure").forGetter(FOTLocationPredicate::structure), ExtraCodecs.strictOptionalField(Continentalness.CODEC, "continentalness").forGetter(FOTLocationPredicate::continentalness), ExtraCodecs.strictOptionalField(Codec.BOOL, "has_raids").forGetter(FOTLocationPredicate::hasRaids)).apply(instance, FOTLocationPredicate::new));

    public boolean matches(ServerLevel level, double x, double y, double z)
    {
        var blockPos = BlockPos.containing(x, y, z);
        var loaded = level.isLoaded(blockPos);

        if (loaded)
        {
            return false;
        }

        if (this.biome.isPresent())
        {
            return level.getBiome(blockPos).is(this.biome.get());
        }
        else if (this.structure.isPresent())
        {
            var structureFeatureHolderSet = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getTag(this.structure.get());
            return structureFeatureHolderSet.isPresent() && this.structureMatched(level, blockPos, structureFeatureHolderSet.get());
        }
        else if (this.continentalness.isPresent())
        {
            return this.continentalness.get() == TerrainUtils.getContinentalness(level, blockPos);
        }
        else if (this.hasRaids.isPresent())
        {
            return this.hasRaids.get() == level.isRaided(blockPos);
        }
        return false;
    }

    private boolean structureMatched(ServerLevel level, BlockPos blockPos, HolderSet.Named<Structure> holders)
    {
        for (var holder : holders)
        {
            return level.structureManager().getStructureWithPieceAt(blockPos, holder.value()).isValid();
        }
        return false;
    }

    public static class Builder
    {
        private Optional<TagKey<Biome>> biome = Optional.empty();
        private Optional<TagKey<Structure>> structure = Optional.empty();
        private Optional<Continentalness> continentalness = Optional.empty();
        private Optional<Boolean> hasRaids = Optional.empty();

        public static Builder location()
        {
            return new Builder();
        }

        public Builder setBiome(TagKey<Biome> biome)
        {
            this.biome = Optional.of(biome);
            return this;
        }

        public Builder setStructure(TagKey<Structure> structure)
        {
            this.structure = Optional.of(structure);
            return this;
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
            return new FOTLocationPredicate(this.biome, this.structure, this.continentalness, this.hasRaids);
        }
    }
}