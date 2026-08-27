package com.stevekung.fishofthieves.loot.function;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import com.stevekung.fishofthieves.registry.FOTPoiTypes;
import com.stevekung.fishofthieves.shoal.ShoalSpawner;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

//TODO Implement dynamic emerald cost
public class TreasuredFishMapFunction extends LootItemConditionalFunction
{
    public static final MapCodec<TreasuredFishMapFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance)
            .and(
                    instance.group(
                            Codec.BYTE.optionalFieldOf("zoom", (byte) 2).forGetter(function -> function.zoom),
                            Codec.INT.fieldOf("mininum_search_radius").forGetter(function -> function.minimumSearchRadius),
                            Codec.INT.fieldOf("maximum_search_radius").forGetter(function -> function.maximumSearchRadius),
                            Codec.INT.fieldOf("max_attempt").forGetter(function -> function.maxAttempt),
                            Codec.FLOAT.optionalFieldOf("high_tier_chance", 0f).forGetter(function -> function.highTierChance),
                            Codec.intRange(1, 2).optionalFieldOf("tier").forGetter(function -> function.tier)))
            .apply(instance, TreasuredFishMapFunction::new));
    private final byte zoom;
    private final int minimumSearchRadius;
    private final int maximumSearchRadius;
    private final int maxAttempt;
    private final float highTierChance;
    private final Optional<Integer> tier;

    TreasuredFishMapFunction(List<LootItemCondition> conditions, byte zoom, int minimumSearchRadius, int maximumSearchRadius, int maxAttempt, float highTierChance, Optional<Integer> tier)
    {
        super(conditions);
        this.zoom = zoom;
        this.minimumSearchRadius = minimumSearchRadius;
        this.maximumSearchRadius = maximumSearchRadius;
        this.maxAttempt = maxAttempt;
        this.highTierChance = highTierChance;
        this.tier = tier;
    }

    @Override
    public MapCodec<? extends LootItemConditionalFunction> codec()
    {
        return CODEC;
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

            if (vec3 == null)
            {
                return stack;
            }

            var serverLevel = context.getLevel();
            var farthest = ShoalSpawner.findFarthest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), BlockPos.containing(vec3), this.minimumSearchRadius, this.maximumSearchRadius, serverLevel.getPoiManager());

            if (farthest.isPresent())
            {
                var blockPos = farthest.get();
                FishOfThieves.LOGGER.debug("Found the farthest shoal at: {}", blockPos);
                return createTreasuredFishMap(serverLevel, blockPos, context, this.zoom, this.highTierChance, this.tier);
            }
            else
            {
                var attemptPos = ShoalSpawner.attemptSpawnShoal(serverLevel, BlockPos.containing(vec3), this.maxAttempt);

                if (attemptPos != null)
                {
                    FishOfThieves.LOGGER.debug("Shoal spawn from map at: {}", attemptPos);
                    return createTreasuredFishMap(serverLevel, attemptPos, context, this.zoom, this.highTierChance, this.tier);
                }
                else
                {
                    var nearest = serverLevel.getPoiManager().findClosest(holder -> holder.is(FOTPoiTypes.NATURAL_SHOAL), BlockPos.containing(vec3), this.maximumSearchRadius, PoiManager.Occupancy.ANY);

                    if (nearest.isPresent())
                    {
                        var blockPos = nearest.get();
                        FishOfThieves.LOGGER.debug("Found nearest shoal at: {}", blockPos);
                        return createTreasuredFishMap(serverLevel, blockPos, context, this.zoom, this.highTierChance, this.tier);
                    }
                }
            }
        }
        return stack;
    }

    private static ItemStack createTreasuredFishMap(ServerLevel serverLevel, BlockPos blockPos, LootContext context, byte zoom, float highTierChance, Optional<Integer> tier)
    {
        var itemStack = MapItem.create(serverLevel, blockPos.getX(), blockPos.getZ(), zoom, true, true);
        MapItem.renderBiomePreviewMap(serverLevel, itemStack);
        MapItemSavedData.addTargetDecoration(itemStack, blockPos, "+", FOTMapDecorationTypes.TREASURED_FISH);
        Shoal.setTreasuredShoal(serverLevel, blockPos, tier.orElseGet(() -> context.getRandom().nextFloat() < highTierChance ? 1 : 2));
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
        private int maxAttempt;
        private float highTierChance;
        private Optional<Integer> tier = Optional.empty();

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

        public TreasuredFishMapFunction.Builder setMaxAttempt(int maxAttempt)
        {
            this.maxAttempt = maxAttempt;
            return this;
        }

        public TreasuredFishMapFunction.Builder setHighTierChance(float highTierChance)
        {
            this.highTierChance = highTierChance;
            return this;
        }

        public TreasuredFishMapFunction.Builder setTier(int tier)
        {
            this.tier = Optional.of(tier);
            return this;
        }

        @Override
        public LootItemFunction build()
        {
            return new TreasuredFishMapFunction(this.getConditions(), this.zoom, this.minimumSearchRadius, this.maximumSearchRadius, this.maxAttempt, this.highTierChance, this.tier);
        }
    }
}