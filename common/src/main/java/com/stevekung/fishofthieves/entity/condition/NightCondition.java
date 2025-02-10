package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public class NightCondition implements SpawnCondition
{
    private static final NightCondition INSTANCE = new NightCondition();
    public static final MapCodec<NightCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return context.level().getLevel().isDarkOutside();
    }

    public static SpawnCondition night()
    {
        return INSTANCE;
    }
}