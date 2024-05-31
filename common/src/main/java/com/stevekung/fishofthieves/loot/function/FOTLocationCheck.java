package com.stevekung.fishofthieves.loot.function;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.registry.FOTLootItemConditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record FOTLocationCheck(Optional<FOTLocationPredicate> predicate, BlockPos offset) implements LootItemCondition
{
    private static final MapCodec<BlockPos> OFFSET_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.INT.optionalFieldOf("offsetX", 0).forGetter(Vec3i::getX), Codec.INT.optionalFieldOf("offsetY", 0).forGetter(Vec3i::getY), Codec.INT.optionalFieldOf("offsetZ", 0).forGetter(Vec3i::getZ)).apply(instance, BlockPos::new));
    public static final MapCodec<FOTLocationCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(FOTLocationPredicate.CODEC.optionalFieldOf("predicate").forGetter(FOTLocationCheck::predicate), OFFSET_CODEC.forGetter(FOTLocationCheck::offset)).apply(instance, FOTLocationCheck::new));

    @Override
    public LootItemConditionType getType()
    {
        return FOTLootItemConditions.FOT_LOCATION_CHECK;
    }

    @Override
    public boolean test(LootContext context)
    {
        var vec3 = context.getParamOrNull(LootContextParams.ORIGIN);
        return vec3 != null && (this.predicate.isEmpty() || this.predicate.get().matches(context.getLevel(), vec3.x() + (double) this.offset.getX(), vec3.y() + (double) this.offset.getY(), vec3.z() + (double) this.offset.getZ()));
    }

    public static LootItemCondition.Builder checkLocation(FOTLocationPredicate.Builder locationPredicateBuilder)
    {
        return () -> new FOTLocationCheck(Optional.of(locationPredicateBuilder.build()), BlockPos.ZERO);
    }
}