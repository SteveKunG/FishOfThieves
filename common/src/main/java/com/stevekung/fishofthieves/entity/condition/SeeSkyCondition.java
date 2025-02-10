package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record SeeSkyCondition() implements SpawnCondition
{
    private static final SeeSkyCondition INSTANCE = new SeeSkyCondition();
    public static final MapCodec<SeeSkyCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return context.level().canSeeSkyFromBelowWater(context.pos());
    }

    public static SpawnCondition seeSky()
    {
        return INSTANCE;
    }
}