package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class FOTDamageTypes
{
    public static final ResourceKey<DamageType> MANGO = ResourceKey.create(Registries.DAMAGE_TYPE, FishOfThieves.id("mango"));
    public static final ResourceKey<DamageType> COCONUT = ResourceKey.create(Registries.DAMAGE_TYPE, FishOfThieves.id("coconut"));

    public static void bootstrap(BootstapContext<DamageType> context)
    {
        context.register(MANGO, new DamageType("falling_mango", 0.1F));
        context.register(COCONUT, new DamageType("falling_coconut", 0.1F));
    }
}