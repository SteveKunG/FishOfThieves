package com.stevekung.fishofthieves.registry.variant.muha.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;

public record HasBeehiveCondition(int honeyLevel, int distance) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<HasBeehiveCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.optionalFieldOf("honey_level", 5).forGetter(HasBeehiveCondition::honeyLevel),
            Codec.INT.fieldOf("distance").forGetter(HasBeehiveCondition::distance)
    ).apply(instance, HasBeehiveCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return SpawnConditions.HAS_BEEHIVE;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        var poiManager = ((ServerLevel) livingEntity.level()).getPoiManager();
        var optional = poiManager.findClosest(type -> type.is(PoiTypes.BEEHIVE) || type.is(PoiTypes.BEE_NEST), livingEntity.blockPosition(), this.distance, PoiManager.Occupancy.ANY);

        if (optional.isPresent())
        {
            var blockState = livingEntity.level().getBlockState(optional.get());
            return BeehiveBlockEntity.getHoneyLevel(blockState) == this.honeyLevel;
        }
        return false;
    }

    public static Builder hasBeehive(int honeyLevel, int distance)
    {
        return () -> new HasBeehiveCondition(honeyLevel, distance);
    }
}