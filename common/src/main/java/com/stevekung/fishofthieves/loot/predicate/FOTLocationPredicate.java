package com.stevekung.fishofthieves.loot.predicate;

import java.util.Optional;

import org.jspecify.annotations.Nullable;

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
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;

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
        var random = level.getRandom();
        var structureRange = structureRangeCondition.range().sample(random);

        for (var structureHolder : structureRangeCondition.structures().stream().toList())
        {
            var structure = structureHolder.value();
            var isInsideStructure = level.structureManager().getStructureWithPieceAt(blockPos, structure).isValid();

            // If it has no source entity, just check if position is inside the structure
            if (entity == null)
            {
                return true;
            }
            else
            {
                // If we are inside the structure, just return true
                if (isInsideStructure)
                {
                    return true;
                }
                // Otherwise find nearest structure within radius in nearby chunks
                else
                {
                    var distFromStructure = Integer.MAX_VALUE;
                    var entityPos = entity.blockPosition();
                    var entityChunkPos = level.getChunk(entityPos).getPos();
                    Structure structure1 = null;
                    ChunkPos chunkPos1 = null;

                    for (var chunkPos : ChunkPos.rangeClosed(entityChunkPos, structureRangeCondition.chunkRadius().sample(random)).toList())
                    {
                        var structureRefMap = level.getChunk(chunkPos.x(), chunkPos.z(), ChunkStatus.STRUCTURE_STARTS).getAllReferences();
                        // Filtering structure within chunks from tag
                        var optional = structureRefMap.keySet().stream().filter(structurex -> structurex.equals(structure)).findAny();

                        if (optional.isPresent())
                        {
                            structure1 = optional.get();
                            chunkPos1 = chunkPos;
                        }
                    }

                    if (structure1 != null)
                    {
                        for (var structureStart : level.structureManager().startsForStructure(SectionPos.of(chunkPos1, 0), structure1))
                        {
                            var structureDist = structureStart.getPieces().stream().map(structurePiece -> structurePiece.getBoundingBox().getCenter().distManhattan(entityPos)).findAny().orElse(Integer.MAX_VALUE);

                            // Get nearest structure range
                            if (structureDist < distFromStructure)
                            {
                                distFromStructure = structureDist;
                            }

                            // If structure is exceed the range, skipped
                            if (distFromStructure > structureRange)
                            {
                                break;
                            }
                        }

                        // Found structure within radius
                        if (distFromStructure < structureRange)
                        {
                            return true;
                        }
                    }
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

        public Builder setStructureInRange(HolderSet<Structure> structure, int range, int chunkRadius)
        {
            this.structureRangeCondition = Optional.of(new StructureRangeCondition(structure, ConstantInt.of(range), ConstantInt.of(chunkRadius)));
            return this;
        }

        public FOTLocationPredicate build()
        {
            return new FOTLocationPredicate(this.continentalness, this.peakType, this.hasRaids, this.structureRangeCondition);
        }
    }
}