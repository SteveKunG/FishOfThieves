package com.stevekung.fishofthieves.loot.function;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.registry.FOTLootItemFunctions;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import com.stevekung.fishofthieves.registry.FOTPoiTypes;
import com.stevekung.fishofthieves.shoal.ShoalSpawner;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
    public static final MapCodec<TreasuredFishMapFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance)
            .and(
                    instance.group(
                            Codec.BYTE.optionalFieldOf("zoom", (byte)2).forGetter(function -> function.zoom),
                            Codec.INT.optionalFieldOf("mininum_search_radius", 50).forGetter(function -> function.minimumSearchRadius),
                            Codec.INT.optionalFieldOf("maximum_search_radius", 50).forGetter(function -> function.maximumSearchRadius),
                            Codec.FLOAT.fieldOf("high_tier_chance").forGetter(function -> function.highTierChance)))
            .apply(instance, TreasuredFishMapFunction::new));
    private final byte zoom;
    private final int minimumSearchRadius;
    private final int maximumSearchRadius;
    private final float highTierChance;

    TreasuredFishMapFunction(List<LootItemCondition> conditions, byte zoom, int minimumSearchRadius, int maximumSearchRadius, float highTierChance)
    {
        super(conditions);
        this.zoom = zoom;
        this.minimumSearchRadius = minimumSearchRadius;
        this.maximumSearchRadius = maximumSearchRadius;
        this.highTierChance = highTierChance;
    }

    @Override
    public LootItemFunctionType<TreasuredFishMapFunction> getType()
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
}