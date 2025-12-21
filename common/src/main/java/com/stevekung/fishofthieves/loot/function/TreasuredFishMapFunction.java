package com.stevekung.fishofthieves.loot.function;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.registry.FOTLootItemFunctions;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import com.stevekung.fishofthieves.registry.FOTPoiTypes;
import com.stevekung.fishofthieves.shoal.ShoalSpawner;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class TreasuredFishMapFunction extends LootItemConditionalFunction
{
    private final byte zoom;
    private final int minimumSearchRadius;
    private final int maximumSearchRadius;
    private final float highTierChance;

    TreasuredFishMapFunction(LootItemCondition[] conditions, byte zoom, int minimumSearchRadius, int maximumSearchRadius, float highTierChance)
    {
        super(conditions);
        this.zoom = zoom;
        this.minimumSearchRadius = minimumSearchRadius;
        this.maximumSearchRadius = maximumSearchRadius;
        this.highTierChance = highTierChance;
    }

    @Override
    public LootItemFunctionType getType()
    {
        return FOTLootItemFunctions.TREASURED_FISH_MAP;
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams()
    {
        return ImmutableSet.of(LootContextParams.ORIGIN);
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context)
    {
        if (stack.is(Items.MAP))
        {
            var vec3 = context.getParamOrNull(LootContextParams.ORIGIN);

            if (vec3 == null)
            {
                return stack;
            }

            var serverLevel = context.getLevel();
            var farthest = ShoalSpawner.findFarthest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), BlockPos.containing(vec3), 50, 100, serverLevel.getPoiManager());

            if (farthest.isPresent())
            {
                var blockPos = farthest.get();
                FishOfThieves.LOGGER.debug("Found farthest shoal at: {}", blockPos);
                return createTreasuredFishMap(serverLevel, blockPos, context, this.zoom, this.highTierChance);
            }
            else
            {
                var attemptPos = ShoalSpawner.attemptSpawnShoal(serverLevel, BlockPos.containing(vec3), 10);

                if (attemptPos != null)
                {
                    FishOfThieves.LOGGER.debug("Shoal spawn from map at: {}", attemptPos);
                    return createTreasuredFishMap(serverLevel, attemptPos, context, this.zoom, this.highTierChance);
                }
                else
                {
                    var nearest = serverLevel.getPoiManager().findClosest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), BlockPos.containing(vec3), 100, PoiManager.Occupancy.ANY);

                    if (nearest.isPresent())
                    {
                        var blockPos = nearest.get();
                        FishOfThieves.LOGGER.debug("Found nearest shoal at: {}", blockPos);
                        return createTreasuredFishMap(serverLevel, blockPos, context, this.zoom, this.highTierChance);
                    }
                }
            }
        }
        return stack;
    }

    private static ItemStack createTreasuredFishMap(ServerLevel serverLevel, BlockPos blockPos, LootContext context, byte zoom, float highTierChance)
    {
        var itemStack = MapItem.create(serverLevel, blockPos.getX(), blockPos.getZ(), zoom, true, true);
        MapItem.renderBiomePreviewMap(serverLevel, itemStack);
        MapItemSavedData.addTargetDecoration(itemStack, blockPos, "+", FOTMapDecorationTypes.TREASURED_FISH);
        Shoal.setTreasuredShoal(serverLevel, blockPos, context.getRandom().nextFloat() < highTierChance ? 1 : 2);
        return itemStack;
    }

    public static TreasuredFishMapFunction.Builder makeTreasuredFishMap()
    {
        return new TreasuredFishMapFunction.Builder();
    }

    public static class Builder extends LootItemConditionalFunction.Builder<TreasuredFishMapFunction.Builder>
    {
        private byte zoom = 2;
        private int minimumSearchRadius;
        private int maximumSearchRadius;
        private float highTierChance;

        @Override
        protected TreasuredFishMapFunction.Builder getThis()
        {
            return this;
        }

        public TreasuredFishMapFunction.Builder setZoom(byte zoom)
        {
            this.zoom = zoom;
            return this;
        }

        public TreasuredFishMapFunction.Builder setMinimumSearchRadius(int minimumSearchRadius)
        {
            this.minimumSearchRadius = minimumSearchRadius;
            return this;
        }

        public TreasuredFishMapFunction.Builder setMaximumSearchRadius(int maximumSearchRadius)
        {
            this.maximumSearchRadius = maximumSearchRadius;
            return this;
        }

        public TreasuredFishMapFunction.Builder setHighTierChance(float highTierChance)
        {
            this.highTierChance = highTierChance;
            return this;
        }

        @Override
        public LootItemFunction build()
        {
            return new TreasuredFishMapFunction(this.getConditions(), this.zoom, this.minimumSearchRadius, this.maximumSearchRadius, this.highTierChance);
        }
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<TreasuredFishMapFunction>
    {
        @Override
        public void serialize(JsonObject json, TreasuredFishMapFunction treasuredFishMapFunction, JsonSerializationContext serializationContext)
        {
            super.serialize(json, treasuredFishMapFunction, serializationContext);

            if (treasuredFishMapFunction.zoom != 2)
            {
                json.addProperty("zoom", treasuredFishMapFunction.zoom);
            }
            json.addProperty("mininum_search_radius", treasuredFishMapFunction.minimumSearchRadius);
            json.addProperty("maximum_search_radius", treasuredFishMapFunction.maximumSearchRadius);
            json.addProperty("high_tier_chance", treasuredFishMapFunction.highTierChance);
        }

        @Override
        public TreasuredFishMapFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions)
        {
            var zoom = GsonHelper.getAsByte(object, "zoom", (byte) 2);
            var minimumSearchRadius = GsonHelper.getAsInt(object, "mininum_search_radius");
            var maximumSearchRadius = GsonHelper.getAsInt(object, "maximum_search_radius");
            var highTierChance = GsonHelper.getAsFloat(object, "high_tier_chance");
            return new TreasuredFishMapFunction(conditions, zoom, minimumSearchRadius, maximumSearchRadius, highTierChance);
        }
    }
}