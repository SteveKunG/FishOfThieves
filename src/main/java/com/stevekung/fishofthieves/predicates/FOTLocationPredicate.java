package com.stevekung.fishofthieves.predicates;

import java.util.Locale;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FOTLocationPredicate
{
    public static final FOTLocationPredicate ANY = new FOTLocationPredicate(null, null);
    private final Biome.BiomeCategory biomeCategory;
    private final String continentalness;

    public FOTLocationPredicate(Biome.BiomeCategory biomeCategory, String continentalness)
    {
        this.biomeCategory = biomeCategory;
        this.continentalness = continentalness;
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
        var chunkX = QuartPos.fromBlock(blockPos.getX());
        var chunkY = QuartPos.fromBlock(blockPos.getY());
        var chunkZ = QuartPos.fromBlock(blockPos.getZ());
        var targetPoint = level.getChunkSource().getGenerator().climateSampler().sample(chunkX, chunkY, chunkZ);
        var continentalness = Climate.unquantizeCoord(targetPoint.continentalness());
        var overworldBiomeBuilder = new OverworldBiomeBuilder();

        if (!(this.biomeCategory == null || loaded && this.biomeCategory == biome.getBiomeCategory()) || !(this.continentalness == null || loaded && this.continentalness.equals(overworldBiomeBuilder.getDebugStringForContinentalness(continentalness).toLowerCase(Locale.ROOT))))
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
            jsonObject.addProperty("continentalness", this.continentalness);
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
        var continentalness = GsonHelper.getAsString(jsonObject, "continentalness");
        return new FOTLocationPredicate(biomeCategory, continentalness);
    }

    public static class Builder
    {
        @Nullable
        private Biome.BiomeCategory biomeCategory;
        @Nullable
        private String continentalness;

        public static Builder location()
        {
            return new Builder();
        }

        public Builder setBiomeCategory(@Nullable Biome.BiomeCategory biomeCategory)
        {
            this.biomeCategory = biomeCategory;
            return this;
        }

        public Builder setContinentalness(@Nullable String continentalness)
        {
            this.continentalness = continentalness;
            return this;
        }

        public FOTLocationPredicate build()
        {
            return new FOTLocationPredicate(this.biomeCategory, this.continentalness);
        }
    }
}