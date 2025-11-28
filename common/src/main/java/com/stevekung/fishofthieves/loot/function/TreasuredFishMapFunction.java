package com.stevekung.fishofthieves.loot.function;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.registry.FOTLootItemFunctions;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import com.stevekung.fishofthieves.registry.FOTPoiTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class TreasuredFishMapFunction extends LootItemConditionalFunction
{
    public static final MapCodec<TreasuredFishMapFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance)
            .and(
                    instance.group(
                            Codec.BYTE.optionalFieldOf("zoom", (byte)2).forGetter(function -> function.zoom),
                            Codec.INT.optionalFieldOf("search_radius", 50).forGetter(function -> function.searchRadius),
                            Codec.INT.optionalFieldOf("tier", 1).forGetter(function -> function.tier)))
            .apply(instance, TreasuredFishMapFunction::new));
    private final byte zoom;
    private final int searchRadius;
    private final int tier;

    TreasuredFishMapFunction(List<LootItemCondition> conditions, byte zoom, int searchRadius, int tier)
    {
        super(conditions);
        this.zoom = zoom;
        this.searchRadius = searchRadius;
        this.tier = tier;
    }

    @Override
    public LootItemFunctionType<TreasuredFishMapFunction> getType()
    {
        return FOTLootItemFunctions.TREASURED_FISH_MAP;
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams()
    {
        return ImmutableSet.of(LootContextParams.ORIGIN);
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context)
    {
        if (stack.is(Items.MAP))
        {
            var vec3 = context.getOptionalParameter(LootContextParams.ORIGIN);

            if (vec3 != null)
            {
                var serverLevel = context.getLevel();
                var optional = serverLevel.getPoiManager().findClosest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), BlockPos.containing(vec3), 100, PoiManager.Occupancy.ANY);

                if (optional.isPresent())
                {
                    var blockPos = optional.get();
                    var itemStack = MapItem.create(serverLevel, blockPos.getX(), blockPos.getZ(), this.zoom, true, true);
                    MapItem.renderBiomePreviewMap(serverLevel, itemStack);
                    MapItemSavedData.addTargetDecoration(itemStack, blockPos, "+", FOTMapDecorationTypes.TREASURED_FISH);
                    Shoal.setTreasuredShoal(serverLevel, blockPos, this.tier);
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
        private int tier = 1;

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

        public TreasuredFishMapFunction.Builder setTier(int tier)
        {
            this.tier = tier;
            return this;
        }

        @Override
        public LootItemFunction build()
        {
            return new TreasuredFishMapFunction(this.getConditions(), this.zoom, this.searchRadius, this.tier);
        }
    }
}