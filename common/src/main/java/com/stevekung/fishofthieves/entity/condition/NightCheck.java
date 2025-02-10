package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public class NightCheck implements SpawnCondition
{
    private static final NightCheck INSTANCE = new NightCheck();
    public static final MapCodec<NightCheck> CODEC = MapCodec.unit(INSTANCE);

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