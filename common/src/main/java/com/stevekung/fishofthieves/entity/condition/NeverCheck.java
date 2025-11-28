package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public class NeverCheck implements SpawnCondition
{
    private static final NeverCheck INSTANCE = new NeverCheck();
    public static final MapCodec<NeverCheck> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return false;
    }

    public static SpawnCondition never()
    {
        return INSTANCE;
    }
}