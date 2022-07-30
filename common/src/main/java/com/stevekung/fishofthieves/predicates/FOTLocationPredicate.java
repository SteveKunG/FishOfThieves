package com.stevekung.fishofthieves.predicates;

import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;

public record FOTLocationPredicate(TagKey<Biome> biome, Continentalness continentalness, Boolean hasRaids)
{
    public static final FOTLocationPredicate ANY = new FOTLocationPredicate(null, null, null);

    public boolean matches(ServerLevel level, double x, double y, double z)
    {
        var blockPos = new BlockPos(x, y, z);
        var loaded = level.isLoaded(blockPos);
        var isRaided = level.isRaided(blockPos);
        return (this.biome == null || loaded && level.getBiome(blockPos).is(this.biome)) && (this.continentalness == null || loaded && this.continentalness == TerrainUtils.getContinentalness(level, blockPos)) && (this.hasRaids == null || loaded && this.hasRaids == isRaided);
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
        Continentalness continentalness = null;
        Boolean hasRaids = null;

        if (jsonObject.has("biome"))
        {
            var string = GsonHelper.getAsString(jsonObject, "biome");
            biome = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(string));
        }
        if (jsonObject.has("continentalness"))
        {
            continentalness = Continentalness.byName(GsonHelper.getAsString(jsonObject, "continentalness"));
        }
        if (jsonObject.has("hasRaids"))
        {
            hasRaids = GsonHelper.getAsBoolean(jsonObject, "hasRaids");
        }
        return new FOTLocationPredicate(biome, continentalness, hasRaids);
    }

    public static class Builder
    {
        @Nullable
        private TagKey<Biome> biome;
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
            return new FOTLocationPredicate(this.biome, this.continentalness, this.hasRaids);
        }
    }
}