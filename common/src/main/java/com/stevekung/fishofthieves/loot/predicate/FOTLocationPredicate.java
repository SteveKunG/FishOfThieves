package com.stevekung.fishofthieves.loot.predicate;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.PeakTypes;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;

public record FOTLocationPredicate(Optional<Continentalness> continentalness, Optional<PeakTypes> peakType, Optional<Boolean> hasRaids, Optional<StructureRangeCondition> structureRangeCondition)
{
    public static final Codec<FOTLocationPredicate> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Continentalness.CODEC.optionalFieldOf("continentalness").forGetter(FOTLocationPredicate::continentalness),
                    PeakTypes.CODEC.optionalFieldOf("peak_type").forGetter(FOTLocationPredicate::peakType),
                    Codec.BOOL.optionalFieldOf("has_raids").forGetter(FOTLocationPredicate::hasRaids),
                    StructureRangeCondition.CODEC.optionalFieldOf("structure_range").forGetter(FOTLocationPredicate::structureRangeCondition))
            .apply(instance, FOTLocationPredicate::new));

    public boolean matches(ServerLevel level, @Nullable Entity entity, double x, double y, double z)
    {
        var blockPos = BlockPos.containing(x, y, z);
        var loaded = level.isLoaded(blockPos);
        var isRaided = level.isRaided(blockPos);
        return (this.structureRangeCondition.isEmpty() || loaded && this.isInRangeOfStructures(level, blockPos, entity, this.structureRangeCondition.get())) && (this.continentalness.isEmpty() || loaded && this.continentalness.get() == TerrainUtils.getContinentalness(level, blockPos)) && (this.hasRaids.isEmpty() || loaded && this.hasRaids.get() == isRaided);
    }

    private boolean isInRangeOfStructures(ServerLevel level, BlockPos blockPos, @Nullable Entity entity, StructureRangeCondition structureRangeCondition)
    {
        for (var structureHolder : structureRangeCondition.structures().stream().toList())
        {
            var structure = structureHolder.value();

            if (entity == null)
            {
                return level.structureManager().getStructureWithPieceAt(blockPos, structureRangeCondition.structures()).isValid();
            }
            else
            {
                for (var structureStart : level.structureManager().startsForStructure(SectionPos.of(blockPos), structure))
                {
                    return entity.getBoundingBox().inflate(structureRangeCondition.range().getValue()).intersects(AABB.of(structureStart.getBoundingBox()));
                }
            }
        }
        return false;
    }

    public static class Builder
    {
        private Optional<Continentalness> continentalness = Optional.empty();
        private Optional<PeakTypes> peakType = Optional.empty();
        private Optional<Boolean> hasRaids = Optional.empty();
        private Optional<StructureRangeCondition> structureRangeCondition = Optional.empty();

        public static Builder location()
        {
            return new Builder();
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

        public Builder setStructureInRange(HolderSet<Structure> structure, int range)
        {
            this.structureRangeCondition = Optional.of(new StructureRangeCondition(structure, ConstantInt.of(range)));
            return this;
        }

        public FOTLocationPredicate build()
        {
            return new FOTLocationPredicate(this.continentalness, this.peakType, this.hasRaids, this.structureRangeCondition);
        }
    }
}