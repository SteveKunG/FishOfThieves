package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public class DayCheck implements SpawnCondition
{
    private static final DayCheck INSTANCE = new DayCheck();
    public static final MapCodec<DayCheck> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return context.level().getLevel().isBrightOutside();
    }

    public static SpawnCondition day()
    {
        return INSTANCE;
    }
}