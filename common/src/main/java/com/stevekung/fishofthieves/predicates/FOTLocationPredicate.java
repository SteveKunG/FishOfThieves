package com.stevekung.fishofthieves.predicates;

import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;

public record FOTLocationPredicate(Biome.BiomeCategory biomeCategory, Continentalness continentalness, Boolean hasRaids)
{
    public static final FOTLocationPredicate ANY = new FOTLocationPredicate(null, null, null);

    public boolean matches(ServerLevel level, double x, double y, double z)
    {
        var blockPos = new BlockPos(x, y, z);
        var loaded = level.isLoaded(blockPos);
        var biome = level.getBiome(blockPos).value();
        var isRaided = level.isRaided(blockPos);
        return (this.biomeCategory == null || loaded && this.biomeCategory == biome.getBiomeCategory()) && (this.continentalness == null || loaded && this.continentalness == TerrainUtils.getContinentalness(level, blockPos)) && (this.hasRaids == null || loaded && this.hasRaids == isRaided);
    }

    public JsonElement serializeToJson()
    {
        if (this == ANY)
        {
            return JsonNull.INSTANCE;
        }

        var jsonObject = new JsonObject();

        if (this.biomeCategory != null)
        {
            jsonObject.addProperty("biomeCategory", this.biomeCategory.getSerializedName());
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
        Biome.BiomeCategory biomeCategory = null;
        Continentalness continentalness = null;
        Boolean hasRaids = null;

        if (jsonObject.has("biomeCategory"))
        {
            biomeCategory = Biome.BiomeCategory.byName(GsonHelper.getAsString(jsonObject, "biomeCategory"));
        }
        if (jsonObject.has("continentalness"))
        {
            continentalness = Continentalness.byName(GsonHelper.getAsString(jsonObject, "continentalness"));
        }
        if (jsonObject.has("hasRaids"))
        {
            hasRaids = GsonHelper.getAsBoolean(jsonObject, "hasRaids");
        }
        return new FOTLocationPredicate(biomeCategory, continentalness, hasRaids);
    }

    public static class Builder
    {
        @Nullable
        private Biome.BiomeCategory biomeCategory;
        @Nullable
        private Continentalness continentalness;
        @Nullable
        private Boolean hasRaids;

        public static Builder location()
        {
            return new Builder();
        }

        public Builder setBiomeCategory(@Nullable Biome.BiomeCategory biomeCategory)
        {
            this.biomeCategory = biomeCategory;
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
            return new FOTLocationPredicate(this.biomeCategory, this.continentalness, this.hasRaids);
        }
    }
}