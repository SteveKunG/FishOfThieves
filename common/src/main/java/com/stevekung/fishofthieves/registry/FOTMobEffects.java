package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class FOTMobEffects
{
    public static final Holder<MobEffect> GUARDIAN_STIFLE = register("guardian_stifle", new MobEffect(MobEffectCategory.BENEFICIAL, 1201297));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Mob Effect");
    }

    private static Holder<MobEffect> register(String key, MobEffect mobEffect)
    {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, FishOfThieves.id(key), mobEffect);
    }
}