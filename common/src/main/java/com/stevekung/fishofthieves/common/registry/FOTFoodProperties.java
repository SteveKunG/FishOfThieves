package com.stevekung.fishofthieves.common.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FOTFoodProperties
{
    public static final FoodProperties WORMS = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f).fast().effect(new MobEffectInstance(MobEffects.CONFUSION, 400), 0.5F).build();
    public static final FoodProperties SPLASHTAIL = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
    public static final FoodProperties PONDIE = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f).build();
    public static final FoodProperties ISLEHOPPER = new FoodProperties.Builder().nutrition(2).saturationMod(0.2f).build();
    public static final FoodProperties ANCIENTSCALE = new FoodProperties.Builder().nutrition(2).saturationMod(0.2f).build();
    public static final FoodProperties PLENTIFIN = new FoodProperties.Builder().nutrition(2).saturationMod(0.15f).build();
    public static final FoodProperties WILDSPLASH = new FoodProperties.Builder().nutrition(2).saturationMod(0.15f).build();
    public static final FoodProperties DEVILFISH = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).effect(new MobEffectInstance(MobEffects.WEAKNESS, 200), 0.05F).build();
    public static final FoodProperties BATTLEGILL = new FoodProperties.Builder().nutrition(2).saturationMod(0.2f).build();
    public static final FoodProperties WRECKER = new FoodProperties.Builder().nutrition(2).saturationMod(0.25f).build();
    public static final FoodProperties STORMFISH = new FoodProperties.Builder().nutrition(2).saturationMod(0.25f).build();

    public static final FoodProperties COOKED_SPLASHTAIL = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build();
    public static final FoodProperties COOKED_PONDIE = new FoodProperties.Builder().nutrition(5).saturationMod(0.5f).build();
    public static final FoodProperties COOKED_ISLEHOPPER = new FoodProperties.Builder().nutrition(5).saturationMod(0.55f).build();
    public static final FoodProperties COOKED_ANCIENTSCALE = new FoodProperties.Builder().nutrition(5).saturationMod(0.5f).build();
    public static final FoodProperties COOKED_PLENTIFIN = new FoodProperties.Builder().nutrition(5).saturationMod(0.55f).effect(new MobEffectInstance(MobEffects.LUCK, 200), 0.1F).build();
    public static final FoodProperties COOKED_WILDSPLASH = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build();
    public static final FoodProperties COOKED_DEVILFISH = new FoodProperties.Builder().nutrition(5).saturationMod(0.6f).build();
    public static final FoodProperties COOKED_BATTLEGILL = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200), 0.1F).build();
    public static final FoodProperties COOKED_WRECKER = new FoodProperties.Builder().nutrition(6).saturationMod(0.65f).build();
    public static final FoodProperties COOKED_STORMFISH = new FoodProperties.Builder().nutrition(6).saturationMod(0.65f).build();
}