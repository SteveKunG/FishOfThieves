package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record ContinentalnessCondition(Continentalness continentalness) implements SpawnCondition
{
    public static final MapCodec<ContinentalnessCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Continentalness.CODEC.fieldOf("continentalness").forGetter(ContinentalnessCondition::continentalness)).apply(instance, ContinentalnessCondition::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        var continentalness = TerrainUtils.getContinentalness(context.level().getLevel(), context.pos());
        return continentalness == this.continentalness;
    }

    public static SpawnCondition continentalness(Continentalness continentalness)
    {
        return new ContinentalnessCondition(continentalness);
    }
}