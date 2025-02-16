package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class FOTMobEffects
{
    private static int BASE_ID = 2542;
    public static final MobEffect GUARDIAN_STIFLE = new MobEffect(MobEffectCategory.BENEFICIAL, 1201297);

    public static void init()
    {
        register(BASE_ID++, "guardian_stifle", GUARDIAN_STIFLE);
    }

    private static void register(int id, String key, MobEffect mobEffect)
    {
        FOTPlatform.registerMobEffect(id, key, mobEffect);
    }
}