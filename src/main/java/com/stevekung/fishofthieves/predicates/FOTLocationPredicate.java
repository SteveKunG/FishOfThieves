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
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FOTLocationPredicate
{
    public static final FOTLocationPredicate ANY = new FOTLocationPredicate(null, null, null);
    private final Biome.BiomeCategory biomeCategory;
    private final Continentalness continentalness;
    private final Boolean raidActive;

    public FOTLocationPredicate(Biome.BiomeCategory biomeCategory, Continentalness continentalness, @Nullable Boolean raidActive)
    {
        this.biomeCategory = biomeCategory;
        this.continentalness = continentalness;
        this.raidActive = raidActive;
    }

    public static LootItemCondition.Builder checkLocation(FOTLocationPredicate.Builder locationPredicateBuilder)
    {
        return () -> new FOTLocationCheck(locationPredicateBuilder.build(), BlockPos.ZERO);
    }

    public boolean matches(ServerLevel level, double x, double y, double z)
    {
        var blockPos = new BlockPos(x, y, z);
        var loaded = level.isLoaded(blockPos);
        var biome = level.getBiome(blockPos);
        var isRaided = level.isRaided(blockPos);

        if (!(this.biomeCategory == null || loaded && this.biomeCategory == biome.getBiomeCategory()) || !(this.continentalness == null || loaded && this.continentalness == TerrainUtils.getContinentalness(level, blockPos)) || !(this.raidActive == null || loaded && this.raidActive == isRaided))
        {
            return false;
        }
        return true;
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
        if (this.raidActive != null)
        {
            jsonObject.addProperty("raidActive", this.raidActive);
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
        var biomeCategory = Biome.BiomeCategory.byName(GsonHelper.getAsString(jsonObject, "biomeCategory"));
        var continentalness = Continentalness.byName(GsonHelper.getAsString(jsonObject, "continentalness"));
        var raidActive = GsonHelper.getAsBoolean(jsonObject, "raidActive");
        return new FOTLocationPredicate(biomeCategory, continentalness, raidActive);
    }

    public static class Builder
    {
        @Nullable
        private Biome.BiomeCategory biomeCategory;
        @Nullable
        private Continentalness continentalness;
        @Nullable
        private Boolean raidActive;

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

        public Builder isRaidActive()
        {
            this.raidActive = true;
            return this;
        }

        public FOTLocationPredicate build()
        {
            return new FOTLocationPredicate(this.biomeCategory, this.continentalness, this.raidActive);
        }
    }
}