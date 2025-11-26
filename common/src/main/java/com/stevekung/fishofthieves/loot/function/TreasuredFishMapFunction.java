package com.stevekung.fishofthieves.loot.function;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stevekung.fishofthieves.registry.FOTLootItemFunctions;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import com.stevekung.fishofthieves.registry.FOTPoiTypes;

import net.minecraft.core.BlockPos;
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
    private final int searchRadius;

    TreasuredFishMapFunction(LootItemCondition[] conditions, byte zoom, int searchRadius)
    {
        super(conditions);
        this.zoom = zoom;
        this.searchRadius = searchRadius;
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

            if (vec3 != null)
            {
                var serverLevel = context.getLevel();
                var optional = serverLevel.getPoiManager().findClosest(holder -> holder.is(FOTPoiTypes.SHOAL), BlockPos.containing(vec3), 100, PoiManager.Occupancy.ANY);

                if (optional.isPresent())
                {
                    var blockPos = optional.get();
                    var itemStack = MapItem.create(serverLevel, blockPos.getX(), blockPos.getZ(), this.zoom, true, true);
                    MapItem.renderBiomePreviewMap(serverLevel, itemStack);
                    MapItemSavedData.addTargetDecoration(itemStack, blockPos, "+", FOTMapDecorationTypes.TREASURED_FISH);
                    return itemStack;
                }
            }
        }
        return stack;
    }

    public static TreasuredFishMapFunction.Builder makeTreasuredFishMap()
    {
        return new TreasuredFishMapFunction.Builder();
    }

    public static class Builder extends LootItemConditionalFunction.Builder<TreasuredFishMapFunction.Builder>
    {
        private byte zoom = 2;
        private int searchRadius = 50;

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

        public TreasuredFishMapFunction.Builder setSearchRadius(int searchRadius)
        {
            this.searchRadius = searchRadius;
            return this;
        }

        @Override
        public LootItemFunction build()
        {
            return new TreasuredFishMapFunction(this.getConditions(), this.zoom, this.searchRadius);
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

            if (treasuredFishMapFunction.searchRadius != 50)
            {
                json.addProperty("search_radius", treasuredFishMapFunction.searchRadius);
            }
        }

        @Override
        public TreasuredFishMapFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions)
        {
            var zoom = GsonHelper.getAsByte(object, "zoom", (byte) 2);
            var searchRadius = GsonHelper.getAsInt(object, "search_radius", 50);
            return new TreasuredFishMapFunction(conditions, zoom, searchRadius);
        }
    }
}