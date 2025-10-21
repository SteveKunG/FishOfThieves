package com.stevekung.fishofthieves.loot.predicate;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public record FOTLocationPredicate(TagKey<Biome> biome, StructureRangeCondition structureRangeCondition, Continentalness continentalness, Boolean hasRaids)
{
    public static final FOTLocationPredicate ANY = new FOTLocationPredicate(null, null, null, null);

    public boolean matches(ServerLevel level, @Nullable Entity entity, double x, double y, double z)
    {
        var blockPos = BlockPos.containing(x, y, z);
        var loaded = level.isLoaded(blockPos);
        var isRaided = level.isRaided(blockPos);
        return (this.biome == null || loaded && level.getBiome(blockPos).is(this.biome)) && (this.structureRangeCondition == null || loaded && this.structureMatched(level, blockPos, entity, this.structureRangeCondition, level.registryAccess().registryOrThrow(Registries.STRUCTURE))) && (this.continentalness == null || loaded && this.continentalness == TerrainUtils.getContinentalness(level, blockPos)) && (this.hasRaids == null || loaded && this.hasRaids == isRaided);
    }

    private boolean structureMatched(ServerLevel level, BlockPos blockPos, @Nullable Entity entity, StructureRangeCondition structureRangeCondition, Registry<Structure> structureRegistry)
    {
        var structureHolderSet = structureRegistry.getTag(structureRangeCondition.structure());

        if (structureHolderSet.isPresent())
        {
            for (var structureHolder : structureHolderSet.get())
            {
                var structure = structureHolder.value();

                if (entity == null)
                {
                    return level.structureManager().getStructureWithPieceAt(blockPos, structure).isValid();
                }
                else
                {
                    for (var structureStart : level.structureManager().startsForStructure(SectionPos.of(blockPos), structure))
                    {
                        var entityDist = structureStart.getPieces().stream()
                                .map(structurePiece -> structurePiece.getBoundingBox().getCenter().distManhattan(entity.blockPosition()))
                                .findAny()
                                .orElse(Integer.MAX_VALUE);
                        return entityDist < structureRangeCondition.range().getValue();
                    }
                }
            }
        }
        return false;
    }

    public JsonElement serializeToJson()
    {
        if (this == ANY)
        {
            return JsonNull.INSTANCE;
        }

        var jsonObject = new JsonObject();

        if (this.biome != null)
        {
            jsonObject.addProperty("biome", this.biome.location().toString());
        }
        if (this.structureRangeCondition != null)
        {
            var jsonStructureObject = new JsonObject();
            jsonStructureObject.addProperty("structure", this.structureRangeCondition.structure().location().toString());
            jsonStructureObject.addProperty("range", this.structureRangeCondition.range().getValue());
            jsonObject.add("structure", jsonStructureObject);
        }
        if (this.continentalness != null)
        {
            jsonObject.addProperty("continentalness", this.continentalness.getSerializedName());
        }
        if (this.hasRaids != null)
        {
            jsonObject.addProperty("hasRaids", this.hasRaids);
        }
        return jsonObject;
    }

    public static FOTLocationPredicate fromJson(@Nullable JsonElement json)
    {
        if (json == null || json.isJsonNull())
        {
            return ANY;
        }

        var jsonObject = GsonHelper.convertToJsonObject(json, "location");
        TagKey<Biome> biome = null;
        StructureRangeCondition structureRangeCondition = null;
        Continentalness continentalness = null;
        Boolean hasRaids = null;

        if (jsonObject.has("biome"))
        {
            var string = GsonHelper.getAsString(jsonObject, "biome");
            biome = TagKey.create(Registries.BIOME, new ResourceLocation(string));
        }
        if (jsonObject.has("structure"))
        {
            var jsonStructureObject = GsonHelper.getAsJsonObject(jsonObject, "structure", new JsonObject());
            var structure = GsonHelper.getAsString(jsonStructureObject, "structure");
            var range = ConstantInt.of(GsonHelper.getAsInt(jsonStructureObject, "range"));
            structureRangeCondition = new StructureRangeCondition(TagKey.create(Registries.STRUCTURE, new ResourceLocation(structure)), range);
        }
        if (jsonObject.has("continentalness"))
        {
            continentalness = Continentalness.byName(GsonHelper.getAsString(jsonObject, "continentalness"));
        }
        if (jsonObject.has("hasRaids"))
        {
            hasRaids = GsonHelper.getAsBoolean(jsonObject, "hasRaids");
        }
        return new FOTLocationPredicate(biome, structureRangeCondition, continentalness, hasRaids);
    }

    public static class Builder
    {
        @Nullable
        private TagKey<Biome> biome;
        @Nullable
        private StructureRangeCondition structureRangeCondition;
        @Nullable
        private Continentalness continentalness;
        @Nullable
        private Boolean hasRaids;

        public static Builder location()
        {
            return new Builder();
        }

        public Builder setBiome(@Nullable TagKey<Biome> biome)
        {
            this.biome = biome;
            return this;
        }

        public Builder setStructureInRange(TagKey<Structure> structure, int range)
        {
            this.structureRangeCondition = new StructureRangeCondition(structure, ConstantInt.of(range));
            return this;
        }

        public Builder setContinentalness(@Nullable Continentalness continentalness)
        {
            this.continentalness = continentalness;
            return this;
        }

        public Builder hasRaids()
        {
            this.hasRaids = true;
            return this;
        }

        public FOTLocationPredicate build()
        {
            return new FOTLocationPredicate(this.biome, this.structureRangeCondition, this.continentalness, this.hasRaids);
        }
    }
}