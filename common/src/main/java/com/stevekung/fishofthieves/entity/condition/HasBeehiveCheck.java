package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;

public record HasBeehiveCheck(int honeyLevel, int distance) implements SpawnCondition
{
    public static final MapCodec<HasBeehiveCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.intRange(0, BeehiveBlock.MAX_HONEY_LEVELS).optionalFieldOf("honey_level", BeehiveBlock.MAX_HONEY_LEVELS).forGetter(HasBeehiveCheck::honeyLevel),
            Codec.intRange(1, 32).fieldOf("distance").forGetter(HasBeehiveCheck::distance)
    ).apply(instance, HasBeehiveCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        var poiManager = context.level().getLevel().getPoiManager();
        var optional = poiManager.findClosest(type -> type.is(PoiTypes.BEEHIVE) || type.is(PoiTypes.BEE_NEST), context.pos(), this.distance, PoiManager.Occupancy.ANY);

        if (optional.isPresent())
        {
            var blockState = context.level().getBlockState(optional.get());
            return blockState.hasProperty(BeehiveBlock.HONEY_LEVEL) && BeehiveBlockEntity.getHoneyLevel(blockState) == this.honeyLevel;
        }
        return false;
    }

    public static SpawnCondition beehive(int honeyLevel, int distance)
    {
        return new HasBeehiveCheck(honeyLevel, distance);
    }
}